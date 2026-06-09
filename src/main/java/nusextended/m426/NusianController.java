package nusextended.m426;

import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import nusextended.m426.game.GameState;
import nusextended.m426.game.NumberFormatter;
import nusextended.m426.game.PrestigeStateManager;
import nusextended.m426.game.UpgradeNode;
import nusextended.m426.game.UpgradeStateManager;
import nusextended.m426.model.Shape;


public class NusianController {
    private final Point2D shapeOrigin = new Point2D(407.5, 250);
    private final double shapeRadius = 225;
    private final double vertexSize = 16;
    private final double lineWidth = 6;
    private final double spinSpeed = 0.1;

    private double lastFrame;
    private double delta;
    private double spinOffset;

    private GameState gameState;
    private UpgradeStateManager upgradeManager;
    private GraphicsContext shapeG2D;
    private PrestigeStateManager prestigeManager;

    @FXML
    public Text currencyDisplay;

    @FXML
    public HBox prestigeCurrencyContainer;

    @FXML
    private Canvas shapeCanvas;

    @FXML
    private Text prestigeCurrencyDisplay;

    @FXML
    private VBox prestigeUpgradesContainer;

    @FXML
    public Canvas upgradesCanvas;

    @FXML
    public Button prestigeButton;

    @FXML
    protected void initialize() {
        shapeG2D = shapeCanvas.getGraphicsContext2D();
        lastFrame = System.currentTimeMillis();
        shapeG2D.setLineWidth(lineWidth);
        shapeG2D.setStroke(Paint.valueOf("white"));
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public void setUpgradeManager(UpgradeStateManager upgradeManager) {
        this.upgradeManager = upgradeManager;
    }

    public void setPrestigeManager(PrestigeStateManager prestigeManager) {
        this.prestigeManager = prestigeManager;

        prestigeButton.setOnAction(e -> {
            gameState.prestige();
            refreshPrestigeUpgradeButtons();
        });
        refreshPrestigeUpgradeButtons();
    }

    private void refreshPrestigeUpgradeButtons() {
        prestigeUpgradesContainer.getChildren().removeIf(
            node -> node instanceof Button && node != prestigeButton
        );
        for (UpgradeNode node : gameState.getPrestigeTree().getNodes()) {
            Button btn = new Button();
            updatePrestigeButton(btn, node);

            btn.setPrefWidth(446);
            btn.setPrefHeight(40);
            btn.setStyle("-fx-font-size: 16px;");

            btn.setOnAction(e -> {
                prestigeManager.attemptPurchase(node.getName());
                updatePrestigeButton(btn, node);
                
            });
            prestigeUpgradesContainer.getChildren().add(btn);
        }
    }

    private void updatePrestigeButton(Button btn, UpgradeNode node) {
        double cost = node.getCurrentCost();
        boolean canAfford = prestigeManager.canPurchase(node.getName());

        btn.setText(node.getDescription()
    + " (Stufe " + node.getPurchaseCount() + ")"
    + " - Kosten: " + NumberFormatter.formatCurrency(cost) + " PP");
    
        btn.setDisable(!canAfford);
    }


    public void updateCurrencyDisplay(double currency, Shape shape, int prestigeLevel, double prestigePoints) {
        delta = System.currentTimeMillis() - lastFrame;
        lastFrame = System.currentTimeMillis();

        currencyDisplay.setText(NumberFormatter.formatCurrency(currency));

        if (prestigeLevel < 1) {
            prestigeCurrencyContainer.setVisible(false);
        } else {
            prestigeCurrencyContainer.setVisible(true);
            prestigeCurrencyDisplay.setText(NumberFormatter.formatCurrency(prestigePoints));
        }

        double pointsOnPrestige = Math.floor(Math.pow(currency, 0.45));
        prestigeButton.setText("Prestige fuer +" + (long) pointsOnPrestige + " Punkte");

        prestigeUpgradesContainer.getChildren().forEach(node -> {
            if (node instanceof Button btn && btn != prestigeButton) {
                int index = prestigeUpgradesContainer.getChildren().indexOf(btn) - 1;
                if (index >= 0 && index < gameState.getPrestigeTree().getNodes().size()) {
                    UpgradeNode upgradeNode = gameState.getPrestigeTree().getNodes().get(index);
                    updatePrestigeButton(btn, upgradeNode);
                }
            }
            });

        shapeG2D.clearRect(0, 0, shapeCanvas.getWidth(), shapeCanvas.getHeight());
        shapeG2D.setFill(Paint.valueOf("#999999"));
        shapeG2D.fillRect(0, 0, shapeCanvas.getWidth(), shapeCanvas.getHeight());

        int vertexCount = gameState.getActiveShape().getVertices();
        double anglePerVertex = Math.TAU / vertexCount;
        Point2D[] points = new Point2D[vertexCount];

        spinOffset += Math.TAU * spinSpeed * delta / 1000.0;

        if (vertexCount > 1) {
            for (int i = 0; i < vertexCount; i++) {
                Point2D p = new Point2D(
                        Math.cos((anglePerVertex * i) + spinOffset),
                        Math.sin((anglePerVertex * i) + spinOffset)
                ).multiply(shapeRadius);

                points[i] = p.add(shapeOrigin);
            }
        } else {
            points[0] = shapeOrigin;
        }

        shapeG2D.setFill(Paint.valueOf("white"));
        for (Point2D p : points) {
            shapeG2D.fillOval(
                    p.getX() - vertexSize / 2, p.getY() - vertexSize / 2,
                    vertexSize, vertexSize);
        }

        for (int i = 0; i < vertexCount; i++) {
            Point2D p1 = points[i];
            Point2D p2;

            if (i == vertexCount - 1) {
                p2 = points[0];
            } else {
                p2 = points[i + 1];
            }

            shapeG2D.strokeLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());
        }
    }
}
