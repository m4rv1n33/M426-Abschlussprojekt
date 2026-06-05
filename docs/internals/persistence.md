# Persistence

## Save file location

The save file path is determined at class-load time by the OS:

| OS | Path |
|---|---|
| Windows | `%LOCALAPPDATA%\nusExtended\M426\game.json` |
| macOS | `~/Library/Application Support/nusExtended/M426/game.json` |
| Linux | `~/.local/share/nusExtended/M426/game.json` |

Detection uses `System.getProperty("os.name").toUpperCase()` checked against `"WIN"` and `"MAC"`.

## Save triggers

- Automatically every 5 seconds during gameplay (accumulator in `GameEngine`)
- On window close via a `setOnCloseRequest` handler in `NusianApplication`

## Serialization

`GameState` is serialized to JSON using Gson with pretty-printing enabled:

```java
private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
```

Gson serializes all non-`transient` fields recursively. `GameState` contains nested objects (`UpgradeTree`, `PrestigeTree`, `ShapeData`) which are all serialized in place.

## transient fields

`upgradeStateManager` is marked `transient` to prevent a circular serialization loop:

```
GameState -> UpgradeStateManager -> GameState (circular)
```

After loading, `GameState.load()` constructs a new `UpgradeStateManager` explicitly:

```java
loaded.upgradeStateManager = new UpgradeStateManager(loaded);
```

## Loading

`GameState.load()` reads the save file if it exists, deserializes it, then runs post-load repairs:

1. If `prestigeTree` is null (save from before prestige was added), a fresh default tree is created.
2. If `prestigeTree` exists, `resolveReferences()` is called to restore transient node links.
3. If `upgradeTree` exists, `resolveReferences()` is called on it too.
4. `upgradeStateManager` is constructed fresh.
5. If the file does not exist, a new `GameState()` is returned.

`resolveReferences()` must be called after Gson deserialization because `previousNodes` on `UpgradeNode` is `transient`. The nodes serialize their prerequisite names as strings; `resolveReferences()` rebuilds the object references from those names using a name-to-node map.

## What is not persisted

- `upgradeStateManager` (transient, recreated on load)
- `previousNodes` on `UpgradeNode` (transient, restored via `resolveReferences()`)
- `BalanceConfig` singleton (not part of game state; reloaded from `balance.json` on each startup)

## Save file format

The file is a pretty-printed JSON object matching the fields of `GameState`. Example structure:

```json
{
  "currency": 1234.5,
  "prestigePoints": 63.0,
  "prestigeLevel": 1,
  "lifetimeCurrencyEarned": 10000.0,
  "activeShapeData": {
    "vertices": 5,
    "level": 4
  },
  "upgradeTree": {
    "nodes": [...]
  },
  "prestigeTree": {
    "nodes": [...]
  }
}
```
