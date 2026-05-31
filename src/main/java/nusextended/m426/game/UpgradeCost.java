package nusextended.m426.game;

public final class UpgradeCost {
    private UpgradeCost() {}

    private static final double SHAPE_BASE_COST = 50;
    private static final double SHAPE_MULTIPLIER = 1.5;

    private static final double PRESTIGE_BASE_COST = 100;
    private static final double PRESTIGE_MULTIPLIER = 2;

    public static double getShapeUpgradeCost(int level) {
        return getCurrentCost(SHAPE_BASE_COST, SHAPE_MULTIPLIER, level);
    }

    public static double getPrestigeUpgradeCost(int level) {
        return getCurrentCost(PRESTIGE_BASE_COST, PRESTIGE_MULTIPLIER, level);
    }

    public static double getCurrentCost(double baseCost, double scalingFactor, int level) {
            if (scalingFactor <= 1.0) {
                throw new IllegalArgumentException("scalingFactor must be greater than 1.0, otherwise costs will not grow :( boo, tomato tomato");
            }
            if (level < 0) {
                throw new IllegalArgumentException("level too low, skill issue");
            }
            if (baseCost <= 0) {
                throw new IllegalArgumentException("no freebies here");
            }
            return baseCost * Math.pow(scalingFactor, level);
        }

    public static boolean canAfford(double money, double cost) {
        return money >= cost;
    }
}