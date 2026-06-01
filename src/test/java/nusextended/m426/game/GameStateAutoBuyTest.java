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
        gameState.setCurrency(100.0);

        gameState.upgradeShape();
        gameState.upgradeShape();

        UpgradeNode shapeFocus = gameState.getUpgradeTree().getNode("shape-focus");
        assertTrue(shapeFocus.canPurchase(gameState.getActiveShape().getType(), gameState.getCurrency()));

        shapeFocus.recordPurchase();

        gameState.setCurrency(20.0);

        int autoPurchased = gameState.autoBuyVertexGrowth();

        assertEquals(1, autoPurchased);
        assertEquals(4, gameState.getActiveShape().getVertices());
    }
}
