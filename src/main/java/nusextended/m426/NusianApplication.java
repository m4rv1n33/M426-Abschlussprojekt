package nusextended.m426;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import nusextended.m426.game.GameState;
import nusextended.m426.game.GameEngine;
import nusextended.m426.game.UpgradeStateManager;
import nusextended.m426.game.PrestigeStateManager;

import java.io.IOException;

public class NusianApplication extends Application {
    private GameState gameState;
    private GameEngine gameEngine;

    @Override
    public void start(Stage stage) throws IOException {
        gameState = GameState.load();
        UpgradeStateManager upgradeManager = new UpgradeStateManager(gameState);
        PrestigeStateManager prestigeManager = new PrestigeStateManager(gameState);

        FXMLLoader fxmlLoader = new FXMLLoader(NusianApplication.class.getResource("nusian-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);

        NusianController controller = fxmlLoader.getController();
        controller.setGameState(gameState);
        controller.setUpgradeManager(upgradeManager);
        controller.setPrestigeManager(prestigeManager);

        gameEngine = new GameEngine(gameState, upgradeManager);
        gameEngine.setCurrencyListener(controller::updateCurrencyDisplay);
        gameEngine.start();

        stage.setTitle("Nusian somethingburger");
        stage.setScene(scene);
        stage.setOnCloseRequest(event -> {
            gameState.save();
            gameEngine.stop();
        });
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
