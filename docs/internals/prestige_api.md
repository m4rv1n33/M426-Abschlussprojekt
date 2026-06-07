# Prestige API - Frontend Integration Guide

Source: `GameState.java` + `PrestigeTree.java` + `PrestigeStateManager.java`
Package: `nusextended.m426.game`

---

## Overview

Prestige resets the player's progress in exchange for permanent bonuses. Prestige upgrades are modeled as `UpgradeNode` instances inside a `PrestigeTree`, mirroring how regular upgrades work.

### Row progression rule

Prestige upgrades are arranged in rows. You must purchase every upgrade in a row at least once before any upgrade in the next row becomes available. This is enforced via the `previousNodes` prerequisite system: each node in row N+1 lists all nodes in row N as its prerequisites.

---

## Public Methods on GameState

### `boolean canPrestige()`

**Reports whether prestiging right now would grant any prestige points.** Use this to enable/disable the Prestige button.

| Aspect | Detail |
|---|---|
| Source | `GameState.java` |
| Input | None (reads `currency` internally) |
| Output | `boolean` - `true` if `floor(currency^0.45) > 0` |

---

### `boolean prestige()`

**Triggers a prestige reset.** Call when the player clicks the Prestige button.

| Aspect | Detail |
|---|---|
| Source | `GameState.java` |
| Input | None (reads `currency` internally) |
| Output | `boolean` - `true` if the reset was applied, `false` if it was a no-op |

**Guard:** if `canPrestige()` is `false` (i.e. `floor(currency^0.45)` would be `0`), `prestige()` does nothing and returns `false`. This prevents wiping all progress for zero reward.

**Prestige points formula (hardcoded):**

```
pointsGained = floor(currency^0.45)
```

**Side effects when the reset is applied (all applied in order):**

1. **Prestige points** - increased by `floor(currency^0.45)`
2. **Prestige level** - incremented by 1
3. **Currency** - reset to `0`
4. **Active shape** - reset to `new ShapeData(1, 0)` (level 0, 1 vertex)
5. **Upgrade tree** (regular) - fully reset via `getUpgradeTree().reset()`; all upgrades lose their progress
6. **Prestige tree** - **NOT reset**; prestige upgrades persist across prestiges

---

### `double getPrestigePoints()`

**Returns the player's accumulated prestige currency.**

| Aspect | Detail |
|---|---|
| Source | `GameState.java:126` |
| Returns | `double` - total prestige points |
| Side effects | None |

Prestige points are spent on prestige upgrades. Display in the Prestige tab.

---

### `int getPrestigeLevel()`

**Returns how many times the player has prestiged.**

| Aspect | Detail |
|---|---|
| Source | `GameState.java:118` |
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
| Source | `GameState.java:122` |
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
| Source | `GameState.java:130` |
| Returns | `PrestigeTree` - container of prestige `UpgradeNode` instances |
| Side effects | Lazily initializes tree if null (old saves) |

---

### `void spendPrestigePoints(double cost)`

**Deducts prestige points (used by PrestigeStateManager).**

| Aspect | Detail |
|---|---|
| Source | `GameState.java:137` |
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
Row 1: vertex-multiplier (infinitely purchaseable, cost: 100 * 1.6^level)
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
        +-- calculates: pointsGained = floor(currency^0.45)
        +-- prestigePoints += pointsGained
        +-- prestigeLevel++
        +-- currency = 0
        +-- activeShapeData = ShapeData(1, 0)     // level 0, 1 vertex
        +-- upgradeTree.reset()                    // regular upgrades cleared
        +-- prestigeTree NOT reset                 // prestige upgrades persist
        |
        v
Production loop (GameEngine)
        |
        +-- production = shapeRate * prestigeBonus * deltaTime
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
| Active shape | Yes | Level 0, 1 vertex |
| Regular upgrade tree | Yes | All nodes reset to unpurchased |
| Prestige tree | **No** | All nodes retain purchase counts |
| Prestige points | **No** | Accumulates across prestiges |
| Prestige level | **No** | Increments each prestige |
