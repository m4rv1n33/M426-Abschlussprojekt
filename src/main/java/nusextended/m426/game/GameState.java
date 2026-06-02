package nusextended.m426.game;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import nusextended.m426.model.PrestigeUpgrades;
import nusextended.m426.model.Shape;
import nusextended.m426.model.ShapeType;

import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;

public class GameState {
    private double currency;
    private double prestigePoints;
    private int prestigeLevel;
    private ShapeData activeShapeData;
    private PrestigeUpgrades prestigeUpgrades;
    private UpgradeTree upgradeTree;
    private double lifetimeCurrencyEarned;
    private static final String OS = System.getProperty("os.name").toUpperCase();
    private static final String OUR_DIRECTORY = "/nusExtended/M426/";
    private static final String SAVE_DIR;
    private static final String SAVE_FILE;
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private UpgradeStateManager upgradeStateManager;

    static {
        StringBuilder saveDirBuilder = new StringBuilder();

        if (OS.contains("WIN")) {
            saveDirBuilder.append(System.getenv("LOCALAPPDATA"));
        } else if (OS.equals("LINUX")) {
            saveDirBuilder.append(System.getProperty("user.home"))
                    .append("/.local/share");
        }

        saveDirBuilder.append(OUR_DIRECTORY);

        SAVE_DIR = saveDirBuilder.toString();
        SAVE_FILE = SAVE_DIR + "game.json";
    }

    public GameState() {
        this.currency = 0;
        this.prestigePoints = 0;
        this.prestigeLevel = 0;
        this.lifetimeCurrencyEarned = 0;
        this.activeShapeData = new ShapeData(0, 1, 0);
        this.prestigeUpgrades = new PrestigeUpgrades();
        this.upgradeTree = UpgradeTree.createDefaultTree();
        this.upgradeStateManager = new UpgradeStateManager(this);
    }

    public Shape getActiveShape() {
        Shape shape = new Shape(0, 1.0);
        shape.setLevel(activeShapeData.level);
        shape.setVertices(activeShapeData.vertices);
        shape.setVertexMultiplier(prestigeUpgrades.getVertexMultiplier());
        return shape;
    }

    public void upgradeShape() {
        UpgradeNode vertexGrowth = getUpgradeTree().getNode("vertex-growth");
        if (vertexGrowth == null) {
            return;
        }

        ShapeType currentShapeType = ShapeType.fromVertices(activeShapeData.vertices);
        double cost = vertexGrowth.getCurrentCost();
        if (currency >= cost && vertexGrowth.canPurchase(currentShapeType, currency)) {
            currency -= cost;
            vertexGrowth.recordPurchase();
            activeShapeData.upgrade();
        }
    }

    public int autoBuyVertexGrowth() {
        UpgradeNode shapeFocus = getUpgradeTree().getNode("shape-focus");
        UpgradeNode vertexGrowth = getUpgradeTree().getNode("vertex-growth");

        if (shapeFocus == null || vertexGrowth == null || !shapeFocus.isPurchased()) {
            return 0;
        }

        int purchases = 0;
        ShapeType currentShapeType = ShapeType.fromVertices(activeShapeData.vertices);

        while (vertexGrowth.canPurchase(currentShapeType, currency)) {
            double cost = vertexGrowth.getCurrentCost();
            currency -= cost;
            vertexGrowth.recordPurchase();
            activeShapeData.upgrade();
            purchases++;

            currentShapeType = ShapeType.fromVertices(activeShapeData.vertices);
        }

        return purchases;
    }

    public void save() {
        try {
            Files.createDirectories(Paths.get(SAVE_DIR));
            Files.write(Paths.get(SAVE_FILE), gson.toJson(this).getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static GameState load() {
        try {
            if (Files.exists(Paths.get(SAVE_FILE))) {
                String json = new String(Files.readAllBytes(Paths.get(SAVE_FILE)));
                return gson.fromJson(json, GameState.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new GameState();
    }

    public UpgradeStateManager getUpgradeStateManager() {
        return upgradeStateManager;
    }

    public double getCurrency() {
        return currency;
    }

    public void setCurrency(double currency) {
        this.currency = currency;
    }

    public void addCurrency(double amount) {
        this.currency += amount;
        this.lifetimeCurrencyEarned += amount;
    }

    public void advanceShape() {
        activeShapeData.upgrade();
    }

    public int getPrestigeLevel() {
        return prestigeLevel;
    }
    
    public double getPrestigeBonus() {
        return 1.0 + (prestigeLevel * 0.10);
    }

    public double getPrestigePoints() {
        return prestigePoints;
    }

    public PrestigeUpgrades getPrestigeUpgrades() {
        return prestigeUpgrades;
    }

    public UpgradeTree getUpgradeTree() {
        if (upgradeTree == null) {
            upgradeTree = UpgradeTree.createDefaultTree();
            upgradeTree.syncShapeGrowthLevel(activeShapeData.level);
        }
        return upgradeTree;
    }

    public double getLifetimeCurrencyEarned() {
        return lifetimeCurrencyEarned;
    }

    public boolean purchaseVertexMultiplier() {
        double cost = prestigeUpgrades.getVertexMultiplierCost();
        if (prestigePoints >= cost) {
            prestigePoints -= cost;
            prestigeUpgrades.applyVertexMultiplierPurchase();
            return true;
        }
        return false;
    }

    public void prestige() {
        double prestigePointsGained = Math.floor(Math.pow(currency, 0.45));
        this.prestigePoints += prestigePointsGained;
        this.prestigeLevel++;
        this.currency = 0;
        this.activeShapeData = new ShapeData(0, 1, 0);
        getUpgradeTree().reset();
    }

    public static class ShapeData {
        public int id;
        public int vertices;
        public int level;
        private static final int UPGRADES_PER_SHAPE = 3;

        public ShapeData(int id, int vertices, int level) {
            this.id = id;
            this.vertices = vertices;
            this.level = level;
        }

        public double getCurrentProductionRate(double baseRate) {
            double levelBonus = 1.0 + (level * 0.2);
            return baseRate * vertices * levelBonus;
        }

        public double getNextUpgradeCost() {
            return UpgradeCost.getShapeUpgradeCost(level);
        }

        public void upgrade() {
            vertices++;
            level++;
        }
    }
}
