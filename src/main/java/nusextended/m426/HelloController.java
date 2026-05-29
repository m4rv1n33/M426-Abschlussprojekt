package nusextended.m426;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

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
        shapeLabel.setText(activeShape.getType().getDisplayName() + " Level " + activeShape.getLevel());

        // update upgrade cost
        double nextCost = activeShape.getNextUpgradeCost();
        upgradeCostLabel.setText("Next: " + NumberFormatter.formatCurrency(nextCost) + " Nusians");

        // enable/disable upgrade button
        upgradeButton.setDisable(currency < nextCost);

        // update prestige button
        double bonus = (prestigeLevel + 1) * 5.0;
        prestigeButton.setText("Prestige (+" + bonus + "%) Level " + prestigeLevel);
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
}
