package nusextended.m426.game;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameStateAutoBuyTest {

    @Test
    @DisplayName("shape-focus should unlock auto-buying vertex-growth")
    void shapeFocusShouldUnlockAutoBuyingVertexGrowth() {
        GameState gameState = new GameState();
        UpgradeStateManager manager = new UpgradeStateManager(gameState);
        gameState.setCurrency(100.0);

        manager.attemptPurchase("vertex-growth");
        manager.attemptPurchase("vertex-growth");

        UpgradeNode shapeFocus = gameState.getUpgradeTree().getNode("shape-focus");
        assertTrue(manager.canPurchase(shapeFocus));

        shapeFocus.recordPurchase();

        gameState.setCurrency(20.0);

        int autoPurchased = manager.performAutoPurchases();

        assertEquals(1, autoPurchased);
        assertEquals(4, gameState.getActiveShape().getVertices());
    }
}
