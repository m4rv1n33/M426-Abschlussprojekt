package nusextended.m426.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShapeTest {

    private Shape shape;

    @BeforeEach
    void setUp() {
        shape = new Shape(7, 10.0, 50.0);
    }

    @Test
    @DisplayName("new shape should start at level 0 with one vertex")
    void newShapeShouldStartAtLevel0WithOneVertex() {
        assertEquals(0, shape.getLevel());
        assertEquals(1, shape.getVertices());
    }

    @Test
    @DisplayName("applyUpgrade should increment level and vertices")
    void applyUpgradeShouldIncrementLevelAndVertices() {
        shape.applyUpgrade();

        assertEquals(1, shape.getLevel());
        assertEquals(2, shape.getVertices());
    }

    @Test
    @DisplayName("getNextUpgradeCost should delegate to UpgradeCost")
    void getNextUpgradeCostShouldDelegateToUpgradeCost() {
        assertEquals(50.0, shape.getNextUpgradeCost(), 0.0001);

        shape.applyUpgrade();

        assertEquals(75.0, shape.getNextUpgradeCost(), 0.0001);
    }

    @Test
    @DisplayName("getCurrentProductionRate should use base rate, vertices, multiplier, and level bonus")
    void getCurrentProductionRateShouldUseAllFactors() {
        shape.setVertices(3);
        shape.setVertexMultiplier(1.5);
        shape.applyUpgrade();

        double expected = 10.0 * 4 * 1.5 * 1.2;

        assertEquals(expected, shape.getCurrentProductionRate(), 0.0001);
    }

    @Test
    @DisplayName("getDisplayName should return the polygon name for the current vertex count")
    void getDisplayNameShouldReturnThePolygonNameForTheCurrentVertexCount() {
        shape.setVertices(8);

        assertEquals("Octagon", shape.getDisplayName());
    }
}
