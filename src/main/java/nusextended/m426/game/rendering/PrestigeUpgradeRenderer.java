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

    public Button createUpgradeBox(UpgradeNode node) {
        Button btn = new Button();
        VBox vbox = new VBox();

        btn.setMinHeight(100);
        btn.setMaxHeight(150);
        btn.setStyle("-fx-background-color: #ffffff; -fx-border-color: #000000; -fx-border-width: 3");
        btn.setMaxWidth(Double.MAX_VALUE);

        HBox.setHgrow(btn, Priority.ALWAYS);

        Label descLabel = new Label(node.getDescription());
        Label costLabel = new Label(String.valueOf(node.getCurrentCost()));
        vbox.setAlignment(Pos.CENTER);
        descLabel.setAlignment(Pos.CENTER);
        costLabel.setAlignment(Pos.CENTER);
        descLabel.setMaxWidth(Double.MAX_VALUE);
        costLabel.setMaxWidth(Double.MAX_VALUE);
        vbox.getChildren().addAll(descLabel, costLabel);
        btn.setGraphic(vbox);

        btn.setOnAction(event -> {
            System.out.println("PP: " + gameState.getPrestigePoints() + " cost: " + node.getCurrentCost());
            if (node.canPurchase(null, gameState.getPrestigePoints()))  {
            double costPaid = node.getCurrentCost();
            node.recordPurchase();
            gameState.spendPrestigePoints(costPaid);
            setPrestigeUpgrades();
            }
        });

        return btn;
    }

    public void setPrestigeUpgrades() {

        prestigeUpgradesContainer.getChildren().clear();

        List<List<UpgradeNode>> rows = new ArrayList<>();
        Queue<UpgradeNode> remainingNodes = new ArrayDeque<>(gameState.getUpgradeTree().getNodes());

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
            maxDepth = Math.max(maxDepth, getDepth(previous))
        }
        return maxDepth + 1;
    }
}