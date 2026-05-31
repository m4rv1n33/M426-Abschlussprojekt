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
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public void updateCurrencyDisplay(double currency, Shape shape, int prestigeLevel) {
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
        Point2D[] points = new Point2D[vertexCount];

        for (int i = 0; i < vertexCount; i++) {

        }
    }
}
