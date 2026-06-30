package nusextended.m426.game;

import nusextended.m426.model.Shape;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        // points = ceil((currency - minimumCurrencyToPrestige) ^ exponent)
        //        = ceil((10000 - 1000) ^ 0.5) = ceil(94.868...) = 95
        assertEquals(95.0, gameState.getPrestigePoints(), 0.0001);

        Shape activeShape = gameState.getActiveShape();
        assertEquals(0, activeShape.getLevel());
        assertEquals(1, activeShape.getVertices());
    }

    @Test
    @DisplayName("prestige should be a no-op when it would grant zero prestige points")
    void prestigeShouldDoNothingWhenItWouldGrantZeroPoints() {
        gameState.setCurrency(0.5);

        boolean prestiged = gameState.prestige();

        assertFalse(prestiged);
        assertFalse(gameState.canPrestige());
        assertEquals(0.5, gameState.getCurrency(), 0.0001);
        assertEquals(0, gameState.getPrestigeLevel());
        assertEquals(0.0, gameState.getPrestigePoints(), 0.0001);
    }

    @Test
    @DisplayName("prestige should succeed and report success when it would grant prestige points")
    void prestigeShouldSucceedWhenItWouldGrantPoints() {
        gameState.setCurrency(10000.0);

        assertTrue(gameState.canPrestige());
        assertTrue(gameState.prestige());
    }

    @Test
    @DisplayName("prestige is gated until currency exceeds the minimum-to-prestige threshold")
    void prestigeShouldBeGatedBelowMinimumPoints() {
        // 50 currency is below minimumCurrencyToPrestige (1000), so the clamped payout base
        // is 0 and prestige must be refused even though currency is non-zero.
        gameState.setCurrency(50.0);

        assertFalse(gameState.canPrestige());
        assertFalse(gameState.prestige());
        assertEquals(50.0, gameState.getCurrency(), 0.0001);
        assertEquals(0, gameState.getPrestigeLevel());
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
