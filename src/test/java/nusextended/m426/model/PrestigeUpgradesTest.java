package nusextended.m426.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PrestigeUpgradesTest {

    private PrestigeUpgrades prestigeUpgrades;

    @BeforeEach
    void setUp() {
        prestigeUpgrades = new PrestigeUpgrades();
    }

    @Test
    @DisplayName("new prestige upgrades should start at level 0")
    void newPrestigeUpgradesShouldStartAtLevel0() {
        assertEquals(0, prestigeUpgrades.getVertexMultiplierLevel());
        assertEquals(1.0, prestigeUpgrades.getVertexMultiplier(), 0.0001);
    }

    @Test
    @DisplayName("getVertexMultiplierCost should use the shared UpgradeCost formula")
    void getVertexMultiplierCostShouldUseTheSharedUpgradeCostFormula() {
        assertEquals(100.0, prestigeUpgrades.getVertexMultiplierCost(), 0.0001);

        prestigeUpgrades.applyVertexMultiplierPurchase();

        assertEquals(200.0, prestigeUpgrades.getVertexMultiplierCost(), 0.0001);
    }

    @Test
    @DisplayName("applyVertexMultiplierPurchase should increment the level")
    void applyVertexMultiplierPurchaseShouldIncrementTheLevel() {
        prestigeUpgrades.applyVertexMultiplierPurchase();

        assertEquals(1, prestigeUpgrades.getVertexMultiplierLevel());
        assertEquals(1.1, prestigeUpgrades.getVertexMultiplier(), 0.0001);
    }

    @Test
    @DisplayName("reset should clear the vertex multiplier level")
    void resetShouldClearTheVertexMultiplierLevel() {
        prestigeUpgrades.applyVertexMultiplierPurchase();
        prestigeUpgrades.applyVertexMultiplierPurchase();

        prestigeUpgrades.reset();

        assertEquals(0, prestigeUpgrades.getVertexMultiplierLevel());
        assertEquals(1.0, prestigeUpgrades.getVertexMultiplier(), 0.0001);
    }

    @Test
    @DisplayName("toString should include level and multiplier")
    void toStringShouldIncludeLevelAndMultiplier() {
        prestigeUpgrades.applyVertexMultiplierPurchase();

        assertEquals("Vertex Multiplier Level: 1 (x1.10)", prestigeUpgrades.toString());
    }
}
