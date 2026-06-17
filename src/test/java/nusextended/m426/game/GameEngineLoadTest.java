package nusextended.m426.game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Simulates sustained shape production over a long run of game ticks to
 * establish baseline throughput and memory metrics for {@link GameEngine}.
 */
public class GameEngineLoadTest {

    private static final long TICK_DELTA_NANOS = 16_666_667L; // ~60 FPS frame pacing
    private static final int TICK_COUNT = 200_000; // roughly 55 minutes of simulated game time

    @Test
    void sustainedProductionOverManyTicksStaysFastAndStable() {
        GameState gameState = new GameState();
        UpgradeStateManager upgradeManager = new UpgradeStateManager(gameState);
        GameEngine engine = new GameEngine(gameState, upgradeManager);

        // seed enough currency to keep the auto-purchase loop busy on every tick:
        // grow the shape into a triangle so shape-focus can be unlocked, which in
        // turn enables UpgradeStateManager.performAutoPurchases() to run each tick
        gameState.setCurrency(1_000_000_000.0);
        upgradeManager.attemptPurchase("vertex-growth");
        upgradeManager.attemptPurchase("vertex-growth");
        upgradeManager.attemptPurchase("shape-focus");
        gameState.setCurrency(1_000_000_000.0);

        Runtime runtime = Runtime.getRuntime();
        runGarbageCollection(runtime);
        long memoryBefore = usedMemory(runtime);

        long simulatedNow = 1_000_000_000L;
        engine.handle(simulatedNow); // primes lastUpdateTime, does not produce currency

        long wallClockStart = System.nanoTime();
        for (int tick = 0; tick < TICK_COUNT; tick++) {
            simulatedNow += TICK_DELTA_NANOS;
            engine.handle(simulatedNow);
        }
        long wallClockEnd = System.nanoTime();

        runGarbageCollection(runtime);
        long memoryAfter = usedMemory(runtime);

        double elapsedSeconds = (wallClockEnd - wallClockStart) / 1_000_000_000.0;
        double ticksPerSecond = TICK_COUNT / elapsedSeconds;
        double memoryDeltaMb = (memoryAfter - memoryBefore) / (1024.0 * 1024.0);

        System.out.println("=== GameEngine Load Test: sustained shape production ===");
        System.out.printf("Simulated ticks:       %d%n", TICK_COUNT);
        System.out.printf("Simulated game time:   %.1f minutes%n", (TICK_COUNT * TICK_DELTA_NANOS) / 60_000_000_000.0);
        System.out.printf("Wall-clock duration:   %.3f s%n", elapsedSeconds);
        System.out.printf("Throughput:            %.0f ticks/sec%n", ticksPerSecond);
        System.out.printf("Heap used delta:       %.2f MB%n", memoryDeltaMb);
        System.out.printf("Final currency:        %.2f%n", gameState.getCurrency());
        System.out.printf("Final shape level:     %d%n", gameState.getActiveShape().getLevel());
        System.out.println("===========================================================");

        // The engine must comfortably outpace real-time frame rates (60 ticks/sec)
        // so a single tick never becomes a visible stutter.
        assertTrue(ticksPerSecond > 1_000,
            "Expected sustained throughput above 1000 ticks/sec, measured " + ticksPerSecond);
    }

    private static long usedMemory(Runtime runtime) {
        return runtime.totalMemory() - runtime.freeMemory();
    }

    private static void runGarbageCollection(Runtime runtime) {
        runtime.gc();
        runtime.gc();
    }
}
