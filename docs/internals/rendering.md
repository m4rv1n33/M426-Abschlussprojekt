# Rendering

## Overview

All visuals are drawn with the JavaFX `Canvas`/`GraphicsContext` API rather than FXML scene-graph nodes (the prestige panel is the one exception - see below). `NusianController` owns three renderer objects, one per canvas/container, and calls them once per frame from `updateCurrencyDisplay()`. See `production.md` for how that per-frame call is triggered.

```
game/rendering/
├── ShapeRenderer.java           draws the active shape on shapeCanvas
├── UpgradeRenderer.java         draws the regular upgrade tree + hover info on upgradesCanvas
├── PrestigeUpgradeRenderer.java lays out prestige upgrade buttons in prestigeUpgradesContainer (VBox)
├── PrestigeUpgradeButton.java   a single prestige upgrade as a JavaFX Button
├── FontHelper.java              shared Font constants
└── PaintHelper.java             shared Paint (color) constants
```

## ShapeRenderer

Draws the player's active shape as a rotating polygon on `shapeCanvas`.

- Vertices are placed on a circle of radius 225px centered at `(407.5, 250)`.
- A rotation offset (`spinOffset`) accumulates every frame: `spinOffset += TAU * spinSpeed * delta / 1000.0`, with `spinSpeed = 0.1` revolutions/second. `delta` is milliseconds since the last frame, passed in from `NusianController`.
- A 1-vertex shape (the starting point) renders as a single dot at the origin instead of a degenerate polygon.
- Each frame: `clearCanvas()` repaints the grey background, then `renderShape(delta)` draws vertices (white filled circles, 16px) and connecting edges (white lines, 6px) between consecutive vertices, wrapping the last vertex back to the first.
- Reads shape data via `gameState.getActiveShape().getVertices()` - see `shape-system.md` for how `Shape` is constructed.

## UpgradeRenderer

Draws the regular upgrade tree as a node graph on `upgradesCanvas`, and handles all mouse interaction for it (hover info, click-to-purchase, drag-to-pan).

- `upgradeTreeOffset` is a pan offset, defaulting to the canvas center. Nodes are drawn at `UpgradeNode.getLocation().add(upgradeTreeOffset)`.
- **Dragging:** `onCanvasMousePressed` records the drag start position and current offset; `onCanvasMouseDragged` recomputes `upgradeTreeOffset` from the delta. This lets the player pan around a tree larger than the canvas.
- **Hovering:** `onCanvasMouseMoved` checks the (offset-corrected) mouse position against every node's `getLocation()` within `getVisualSize() / 2`. On a hit it calls `setDrawnUpgradeInfo()`, which is read by `renderUpgradeInfo()` to draw a tooltip box (name, cost or "MAX", description) offset 20px from the cursor.
- **Clicking:** `onCanvasMouseClicked` only acts on a primary-button click while hovering a node, and only if `UpgradeNode.canPurchase(...)` passes; it then calls `gameState.getUpgradeStateManager().attemptPurchase(node.getName())`. See `upgrades.md` for the purchase flow.
- **Tree drawing (`renderUpgradeTree`):** first pass draws thin (1px) lines from every node to each of its `getPreviousNodes()` (the dependency edges); second pass draws each node as a filled+stroked circle (`getVisualSize()` diameter) with its `getIcon()` text centered inside. Purchased nodes are filled off-white with black text/outline; unpurchased nodes are filled grey with white text/outline.
- Uses `FontHelper.titleFont` / `bodyFont` and `PaintHelper` colors throughout; no font or color literals live in this class.

## PrestigeUpgradeRenderer

Unlike the two canvas renderers above, the prestige panel is built from real JavaFX controls (`VBox`/`HBox`/`Button`) inside `prestigeUpgradesContainer`, because it needed richer layout (wrapping rows, multi-line labels) than hand-rolled canvas drawing would give cheaply.

`setPrestigeUpgrades()` rebuilds the whole panel on every call:

1. Removes every child except the node with fx:id `prestigeButton` (matched by `getId()`).
2. Buckets `gameState.getPrestigeTree().getNodes()` into rows using `allPrerequisitesPlaced()`: a node goes into the next row once every node in its `getPreviousNodes()` has already been placed in an earlier row. This is a simple topological layering, not a stored row number - see `prestige_api.md` for the row-progression rule it visualizes.
3. Each row becomes an `HBox` of `PrestigeUpgradeButton` instances, appended to the `VBox`.

It is called once from `NusianController.setGameState()` and again after every prestige purchase/reset (from `PrestigeUpgradeButton`'s own action handler and from the prestige button's handler in `NusianController`), since a purchase can unlock a new row. It is intentionally **not** called every frame - the comment in `NusianController.updateCurrencyDisplay()` calls out that per-frame rebuilding of buttons was tried and rejected as wasteful.

## PrestigeUpgradeButton

A `Button` subclass representing one prestige `UpgradeNode`. Its constructor builds the entire visual state (no separate render step):

- A `VBox` graphic containing a description label, a cost label (hidden once a non-repeatable node is maxed), and, for infinitely-purchaseable nodes, a "Lv. N" label.
- Purchased styling (black background, white text) vs. unpurchased (white background, black text), plus a hover style swap on mouse enter/exit for unpurchased buttons.
- `setOnAction`: re-checks `node.canPurchase(...)`, then directly mutates state (`node.recordPurchase()`, `gameState.spendPrestigePoints(cost)`) and calls back into `PrestigeUpgradeRenderer.setPrestigeUpgrades()` to redraw. This bypasses `PrestigeStateManager.attemptPurchase()` - the button performs the purchase mutation itself rather than delegating to the manager class documented in `prestige_api.md`.

## FontHelper / PaintHelper

Small constant-holder classes so canvas code never repeats font or color literals:

```java
FontHelper.titleFont  // Helvetica 25
FontHelper.bodyFont    // Helvetica 17

PaintHelper.WHITE
PaintHelper.BLACK
PaintHelper.GREY      // #999999, canvas background
PaintHelper.OFFWHITE  // #f2f0ef, purchased-node fill
```

## Rendering order per frame

`NusianController.updateCurrencyDisplay()` runs, in order:

1. Update text labels (currency, prestige currency, prestige button text).
2. `shapeRenderer.clearCanvas()` then `renderShape(delta)`.
3. `upgradeRenderer.clearCanvas()`, `renderUpgradeTree()`, then `renderUpgradeInfo()` (so the hover tooltip paints on top of the tree).

The prestige panel is not touched here - it only rebuilds on the discrete events described above.
