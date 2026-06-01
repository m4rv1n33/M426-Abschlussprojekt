package nusextended.m426.model;

import java.util.HashMap;
import java.util.Map;

public enum ShapeType {
    TRIANGLE(3, "Triangle"),
    SQUARE(4, "Square"),
    PENTAGON(5, "Pentagon"),
    HEXAGON(6, "Hexagon"),
    HEPTAGON(7, "Heptagon"),
    OCTAGON(8, "Octagon");

    private final int vertices;
    private final String displayName;

    ShapeType(int vertices, String displayName) {
        this.vertices = vertices;
        this.displayName = displayName;
    }

    public int getVertices() {
        return vertices;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static ShapeType fromVertices(int vertexCount) {
        for (ShapeType type : ShapeType.values()) {
            if (type.vertices == vertexCount) {
                return type;
            }
        }
        return OCTAGON;
    }

    public static String getPolygonName(int vertexCount) {
        if (vertexCount < 3) {
            return "Point";
        }

        String namedPolygon = POLYGON_NAMES.get(vertexCount);
        if (namedPolygon != null) {
            return namedPolygon;
        }

        if (vertexCount > 100) {
            return vertexCount + "-gon";
        }

        int tens = vertexCount / 10;
        int ones = vertexCount % 10;

        if (tens == 2) {
            return (ones == 1 ? "Hen" : ONES_PREFIXES[ones]) + "cosagon";
        }

        String tensRoot = TENS_ROOTS.get(tens * 10);
        if (tensRoot == null) {
            return vertexCount + "-gon";
        }

        if (ones == 0) {
            return capitalize(tensRoot + "agon");
        }

        return capitalize(ONES_PREFIXES[ones] + tensRoot + "agon");
    }

    private static String capitalize(String value) {
        if (value.isEmpty()) {
            return value;
        }
        return Character.toUpperCase(value.charAt(0)) + value.substring(1);
    }

    private static final Map<Integer, String> POLYGON_NAMES = createPolygonNames();
    private static final String[] ONES_PREFIXES = {
        "", "Hen", "Do", "Tri", "Tetra", "Penta", "Hexa", "Hepta", "Octa", "Ennea"
    };
    private static final Map<Integer, String> TENS_ROOTS = createTensRoots();

    private static Map<Integer, String> createPolygonNames() {
        Map<Integer, String> names = new HashMap<>();
        names.put(3, "Triangle");
        names.put(4, "Square");
        names.put(5, "Pentagon");
        names.put(6, "Hexagon");
        names.put(7, "Heptagon");
        names.put(8, "Octagon");
        names.put(9, "Nonagon");
        names.put(10, "Decagon");
        names.put(11, "Hendecagon");
        names.put(12, "Dodecagon");
        names.put(13, "Tridecagon");
        names.put(14, "Tetradecagon");
        names.put(15, "Pentadecagon");
        names.put(16, "Hexadecagon");
        names.put(17, "Heptadecagon");
        names.put(18, "Octadecagon");
        names.put(19, "Enneadecagon");
        names.put(20, "Icosagon");
        names.put(21, "Henicosagon");
        names.put(100, "Hectagon");
        return Map.copyOf(names);
    }

    private static Map<Integer, String> createTensRoots() {
        Map<Integer, String> roots = new HashMap<>();
        roots.put(20, "icos");
        roots.put(30, "triacont");
        roots.put(40, "tetracont");
        roots.put(50, "pentacont");
        roots.put(60, "hexacont");
        roots.put(70, "heptacont");
        roots.put(80, "octacont");
        roots.put(90, "enneacont");
        return Map.copyOf(roots);
    }
}
