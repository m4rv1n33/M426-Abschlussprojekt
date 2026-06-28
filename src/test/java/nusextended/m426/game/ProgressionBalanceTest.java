package nusextended.m426.game;

import nusextended.m426.model.UpgradeNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Automated balance playtest. Drives the real {@link GameState} and managers with a
 * simple but realistic player policy, one simulated second at a time, and measures the
 * shape of the progression curve produced by the values in {@code balance.json}.
 *
 * <p>These tests are the data-driven evidence for issue #31: they fail if the curve
 * regresses into an excessively long flat spot (a single shape level taking far too
 * long) or if the prestige loop stops being reachable and rewarding.
 */
@DisplayName("Progression balance playtest")
class ProgressionBalanceTest {

    /** How far a prestige-free run is pushed when measuring single-run smoothness. */
    private static final int SMOOTHNESS_TARGET_LEVEL = 50;
    /** Hard cap so a broken (runaway-cost) config fails fast instead of looping forever. */
    private static final int SMOOTHNESS_SECOND_CAP = 6 * 60 * 60; // 6 simulated hours
    /** Budget for the full prestige-loop session. */
    private static final int PRESTIGE_SESSION_SECONDS = 60 * 60; // 1 simulated hour

    @BeforeEach
    void setUp() {
        // Reload the shipped balance.json so the measured curve reflects production values.
        BalanceConfig.resetForTesting();
    }

    @Test
    @DisplayName("A prestige-free run climbs steadily without an excessively long flat spot")
    void prestigeFreeRunHasNoExcessivelyLongFlatSpot() {
        GameState state = new GameState();
        UpgradeStateManager upgrades = state.getUpgradeStateManager();

        int[] secondReachedForLevel = new int[SMOOTHNESS_TARGET_LEVEL + 1];
        int currentLevel = 0;
        int second = 0;

        while (currentLevel < SMOOTHNESS_TARGET_LEVEL && second < SMOOTHNESS_SECOND_CAP) {
            second++;
            produceOneSecond(state);
            greedyBuyStructuralAndGrowth(state, upgrades);

            int level = state.getActiveShape().getLevel();
            while (currentLevel < level && currentLevel < SMOOTHNESS_TARGET_LEVEL) {
                currentLevel++;
                secondReachedForLevel[currentLevel] = second;
            }
        }

        int worstGap = 0;
        int worstGapLevel = 0;
        for (int level = 1; level <= SMOOTHNESS_TARGET_LEVEL; level++) {
            int gap = secondReachedForLevel[level] - secondReachedForLevel[level - 1];
            if (gap > worstGap) {
                worstGap = gap;
                worstGapLevel = level;
            }
        }

        System.out.println("=== Prestige-free progression (single run) ===");
        for (int level = 5; level <= SMOOTHNESS_TARGET_LEVEL; level += 5) {
            System.out.printf("  level %2d reached at %6d s (%.1f min)%n",
                    level, secondReachedForLevel[level], secondReachedForLevel[level] / 60.0);
        }
        System.out.printf("  worst single-level wait: %d s at level %d%n", worstGap, worstGapLevel);
        System.out.println("==============================================");

        assertTrue(currentLevel == SMOOTHNESS_TARGET_LEVEL,
                "Run must reach level " + SMOOTHNESS_TARGET_LEVEL + " within the time cap; reached " + currentLevel);
        assertTrue(secondReachedForLevel[SMOOTHNESS_TARGET_LEVEL] <= 30 * 60,
                "Reaching level " + SMOOTHNESS_TARGET_LEVEL + " should take at most 30 min, took "
                        + secondReachedForLevel[SMOOTHNESS_TARGET_LEVEL] + " s");
        assertTrue(worstGap <= 60,
                "No single shape level should take more than 60 s in this range; worst was "
                        + worstGap + " s at level " + worstGapLevel);
    }

    @Test
    @DisplayName("The prestige loop is reachable and rewarding within one simulated hour")
    void prestigeLoopIsReachableAndRewarding() {
        BalanceConfig cfg = BalanceConfig.get();
        GameState state = new GameState();
        UpgradeStateManager upgrades = state.getUpgradeStateManager();
        PrestigeStateManager prestigeUpgrades = new PrestigeStateManager(state);

        int prestiges = 0;
        int multiplierPurchases = 0;
        int secondOfFirstMultiplier = -1;
        double startRate = ratePerSecond(state);

        for (int second = 1; second <= PRESTIGE_SESSION_SECONDS; second++) {
            produceOneSecond(state);

            double prestigeThresholdPoints = Math.max(cfg.prestigeMinimumPoints, nextMultiplierCost(state));
            double pointsOnPrestige = Math.floor(Math.pow(state.getCurrency(), cfg.prestigeFormulaExponent));

            if (pointsOnPrestige >= prestigeThresholdPoints && state.canPrestige()) {
                state.prestige();
                prestiges++;
                while (prestigeUpgrades.attemptPurchase("vertex-multiplier")) {
                    multiplierPurchases++;
                    if (secondOfFirstMultiplier < 0) {
                        secondOfFirstMultiplier = second;
                    }
                }
                continue;
            }

            // Grow the shape only while upgrades are cheap relative to the prestige target,
            // then save the remainder of the run toward the next prestige.
            double prestigeCurrencyTarget = Math.pow(prestigeThresholdPoints, 1.0 / cfg.prestigeFormulaExponent);
            buyGrowthWhileCheap(state, upgrades, prestigeCurrencyTarget * 0.25);
        }

        double endRate = ratePerSecond(state);

        System.out.println("=== Prestige loop session (1 simulated hour) ===");
        System.out.printf("  prestiges performed:        %d%n", prestiges);
        System.out.printf("  vertex-multiplier purchases: %d%n", multiplierPurchases);
        System.out.printf("  first multiplier bought at: %d s%n", secondOfFirstMultiplier);
        System.out.printf("  prestige level reached:     %d%n", state.getPrestigeLevel());
        System.out.printf("  production rate start->end: %.2f -> %.2f /s%n", startRate, endRate);
        System.out.println("================================================");

        assertTrue(prestiges >= 3,
                "Prestige loop should allow at least 3 prestiges in an hour, did " + prestiges);
        assertTrue(multiplierPurchases >= 2,
                "Prestige should be rewarding: expected at least 2 multiplier purchases, got " + multiplierPurchases);
        assertTrue(secondOfFirstMultiplier > 0 && secondOfFirstMultiplier <= 20 * 60,
                "First prestige upgrade should be affordable within 20 min, was at " + secondOfFirstMultiplier + " s");
        assertTrue(state.getPrestigeLevel() > 0, "At least one prestige must have happened");
    }

    // --- player-policy helpers -------------------------------------------------

    private static double ratePerSecond(GameState state) {
        return state.getActiveShape().getCurrentProductionRate() * state.getPrestigeBonus();
    }

    private static void produceOneSecond(GameState state) {
        state.addCurrency(ratePerSecond(state)); // deltaSeconds == 1
    }

    /** Buys every upgrade that is currently affordable (structural one-times and vertex-growth). */
    private static void greedyBuyStructuralAndGrowth(GameState state, UpgradeStateManager upgrades) {
        boolean bought = true;
        while (bought) {
            bought = false;
            if (upgrades.attemptPurchase("shape-focus")) {
                bought = true;
            }
            if (upgrades.attemptPurchase("square-something")) {
                bought = true;
            }
            if (upgrades.attemptPurchase("vertex-growth")) {
                bought = true;
            }
        }
    }

    /** Grows the shape while the next growth purchase stays below the given cost ceiling. */
    private static void buyGrowthWhileCheap(GameState state, UpgradeStateManager upgrades, double costCeiling) {
        upgrades.attemptPurchase("shape-focus");
        upgrades.attemptPurchase("square-something");
        UpgradeNode growth = state.getUpgradeTree().getNode("vertex-growth");
        while (growth != null
                && growth.getCurrentCost() <= costCeiling
                && upgrades.attemptPurchase("vertex-growth")) {
            // keep growing while it is cheap
        }
    }

    private static double nextMultiplierCost(GameState state) {
        UpgradeNode node = state.getPrestigeTree().getNode("vertex-multiplier");
        return node != null ? node.getCurrentCost() : Double.MAX_VALUE;
    }
}
