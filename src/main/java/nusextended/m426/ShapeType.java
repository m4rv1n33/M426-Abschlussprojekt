package nusextended.m426;

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
}
