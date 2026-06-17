# Balance Configuration

## Overview

All numeric game balance parameters are stored in `balance.json` at the project root. The file is loaded once at startup by `BalanceConfig`. Edit the file and restart the game; no recompilation needed.

## Loading behaviour

`BalanceConfig.get()` returns a singleton. On the first call it reads `balance.json` from the working directory (the project root when launched via `mvn javafx:run`). If the file is missing or unreadable, the built-in defaults are used and the game continues normally.

Console output on startup:
- `[BalanceConfig] Loaded from <path>` - file found and applied
- `[BalanceConfig] balance.json not found, using built-in defaults.` - file missing

## Parameters

### Shape production

| Key | Default | Effect |
|---|---|---|
| `shapeBaseProductionRate` | 1.0 | Base currency per second at level 0, 1 vertex, no bonuses |
| `levelBonusPerLevel` | 0.2 | Additive multiplier added per upgrade level (+20% per level) |

Production formula: `baseRate * vertices * vertexMultiplier * (1 + level * levelBonusPerLevel)`

### Vertex-growth upgrade costs

| Key | Default | Effect |
|---|---|---|
| `shapeUpgradeBaseCost` | 10.0 | Cost of the first vertex-growth purchase |
| `shapeUpgradeScaling` | 1.2 | Exponential cost multiplier per purchase |

Cost at purchase N: `shapeUpgradeBaseCost * shapeUpgradeScaling ^ N`

### Upgrade tree node costs

| Key | Default | Node |
|---|---|---|
| `shapeFocusBaseCost` | 35.0 | shape-focus (one-time) |
| `shapeFocusScaling` | 1.25 | shape-focus |
| `squareSomethingBaseCost` | 120.0 | square-something (one-time) |
| `squareSomethingScaling` | 1.35 | square-something |

One-time upgrades are only purchased once so the scaling factor has no practical effect unless the node is later made repeatable.

### Prestige upgrade costs

| Key | Default | Effect |
|---|---|---|
| `prestigeUpgradeBaseCost` | 100.0 | Cost of the first vertex-multiplier purchase |
| `prestigeUpgradeScaling` | 1.6 | Exponential cost multiplier per purchase |

### Prestige system

| Key | Default | Effect |
|---|---|---|
| `prestigeFormulaExponent` | 0.45 | Exponent in `floor(currency^exponent)` |
| `prestigeBonusPerLevel` | 0.10 | Additive production bonus per prestige level (+10% per level) |
| `vertexMultiplierPerPurchase` | 0.10 | Additive vertex multiplier per vertex-multiplier purchase |

Prestige points gained on reset: `floor(currency ^ prestigeFormulaExponent)`

## Adding a new parameter

1. Add the field to `BalanceConfig.java` with a sensible default value.
2. Add the corresponding key to `balance.json`.
3. Access it anywhere via `BalanceConfig.get().yourField`.

New fields not present in an existing `balance.json` will receive their Java default value when that file is loaded by Gson.

## Testing

`BalanceConfig.resetForTesting()` clears the singleton so tests can set up controlled values. Call it in `@BeforeEach` when a test depends on specific balance parameters. Do not call it in production code.
