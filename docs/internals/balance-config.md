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
| `shapeUpgradeScaling` | 1.15 | Exponential cost multiplier per purchase |

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
| `prestigeUpgradeBaseCost` | 50.0 | Cost of the first vertex-multiplier purchase |
| `prestigeUpgradeScaling` | 1.6 | Exponential cost multiplier per purchase |

### Prestige system

| Key | Default | Effect |
|---|---|---|
| `prestigeFormulaExponent` | 0.5 | Exponent in `floor(currency^exponent)` (0.5 = `floor(sqrt(currency))`) |
| `prestigeMinimumPoints` | 10.0 | Minimum points a prestige must grant before it is allowed |
| `prestigeBonusPerLevel` | 0.10 | Additive production bonus per prestige level (+10% per level) |
| `vertexMultiplierPerPurchase` | 0.10 | Additive vertex multiplier per vertex-multiplier purchase |

Prestige points gained on reset: `floor(currency ^ prestigeFormulaExponent)`

Prestige is only permitted when `floor(currency ^ prestigeFormulaExponent) >= prestigeMinimumPoints`. With the defaults this requires at least `10^2 = 100` currency, which prevents the degenerate "prestige for a single point" reset that was possible when the threshold was simply `> 0`.

## Tuning history and rationale

The balance was tuned against `ProgressionBalanceTest`, an automated playtest that drives the
real game logic one simulated second at a time and measures the progression curve. The figures
below come from that test (before = the original values, after = the values shipped now).

| Parameter | Before | After | Rationale |
|---|---|---|---|
| `shapeUpgradeScaling` | 1.20 | 1.15 | Cost grew exponentially while production grows only polynomially, so a single shape level eventually took minutes to afford. Lowering the scaling keeps the climb steady instead of hitting a wall. |
| `prestigeFormulaExponent` | 0.45 | 0.5 | Switches the payout to `floor(sqrt(currency))`: intuitive, and it makes the first prestige meaningful instead of yielding a partial upgrade. |
| `prestigeUpgradeBaseCost` | 100 | 50 | At 100 the very first prestige could not afford a single upgrade (a dead first reset). At 50 the first realistic prestige run buys exactly one vertex-multiplier. |
| `prestigeMinimumPoints` | n/a (`> 0`) | 10 | New, data-driven gate. The old implicit `> 0` threshold let the player wipe all progress for a single prestige point. |

### Measured effect (from `ProgressionBalanceTest`)

| Metric | Before | After |
|---|---|---|
| Time to reach shape level 50 (single run) | ~18.9 min | ~3.8 min |
| Worst wait on a single shape level (to level 50) | ~140 s | ~18 s |
| First prestige upgrade affordable at | ~305 s | ~77 s |
| Prestiges completed in a 1-hour session | 4 | 7 |

The "worst wait on a single shape level" is the flat-spot metric: the original 140 s gap is the
excessively long flat spot the issue calls out; after tuning the largest gap in the same range is
about 18 s. `ProgressionBalanceTest` asserts this stays at or below 60 s, so a future regression
back toward harsh scaling fails the build.

## Adding a new parameter

1. Add the field to `BalanceConfig.java` with a sensible default value.
2. Add the corresponding key to `balance.json`.
3. Access it anywhere via `BalanceConfig.get().yourField`.

New fields not present in an existing `balance.json` will receive their Java default value when that file is loaded by Gson.

## Testing

`BalanceConfig.resetForTesting()` clears the singleton so tests can set up controlled values. Call it in `@BeforeEach` when a test depends on specific balance parameters. Do not call it in production code.
