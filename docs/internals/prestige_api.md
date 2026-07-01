# Prestige API - Frontend Integration Guide

Source: `GameState.java` + `PrestigeTree.java` + `PrestigeStateManager.java`
Package: `nusextended.m426.game`

---

## Overview

Prestige resets the player's progress in exchange for permanent bonuses. Prestige upgrades are modeled as `UpgradeNode` instances inside a `PrestigeTree`, mirroring how regular upgrades work.

### Row progression rule

Prestige upgrades are arranged in rows. You must purchase every upgrade in a row at least once before any upgrade in the next row becomes available. This is enforced via the `previousNodes` prerequisite system: each node in row N+1 lists all nodes in row N as its prerequisites.

### Prestige payout is based on your peak currency, not your spendable balance

`GameState` tracks a second field, `currencyThisPrestige`, alongside `currency`. It is a high-water mark: `addCurrency()` sets it to `Math.max(currency, currencyThisPrestige)` every time currency is earned, so it only ever goes up (or resets to `0` on prestige, alongside `currency`).

The prestige payout formula reads `currencyThisPrestige`, **not** `currency`. This means spending currency - e.g. via `UpgradeStateManager.performAutoPurchases()` buying `vertex-growth` every tick once `shape-focus` is unlocked - does not reduce how many prestige points a reset will grant. Before this change, the auto-buyer competed with the prestige payout: every automatic purchase lowered `currency` and therefore `getPendingPrestigePoints()`, even though the player had genuinely earned (and just reinvested) that currency. Tracking the peak instead of the live balance removes that penalty.

---

## Public Methods on GameState

### `boolean canPrestige()`

**Reports whether prestiging right now would grant a worthwhile number of prestige points.** Use this to enable/disable the Prestige button.

| Aspect | Detail |
|---|---|
| Source | `GameState.java` |
| Input | None (reads `currencyThisPrestige` internally) |
| Output | `boolean` - `true` if `getPendingPrestigePoints() > 0` |

`getPendingPrestigePoints()` returns `ceil((currencyThisPrestige - minimumCurrencyToPrestige)^prestigeFormulaExponent)` with the base clamped to `0`. `currencyThisPrestige` is the highest `currency` value reached since the last prestige (see "Prestige payout is based on your peak currency" above), so spending currency on upgrades before prestiging does not lower this value. With the default balance (`prestigeFormulaExponent = 0.5`, `minimumCurrencyToPrestige = 1000`) the button stays disabled until the player has reached more than `1000` currency at some point this run, which removes the old "prestige for a single point" trap.

---

### `boolean prestige()`

**Triggers a prestige reset.** Call when the player clicks the Prestige button.

| Aspect | Detail |
|---|---|
| Source | `GameState.java` |
| Input | None (reads `currencyThisPrestige` internally) |
| Output | `boolean` - `true` if the reset was applied, `false` if it was a no-op |

**Guard:** if `canPrestige()` is `false` (i.e. `currencyThisPrestige` is not above `minimumCurrencyToPrestige`), `prestige()` does nothing and returns `false`. This prevents wiping all progress for a negligible reward.

**Prestige points formula (data-driven via `balance.json`):**

```
pointsGained = ceil((currencyThisPrestige - minimumCurrencyToPrestige)^prestigeFormulaExponent)   // base clamped to >= 0; default exponent 0.5 -> square root
```

`currencyThisPrestige` is the high-water mark described above, not the live `currency` balance - see "Prestige payout is based on your peak currency" for why.

**Side effects when the reset is applied (all applied in order):**

1. **Prestige points** - increased by `pointsGained`
2. **Prestige level** - incremented by 1
3. **Currency** - reset to `0`
4. **`currencyThisPrestige`** - reset to `0`
5. **Active shape** - reset to `new ShapeData(1, 0)` (level 0, 1 vertex)
6. **Upgrade tree** (regular) - fully reset via `getUpgradeTree().reset()`; all upgrades lose their progress
7. **Prestige tree** - **NOT reset**; prestige upgrades persist across prestiges

---

### `double getPrestigePoints()`

**Returns the player's accumulated prestige currency.**

| Aspect | Detail |
|---|---|
| Source | `GameState.java:173` |
| Returns | `double` - total prestige points |
| Side effects | None |

Prestige points are spent on prestige upgrades. Display in the Prestige tab.

---

### `int getPrestigeLevel()`

**Returns how many times the player has prestiged.**

| Aspect | Detail |
|---|---|
| Source | `GameState.java:165` |
| Returns | `int` - prestige count |
| Side effects | None |

**Frontend usage:**
- If `prestigeLevel < 1`, hide the prestige currency container.
- Passed to `updateCurrencyDisplay()` to toggle `#prestigeCurrencyContainer` visibility.

---

### `double getPrestigeBonus()`

**Returns the global production multiplier from prestige levels.**

| Aspect | Detail |
|---|---|
| Source | `GameState.java:169` |
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

### `PrestigeTree getPrestigeTree()`

**Returns the prestige upgrade tree.**

| Aspect | Detail |
|---|---|
| Source | `GameState.java:177` |
| Returns | `PrestigeTree` - container of prestige `UpgradeNode` instances |
| Side effects | Lazily initializes tree if null (old saves) |

---

### `void spendPrestigePoints(double cost)`

**Deducts prestige points (used by PrestigeStateManager).**

| Aspect | Detail |
|---|---|
| Source | `GameState.java:184` |
| Input | `cost` - amount to deduct |
| Output | `void` |
| Side effects | Reduces `prestigePoints` by `cost` |

---

## PrestigeTree

**File:** `src/main/java/nusextended/m426/game/PrestigeTree.java`

Holds the list of prestige `UpgradeNode` instances.

| Method | Description |
|---|---|
| `createDefaultTree()` | Factory: returns tree with `vertex-multiplier` node |
| `getNode(String name)` | Look up a node by name |
| `getNodes()` | Returns all nodes (unmodifiable) |
| `getPurchasedNodes()` | Returns nodes where `purchaseCount > 0` |

### Default tree structure (Row 1)

```
Row 1: vertex-multiplier (infinitely purchaseable, cost: 50 * 1.6^level)
```

**Row constraint:** Since this is the only node in row 1, there are no prerequisites. When future rows are added, each node in row 2 must list `vertex-multiplier` (and any other row 1 nodes) as `previousNodes`.

---

## PrestigeStateManager

**File:** `src/main/java/nusextended/m426/game/PrestigeStateManager.java`

Manages purchase validation and execution for prestige upgrades (analogous to `UpgradeStateManager` for regular upgrades).

| Method | Description |
|---|---|
| `canPurchase(String nodeName)` | Checks prerequisites, repeatability, and prestige point balance |
| `attemptPurchase(String nodeName)` | Deducts prestige points, records purchase, notifies listeners |
| `addListener(UpgradeStateListener)` | Register for `onUpgradePurchased` callbacks |

**Purchase rules:**
1. The node must exist in the prestige tree
2. All prerequisite nodes (previous row) must be purchased at least once
3. The node must be repeatable (infinitely purchaseable) or not yet purchased
4. `prestigePoints >= node.getCurrentCost()`

---

## Vertex Multiplier

The `vertex-multiplier` prestige upgrade is an `UpgradeNode` with `infinitelyPurchaseable = true`. Its purchase count drives the vertex multiplier applied in `GameState.getActiveShape()`:

```java
UpgradeNode vertexMultiplier = prestigeTree.getNode("vertex-multiplier");
double multiplier = 1.0 + (vertexMultiplier.getPurchaseCount() * 0.10);
shape.setVertexMultiplier(multiplier);
```

---

## Data Flow Summary

```
Player clicks Prestige button
        |
        v
gameState.prestige()
        |
        +-- canPrestige()? -- false --> no-op, returns false (progress untouched)
        |       |
        |      true
        |       v
        +-- calculates: pointsGained = ceil((currencyThisPrestige - minimumCurrencyToPrestige)^prestigeFormulaExponent)
        +-- prestigePoints += pointsGained
        +-- prestigeLevel++
        +-- currency = 0
        +-- currencyThisPrestige = 0
        +-- activeShapeData = ShapeData(1, 0)     // level 0, 1 vertex
        +-- upgradeTree.reset()                    // regular upgrades cleared
        +-- prestigeTree NOT reset                 // prestige upgrades persist
        |
        v
Production loop (GameEngine)
        |
        +-- production = shapeRate * prestigeBonus * deltaTime
        +-- addCurrency(production)                // also raises currencyThisPrestige if this is a new peak
        +-- onCurrencyChanged(currency, shape, prestigeLevel)
                |
                v
        updateCurrencyDisplay()   // toggle prestige container, update texts

Player purchases prestige upgrade
        |
        v
prestigeManager.attemptPurchase("vertex-multiplier")
        |
        +-- checks canPurchase() (prerequisites, repeatable, points)
        +-- gameState.spendPrestigePoints(cost)
        +-- node.recordPurchase()
        +-- notifies listeners
```

## Resets on Prestige - Summary

| Resource | Reset? | Detail |
|---|---|---|
| Currency | Yes | Set to `0` |
| `currencyThisPrestige` (peak currency, drives payout) | Yes | Set to `0` |
| Active shape | Yes | Level 0, 1 vertex |
| Regular upgrade tree | Yes | All nodes reset to unpurchased |
| Prestige tree | **No** | All nodes retain purchase counts |
| Prestige points | **No** | Accumulates across prestiges |
| Prestige level | **No** | Increments each prestige |
