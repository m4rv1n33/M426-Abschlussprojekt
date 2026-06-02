package nusextended.m426.game;

import javafx.animation.AnimationTimer;
import nusextended.m426.model.Shape;

public class GameEngine extends AnimationTimer {
    private GameState gameState;
    private UpgradeStateManager upgradeManager;
    private CurrencyListener currencyListener;
    private long lastUpdateTime = -1;
    private long saveAccumulator = 0;
    private static final long SAVE_INTERVAL = 5_000_000_000L; // save every 5 seconds in nanoseconds (yes, nanoseconds)

    public GameEngine(GameState gameState, UpgradeStateManager upgradeManager) {
        this.gameState = gameState;
        this.upgradeManager = upgradeManager;
    }

    public void setCurrencyListener(CurrencyListener listener) {
        this.currencyListener = listener;
    }

    @Override
    public void handle(long now) {
        if (lastUpdateTime == -1) {
            lastUpdateTime = now;
            return;
        }

        // calculate delta time in seconds
        long deltaTime = now - lastUpdateTime;
        double deltaSeconds = deltaTime / 1_000_000_000.0;
        lastUpdateTime = now;

        // number go up (not fps dependent anymore)
        Shape activeShape = gameState.getActiveShape();
        double production = activeShape.getCurrentProductionRate() * gameState.getPrestigeBonus() * deltaSeconds;
        gameState.addCurrency(production);
        upgradeManager.performAutoPurchases();

        if (currencyListener != null) {
            currencyListener.onCurrencyChanged(gameState.getCurrency(), activeShape, gameState.getPrestigeLevel());
        }

        // save periodically every 5 seconds
        saveAccumulator += deltaTime;
        if (saveAccumulator >= SAVE_INTERVAL) {
            gameState.save();
            saveAccumulator = 0;
        }
    }

    public interface CurrencyListener {
        void onCurrencyChanged(double currency, Shape activeShape, int prestigeLevel);
    }
}
