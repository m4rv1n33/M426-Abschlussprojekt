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
        return 100.0 * Math.pow(1.5, vertexMultiplierLevel);
    }

    public boolean purchaseVertexMultiplier(double prestigePoints) {
        double cost = getVertexMultiplierCost();
        if (prestigePoints >= cost) {
            vertexMultiplierLevel++;
            return true;
        }
        return false;
    }

    public void reset() {
    }

    @Override
    public String toString() {
        return String.format("Vertex Multiplier Level: %d (x%.2f)", 
            vertexMultiplierLevel, getVertexMultiplier());
    }
}
