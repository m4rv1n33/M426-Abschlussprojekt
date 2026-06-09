# GameEngine Load Test: Sustained Shape Production

**Date:** 07.06.2026

**Test:** `src/test/java/nusextended/m426/game/GameEngineLoadTest.java`

---

## What it does

The test drives `GameEngine.handle(now)` directly with synthetic, monotonically
increasing nanosecond timestamps spaced ~16.6ms apart (60 FPS frame pacing), so
it can simulate long sustained play sessions without waiting in real time.

Setup grows the active shape into a triangle and unlocks `shape-focus`, which
activates `UpgradeStateManager.performAutoPurchases()` on every tick, the same
hot path that runs during normal gameplay once a player automates upgrades.

Each run measures:
- **Throughput** - simulated ticks processed per wall-clock second
- **Heap usage** - `Runtime` used-memory delta before/after the run (after
  forcing GC on both ends)
- **Game-state drift** - final currency and shape level, as a sanity check that
  production and auto-purchasing behave correctly under sustained load

## Baseline metrics

Measured on a development machine (Temurin 21, Windows 11):

| Ticks simulated | Simulated game time | Wall-clock duration | Throughput | Heap delta |
|---:|---:|---:|---:|---:|
| 200,000 | ~56 min | ~0.25 s | ~770,000-820,000 ticks/sec | ~0.1 MB |
| 2,000,000 | ~556 min | ~2.0 s | ~1,000,000 ticks/sec | ~0.1 MB |

The committed test runs the 200,000-tick scenario (fast enough to stay in the
regular suite) and asserts throughput stays above 1,000 ticks/sec - a generous
floor, since the engine only needs to sustain ~60 ticks/sec in production to
keep up with the render loop.

Two things stand out:
- Throughput **improves** at higher tick counts, consistent with JIT warm-up
  rather than any slowdown from accumulated state.
- Heap usage stays effectively flat across both scales, i.e. no observable
  memory growth from sustained ticking.

## Bottlenecks

No real bottleneck was found at the scales tested - the engine comfortably
processes 4-5 orders of magnitude more ticks per second than it needs to in
production (~60 ticks/sec).

One inefficiency worth noting for future optimization passes, though it had no
measurable impact here:

- `GameState.getActiveShape()` allocates a **new `Shape` instance** on every
  call. It is called once per tick for production, and again inside
  `UpgradeStateManager.canPurchase()` for every purchase attempt in the
  auto-purchase loop - so a single tick with several auto-purchases can
  allocate a handful of short-lived `Shape` objects. At the throughput levels
  tested this is invisible (heap delta ~0 MB), but it would be the first thing
  to look at if shape production logic ever needs to scale up significantly
  (e.g. multiple simultaneous shapes, much higher tick rates, or much larger
  auto-purchase batches).

## Conclusion

`GameEngine` has no performance issues at the scale the game currently
exercises. Sustained shape production with active auto-purchasing runs at
roughly 4 orders of magnitude above the required real-time tick rate, with
stable memory usage. No action items required; the `Shape` allocation pattern
noted above is a minor observation for awareness, not a current problem.
