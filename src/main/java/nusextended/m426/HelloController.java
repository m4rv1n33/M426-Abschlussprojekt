package nusextended.m426;

import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import nusextended.m426.game.GameState;
import nusextended.m426.game.NumberFormatter;
import nusextended.m426.model.Shape;

public class HelloController {
    private final Point2D shapeOrigin = new Point2D(407.5, 250);
    private final double shapeRadius = 225;
    private final double vertexSize = 16;

    private double lastFrame;
    private double delta;
    private double time = 0;

    private GameState gameState;
    private GraphicsContext shapeG2D;
    private GraphicsContext upgradesG2D;

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
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public void updateCurrencyDisplay(double currency, Shape shape, int prestigeLevel) {
        delta = System.currentTimeMillis() - lastFrame;
        time += delta;

        currencyDisplay.setText(NumberFormatter.formatCurrency(currency));

        if (prestigeLevel < 1) {
            prestigeCurrencyContainer.setVisible(false);
        } else {
            prestigeCurrencyContainer.setVisible(true);
            prestigeCurrencyDisplay.setText(NumberFormatter.formatCurrency(prestigeLevel));
        }

        shapeG2D.clearRect(0, 0, shapeCanvas.getWidth(), shapeCanvas.getHeight());
        shapeG2D.setFill(Paint.valueOf("#999999"));
        shapeG2D.fillRect(0, 0, shapeCanvas.getWidth(), shapeCanvas.getHeight());

        int vertexCount = gameState.getActiveShape().getVertices();
        double anglePerVertex = Math.PI * 2f / vertexCount;
        Point2D[] points = new Point2D[vertexCount];

        if (vertexCount > 1) {
            for (int i = 0; i < vertexCount; i++) {
                Point2D p = new Point2D(
                        Math.cos(anglePerVertex * i),
                        Math.sin(anglePerVertex * i)
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
    }
}
