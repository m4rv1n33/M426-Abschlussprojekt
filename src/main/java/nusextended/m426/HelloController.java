package nusextended.m426;

import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import nusextended.m426.game.GameState;
import nusextended.m426.game.NumberFormatter;
import nusextended.m426.model.Shape;
import nusextended.m426.model.UpgradeNode;

public class HelloController {
    private final Point2D shapeOrigin = new Point2D(407.5, 250);
    private final double shapeRadius = 225;
    private final double vertexSize = 16;
    private final double lineWidth = 6;
    private final double spinSpeed = 0.1; // radii per second

    private double lastFrame;
    private double delta;
    private double time = 0;

    private double spinOffset;

    private GameState gameState;
    private GraphicsContext shapeG2D;
    private GraphicsContext upgradesG2D;
    private Point2D upgradeTreeOffset;

    @FXML
    public Text currencyDisplay;

    @FXML
    public HBox prestigeCurrencyContainer;

    @FXML
    private Canvas shapeCanvas;

    @FXML
    private Text prestigeCurrencyDisplay;

    @FXML
    private Button prestigeButton;

    @FXML
    private Canvas upgradesCanvas;

    @FXML
    protected void initialize() {
        shapeG2D = shapeCanvas.getGraphicsContext2D();
        upgradesG2D = upgradesCanvas.getGraphicsContext2D();

        lastFrame = System.currentTimeMillis();

        shapeG2D.setLineWidth(lineWidth);
        shapeG2D.setStroke(Paint.valueOf("white"));

        upgradesG2D.setStroke(Paint.valueOf("white"));
        upgradesG2D.setTextAlign(TextAlignment.CENTER);
        upgradesG2D.setTextBaseline(VPos.CENTER);

        shapeG2D.setFill(Paint.valueOf("black"));
        shapeG2D.fillRect(0, 0, 5, shapeCanvas.getHeight());
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public void updateCurrencyDisplay(double currency, Shape shape, int prestigeLevel) {
        delta = System.currentTimeMillis() - lastFrame;
        lastFrame = System.currentTimeMillis();
        time += delta / 1000.0;

        currencyDisplay.setText(NumberFormatter.formatCurrency(currency));

        if (prestigeLevel < 1) {
            prestigeCurrencyContainer.setVisible(false);
        } else {
            prestigeCurrencyContainer.setVisible(true);
            prestigeCurrencyDisplay.setText(NumberFormatter.formatCurrency(prestigeLevel));
        }

        shapeG2D.clearRect(4, 0, shapeCanvas.getWidth(), shapeCanvas.getHeight());
        shapeG2D.setFill(Paint.valueOf("#999999"));
        shapeG2D.fillRect(4, 0, shapeCanvas.getWidth(), shapeCanvas.getHeight());

        renderShape();
        renderUpgradeTree();
    }

    private void renderUpgradeTree() {
        upgradesG2D.clearRect(0, 0, upgradesCanvas.getWidth(), upgradesCanvas.getHeight());
        upgradesG2D.setFill(Paint.valueOf("#999999"));
        upgradesG2D.fillRect(0, 0, upgradesCanvas.getWidth(), upgradesCanvas.getHeight());

        upgradesG2D.setFill(Paint.valueOf("#ffffff"));

        upgradeTreeOffset = new Point2D(
                upgradesCanvas.getWidth() / 2, upgradesCanvas.getHeight() / 2);

        // do this in two steps so the lines actually look nice

        upgradesG2D.setLineWidth(1);
        for (UpgradeNode upgrade : gameState.getUpgradeTree().getNodes()) {
            Point2D transformed = upgrade.getLocation().add(upgradeTreeOffset);


            for (UpgradeNode dep : upgrade.getPreviousNodes()) {
                Point2D p = dep.getLocation().add(upgradeTreeOffset);
                upgradesG2D.strokeLine(transformed.getX(), transformed.getY(), p.getX(), p.getY());
            }
        }

        upgradesG2D.setLineWidth(3);
        for (UpgradeNode upgrade : gameState.getUpgradeTree().getNodes()) {
            double radius = upgrade.getVisualSize();
            Point2D transformed = upgrade.getLocation().add(upgradeTreeOffset);

            upgradesG2D.setFill(Paint.valueOf("#999999"));
            upgradesG2D.fillOval(transformed.getX() - radius / 2, transformed.getY() - radius / 2, radius, radius);

            upgradesG2D.setFill(Paint.valueOf("white"));
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

        shapeG2D.setFill(Paint.valueOf("white"));
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
