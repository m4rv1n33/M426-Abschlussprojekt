package nusextended.m426.game;

import nusextended.m426.model.ShapeType;
import nusextended.m426.model.UpgradeNode;

import java.util.ArrayList;
import java.util.List;

public class UpgradeStateManager {
    private final GameState gameState;
    private final List<UpgradeStateListener> listeners = new ArrayList<>();

    public UpgradeStateManager(GameState gameState) {
        this.gameState = gameState;
    }

    public void addListener(UpgradeStateListener listener) {
        listeners.add(listener);
    }

    public void removeListener(UpgradeStateListener listener) {
        listeners.remove(listener);
    }

    public boolean canPurchase(UpgradeNode node) {
        if (node == null) {
            return false;
        }
        ShapeType currentType = gameState.getActiveShape().getType();
        return node.canPurchase(currentType, gameState.getCurrency());
    }

    public boolean attemptPurchase(String nodeName) {
        UpgradeNode node = gameState.getUpgradeTree().getNode(nodeName);
        if (node == null) {
            return false;
        }

        if (canPurchase(node)) {
            double cost = node.getCurrentCost();
            gameState.setCurrency(gameState.getCurrency() - cost);
            node.recordPurchase();
            applyUpgradeEffect(nodeName);
            notifyUpgradePurchased(node);
            return true;
        }
        return false;
    }

    public int performAutoPurchases() {
        UpgradeNode shapeFocus = gameState.getUpgradeTree().getNode("shape-focus");
        if (shapeFocus == null || !shapeFocus.isPurchased()) {
            return 0;
        }

        int purchases = 0;
        while (attemptPurchase("vertex-growth")) {
            purchases++;
        }
        return purchases;
    }

    private void applyUpgradeEffect(String nodeName) {
        if ("vertex-growth".equals(nodeName)) {
            gameState.advanceShape();
        }
    }

    private void notifyUpgradePurchased(UpgradeNode node) {
        for (UpgradeStateListener listener : listeners) {
            listener.onUpgradePurchased(node);
        }
    }
}
