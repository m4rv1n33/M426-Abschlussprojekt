package nusextended.m426.game;

public final class UpgradeCost {
    private UpgradeCost() {}

    private static final double SHAPE_BASE_COST = 10.0;
    private static final double SHAPE_MULTIPLIER = 1.2;

    private static final double PRESTIGE_BASE_COST = 100.0;
    private static final double PRESTIGE_MULTIPLIER = 1.6;

    public static double getShapeUpgradeCost(int level) {
        return getCurrentCost(SHAPE_BASE_COST, SHAPE_MULTIPLIER, level);
    }

    public static double getPrestigeUpgradeCost(int level) {
        return getCurrentCost(PRESTIGE_BASE_COST, PRESTIGE_MULTIPLIER, level);
    }

    public static double getCurrentCost(double baseCost, double scalingFactor, int level) {
        if (level < 0) {
            throw new IllegalArgumentException("level must be >= 0");
        }
        if (baseCost <= 0.0) {
            throw new IllegalArgumentException("baseCost must be > 0");
        }
        if (scalingFactor <= 1.0) {
            throw new IllegalArgumentException("scalingFactor must be > 1.0");
        }
        return baseCost * Math.pow(scalingFactor, level);
    }

    public static boolean canAfford(double money, double cost) {
        return money >= cost;
    }

    public static boolean canAfford(double money, double baseCost, double scalingFactor, int level) {
        return money >= getCurrentCost(baseCost, scalingFactor, level);
    }
}