package nusextended.m426.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UpgradeStateManagerTest {
    private GameState gameState;
    private UpgradeStateManager manager;
    private boolean listenerCalled;

    @BeforeEach
    void setUp() {
        gameState = new GameState();
        manager = new UpgradeStateManager(gameState);
        listenerCalled = false;
    }

    @Test
    void shouldPurchaseUpgradeAndNotifyListener() {
        gameState.setCurrency(100.0);
        manager.addListener(node -> listenerCalled = true);

        boolean success = manager.attemptPurchase("vertex-growth");

        assertTrue(success);
        assertTrue(listenerCalled);
        assertEquals(2, gameState.getActiveShape().getVertices());
        assertEquals(90.0, gameState.getCurrency());
    }

    @Test
    void shouldFailIfInsufficientCurrency() {
        gameState.setCurrency(5.0);
        
        boolean success = manager.attemptPurchase("vertex-growth");

        assertFalse(success);
        assertEquals(1, gameState.getActiveShape().getVertices());
    }
}
