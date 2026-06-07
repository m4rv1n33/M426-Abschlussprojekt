package nusextended.m426.game;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import nusextended.m426.model.Shape;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class GameState {
    private double currency;
    private double prestigePoints;
    private int prestigeLevel;
    private ShapeData activeShapeData;
    private PrestigeTree prestigeTree;
    private UpgradeTree upgradeTree;
    private double lifetimeCurrencyEarned;
    private static final String OS = System.getProperty("os.name").toUpperCase();
    private static final String OUR_DIRECTORY = "/nusExtended/M426/";
    private static final String SAVE_DIR;
    private static final String SAVE_FILE;
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private transient UpgradeStateManager upgradeStateManager;

    static {
        StringBuilder saveDirBuilder = new StringBuilder();

        if (OS.contains("WIN")) {
            saveDirBuilder.append(System.getenv("LOCALAPPDATA"));
        } else {
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
        this.activeShapeData = new ShapeData(1, 0);
        this.prestigeTree = PrestigeTree.createDefaultTree();
        this.upgradeTree = UpgradeTree.createDefaultTree();
        this.upgradeStateManager = new UpgradeStateManager(this);
    }

    public Shape getActiveShape() {
        BalanceConfig cfg = BalanceConfig.get();
        Shape shape = new Shape(0, cfg.shapeBaseProductionRate);
        shape.setLevel(activeShapeData.level);
        shape.setVertices(activeShapeData.vertices);
        UpgradeNode vertexMultiplier = getPrestigeTree().getNode("vertex-multiplier");
        double multiplier = vertexMultiplier != null
            ? 1.0 + (vertexMultiplier.getPurchaseCount() * cfg.vertexMultiplierPerPurchase)
            : 1.0;
        shape.setVertexMultiplier(multiplier);
        return shape;
    }

    public void save() {
        try {
            Files.createDirectories(Paths.get(SAVE_DIR));
            Files.write(Paths.get(SAVE_FILE), GSON.toJson(this).getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static GameState load() {
        try {
            if (Files.exists(Paths.get(SAVE_FILE))) {
                String json = new String(Files.readAllBytes(Paths.get(SAVE_FILE)));
                GameState loaded = GSON.fromJson(json, GameState.class);
                if (loaded == null) {
                    System.err.println("[GameState] Save file is empty or unreadable, starting a new game.");
                    return new GameState();
                }
                if (loaded.prestigeTree == null) {
                    loaded.prestigeTree = PrestigeTree.createDefaultTree();
                } else {
                    loaded.prestigeTree.resolveReferences();
                }
                if (loaded.upgradeTree != null) {
                    loaded.upgradeTree.resolveReferences();
                }
                loaded.upgradeStateManager = new UpgradeStateManager(loaded);
                return loaded;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JsonSyntaxException e) {
            System.err.println("[GameState] Save file is corrupted, starting a new game: " + e.getMessage());
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
        return 1.0 + (prestigeLevel * BalanceConfig.get().prestigeBonusPerLevel);
    }

    public double getPrestigePoints() {
        return prestigePoints;
    }

    public PrestigeTree getPrestigeTree() {
        if (prestigeTree == null) {
            prestigeTree = PrestigeTree.createDefaultTree();
        }
        return prestigeTree;
    }

    public void spendPrestigePoints(double cost) {
        this.prestigePoints -= cost;
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

    public boolean canPrestige() {
        return Math.floor(Math.pow(currency, BalanceConfig.get().prestigeFormulaExponent)) > 0;
    }

    public boolean prestige() {
        if (!canPrestige()) {
            return false;
        }
        double prestigePointsGained = Math.floor(Math.pow(currency, BalanceConfig.get().prestigeFormulaExponent));
        this.prestigePoints += prestigePointsGained;
        this.prestigeLevel++;
        this.currency = 0;
        this.activeShapeData = new ShapeData(1, 0);
        getUpgradeTree().reset();
        return true;
    }

    public static class ShapeData {
        public int vertices;
        public int level;

        public ShapeData(int vertices, int level) {
            this.vertices = vertices;
            this.level = level;
        }

        public void upgrade() {
            vertices++;
            level++;
        }
    }
}
