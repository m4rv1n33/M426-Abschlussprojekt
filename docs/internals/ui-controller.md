# Application Entry Point & Controller

## Overview

This covers the two classes that sit above `game/` and `game/rendering/`: `NusianApplication` (JavaFX `Application`, wires everything together) and `NusianController` (the FXML controller, owns all UI state and per-frame update logic). It also covers `TutorialManager` and `NumberFormatter`, two small support classes used by the controller. For the renderers the controller delegates to, see `rendering.md`; for the loop that drives `updateCurrencyDisplay()`, see `production.md`.

## NusianApplication

Entry point (`main()`) and JavaFX lifecycle owner (`start()`). See `architecture.md` for the full initialization sequence; the key points not covered there:

- `main()` handles dev-only CLI flags (`--reset`, `--max`/`--boost`) **before** `launch()` is called, so they run headless and never open a window. See `persistence.md` for what each flag does.
- `start()` wires dependencies manually in this order: load `GameState` -> construct `UpgradeStateManager` and `PrestigeStateManager` -> load FXML -> push the state/managers into the controller -> construct `GameEngine` -> register the controller as the `CurrencyListener` -> start the engine -> show the stage.
- `stage.setOnCloseRequest` saves the game and stops the `GameEngine` before the window closes - this is the only explicit non-timer save trigger besides the 5-second autosave.
- The tutorial is shown **after** `stage.show()`, gated on `gameState.hasSeenTutorial()`. It runs synchronously on the JavaFX thread (`Alert.showAndWait()` inside `TutorialManager`), so the main window is visible but blocked behind modal dialogs on a fresh save.

## NusianController

Holds every `@FXML`-injected node (canvases, labels, containers, the prestige button) and all UI-side mutable state. It does not own `GameState` itself - it's handed one via `setGameState()` by `NusianApplication`.

### Wiring lifecycle

1. `initialize()` (JavaFX-invoked after FXML load, before `setGameState`): constructs `ShapeRenderer` and `UpgradeRenderer` against the canvases. `gameState` is not yet set at this point, which is why both renderers accept a `null`/later-replaced `GameState` and re-check it via `setGameState()`.
2. `setGameState(gameState)`: stores the reference, constructs `PrestigeUpgradeRenderer` and does the first `setPrestigeUpgrades()` build, then pushes the state into both existing renderers.
3. `setUpgradeManager(...)` / `setPrestigeManager(...)`: called separately by `NusianApplication`. `setPrestigeManager` is also where the prestige button's click handler is attached (`gameState.prestige()` followed by a panel rebuild) - so the button does nothing until this setter runs.

This means the three `set*` calls have an implicit order dependency: `setGameState` must run before `setPrestigeManager`, since the button handler and `prestigeRenderer` it manipulates are created in `setGameState`. `NusianApplication.start()` already calls them in the safe order; do not reorder those calls without checking this.

### Per-frame update

`updateCurrencyDisplay(currency, shape, prestigeLevel, prestigePoints)` is the `CurrencyListener` callback invoked once per frame by `GameEngine` (see `production.md`). It:

1. Recomputes `delta` (ms since last call) and accumulates `time` - `delta` is threaded through to `ShapeRenderer.renderShape(delta)` for frame-rate-independent spin.
2. Updates the currency text via `NumberFormatter.formatCurrency`.
3. Toggles `prestigeCurrencyContainer` visibility based on `prestigeLevel < 1`, and updates the prestige currency label.
4. Updates the prestige button's label/disabled state via `gameState.getPendingPrestigePoints()` / `canPrestige()`.
5. Clears and redraws both canvases through `shapeRenderer` and `upgradeRenderer`.

There is a commented-out block that would refresh every prestige upgrade button's cost/enabled state each frame; it's left in intentionally with a note ("don't do this every frame that's ridiculous") - prestige button state is instead refreshed only when `setPrestigeUpgrades()` rebuilds the panel (see `rendering.md`).

## TutorialManager

Loads `tutorial.json` (a resource, `Map<String, String>` of step id -> body text) and shows each step in `STEP_ORDER` as a sequence of modal `Alert` dialogs (`showAndWait()`, so the player must click OK through each one).

- `STEP_ORDER` is a fixed array of five step ids (`tutorial-0-welcome` through `tutorial-4-prestige-upgrades`); any id in the map but not in this array is never shown, and any id in the array missing from the map is silently skipped.
- If the resource fails to load (missing file, bad JSON), the error is logged to stderr and `texts` becomes an empty map - `showTutorial()` then shows nothing rather than crashing.
- Whether the tutorial has already been seen is tracked on `GameState` (`hasSeenTutorial()` / `setHasSeenTutorial()`), not inside `TutorialManager` itself, so it persists across restarts. See `persistence.md` for how `GameState` is saved.

## NumberFormatter

Static formatting helpers used by both the controller and `PrestigeUpgradeButton`/renderers wherever a number is shown to the player:

- `formatCurrency(amount)`: scales to K/M/B/T suffixes at the 1,000 / 1,000,000 / 1,000,000,000 / 1,000,000,000,000 thresholds, one decimal place; below 1,000 it prints a whole number.
- `formatCurrencyWithLabel(amount)`: appends `"Nusian"` or `"Nusians"` (singular only for exactly `1.0`, compared with an epsilon) - not currently wired into the UI, but available for text that needs the unit name.
- `formatProductionRate(rate)`: two decimal places plus `" per second"`.
