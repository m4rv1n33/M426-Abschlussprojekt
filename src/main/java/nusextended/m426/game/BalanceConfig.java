package nusextended.m426.game;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class BalanceConfig {

    public double shapeBaseProductionRate = 1.0;
    public double levelBonusPerLevel = 0.2;

    public double shapeUpgradeBaseCost = 10.0;
    public double shapeUpgradeScaling = 1.15;

    public double shapeFocusBaseCost = 35.0;
    public double shapeFocusScaling = 1.25;
    public double squareSomethingBaseCost = 120.0;
    public double squareSomethingScaling = 1.35;

    public double prestigeUpgradeBaseCost = 50.0;
    public double prestigeUpgradeScaling = 1.6;

    public double minimumCurrencyToPrestige = 1000.0;
    public double prestigeFormulaExponent = 0.45;
    public double prestigeBonusPerLevel = 0.10;
    public double vertexMultiplierPerPurchase = 0.10;

    private static final String CONFIG_FILE = "balance.json";
    private static BalanceConfig instance;

    private BalanceConfig() {
    }

    public static BalanceConfig get() {
        if (instance == null) {
            instance = loadOrDefault();
        }
        return instance;
    }

    private static BalanceConfig load() {
        File file = new File(CONFIG_FILE);
        if (!file.exists()) {
            return null;
        }
        try (FileReader reader = new FileReader(file)) {
            BalanceConfig loaded = new Gson().fromJson(reader, BalanceConfig.class);
            if (loaded != null) {
                System.out.println("[BalanceConfig] Loaded from " + file.getAbsolutePath());
                return loaded;
            }
        } catch (IOException e) {
            System.err.println("[BalanceConfig] Could not read " + CONFIG_FILE + ": " + e.getMessage());
        }
        return null;
    }

    private static BalanceConfig loadOrDefault() {
        BalanceConfig fromFile = load();
        if (fromFile != null) {
            return fromFile;
        }
        System.out.println("[BalanceConfig] " + CONFIG_FILE + " not found, using built-in defaults.");
        return new BalanceConfig();
    }

    static void resetForTesting() {
        instance = null;
    }
}
