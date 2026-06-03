package nusextended.m426.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PrestigeStateManagerTest {

    private GameState gameState;
    private PrestigeStateManager manager;

    @BeforeEach
    void setUp() {
        gameState = new GameState();
        manager = new PrestigeStateManager(gameState);
    }

    @Test
    @DisplayName("canPurchase returns false when node does not exist")
    void canPurchaseReturnsFalseForUnknownNode() {
        assertFalse(manager.canPurchase("non-existent"));
    }

    @Test
    @DisplayName("canPurchase returns false with insufficient prestige points")
    void canPurchaseReturnsFalseWithInsufficientPoints() {
        gameState.spendPrestigePoints(-5.0);
        assertFalse(manager.canPurchase("vertex-multiplier"));
    }

    @Test
    @DisplayName("canPurchase returns true with sufficient prestige points")
    void canPurchaseReturnsTrueWithSufficientPoints() {
        gameState.spendPrestigePoints(-100.0);
        assertTrue(manager.canPurchase("vertex-multiplier"));
    }

    @Test
    @DisplayName("attemptPurchase deducts prestige points and records purchase")
    void attemptPurchaseDeductsPointsAndRecordsPurchase() {
        gameState.spendPrestigePoints(-100.0);

        boolean success = manager.attemptPurchase("vertex-multiplier");

        assertTrue(success);
        assertEquals(1, gameState.getPrestigeTree().getNode("vertex-multiplier").getPurchaseCount());
        assertEquals(0.0, gameState.getPrestigePoints(), 0.0001);
    }

    @Test
    @DisplayName("attemptPurchase fails with insufficient points")
    void attemptPurchaseFailsWithInsufficientPoints() {
        boolean success = manager.attemptPurchase("vertex-multiplier");

        assertFalse(success);
        assertEquals(0, gameState.getPrestigeTree().getNode("vertex-multiplier").getPurchaseCount());
    }

    @Test
    @DisplayName("can repeatedly purchase infinitely purchaseable node")
    void canRepeatPurchaseInfinitelyPurchaseableNode() {
        gameState.spendPrestigePoints(-260.0);

        assertTrue(manager.attemptPurchase("vertex-multiplier"));
        assertTrue(manager.attemptPurchase("vertex-multiplier"));

        assertEquals(2, gameState.getPrestigeTree().getNode("vertex-multiplier").getPurchaseCount());
        assertEquals(0.0, gameState.getPrestigePoints(), 0.0001);
    }

    @Test
    @DisplayName("notifies listeners on purchase")
    void notifiesListenersOnPurchase() {
        gameState.spendPrestigePoints(-100.0);
        boolean[] called = {false};
        manager.addListener(node -> called[0] = true);

        manager.attemptPurchase("vertex-multiplier");

        assertTrue(called[0]);
    }
}
