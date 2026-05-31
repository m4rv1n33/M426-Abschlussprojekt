package nusextended.m426;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import nusextended.m426.game.GameState;
import nusextended.m426.game.NumberFormatter;
import nusextended.m426.model.Shape;
import nusextended.m426.model.PrestigeUpgrades;

public class HelloController {
    private GameState gameState;

    @FXML
    private Label currencyLabel;

    @FXML
    private Label productionLabel;

    @FXML
    private Label shapeLabel;

    @FXML
    private Label upgradeCostLabel;

    @FXML
    private Button upgradeButton;

    @FXML
    private Button prestigeButton;

    @FXML
    private Label prestigePointsLabel;

    @FXML
    private Label vertexMultiplierLabel;

    @FXML
    private Button buyVertexMultiplierButton;

    @FXML
    private Label lifetimeCurrencyLabel;

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
        updateDisplay(gameState.getCurrency(), gameState.getActiveShape(), gameState.getPrestigeLevel());
    }

    public void updateDisplay(double currency, Shape activeShape, int prestigeLevel) {
        // update currency
        currencyLabel.setText(NumberFormatter.formatCurrencyWithLabel(currency));

        // update production rate
        double production = activeShape.getCurrentProductionRate() * gameState.getPrestigeBonus();
        productionLabel.setText(NumberFormatter.formatProductionRate(production));

        // update shape info
        shapeLabel.setText(activeShape.getDisplayName() + " Level " + activeShape.getLevel());

        // update upgrade cost
        double nextCost = activeShape.getNextUpgradeCost();
        upgradeCostLabel.setText("Next: " + NumberFormatter.formatCurrency(nextCost) + " Nusians");

        // enable/disable upgrade button
        upgradeButton.setDisable(currency < nextCost);

        // update prestige button
        double bonus = (prestigeLevel + 1) * 5.0;
        prestigeButton.setText("Prestige (+" + bonus + "%) Level " + prestigeLevel);

        // update prestige currency display
        double prestigePoints = gameState.getPrestigePoints();
        prestigePointsLabel.setText(NumberFormatter.formatCurrencyWithLabel(prestigePoints) + " Prestige");

        // update vertex multiplier display
        PrestigeUpgrades upgrades = gameState.getPrestigeUpgrades();
        vertexMultiplierLabel.setText(String.format("Vertex Multiplier: x%.2f", upgrades.getVertexMultiplier()));

        // update vertex upgrade button
        double vertexUpgradeCost = upgrades.getVertexMultiplierCost();
        buyVertexMultiplierButton.setText("Buy Vertex x1.1 (" + NumberFormatter.formatCurrency(vertexUpgradeCost) + ")");
        buyVertexMultiplierButton.setDisable(prestigePoints < vertexUpgradeCost);

        // update lifetime currency
        lifetimeCurrencyLabel.setText("Lifetime: " + NumberFormatter.formatCurrencyWithLabel(gameState.getLifetimeCurrencyEarned()));
    }

    @FXML
    protected void onUpgradeClick() {
        if (gameState.getCurrency() >= gameState.getActiveShape().getNextUpgradeCost()) {
            gameState.upgradeShape();
            gameState.save();
        }
    }

    @FXML
    protected void onPrestigeClick() {
        gameState.prestige();
        gameState.save();
        updateDisplay(gameState.getCurrency(), gameState.getActiveShape(), gameState.getPrestigeLevel());
    }

    @FXML
    protected void onBuyVertexMultiplierClick() {
        if (gameState.purchaseVertexMultiplier()) {
            gameState.save();
            updateDisplay(gameState.getCurrency(), gameState.getActiveShape(), gameState.getPrestigeLevel());
        }
    }
}
