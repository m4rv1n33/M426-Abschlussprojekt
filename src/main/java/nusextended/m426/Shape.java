package nusextended.m426;

public class Shape {
    private final int id;
    private int level;
    private int vertices;
    private double baseProductionRate;
    private double upgradeBaseCost;
    private double vertexMultiplier;
    private ShapeType currentType;
    private static final int UPGRADES_PER_SHAPE = 3;

    public Shape(int id, ShapeType startType, double baseProductionRate, double upgradeBaseCost) {
        this.id = id;
        this.level = 0;
        this.vertices = startType.getVertices();
        this.currentType = startType;
        this.baseProductionRate = baseProductionRate;
        this.upgradeBaseCost = upgradeBaseCost;
        this.vertexMultiplier = 1.0;
    }

    public Shape(Shape other) {
        this.id = other.id;
        this.level = 0;
        this.vertices = other.currentType.getVertices();
        this.currentType = other.currentType;
        this.baseProductionRate = other.baseProductionRate;
        this.upgradeBaseCost = other.upgradeBaseCost;
    }

    public int getId() {
        return id;
    }

    public ShapeType getType() {
        return currentType;
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

    public double getUpgradeBaseCost() {
        return upgradeBaseCost;
    }

    public double getCurrentProductionRate() {
        double levelBonus = 1.0 + (level * 0.2);
        return baseProductionRate * vertices * vertexMultiplier * levelBonus;
    }

    public double getNextUpgradeCost() {
        return upgradeBaseCost * Math.pow(1.15, level);
    }

    // this is basically shapez 3
    public boolean upgrade() {
        level++;
        if (level >= UPGRADES_PER_SHAPE) {
            evolveToNextShape();
        }
        return true;
    }

    private void evolveToNextShape() {
        ShapeType nextType = getNextShapeType(currentType);
        if (nextType != null && nextType != currentType) {
            currentType = nextType;
            vertices = nextType.getVertices();
            level = 0;
        }
    }

    private ShapeType getNextShapeType(ShapeType current) {
        ShapeType[] types = ShapeType.values();
        for (int i = 0; i < types.length - 1; i++) {
            if (types[i] == current) {
                return types[i + 1];
            }
        }
        return current;
    }

    public void reset() {
        this.level = 0;
        this.currentType = ShapeType.TRIANGLE;
        this.vertices = ShapeType.TRIANGLE.getVertices();
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setCurrentType(ShapeType currentType) {
        this.currentType = currentType;
    }

    public void setVertices(int vertices) {
        this.vertices = vertices;
    }

    public void setVertexMultiplier(double vertexMultiplier) {
        this.vertexMultiplier = vertexMultiplier;
    }

    @Override
    public String toString() {
        return String.format("%s Level %d (Vertices: %d, Rate: %.2f/s)",
                currentType.getDisplayName(), level, vertices, getCurrentProductionRate());
    }
}
