# Production System

## Overview

Currency is generated passively on every frame by the `GameEngine`. The amount earned per frame depends on the active shape's production rate, the prestige bonus, and the elapsed time since the last frame.

## GameEngine loop

`GameEngine` extends `AnimationTimer`. Its `handle(long now)` method is called by JavaFX on every screen refresh (typically ~60 fps).

```
handle(now):
  1. deltaSeconds = (now - lastUpdateTime) / 1_000_000_000.0
  2. production = activeShape.getCurrentProductionRate() * prestigeBonus * deltaSeconds
  3. gameState.addCurrency(production)
  4. upgradeStateManager.performAutoPurchases()
  5. notify currencyListener (updates UI)
  6. if saveAccumulator >= 5s: gameState.save()
```

The first call to `handle()` initializes `lastUpdateTime` and returns without producing currency, so no spike occurs on startup.

## Production formula

```
production per second = baseRate * vertices * vertexMultiplier * (1 + level * levelBonusPerLevel)
                        * prestigeBonus
```

Where:
- `baseRate`: `BalanceConfig.shapeBaseProductionRate` (default 1.0)
- `vertices`: current vertex count of the active shape
- `vertexMultiplier`: `1.0 + (vertexMultiplierPurchaseCount * 0.10)` from the prestige tree
- `level`: shape upgrade level
- `levelBonusPerLevel`: `BalanceConfig.levelBonusPerLevel` (default 0.2)
- `prestigeBonus`: `1.0 + (prestigeLevel * 0.10)` from `GameState.getPrestigeBonus()`

The per-frame earn is `productionPerSecond * deltaSeconds`.

## Shape object lifetime

`GameState.getActiveShape()` constructs a fresh `Shape` object on every call and does not cache it. The method reads the `ShapeData` (vertices + level) stored in `GameState` and applies the current vertex multiplier from the prestige tree. The returned object is used once by `GameEngine` for the rate calculation and then discarded.

## Auto-purchasing

After currency is added each frame, `UpgradeStateManager.performAutoPurchases()` is called. It auto-buys `vertex-growth` in a loop until the player cannot afford another purchase. Auto-purchasing is only active if the `shape-focus` upgrade has been purchased. See `upgrades.md` for upgrade details.

## CurrencyListener interface

```java
interface CurrencyListener {
    void onCurrencyChanged(double currency, Shape activeShape, int prestigeLevel, double prestigePoints);
}
```

`NusianController` implements this interface. It is called once per frame after currency is updated and auto-purchases are applied. The controller uses the passed `Shape` to render the polygon and the currency values to update the text labels.
