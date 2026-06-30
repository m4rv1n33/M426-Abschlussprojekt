package nusextended.m426.game.rendering;

import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;

import java.util.*;

import javafx.scene.layout.Priority;
import nusextended.m426.model.UpgradeNode;
import nusextended.m426.game.GameState;

public class PrestigeUpgradeRenderer {
    private VBox prestigeUpgradesContainer;
    private GameState gameState;

    public PrestigeUpgradeRenderer(VBox container, GameState gameState) {
        this.prestigeUpgradesContainer = container;
        this.gameState = gameState;
    }

    public void setPrestigeUpgrades() {
        prestigeUpgradesContainer.getChildren().removeIf(node -> !Objects.equals(node.getId(), "prestigeButton"));

        List<List<UpgradeNode>> rows = new ArrayList<>();
        Queue<UpgradeNode> remainingNodes = new ArrayDeque<>(gameState.getPrestigeTree().getNodes());

        while (!remainingNodes.isEmpty()) {
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

            if (!found) return false;
        }

        return true;
    }
}