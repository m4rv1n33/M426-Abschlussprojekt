# Architecture

## Package layout

```
nusextended.m426
├── NusianApplication.java     entry point, wires all components
├── NusianController.java      JavaFX controller, canvas rendering
└── game/
    ├── GameEngine.java        AnimationTimer, production loop
    ├── GameState.java         central data store + persistence
    ├── BalanceConfig.java     runtime config loader
    ├── NumberFormatter.java   currency/rate display formatting
    ├── TutorialManager.java   first-run tutorial flow
    ├── UpgradeStateManager.java  purchase logic for regular upgrades
    ├── UpgradeStateListener.java interface for purchase callbacks
    ├── PrestigeTree.java      prestige upgrade tree container
    ├── PrestigeStateManager.java purchase logic for prestige upgrades
    └── rendering/             canvas renderers and paint/font helpers
    model/
    ├── Shape.java             active shape with production calculation
    ├── ShapeType.java         polygon type enum with name generation
    ├── UpgradeTree.java       regular upgrade tree container
    ├── UpgradeNode.java       single upgrade node
    └── UpgradeCost.java       exponential cost calculator
```

## Component relationships

```
NusianApplication
  creates --> GameState (loaded from disk)
  creates --> UpgradeStateManager (wraps GameState)
  creates --> GameEngine (drives the loop)
  wires   --> NusianController (receives per-frame callbacks)

GameEngine
  reads   --> GameState (currency, prestige bonus, active shape)
  calls   --> UpgradeStateManager.performAutoPurchases()
  calls   --> CurrencyListener.onCurrencyChanged() (implemented by NusianController)

GameState
  owns    --> UpgradeTree
  owns    --> PrestigeTree
  owns    --> ShapeData (vertices + level)
  reads   --> BalanceConfig (all numeric parameters)

UpgradeStateManager  --> GameState, UpgradeTree, UpgradeNode
PrestigeStateManager --> GameState, PrestigeTree, UpgradeNode
```

## Initialization sequence

1. `NusianApplication.start()` loads the FXML view and resolves `NusianController`.
2. `GameState.load()` reads the save file from disk (or constructs a fresh state).
3. `UpgradeStateManager` is constructed with the loaded `GameState`.
4. `NusianController` receives references to `GameState` and `UpgradeStateManager`.
5. `GameEngine` is constructed and `setCurrencyListener()` is called with the controller.
6. `GameEngine.start()` begins the `AnimationTimer`; `handle()` is called every screen refresh (~60 fps).

## No dependency injection

There is no DI framework. Dependencies are wired manually in `NusianApplication.start()`. All singletons (`BalanceConfig`) are accessed via static `get()` methods.

## Threading

All game logic runs on the JavaFX Application Thread via `AnimationTimer`. There is no background thread for game updates. Saving to disk (`GameState.save()`) is called from the game loop and is synchronous.

## Further reading

Each of the following documents covers one component in depth:

| Document | Covers |
|---|---|
| `production.md` | `GameEngine` loop, production formula, `CurrencyListener` |
| `shape-system.md` | `Shape`, `ShapeType`, shape upgrade cost |
| `upgrades.md` | `UpgradeTree`, `UpgradeNode`, `UpgradeStateManager`, adding new upgrades |
| `prestige_api.md` | `PrestigeTree`, `PrestigeStateManager`, prestige reset flow |
| `rendering.md` | `game/rendering/*` - canvas drawing and the prestige button panel |
| `ui-controller.md` | `NusianApplication`, `NusianController`, `TutorialManager`, `NumberFormatter` |
| `persistence.md` | Save file location, serialization, dev launch flags |
| `balance-config.md` | `BalanceConfig`, tuning history |
