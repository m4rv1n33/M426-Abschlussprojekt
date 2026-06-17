package nusextended.m426.model;

import nusextended.m426.game.BalanceConfig;

public final class UpgradeCost {

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
        BalanceConfig cfg = BalanceConfig.get();
        return new UpgradeCost(cfg.shapeUpgradeBaseCost, cfg.shapeUpgradeScaling);
    }

    public static UpgradeCost prestigeUpgrade() {
        BalanceConfig cfg = BalanceConfig.get();
        return new UpgradeCost(cfg.prestigeUpgradeBaseCost, cfg.prestigeUpgradeScaling);
    }

    public static double getShapeUpgradeCost(int level) {
        return shapeUpgrade().getCurrentCost(level);
    }

    public static double getPrestigeUpgradeCost(int level) {
        return prestigeUpgrade().getCurrentCost(level);
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