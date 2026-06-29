package nusextended.m426.game.rendering;

import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import java.util.List;
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
        btn.setMaxHeight(Double.MAX_VALUE);

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

        List<UpgradeNode> nodes = gameState.getPrestigeTree().getNodes();
        HBox currentRow = new HBox(5);
        VBox.setVgrow(currentRow, Priority.ALWAYS);

        for (int i = 0; i < nodes.size(); i++) {
            currentRow.getChildren().add(createUpgradeBox(nodes.get(i)));

            if (i % 3 == 2) {
                prestigeUpgradesContainer.getChildren().add(currentRow);
                currentRow = new HBox(5);
                VBox.setVgrow(currentRow, Priority.ALWAYS);
            }
        }

        if (!currentRow.getChildren().isEmpty()) {
            prestigeUpgradesContainer.getChildren().add(currentRow);
        }
    }
}