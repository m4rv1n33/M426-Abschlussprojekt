package nusextended.m426.game;

import java.util.ArrayList;
import java.util.List;

public class PrestigeStateManager {
    private final GameState gameState;
    private final List<UpgradeStateListener> listeners = new ArrayList<>();

    public PrestigeStateManager(GameState gameState) {
        this.gameState = gameState;
    }

    public void addListener(UpgradeStateListener listener) {
        listeners.add(listener);
    }

    public void removeListener(UpgradeStateListener listener) {
        listeners.remove(listener);
    }

    public boolean canPurchase(String nodeName) {
        UpgradeNode node = gameState.getPrestigeTree().getNode(nodeName);
        if (node == null) {
            return false;
        }
        boolean repeatable = node.isInfinitelyPurchaseable() || !node.isPurchased();
        return node.hasUnlockedPrerequisites()
            && repeatable
            && gameState.getPrestigePoints() >= node.getCurrentCost();
    }

    public boolean attemptPurchase(String nodeName) {
        if (!canPurchase(nodeName)) {
            return false;
        }
        UpgradeNode node = gameState.getPrestigeTree().getNode(nodeName);
        double cost = node.getCurrentCost();
        gameState.spendPrestigePoints(cost);
        node.recordPurchase();
        notifyPurchased(node);
        return true;
    }

    private void notifyPurchased(UpgradeNode node) {
        for (UpgradeStateListener listener : listeners) {
            listener.onUpgradePurchased(node);
        }
    }
}
