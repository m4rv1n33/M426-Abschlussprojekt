package nusextended.m426.game.rendering;

import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.geometry.Insets;
import javafx.scene.layout.Priority;
import javafx.geometry.Pos;

public class PrestigeUpgradeRenderer {
    private VBox prestigeUpgradesContainer;

public PrestigeUpgradeRenderer(VBox container) {
    this.prestigeUpgradesContainer = container;
}

public Button createUpgradeBox(String description, String cost) {
    Button btn = new Button();
    VBox vbox = new VBox(); 
    btn.setMinHeight(100); 
    btn.setStyle("-fx-background-color: #ffffff; -fx-border-color: #000000; -fx-border-width: 3");
    HBox.setHgrow(btn, Priority.ALWAYS); 
    btn.setMaxWidth(Double.MAX_VALUE);
    btn.setMaxHeight(Double.MAX_VALUE);
    Label descLabel = new Label(description); 
    Label costLabel = new Label(cost); 
    vbox.setAlignment(Pos.CENTER);
    descLabel.setAlignment(Pos.CENTER);
    costLabel.setAlignment(Pos.CENTER);
    descLabel.setMaxWidth(Double.MAX_VALUE);
    costLabel.setMaxWidth(Double.MAX_VALUE);
    vbox.getChildren().addAll(descLabel, costLabel);
    btn.setGraphic(vbox);
    return btn;
    }

public void setPrestigeUpgrades() {
    HBox row1 = new HBox(5);
    VBox.setVgrow(row1, Priority.ALWAYS);
    row1.getChildren().addAll(
    createUpgradeBox("smtburgir", "50"),
    createUpgradeBox("smtburgir", "50"),
    createUpgradeBox("smtburgir", "50")
);
    HBox row2 = new HBox(5);
    VBox.setVgrow(row2, Priority.ALWAYS);
    row2.getChildren().add(createUpgradeBox("smtburgir", "50")
);
    HBox row3= new HBox(5);
    VBox.setVgrow(row3, Priority.ALWAYS);
     row3.getChildren().addAll(
     createUpgradeBox("smtburgir", "50"),
    createUpgradeBox("smtburgir", "50"),
    createUpgradeBox("smtburgir", "50")
 );
    HBox row4 = new HBox(5);
    VBox.setVgrow(row4, Priority.ALWAYS);
     row4.getChildren().addAll(
     createUpgradeBox("smtburgir", "50"),
     createUpgradeBox("smtburgir", "50"), 
     createUpgradeBox("smtburgir", "50")
);

    System.out.println("Children count: " + prestigeUpgradesContainer.getChildren().size());
    prestigeUpgradesContainer.getChildren().addAll(row1, row2, row3, row4);
    }
}