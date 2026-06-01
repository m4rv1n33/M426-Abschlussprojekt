package nusextended.m426.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShapeTypeTest {

    @Test
    @DisplayName("getPolygonName should return the small polygon names")
    void getPolygonNameShouldReturnTheSmallPolygonNames() {
        assertEquals("Triangle", ShapeType.getPolygonName(3));
        assertEquals("Square", ShapeType.getPolygonName(4));
        assertEquals("Pentagon", ShapeType.getPolygonName(5));
        assertEquals("Hexagon", ShapeType.getPolygonName(6));
        assertEquals("Heptagon", ShapeType.getPolygonName(7));
        assertEquals("Octagon", ShapeType.getPolygonName(8));
    }

    @Test
    @DisplayName("getPolygonName should build larger polygon names without a switch")
    void getPolygonNameShouldBuildLargerPolygonNamesWithoutASwitch() {
        assertEquals("Hendecagon", ShapeType.getPolygonName(11));
        assertEquals("Docosagon", ShapeType.getPolygonName(22));
        assertEquals("Hentriacontagon", ShapeType.getPolygonName(31));
        assertEquals("Hectagon", ShapeType.getPolygonName(100));
    }

    @Test
    @DisplayName("getPolygonName should fall back for values above 100")
    void getPolygonNameShouldFallBackForValuesAbove100() {
        assertEquals("101-gon", ShapeType.getPolygonName(101));
    }
}
