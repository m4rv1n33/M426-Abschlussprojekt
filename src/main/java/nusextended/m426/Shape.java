package nusextended.m426;

public class Shape {
    private final int id;
    private int vertices;
    private double baseProductionRate;
    private double upgradeBaseCost;
    private int startVertices;

    public Shape(int id, ShapeType startType, double baseProductionRate, double upgradeBaseCost) {
        this.id = id;
        this.startVertices = startType.getVertices();
        this.vertices = startType.getVertices();
        this.baseProductionRate = baseProductionRate;
        this.upgradeBaseCost = upgradeBaseCost;
    }

    public Shape(Shape other) {
        this.id = other.id;
        this.startVertices = other.startVertices;
        this.vertices = other.startVertices;
        this.baseProductionRate = other.baseProductionRate;
        this.upgradeBaseCost = other.upgradeBaseCost;
    }

    public int getId() {
        return id;
    }

    public ShapeType getType() {
        return ShapeType.fromVertices(vertices);
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
        return baseProductionRate * vertices;
    }

    public double getNextUpgradeCost() {
        int upgradeCount = vertices - startVertices;
        return upgradeBaseCost * Math.pow(1.15, upgradeCount);
    }

    public boolean upgrade() {
        vertices++;
        return true;
    }

    public void reset() {
        vertices = startVertices;
    }

    @Override
    public String toString() {
        return String.format("%s (Vertices: %d, Rate: %.2f/s)",
                getType().getDisplayName(), vertices, getCurrentProductionRate());
    }
}
