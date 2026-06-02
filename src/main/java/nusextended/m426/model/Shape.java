package nusextended.m426.model;

public class Shape {
    private final int id;
    private int level;
    private int vertices;
    private double baseProductionRate;
    private double vertexMultiplier;

    public Shape(int id, double baseProductionRate) {
        this.id = id;
        this.level = 0;
        this.vertices = 1;
        this.baseProductionRate = baseProductionRate;
        this.vertexMultiplier = 1.0;
    }

    public int getId() {
        return id;
    }

    public ShapeType getType() {
        return ShapeType.fromVertices(vertices);
    }

    public String getDisplayName() {
        return ShapeType.getPolygonName(vertices);
    }

    public int getLevel() {
        return level;
    }

    public int getVertices() {
        return vertices;
    }

    public double getBaseProductionRate() {
        return baseProductionRate;
    }

    public double getCurrentProductionRate() {
        double levelBonus = 1.0 + (level * 0.2);
        return baseProductionRate * vertices * vertexMultiplier * levelBonus;
    }

    public double getNextUpgradeCost() {
        return UpgradeCost.getShapeUpgradeCost(level);
    }

    // this is basically shapez 3
    public void applyUpgrade() {
        level++;
        vertices++;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setVertices(int vertices) {
        this.vertices = vertices;
    }

    public void setVertexMultiplier(double vertexMultiplier) {
        this.vertexMultiplier = vertexMultiplier;
    }

    @Override
    public String toString() {
        return String.format("Shape Level %d (Vertices: %d, Rate: %.2f/s)",
                level, vertices, getCurrentProductionRate());
    }
}
