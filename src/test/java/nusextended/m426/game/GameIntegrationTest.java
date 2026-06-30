package nusextended.m426.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Full game loop integration tests")
class GameIntegrationTest {

    private GameState state;
    private UpgradeStateManager upgradeManager;

    @BeforeEach
    void setUp() {
        BalanceConfig.resetForTesting();
        state = new GameState();
        upgradeManager = state.getUpgradeStateManager();
    }

    @Test
    @DisplayName("Currency accumulates proportionally to production rate over multiple engine ticks")
    void currencyAccumulatesOverMultipleTicks() {
        GameEngine engine = new GameEngine(state, upgradeManager);
        double expectedPerSecond = state.getActiveShape().getCurrentProductionRate() * state.getPrestigeBonus();

        long t = 1_000_000_000L;
        engine.handle(t);                    // primes lastUpdateTime, no production
        engine.handle(t += 1_000_000_000L); // +1 s
        engine.handle(t += 1_000_000_000L); // +1 s
        engine.handle(t += 1_000_000_000L); // +1 s

        assertEquals(expectedPerSecond * 3, state.getCurrency(), 1e-9,
                "3 productive ticks must yield exactly 3 seconds of base production");
    }

    @Test
    @DisplayName("Purchasing vertex-growth upgrade increases production rate")
    void upgradeIncreasesProductionRate() {
        double rateBefore = state.getActiveShape().getCurrentProductionRate();

        state.setCurrency(15.0);
        assertTrue(upgradeManager.attemptPurchase("vertex-growth"));

        double rateAfter = state.getActiveShape().getCurrentProductionRate();
        assertTrue(rateAfter > rateBefore, "Production rate must increase after purchasing vertex-growth");
    }

    @Test
    @DisplayName("Unlocking shape-focus triggers auto-buy of vertex-growth on each engine tick")
    void shapeFocusUnlocksAutoBuy() {
        // shape-focus requires TRIANGLE (3 vertices) and vertex-growth as prerequisite;
        // initial shape has 1 vertex, so two vertex-growth purchases are needed first
        state.setCurrency(1_000.0);
        assertTrue(upgradeManager.attemptPurchase("vertex-growth")); // 1 vertex → 2
        assertTrue(upgradeManager.attemptPurchase("vertex-growth")); // 2 vertices → 3 (TRIANGLE)
        assertTrue(upgradeManager.attemptPurchase("shape-focus"));

        state.setCurrency(500.0);
        int autoPurchased = upgradeManager.performAutoPurchases();

        assertTrue(autoPurchased > 0,
                "Auto-buy must purchase vertex-growth upgrades when shape-focus is unlocked");
    }

    @Test
    @DisplayName("Prestige resets currency and upgrade progress then applies production bonus via engine")
    void prestigeResetsStateAndAppliesBonus() {
        state.addCurrency(5000.0);
        upgradeManager.attemptPurchase("vertex-growth");

        assertTrue(state.canPrestige());
        int levelBefore = state.getPrestigeLevel();
        assertTrue(state.prestige());

        assertEquals(0.0, state.getCurrency(), "Currency must be zero after prestige");
        assertEquals(levelBefore + 1, state.getPrestigeLevel(), "Prestige level must increment by 1");
        assertEquals(0, state.getUpgradeTree().getNode("vertex-growth").getPurchaseCount(),
                "Upgrade tree must be reset after prestige");

        double bonus = state.getPrestigeBonus();
        assertTrue(bonus > 1.0, "Prestige bonus must exceed 1.0 after first prestige");

        // Verify the engine multiplies production by the prestige bonus during a tick
        GameEngine engine = new GameEngine(state, upgradeManager);
        double expectedAfterOneTick = state.getActiveShape().getCurrentProductionRate() * bonus;
        engine.handle(1_000_000_000L);
        engine.handle(2_000_000_000L);

        assertEquals(expectedAfterOneTick, state.getCurrency(), 1e-9,
                "Engine must apply prestige bonus to currency production");
    }

    @Test
    @DisplayName("Save and load preserves currency, prestige fields, and upgrade purchase counts")
    void saveLoadRoundTrip() {
        // Build non-trivial state: upgrade, prestige, then earn post-prestige currency and upgrade again
        state.addCurrency(500.0);
        upgradeManager.attemptPurchase("vertex-growth");
        state.prestige();

        state.addCurrency(50.0);
        upgradeManager.attemptPurchase("vertex-growth");

        double currencyBefore = state.getCurrency();
        int prestigeLevelBefore = state.getPrestigeLevel();
        double prestigePointsBefore = state.getPrestigePoints();
        int purchaseCountBefore = state.getUpgradeTree().getNode("vertex-growth").getPurchaseCount();

        state.save();
        GameState loaded = GameState.load();

        assertEquals(currencyBefore, loaded.getCurrency(), 0.001,
                "Currency must survive a save/load round-trip");
        assertEquals(prestigeLevelBefore, loaded.getPrestigeLevel(),
                "Prestige level must survive a save/load round-trip");
        assertEquals(prestigePointsBefore, loaded.getPrestigePoints(), 0.001,
                "Prestige points must survive a save/load round-trip");
        assertEquals(purchaseCountBefore, loaded.getUpgradeTree().getNode("vertex-growth").getPurchaseCount(),
                "Upgrade purchase count must survive a save/load round-trip");
    }
}
