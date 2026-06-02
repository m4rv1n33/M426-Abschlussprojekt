package nusextended.m426.game;

import nusextended.m426.model.ShapeType;

import java.util.ArrayList;
import java.util.List;

public class UpgradeStateManager {

    private final GameState gameState;
    private final UpgradeTree upgradeTree;
    private final List<UpgradeStateListener> listeners = new ArrayList<>();

    public UpgradeStateManager(GameState gameState) {
        this.gameState = gameState;
        this.upgradeTree = gameState.getUpgradeTree();
    }

    public boolean canPurchase(UpgradeNode node) {
        ShapeType currentShapeType =
                gameState.getActiveShape().getType();

        return node.canPurchase(
                currentShapeType,
                gameState.getCurrency()
        );
    }

    public boolean attemptPurchase(String nodeName) {
        UpgradeNode node = upgradeTree.getNode(nodeName);

        if (node == null) {
            return false;
        }

        if (!canPurchase(node)) {
            return false;
        }

        double cost = node.getCurrentCost();
        gameState.setCurrency(
                gameState.getCurrency() - cost
        );

        node.recordPurchase();
        applyUpgradeEffect(nodeName);
        notifyListeners(node);
        return true;
    }

    private void applyUpgradeEffect(String nodeName) {
        if ("vertex-growth".equals(nodeName)) {
            gameState.advanceShape();
        }
    }

    public void addListener(UpgradeStateListener listener) {
        listeners.add(listener);
    }

    private void notifyListeners(UpgradeNode node) {
        for (UpgradeStateListener listener : listeners) {
            listener.onUpgradePurchased(node);
        }
    }
}