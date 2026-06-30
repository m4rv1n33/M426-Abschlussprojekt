# Upgrades

## How upgrades work now

Regular upgrades are stored as `UpgradeNode` objects in `UpgradeTree`.
Each node has:
- a name
- a description
- a cost object (`UpgradeCost`)
- a list of prerequisite nodes
- a flag for infinite purchases

`GameState` owns the active `UpgradeTree`; purchase logic lives in `UpgradeStateManager`.
`GameEngine` calls `UpgradeStateManager.performAutoPurchases()` every tick, so unlocked repeatable upgrades can be bought automatically.

Current examples:
- `vertex-growth` is the regular shape upgrade
- `shape-focus` unlocks auto-buying `vertex-growth`

## How to add a new upgrade

1. Create a new `UpgradeNode` in `UpgradeTree.createDefaultTree()`.
2. Give it a name, description, cost, and `infinitelyPurchaseable` value.
3. Add prerequisite nodes if the upgrade should unlock later.
4. Use a `ShapeType` requirement if the upgrade needs a minimum shape. The requirement is met once the active shape has at least that many vertices, so it stays buyable on larger shapes too.
5. Add a test that checks unlock behavior and purchase behavior.

## How to implement the upgrade effect

Creating the node only defines the upgrade in the tree. The gameplay effect must be added in the code that actually runs the game.

Use this pattern:
- if the upgrade changes currency production, apply it in `Shape.getCurrentProductionRate()` or `GameEngine`
- if the upgrade unlocks automation, check for it in `UpgradeStateManager.performAutoPurchases()`, called each tick
- if the upgrade changes costs, make the cost calculation read the purchased node state
- if the upgrade affects prestige, apply it in `GameState.prestige()` or the prestige bonus methods

For example, to add a production bonus upgrade:
1. Add the node in `UpgradeTree.createDefaultTree()`.
2. Store whether the node was purchased.
3. Read that state where production is calculated.
4. Multiply the production result by the bonus.
5. Add a test that proves the bonus is applied.

## Example

```java
UpgradeNode newNode = new UpgradeNode(
    "new-upgrade",
    "My new upgrade.",
    new UpgradeCost(50.0, 1.25),
    false,
    ShapeType.TRIANGLE,
    existingNode
);
```

If the upgrade should repeat forever, set `infinitelyPurchaseable` to `true`.
If it should only be bought once, set it to `false`.
