# Prestige API - Frontend Integration Guide

Source: `GameState.java`  
Package: `nusextended.m426.game`

---

## Overview

Prestige resets the player's progress in exchange for permanent bonuses. The mechanic is driven by four public methods on `GameState`.

---

## Methods

### `void prestige()`

**Triggers a prestige reset.** Call when the player clicks the Prestige button.

| Aspect | Detail |
|---|---|
| Source | `GameState.java:150` |
| Input | None (reads `currency` internally) |
| Output | `void` |

**Prestige points formula (hardcoded):**

```
pointsGained = floor(currency^0.45)
```

**Side effects (all applied in order):**

1. **Prestige points** - increased by `floor(currency^0.45)`
2. **Prestige level** - incremented by 1
3. **Currency** - reset to `0`
4. **Active shape** - reset to `new ShapeData(1, 0)` (level 0, 1 vertex)
5. **Upgrade tree** - fully reset via `getUpgradeTree().reset()`; all upgrades (vertex-growth, shape-focus, square-something) lose their progress
6. **Prestige upgrades** - **NOT reset**; vertex multiplier purchases persist across prestiges

**Frontend call site:** The Prestige button (`#prestigeButton` in `nusian-view.fxml`) should invoke `gameState.prestige()`, then refresh the display.

---

### `double getPrestigePoints()`

**Returns the player's accumulated prestige currency.**

| Aspect | Detail |
|---|---|
| Source | `GameState.java:120` |
| Returns | `double` - total prestige points |
| Side effects | None |

Prestige points are spent on prestige upgrades (e.g. vertex multiplier). Display in the Prestige tab.

---

### `int getPrestigeLevel()`

**Returns how many times the player has prestiged.**

| Aspect | Detail |
|---|---|
| Source | `GameState.java:112` |
| Returns | `int` - prestige count |
| Side effects | None |

**Frontend usage:**  
- If `prestigeLevel < 1`, hide the prestige currency container (line 62 of `NusianController.java`).  
- Passed to `updateCurrencyDisplay()` to toggle `#prestigeCurrencyContainer` visibility.

---

### `double getPrestigeBonus()`

**Returns the global production multiplier from prestige levels.**

| Aspect | Detail |
|---|---|
| Source | `GameState.java:116` |
| Returns | `double` - e.g. `1.0` (level 0), `1.1` (level 1), `1.2` (level 2) |
| Side effects | None |

**Formula:**

```
bonus = 1.0 + (prestigeLevel * 0.10)
```

Applied in `GameEngine.java:37`:

```java
double production = activeShape.getCurrentProductionRate()
                  * gameState.getPrestigeBonus()
                  * deltaSeconds;
```

---

## Additional Public Method

### `boolean purchaseVertexMultiplier()`

**Spends prestige points on the vertex multiplier prestige upgrade.**

| Aspect | Detail |
|---|---|
| Source | `GameState.java:140` |
| Input | None (reads cost from `PrestigeUpgrades.getVertexMultiplierCost()`) |
| Returns | `boolean` - `true` if purchased, `false` if insufficient prestige points |
| Side effects | Deducts prestige points; increments `vertexMultiplierLevel` |

The multiplier is `1.0 + (vertexMultiplierLevel * 0.10)` and is applied to the active shape's vertex count at `GameState.getActiveShape()` line 62:

```java
shape.setVertexMultiplier(prestigeUpgrades.getVertexMultiplier());
```

**Cost scaling:** Defined in `UpgradeCost.getPrestigeUpgradeCost(level)` - check `UpgradeCost.java` for the formula.

---

## Data Flow Summary

```
Player clicks Prestige button
        │
        ▼
gameState.prestige()
        │
        ├── calculates: pointsGained = floor(currency^0.45)
        ├── prestigePoints += pointsGained
        ├── prestigeLevel++
        ├── currency = 0
        ├── activeShapeData = ShapeData(1, 0)     // level 0, 1 vertex
        └── upgradeTree.reset()                    // all node progress cleared
        │
        ▼
Production loop (GameEngine)
        │
        ├── production = shapeRate * prestigeBonus * deltaTime
        └── onCurrencyChanged(currency, shape, prestigeLevel)
                │
                ▼
        updateCurrencyDisplay()   // toggle prestige container, update texts
```

## Resets on Prestige - Summary

| Resource | Reset? | Detail |
|---|---|---|
| Currency | Yes | Set to `0` |
| Active shape | Yes | Level 0, 1 vertex |
| Upgrade tree | Yes | All nodes reset to unpurchased |
| Prestige upgrades | **No** | Vertex multiplier persists |
| Prestige points | **No** | Accumulates across prestiges |
| Prestige level | **No** | Increments each prestige |

## Commit Message

**Title:** docs: document prestige API for frontend integration

**Description:**

Add `docs/prestige-api.md` covering all public prestige methods on
GameState: prestige(), getPrestigePoints(), getPrestigeLevel(),
getPrestigeBonus(), and purchaseVertexMultiplier(). Includes expected
inputs, return values, side effects (currency, shape, and upgrade tree
resets), and the data flow consumed by the JavaFX frontend.
