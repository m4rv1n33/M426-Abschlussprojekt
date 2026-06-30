package nusextended.m426.game.rendering;

import nusextended.m426.game.GameState;
import nusextended.m426.model.UpgradeNode;
import nusextended.m426.game.NumberFormatter;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;

public class PrestigeUpgradeButton extends Button {
    private final UpgradeNode node;

    public PrestigeUpgradeButton(UpgradeNode node, GameState gameState, PrestigeUpgradeRenderer renderer) {
        this.node = node;

        VBox vbox = new VBox();

        this.setMinHeight(100);
        this.setMaxHeight(150);
        this.setStyle("-fx-background-color: #ffffff; -fx-border-color: #000000; -fx-border-width: 3");
        this.getStyleClass().remove("button");
        this.setMaxWidth(Double.MAX_VALUE);

        HBox.setHgrow(this, Priority.ALWAYS);

        Label descLabel = new Label(node.getDescription());
        Label costLabel = new Label(String.valueOf((long) node.getCurrentCost()));

        if (node.isPurchased()) {   
            this.setStyle("-fx-background-color: #000000; -fx-border-color: #000000; -fx-border-width: 3");
            descLabel.setStyle("-fx-text-fill: #ffffff");
            costLabel.setStyle("-fx-text-fill: #ffffff");
        }

        descLabel.setStyle(descLabel.getStyle() + "; -fx-font-size: 16px");

        vbox.setAlignment(Pos.CENTER);

        descLabel.setAlignment(Pos.CENTER);
        costLabel.setAlignment(Pos.CENTER);

        descLabel.setMaxWidth(Double.MAX_VALUE);
        costLabel.setMaxWidth(Double.MAX_VALUE);

        vbox.getChildren().add(descLabel);
        if (!node.isPurchased() || node.isInfinitelyPurchaseable()) vbox.getChildren().add(costLabel);

        if (node.isInfinitelyPurchaseable()) {
            Label levelLabel = new Label("Lv. " + node.getPurchaseCount());

            levelLabel.setAlignment(Pos.CENTER);
            levelLabel.setMaxWidth(Double.MAX_VALUE);

            if (node.isPurchased()) {
                levelLabel.setStyle("-fx-text-fill: #ffffff");
            }

            vbox.getChildren().add(levelLabel);
        }

        this.setGraphic(vbox);

        this.setOnAction(event -> {
           if (node.canPurchase(gameState.getActiveShape().getType(), gameState.getPrestigePoints()))  {
                double costPaid = node.getCurrentCost();
                node.recordPurchase();
                gameState.spendPrestigePoints(costPaid);
                renderer.setPrestigeUpgrades();

                System.out.println("Purchased " + node.getName() + " for " + costPaid);
            }
        });

        this.setOnMouseEntered(e -> {
            if (!node.isPurchased()) {
                this.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #000000; -fx-border-width: 3");
            }
        });

        this.setOnMouseExited(e -> {
            if (!node.isPurchased()) {
                this.setStyle("-fx-background-color: #ffffff; -fx-border-color: #000000; -fx-border-width: 3");
            }
        });

    }
}