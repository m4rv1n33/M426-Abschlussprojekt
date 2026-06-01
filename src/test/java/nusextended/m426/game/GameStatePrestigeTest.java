package nusextended.m426.game;

import nusextended.m426.model.Shape;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameStatePrestigeTest {

    private GameState gameState;

    @BeforeEach
    void setUp() {
        gameState = new GameState();
    }

    @Test
    @DisplayName("prestige should reset currency and shape state while granting late-game prestige points")
    void prestigeShouldResetCurrencyAndShapeStateWhileGrantingLateGamePrestigePoints() {
        gameState.setCurrency(10000.0);

        gameState.prestige();

        assertEquals(0.0, gameState.getCurrency(), 0.0001);
        assertEquals(1, gameState.getPrestigeLevel());
        assertEquals(63.0, gameState.getPrestigePoints(), 0.0001);

        Shape activeShape = gameState.getActiveShape();
        assertEquals(0, activeShape.getLevel());
        assertEquals(1, activeShape.getVertices());
    }

    @Test
    @DisplayName("prestige bonus should grow by ten percent per prestige level")
    void prestigeBonusShouldGrowByTenPercentPerPrestigeLevel() {
        gameState.setCurrency(10000.0);
        gameState.prestige();

        gameState.setCurrency(10000.0);
        gameState.prestige();

        assertEquals(1.2, gameState.getPrestigeBonus(), 0.0001);
    }
}
