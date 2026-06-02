package nusextended.m426.game;

import nusextended.m426.model.Shape;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameStateUpgradeTreeTest {

    @Test
    @DisplayName("upgradeShape should buy the vertex growth node and advance the shape")
    void upgradeShapeShouldBuyTheVertexGrowthNodeAndAdvanceTheShape() {
        GameState gameState = new GameState();
        UpgradeStateManager manager = new UpgradeStateManager(gameState);
        gameState.setCurrency(100.0);

        manager.attemptPurchase("vertex-growth");

        Shape shape = gameState.getActiveShape();
        UpgradeNode vertexGrowth = gameState.getUpgradeTree().getNode("vertex-growth");

        assertEquals(1, shape.getLevel());
        assertEquals(2, shape.getVertices());
        assertEquals(1, vertexGrowth.getPurchaseCount());
        assertEquals(90.0, gameState.getCurrency(), 0.0001);
    }

    @Test
    @DisplayName("prestige should reset the upgrade tree progress")
    void prestigeShouldResetTheUpgradeTreeProgress() {
        GameState gameState = new GameState();
        UpgradeStateManager manager = new UpgradeStateManager(gameState);
        gameState.setCurrency(100.0);
        manager.attemptPurchase("vertex-growth");
        gameState.setCurrency(10000.0);

        gameState.prestige();

        assertEquals(0, gameState.getUpgradeTree().getNode("vertex-growth").getPurchaseCount());
        assertEquals(0, gameState.getUpgradeTree().getPurchasedNodes().size());
    }
}
