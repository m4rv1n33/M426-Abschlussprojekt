package nusextended.m426.game;

import nusextended.m426.model.UpgradeCost;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UpgradeCostCalculationTest {

    private double shapeBaseCost;
    private double shapeMultiplier;
    private double prestigeBaseCost;
    private double prestigeMultiplier;

    @BeforeEach
    void setUp() {
        shapeBaseCost = 10.0;
        shapeMultiplier = 1.15;
        prestigeBaseCost = 50.0;
        prestigeMultiplier = 1.6;
    }

    @Test
    @DisplayName("shape cost at level 0 should equal base cost")
    void shapeCostAtLevel0ShouldEqualBaseCost() {
        double actual = UpgradeCost.getShapeUpgradeCost(0);

        assertEquals(shapeBaseCost, actual, 0.0001);
    }

    @Test
    @DisplayName("shape cost should grow exponentially")
    void shapeCostShouldGrowExponentially() {
        double actual = UpgradeCost.getShapeUpgradeCost(2);
        double expected = shapeBaseCost * Math.pow(shapeMultiplier, 2);

        assertEquals(expected, actual, 0.0001);
    }

    @Test
    @DisplayName("prestige cost at level 0 should equal base cost")
    void prestigeCostAtLevel0ShouldEqualBaseCost() {
        double actual = UpgradeCost.getPrestigeUpgradeCost(0);

        assertEquals(prestigeBaseCost, actual, 0.0001);
    }

    @Test
    @DisplayName("prestige cost should grow exponentially")
    void prestigeCostShouldGrowExponentially() {
        double actual = UpgradeCost.getPrestigeUpgradeCost(3);
        double expected = prestigeBaseCost * Math.pow(prestigeMultiplier, 3);

        assertEquals(expected, actual, 0.0001);
    }

    @Test
    @DisplayName("canAfford should return true when money is enough")
    void canAffordShouldReturnTrueWhenMoneyIsEnough() {
        double cost = UpgradeCost.getShapeUpgradeCost(1);

        boolean result = UpgradeCost.canAfford(100.0, cost);

        assertEquals(true, result);
    }

    @Test
    @DisplayName("canAfford should return false when money is not enough")
    void canAffordShouldReturnFalseWhenMoneyIsNotEnough() {
        double cost = UpgradeCost.getPrestigeUpgradeCost(2);

        boolean result = UpgradeCost.canAfford(50.0, cost);

        assertEquals(false, result);
    }

    @Test
    @DisplayName("getCurrentCost should reject invalid scaling factor")
    void getCurrentCostShouldRejectInvalidScalingFactor() {
        assertThrows(IllegalArgumentException.class,
            () -> UpgradeCost.getCurrentCost(10.0, 1.0, 1));
    }

    @Test
    @DisplayName("getCurrentCost should reject negative level")
    void getCurrentCostShouldRejectNegativeLevel() {
        assertThrows(IllegalArgumentException.class,
            () -> UpgradeCost.getCurrentCost(10.0, 1.5, -1));
    }

    @Test
    @DisplayName("getCurrentCost should reject non-positive base cost")
    void getCurrentCostShouldRejectNonPositiveBaseCost() {
        assertThrows(IllegalArgumentException.class,
            () -> UpgradeCost.getCurrentCost(0.0, 1.5, 1));
    }

    @Test
    @DisplayName("canAfford should return true when money equals cost")
    void canAffordShouldReturnTrueWhenMoneyEqualsCost() {
        double cost = UpgradeCost.getShapeUpgradeCost(1);

        boolean result = UpgradeCost.canAfford(cost, cost);

        assertEquals(true, result);
    }
}
