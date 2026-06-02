package nusextended.m426.model;

import javafx.geometry.Point2D;
import nusextended.m426.game.UpgradeCost;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UpgradeNode {
    private String name;
    private String description;
    private UpgradeCost cost;
    private boolean infinitelyPurchaseable;
    private ShapeType requiredShapeType;
    private List<UpgradeNode> previousNodes;
    private int purchaseCount;

    private double visualX;
    private double visualY;
    private double visualSize;
    private String icon;

    public UpgradeNode() {
        this.previousNodes = new ArrayList<>();
        this.purchaseCount = 0;
    }

    public UpgradeNode(String name,
                       String description,
                       UpgradeCost cost,
                       boolean infinitelyPurchaseable,
                       ShapeType requiredShapeType,
                       UpgradeNode... previousNodes) {
        this();
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.infinitelyPurchaseable = infinitelyPurchaseable;
        this.requiredShapeType = requiredShapeType;
        if (previousNodes != null) {
            Collections.addAll(this.previousNodes, previousNodes);
        }
    }

    public UpgradeNode(String name,
                       String description,
                       UpgradeCost cost,
                       boolean infinitelyPurchaseable,
                       UpgradeNode... previousNodes) {
        this(name, description, cost, infinitelyPurchaseable, null, previousNodes);
    }

    public double getVisualSize() { return visualSize; }

    public void setVisualSize(double visualSize) { this.visualSize = visualSize; }

    public Point2D getLocation() { return new Point2D(visualX, visualY); }

    public void setLocation(double x, double y) {
        this.visualX = x;
        this.visualY = y;
    }

    public String getIcon() { return icon; }

    public void setIcon(String icon) { this.icon = icon; }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public UpgradeCost getCost() {
        return cost;
    }

    public boolean isInfinitelyPurchaseable() {
        return infinitelyPurchaseable;
    }

    public ShapeType getRequiredShapeType() {
        return requiredShapeType;
    }

    public List<UpgradeNode> getPreviousNodes() {
        return Collections.unmodifiableList(previousNodes);
    }

    public int getPurchaseCount() {
        return purchaseCount;
    }

    public boolean isPurchased() {
        return purchaseCount > 0;
    }

    public double getCurrentCost() {
        return cost.getCurrentCost(purchaseCount);
    }

    public boolean hasUnlockedPrerequisites() {
        for (UpgradeNode previousNode : previousNodes) {
            if (!previousNode.isPurchased()) {
                return false;
            }
        }
        return true;
    }

    public boolean canPurchase(ShapeType currentShapeType, double currency) {
        boolean shapeRequirementMet = requiredShapeType == null || requiredShapeType == currentShapeType;
        boolean repeatable = infinitelyPurchaseable || purchaseCount == 0;
        return shapeRequirementMet
            && hasUnlockedPrerequisites()
            && repeatable
            && currency >= getCurrentCost();
    }

    public void recordPurchase() {
        if (!infinitelyPurchaseable && purchaseCount > 0) {
            throw new IllegalStateException("Upgrade node can only be purchased once.");
        }
        purchaseCount++;
    }

    public void resetProgress() {
        purchaseCount = 0;
    }

    void syncPurchaseCount(int purchaseCount) {
        this.purchaseCount = Math.max(0, purchaseCount);
    }
}