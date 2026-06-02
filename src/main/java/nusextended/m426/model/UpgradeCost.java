package nusextended.m426.model;

public final class UpgradeCost {
    private static final UpgradeCost SHAPE_UPGRADE = new UpgradeCost(10.0, 1.2);
    private static final UpgradeCost PRESTIGE_UPGRADE = new UpgradeCost(100.0, 1.6);

    private final double baseCost;
    private final double scalingFactor;

    public UpgradeCost(double baseCost, double scalingFactor) {
        if (baseCost <= 0.0) {
            throw new IllegalArgumentException("baseCost must be > 0");
        }
        if (scalingFactor <= 1.0) {
            throw new IllegalArgumentException("scalingFactor must be > 1.0");
        }
        this.baseCost = baseCost;
        this.scalingFactor = scalingFactor;
    }

    public double getBaseCost() {
        return baseCost;
    }

    public double getScalingFactor() {
        return scalingFactor;
    }

    public double getCurrentCost(int level) {
        if (level < 0) {
            throw new IllegalArgumentException("level must be >= 0");
        }
        return baseCost * Math.pow(scalingFactor, level);
    }

    public boolean canAfford(double money, int level) {
        return money >= getCurrentCost(level);
    }

    public static UpgradeCost shapeUpgrade() {
        return SHAPE_UPGRADE;
    }

    public static UpgradeCost prestigeUpgrade() {
        return PRESTIGE_UPGRADE;
    }

    public static double getShapeUpgradeCost(int level) {
        return SHAPE_UPGRADE.getCurrentCost(level);
    }

    public static double getPrestigeUpgradeCost(int level) {
        return PRESTIGE_UPGRADE.getCurrentCost(level);
    }

    public static double getCurrentCost(double baseCost, double scalingFactor, int level) {
        return new UpgradeCost(baseCost, scalingFactor).getCurrentCost(level);
    }

    public static boolean canAfford(double money, double cost) {
        return money >= cost;
    }

    public static boolean canAfford(double money, double baseCost, double scalingFactor, int level) {
        return money >= getCurrentCost(baseCost, scalingFactor, level);
    }
}