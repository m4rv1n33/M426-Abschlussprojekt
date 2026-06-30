package nusextended.m426;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import nusextended.m426.game.BalanceConfig;
import nusextended.m426.game.GameState;
import nusextended.m426.game.NumberFormatter;
import nusextended.m426.game.PrestigeStateManager;
import nusextended.m426.game.UpgradeStateManager;
import nusextended.m426.game.rendering.PrestigeUpgradeRenderer;
import nusextended.m426.game.rendering.ShapeRenderer;
import nusextended.m426.game.rendering.UpgradeRenderer;
import nusextended.m426.model.Shape;
import nusextended.m426.model.UpgradeNode;

public class NusianController {
    private double lastFrame;
    private double delta;
    private double time = 0;

    private GameState gameState;
    private UpgradeStateManager upgradeManager;
    private PrestigeStateManager prestigeManager;
    private PrestigeUpgradeRenderer prestigeRenderer;
    private ShapeRenderer shapeRenderer;
    private UpgradeRenderer upgradeRenderer;

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
        shapeRenderer = new ShapeRenderer(shapeCanvas, gameState);
        upgradeRenderer = new UpgradeRenderer(upgradesCanvas, gameState);

        lastFrame = System.currentTimeMillis();
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
        prestigeRenderer = new PrestigeUpgradeRenderer(prestigeUpgradesContainer, gameState);
        prestigeRenderer.setPrestigeUpgrades();
        shapeRenderer.setGameState(gameState);
        upgradeRenderer.setGameState(gameState);
    }

    public void setUpgradeManager(UpgradeStateManager upgradeManager) {
        this.upgradeManager = upgradeManager;
    }

    public void setPrestigeManager(PrestigeStateManager prestigeManager) {
        this.prestigeManager = prestigeManager;

        prestigeButton.setOnAction(e -> {
            gameState.prestige();
            prestigeRenderer.setPrestigeUpgrades();
        });
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
        long pointsGained = (long) gameState.getPendingPrestigePoints();

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

        shapeRenderer.clearCanvas();
        shapeRenderer.renderShape(delta);

        upgradeRenderer.clearCanvas();
        upgradeRenderer.renderUpgradeTree();
        upgradeRenderer.renderUpgradeInfo();
    }

}
