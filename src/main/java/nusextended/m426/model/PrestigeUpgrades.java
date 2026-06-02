package nusextended.m426.model;

public class PrestigeUpgrades {
    private int vertexMultiplierLevel;

    public PrestigeUpgrades() {
        this.vertexMultiplierLevel = 0;
    }

    public int getVertexMultiplierLevel() {
        return vertexMultiplierLevel;
    }

    public double getVertexMultiplier() {
        return 1.0 + (vertexMultiplierLevel * 0.1);
    }

    public double getVertexMultiplierCost() {
        return UpgradeCost.getPrestigeUpgradeCost(vertexMultiplierLevel);
    }

    // Called by the owning code after currency/prestige deduction
    public void applyVertexMultiplierPurchase() {
        vertexMultiplierLevel++;
    }

    public void reset() {
        this.vertexMultiplierLevel = 0;
    }

    @Override
    public String toString() {
        return String.format("Vertex Multiplier Level: %d (x%.2f)", 
            vertexMultiplierLevel, getVertexMultiplier());
    }
}
