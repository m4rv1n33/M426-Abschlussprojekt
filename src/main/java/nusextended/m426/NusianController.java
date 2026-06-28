package nusextended.m426;

import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import nusextended.m426.game.BalanceConfig;
import nusextended.m426.game.GameState;
import nusextended.m426.game.NumberFormatter;
import nusextended.m426.game.PrestigeStateManager;
import nusextended.m426.game.UpgradeStateManager;
import nusextended.m426.game.rendering.PaintHelper;
import nusextended.m426.model.Shape;
import nusextended.m426.model.UpgradeNode;


public class NusianController {
    private final Point2D shapeOrigin = new Point2D(407.5, 250);
    private final double shapeRadius = 225;
    private final double vertexSize = 16;
    private final double lineWidth = 6;
    private final double spinSpeed = 0.1; // radii per second

    private final double upgradeInfoWidth = 260;
    private final double upgradeInfoHeight = 140;

    private final Font titleFont = new Font("Helvetica",25);
    private final Font bodyFont = new Font("Helvetica",17);

    private double lastFrame;
    private double delta;
    private double time = 0;

    private double spinOffset;

    private GameState gameState;
    private UpgradeStateManager upgradeManager;
    private GraphicsContext shapeG2D;
    private PrestigeStateManager prestigeManager;
    private GraphicsContext upgradesG2D;
    private Point2D upgradeTreeOffset;

    private Point2D mouseDragStartPos;
    private Point2D mouseDragStartOffset;

    private UpgradeNode upgradeInfoNode;
    private Point2D upgradeInfoLoc;
    private boolean isHoveringUpgrade;

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
    private Button prestigeButton;

    @FXML
    protected void initialize() {
        shapeG2D = shapeCanvas.getGraphicsContext2D();
        upgradesG2D = upgradesCanvas.getGraphicsContext2D();

        lastFrame = System.currentTimeMillis();

        shapeG2D.setLineWidth(lineWidth);
        shapeG2D.setStroke(PaintHelper.WHITE);

        upgradesG2D.setStroke(PaintHelper.WHITE);

        shapeG2D.setFill(PaintHelper.BLACK);
        shapeG2D.fillRect(0, 0, 5, shapeCanvas.getHeight());

        upgradeTreeOffset = new Point2D(
                upgradesCanvas.getWidth() / 2, upgradesCanvas.getHeight() / 2);

        upgradesCanvas.setOnMousePressed(ev -> {
            mouseDragStartPos = new Point2D(
                    ev.getX(),
                    ev.getY());

            mouseDragStartOffset = upgradeTreeOffset;
        });

        upgradesCanvas.setOnMouseClicked(ev -> {
            if (ev.getButton() != MouseButton.PRIMARY) return;
            if (!isHoveringUpgrade) return;
            if (!upgradeInfoNode.canPurchase(gameState.getActiveShape().getType(), gameState.getCurrency())) return;

            gameState.getUpgradeStateManager().attemptPurchase(upgradeInfoNode.getName());
        });

        upgradesCanvas.setOnMouseDragged(ev -> {
            Point2D diff = mouseDragStartPos.subtract(
                    ev.getX(),
                    ev.getY());

            upgradeTreeOffset = mouseDragStartOffset.subtract(diff);
        });

        upgradesCanvas.setOnMouseMoved(ev -> {
            Point2D mouseLoc = new Point2D(ev.getX(), ev.getY());
            Point2D hoverLoc = mouseLoc.subtract(upgradeTreeOffset);
            Point2D infoLoc = mouseLoc.add(20, 0); // mouse pointer offset

            isHoveringUpgrade = false;

            for (UpgradeNode upgrade : gameState.getUpgradeTree().getNodes()) {
                double visualSize = upgrade.getVisualSize() / 2;

                if (upgrade.getLocation().distance(hoverLoc) <= visualSize) {
                    setDrawnUpgradeInfo(upgrade, infoLoc);
                    isHoveringUpgrade = true;
                }
            }
        });
    }

    public void setDrawnUpgradeInfo(UpgradeNode upgrade, Point2D position) {
        upgradeInfoLoc = position;
        upgradeInfoNode = upgrade;
    }

    public void renderUpgradeInfo() {
        if (!isHoveringUpgrade) return;

        double x = upgradeInfoLoc.getX();
        double y = upgradeInfoLoc.getY();

        upgradesG2D.setStroke(PaintHelper.BLACK);
        upgradesG2D.setFill(PaintHelper.GREY);

        upgradesG2D.fillRect(x, y, upgradeInfoWidth, upgradeInfoHeight);

        upgradesG2D.beginPath();
        upgradesG2D.strokeLine(x, y, x + upgradeInfoWidth, y);
        upgradesG2D.strokeLine(x + upgradeInfoWidth, y, x + upgradeInfoWidth, y + upgradeInfoHeight);
        upgradesG2D.strokeLine(x + upgradeInfoWidth, y + upgradeInfoHeight, x, y + upgradeInfoHeight);
        upgradesG2D.strokeLine(x, y + upgradeInfoHeight, x, y);
        upgradesG2D.stroke();
        upgradesG2D.closePath();

        upgradesG2D.setFill(PaintHelper.WHITE);
        upgradesG2D.setFont(titleFont);
        upgradesG2D.setTextAlign(TextAlignment.LEFT);
        upgradesG2D.setTextBaseline(VPos.TOP);

        upgradesG2D.fillText(upgradeInfoNode.getName(), x + 7, y + 7, upgradeInfoWidth - 14);

        upgradesG2D.setFont(bodyFont);
        upgradesG2D.setTextAlign(TextAlignment.RIGHT);

        String priceText;
        if (upgradeInfoNode.isPurchased() && !upgradeInfoNode.isInfinitelyPurchaseable()) { priceText = "MAX"; }
        else { priceText = "$" + Math.round(Math.floor(upgradeInfoNode.getCurrentCost())); }

        upgradesG2D.fillText(priceText, x + upgradeInfoWidth - 7, y + 36, upgradeInfoWidth - 14);

        upgradesG2D.setTextAlign(TextAlignment.LEFT);
        upgradesG2D.fillText(upgradeInfoNode.getDescription(),
                x + 7, y + 54, upgradeInfoWidth - 14);
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
        prestigeUpgradesContainer.getChildren().clear();

        for (UpgradeNode node : gameState.getPrestigeTree().getNodes()) {
            Button btn = new Button();
            updatePrestigeUpgradeButton(btn, node);

            btn.setPrefWidth(446);
            btn.setPrefHeight(40);
            btn.setStyle("-fx-font-size: 16px;");

            btn.setOnAction(e -> {
                prestigeManager.attemptPurchase(node.getName());
                updatePrestigeUpgradeButton(btn, node);
            });

            prestigeUpgradesContainer.getChildren().add(btn);
        }
    }

    private void updatePrestigeUpgradeButton(Button btn, UpgradeNode node) {
        double cost = node.getCurrentCost();
        boolean canAfford = prestigeManager.canPurchase(node.getName());

        btn.setText(node.getName()
    + " (Lv. " + node.getPurchaseCount() + ")"
    + " / " + NumberFormatter.formatCurrency(cost) + " PP");

        btn.setDisable(!canAfford);
    }

    private void updatePrestigeButtonText(double currency) {
        long pointsGained = (long) Math.floor(Math.pow(currency, BalanceConfig.get().prestigeFormulaExponent));

        prestigeButton.setText("Prestige for +" + pointsGained + " PP");
        prestigeButton.setDisable(!gameState.canPrestige());
    }

    public void updateCurrencyDisplay(double currency, Shape shape, int prestigeLevel, double prestigePoints) {
        delta = System.currentTimeMillis() - lastFrame;
        lastFrame = System.currentTimeMillis();
        time += delta / 1000.0;

        currencyDisplay.setText(NumberFormatter.formatCurrency(currency));

        if (prestigeLevel < 1) {
            prestigeCurrencyContainer.setVisible(false);
        } else {
            prestigeCurrencyContainer.setVisible(true);
            prestigeCurrencyDisplay.setText(NumberFormatter.formatCurrency(prestigePoints));
        }

        updatePrestigeButtonText(currency);

        // don't do this every frame that's ridiculous
        /*
        prestigeUpgradesContainer.getChildren().forEach(node -> {
            if (node instanceof Button btn && btn != prestigeButton) {
                int index = prestigeUpgradesContainer.getChildren().indexOf(btn) - 1;
                if (index >= 0 && index < gameState.getPrestigeTree().getNodes().size()) {
                    UpgradeNode upgradeNode = gameState.getPrestigeTree().getNodes().get(index);
                    updatePrestigeUpgradeButton(btn, upgradeNode);
                }
            }
        });
        */

        shapeG2D.clearRect(4, 0, shapeCanvas.getWidth(), shapeCanvas.getHeight());
        shapeG2D.setFill(PaintHelper.GREY);
        shapeG2D.fillRect(4, 0, shapeCanvas.getWidth(), shapeCanvas.getHeight());

        renderShape();
        renderUpgradeTree();
        renderUpgradeInfo();
    }

    private void renderUpgradeTree() {
        upgradesG2D.clearRect(0, 0, upgradesCanvas.getWidth(), upgradesCanvas.getHeight());
        upgradesG2D.setFill(PaintHelper.GREY);
        upgradesG2D.fillRect(0, 0, upgradesCanvas.getWidth(), upgradesCanvas.getHeight());

        upgradesG2D.setFill(PaintHelper.WHITE);

        // do this in two steps so the lines actually look nice

        upgradesG2D.setLineWidth(1);
        upgradesG2D.setStroke(PaintHelper.WHITE);
        for (UpgradeNode upgrade : gameState.getUpgradeTree().getNodes()) {
            Point2D transformed = upgrade.getLocation().add(upgradeTreeOffset);

            for (UpgradeNode dep : upgrade.getPreviousNodes()) {
                Point2D p = dep.getLocation().add(upgradeTreeOffset);
                upgradesG2D.strokeLine(transformed.getX(), transformed.getY(), p.getX(), p.getY());
            }
        }

        upgradesG2D.setLineWidth(3);
        upgradesG2D.setTextAlign(TextAlignment.CENTER);
        upgradesG2D.setTextBaseline(VPos.CENTER);
        upgradesG2D.setFont(bodyFont);

        for (UpgradeNode upgrade : gameState.getUpgradeTree().getNodes()) {
            double radius = upgrade.getVisualSize();
            Point2D transformed = upgrade.getLocation().add(upgradeTreeOffset);

            if (upgrade.isPurchased()) { upgradesG2D.setFill(PaintHelper.OFFWHITE); }
            else { upgradesG2D.setFill(PaintHelper.GREY); }

            upgradesG2D.fillOval(transformed.getX() - radius / 2, transformed.getY() - radius / 2, radius, radius);

            if (upgrade.isPurchased()) { upgradesG2D.setFill(PaintHelper.BLACK); }
            else { upgradesG2D.setFill(PaintHelper.WHITE); }

            upgradesG2D.strokeOval(transformed.getX() - radius / 2, transformed.getY() - radius / 2, radius, radius);
            upgradesG2D.fillText(upgrade.getIcon(), transformed.getX(), transformed.getY());
        }
    }

    private void renderShape() {
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

        shapeG2D.setFill(PaintHelper.WHITE);
        for (Point2D p : points) {
            shapeG2D.fillOval(
                    p.getX() - vertexSize / 2, p.getY() - vertexSize / 2,
                    vertexSize, vertexSize);
        }

        for (int i = 0; i < vertexCount; i++) {
            Point2D p1 = points[i];
            Point2D p2;

            if (i == vertexCount - 1) { p2 = points[0]; }
            else { p2 = points[i + 1]; }

            shapeG2D.strokeLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());
        }
    }
}
