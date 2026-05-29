package nusextended.m426;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;

public class GameState {
    private double currency;
    private int prestigeLevel;
    private ShapeData activeShapeData;
    private static final String SAVE_DIR = System.getenv("LOCALAPPDATA") + "\\nusExtended\\M426";
    private static final String SAVE_FILE = SAVE_DIR + "\\game.json";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public GameState() {
        this.currency = 0;
        this.prestigeLevel = 0;
        this.activeShapeData = new ShapeData(0, ShapeType.TRIANGLE.getVertices(), 0);
    }

    public Shape getActiveShape() {
        Shape shape = new Shape(0, ShapeType.TRIANGLE, 1.0, 10.0);
        shape.setLevel(activeShapeData.level);
        shape.setCurrentType(activeShapeData.getCurrentType());
        shape.setVertices(activeShapeData.vertices);
        return shape;
    }

    public void upgradeShape() {
        double cost = activeShapeData.getNextUpgradeCost();
        if (currency >= cost) {
            currency -= cost;
            activeShapeData.upgrade();
        }
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

    public double getCurrency() {
        return currency;
    }

    public void setCurrency(double currency) {
        this.currency = currency;
    }

    public void addCurrency(double amount) {
        this.currency += amount;
    }

    public int getPrestigeLevel() {
        return prestigeLevel;
    }
    
    public double getPrestigeBonus() {
        return 1.0 + (prestigeLevel * 0.05);
    }

    public void prestige() {
        this.prestigeLevel++;
        this.currency = 0;
        this.activeShapeData = new ShapeData(0, ShapeType.TRIANGLE.getVertices(), 0);
    }

    public static class ShapeData {
        public int id;
        public int vertices;
        public int level;
        public int shapeTypeIndex;
        private static final int UPGRADES_PER_SHAPE = 3;

        public ShapeData(int id, int vertices, int level) {
            this.id = id;
            this.vertices = vertices;
            this.level = level;
            this.shapeTypeIndex = 0;
        }

        public double getCurrentProductionRate(double baseRate) {
            double levelBonus = 1.0 + (level * 0.2);
            return baseRate * vertices * levelBonus;
        }

        public double getNextUpgradeCost() {
            double baseCost = 10.0;
            return baseCost * Math.pow(1.15, level);
        }

        public void upgrade() {
            level++;
            if (level >= UPGRADES_PER_SHAPE) {
                evolveToNextShape();
            }
        }

        private void evolveToNextShape() {
            ShapeType[] types = ShapeType.values();
            if (shapeTypeIndex < types.length - 1) {
                shapeTypeIndex++;
                vertices = types[shapeTypeIndex].getVertices();
                level = 0;
            }
        }

        public ShapeType getCurrentType() {
            return ShapeType.values()[shapeTypeIndex];
        }
    }
}
