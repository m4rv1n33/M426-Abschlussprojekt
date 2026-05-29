package nusextended.m426;

import javafx.animation.AnimationTimer;

public class GameEngine extends AnimationTimer {
    private GameState gameState;
    private CurrencyListener currencyListener;
    private int saveCounter = 0;
    private static final int SAVE_INTERVAL = 300; // save every 5 seconds buh

    public GameEngine(GameState gameState) {
        this.gameState = gameState;
    }

    public void setCurrencyListener(CurrencyListener listener) {
        this.currencyListener = listener;
    }

    @Override
    public void handle(long now) {
        // number go brrr
        Shape activeShape = gameState.getActiveShape();
        double production = activeShape.getCurrentProductionRate() * gameState.getPrestigeBonus() / 60.0;
        gameState.addCurrency(production);

        if (currencyListener != null) {
            currencyListener.onCurrencyChanged(gameState.getCurrency(), activeShape, gameState.getPrestigeLevel());
        }

        // save periodically
        saveCounter++;
        if (saveCounter >= SAVE_INTERVAL) {
            gameState.save();
            saveCounter = 0;
        }
    }

    public interface CurrencyListener {
        void onCurrencyChanged(double currency, Shape activeShape, int prestigeLevel);
    }
}
