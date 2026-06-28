package nusextended.m426.game.rendering;

import javafx.geometry.Point2D;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.TextAlignment;
import nusextended.m426.game.GameState;
import nusextended.m426.model.UpgradeNode;

public class UpgradeRenderer {
    private final double upgradeInfoWidth = 260;
    private final double upgradeInfoHeight = 140;

    private Point2D upgradeTreeOffset;
    private final GraphicsContext upgradesG2D;

    private UpgradeNode upgradeInfoNode;
    private Point2D upgradeInfoLoc;
    private boolean isHoveringUpgrade;

    private Point2D mouseDragStartPos;
    private Point2D mouseDragStartOffset;

    private final Canvas upgradesCanvas;
    private GameState gameState;

    public UpgradeRenderer(Canvas canvas, GameState gameState) {
        this.upgradesCanvas = canvas;
        this.gameState = gameState;
        upgradesG2D = upgradesCanvas.getGraphicsContext2D();
        upgradesG2D.setStroke(PaintHelper.WHITE);

        upgradeTreeOffset = new Point2D(
                upgradesCanvas.getWidth() / 2, upgradesCanvas.getHeight() / 2);
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
        upgradesCanvas.setOnMousePressed(this::onCanvasMousePressed);
        upgradesCanvas.setOnMouseClicked(this::onCanvasMouseClicked);
        upgradesCanvas.setOnMouseDragged(this::onCanvasMouseDragged);
        upgradesCanvas.setOnMouseMoved(this::onCanvasMouseMoved);
    }

    private void onCanvasMousePressed(MouseEvent ev) {
        mouseDragStartPos = new Point2D(
                ev.getX(),
                ev.getY());

        mouseDragStartOffset = upgradeTreeOffset;
    }

    private void onCanvasMouseClicked(MouseEvent ev) {
        if (gameState == null) return;
        if (ev.getButton() != MouseButton.PRIMARY) return;
        if (!isHoveringUpgrade) return;
        if (!upgradeInfoNode.canPurchase(gameState.getActiveShape().getType(), gameState.getCurrency())) return;

        gameState.getUpgradeStateManager().attemptPurchase(upgradeInfoNode.getName());
    }

    private void onCanvasMouseDragged(MouseEvent ev) {
        Point2D diff = mouseDragStartPos.subtract(
                ev.getX(),
                ev.getY());

        upgradeTreeOffset = mouseDragStartOffset.subtract(diff);
    }

    private void onCanvasMouseMoved(MouseEvent ev) {
        if (gameState == null) return;
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
        upgradesG2D.setFont(FontHelper.titleFont);
        upgradesG2D.setTextAlign(TextAlignment.LEFT);
        upgradesG2D.setTextBaseline(VPos.TOP);

        upgradesG2D.fillText(upgradeInfoNode.getName(), x + 7, y + 7, upgradeInfoWidth - 14);

        upgradesG2D.setFont(FontHelper.bodyFont);
        upgradesG2D.setTextAlign(TextAlignment.RIGHT);

        String priceText;
        if (upgradeInfoNode.isPurchased() && !upgradeInfoNode.isInfinitelyPurchaseable()) { priceText = "MAX"; }
        else { priceText = "$" + Math.round(Math.floor(upgradeInfoNode.getCurrentCost())); }

        upgradesG2D.fillText(priceText, x + upgradeInfoWidth - 7, y + 36, upgradeInfoWidth - 14);

        upgradesG2D.setTextAlign(TextAlignment.LEFT);
        upgradesG2D.fillText(upgradeInfoNode.getDescription(),
                x + 7, y + 54, upgradeInfoWidth - 14);
    }

    public void clearCanvas() {
        upgradesG2D.clearRect(0, 0, upgradesCanvas.getWidth(), upgradesCanvas.getHeight());
        upgradesG2D.setFill(PaintHelper.GREY);
        upgradesG2D.fillRect(0, 0, upgradesCanvas.getWidth(), upgradesCanvas.getHeight());
    }

    public void renderUpgradeTree() {
        if (gameState == null) return;

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
        upgradesG2D.setFont(FontHelper.bodyFont);

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
}