package nusextended.m426;

import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import nusextended.m426.game.GameState;
import nusextended.m426.game.NumberFormatter;
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
    private GraphicsContext shapeG2D;

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
        setPrestigeUpgrades();
    }

    public VBox createUpgradeBox(String description, String cost) {
        VBox vbox = new VBox(); 
        vbox.setMinHeight(100); 
        vbox.setStyle("-fx-background-color: #FFEDFB; -fx-border-color: #CBBFFF");
        HBox.setHgrow(vbox, Priority.ALWAYS); 
        vbox.setMaxWidth(Double.MAX_VALUE);
        Label descLabel = new Label(description); 
        Label costLabel = new Label(cost); 
        vbox.getChildren().addAll(descLabel, costLabel);
        return vbox;
    }

    public void setPrestigeUpgrades() {
        HBox row1 = new HBox(5);
        row1.getChildren().addAll(
        createUpgradeBox("smtburgir", "50"),
        createUpgradeBox("smtburgir", "50"),
        createUpgradeBox("smtburgir", "50")
    );
        HBox row2 = new HBox(5);
        row2.getChildren().addAll(
        createUpgradeBox("smtburgir", "50"),
        createUpgradeBox("smtburgir", "50"),
        createUpgradeBox("smtburgir", "50")
    );
        HBox row3= new HBox(5);
        row3.getChildren().addAll(
        createUpgradeBox("smtburgir", "50"),
        createUpgradeBox("smtburgir", "50"),
        createUpgradeBox("smtburgir", "50")
    );
        HBox row4 = new HBox(5);
        row4.getChildren().addAll(
        createUpgradeBox("smtburgir", "50"),
        createUpgradeBox("smtburgir", "50"),
        createUpgradeBox("smtburgir", "50")
    );

    System.out.println("Children count: " + prestigeUpgradesContainer.getChildren().size());

    prestigeUpgradesContainer.getChildren().addAll(row1, row2, row3, row4);
    }


    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public void setUpgradeManager(UpgradeStateManager upgradeManager) {
    }

    public void updateCurrencyDisplay(double currency, Shape shape, int prestigeLevel) {
        delta = System.currentTimeMillis() - lastFrame;
        lastFrame = System.currentTimeMillis();

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
