# Shape System

## Overview

The player controls a single active shape. The shape has a vertex count and an upgrade level, both of which increase together each time a `vertex-growth` upgrade is purchased. The shape determines the currency production rate and drives the canvas rendering.

## ShapeData vs Shape

`GameState` stores the shape as a `ShapeData` inner class:

```java
class ShapeData {
    int vertices;
    int level;
}
```

This is the persisted form serialized to the save file. `GameState.getActiveShape()` converts it into a `Shape` object on each call by applying the vertex multiplier from the prestige tree. `Shape` objects are ephemeral and are not stored in `GameState`.

## Shape fields

| Field | Description |
|---|---|
| `id` | Unused in current gameplay. Reserved for future multi-shape support. |
| `level` | Upgrade level. Incremented alongside vertices by `vertex-growth`. |
| `vertices` | Polygon vertex count. 1 = point, 3 = triangle, 4 = square, etc. |
| `baseProductionRate` | Base currency per second. Currently always `BalanceConfig.shapeBaseProductionRate` (1.0). |
| `vertexMultiplier` | Production multiplier from prestige upgrades. Set by `GameState.getActiveShape()`. |

## Production rate formula

```java
double getCurrentProductionRate() {
    double levelBonus = 1.0 + (level * BalanceConfig.get().levelBonusPerLevel);
    return baseProductionRate * vertices * vertexMultiplier * levelBonus;
}
```

Example at level 3, 4 vertices, no prestige multiplier:
- levelBonus = 1.0 + (3 * 0.2) = 1.6
- rate = 1.0 * 4 * 1.0 * 1.6 = 6.4 per second

## ShapeType enum

`ShapeType` maps vertex counts to polygon names. It is used for:
- Determining which upgrade tree nodes are available (a node's shape requirement is met once the active shape has at least the required vertex count)
- Displaying the shape name in the UI

Defined constants: `TRIANGLE(3)`, `SQUARE(4)`, `PENTAGON(5)`, `HEXAGON(6)`, `HEPTAGON(7)`, `OCTAGON(8)`.

`ShapeType.fromVertices(int)` returns the matching constant or `OCTAGON` as a fallback for higher counts.

`ShapeType.getPolygonName(int)` generates the mathematical polygon name for any vertex count:
- Below 3: "Point"
- 3 to 20: hardcoded names (Triangle through Icosagon)
- 21 to 100: algorithmic Greek/Latin prefix combination
- Above 100: "{count}-gon"

## Upgrade cost

Each shape upgrade costs more than the previous one. The cost at the current level is:

```
cost = shapeUpgradeBaseCost * shapeUpgradeScaling ^ level
```

Default: `10.0 * 1.15^level`

`Shape.getNextUpgradeCost()` delegates to `UpgradeCost.getShapeUpgradeCost(level)`.

## Canvas rendering

`NusianController` renders the shape each frame in `updateCurrencyDisplay()`:

- Vertices are placed on a circle of radius 225 px centred at (407.5, 250)
- A rotation offset accumulates each frame based on `spinSpeed * delta`
- Vertices are drawn as white filled circles (diameter 16 px)
- Edges connecting consecutive vertices are drawn as white lines (width 6 px)
- A shape with 1 vertex renders as a single point at the origin
