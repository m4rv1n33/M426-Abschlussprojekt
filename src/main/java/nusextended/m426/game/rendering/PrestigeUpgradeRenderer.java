package nusextended.m426.game.rendering;

import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import javafx.scene.layout.Priority;
import javafx.geometry.Pos;
import nusextended.m426.game.UpgradeNode;
import nusextended.m426.game.GameState;

public class PrestigeUpgradeRenderer {
    private VBox prestigeUpgradesContainer;
    private GameState gameState;

    public PrestigeUpgradeRenderer(VBox container, GameState gameState) {
    this.prestigeUpgradesContainer = container;
    this.gameState = gameState; 
    }

    public void setPrestigeUpgrades() {

        prestigeUpgradesContainer.getChildren().removeIf(node -> node instanceof PrestigeUpgradeButton);

        List<List<UpgradeNode>> rows = new ArrayList<>();
        Queue<UpgradeNode> remainingNodes = new ArrayDeque<>(gameState.getPrestigeTree().getNodes());

        while (remainingNodes.size() > 0) {
            List<UpgradeNode> currentRow = new ArrayList<>();
            List<UpgradeNode> toRemove = new ArrayList<>();

            for (UpgradeNode node : remainingNodes) {
                if (allPrerequisitesPlaced(node, rows)) {
                    currentRow.add(node);
                    toRemove.add(node);
                }
            }

            remainingNodes.removeAll(toRemove);
            rows.add(currentRow);

        }

        for (List<UpgradeNode> row : rows) {
            HBox hbox = new HBox(5);
            VBox.setVgrow(hbox, Priority.ALWAYS);

            for (UpgradeNode node : row) {
                PrestigeUpgradeButton btn = new PrestigeUpgradeButton(node, gameState, this);
                hbox.getChildren().add(btn);
            }

            prestigeUpgradesContainer.getChildren().add(hbox);

        }

    }

    private boolean allPrerequisitesPlaced(UpgradeNode node, List<List<UpgradeNode>> alreadyPlaced) {
    for (UpgradeNode previous : node.getPreviousNodes()) {
        boolean found = false;
        for (List<UpgradeNode> row : alreadyPlaced) {
            if (row.contains(previous)) {
                found = true;
                break;
            }
        }
        if (!found) {
            return false;
        }
    }
    return true;
}

    private int getDepth(UpgradeNode node) {
        if (node.getPreviousNodes().isEmpty()) {
            return 0;
        }
        int maxDepth = 0;
        for (UpgradeNode previous : node.getPreviousNodes()) {
            maxDepth = Math.max(maxDepth, getDepth(previous));
        }
        return maxDepth + 1;
    }
}