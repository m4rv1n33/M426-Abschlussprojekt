package nusextended.m426;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    private GameState gameState;
    private GameEngine gameEngine;

    @Override
    public void start(Stage stage) throws IOException {
        // load game state from file or create new
        gameState = GameState.load();

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        
        // inject game state into controller
        HelloController controller = fxmlLoader.getController();
        controller.setGameState(gameState);

        // start game engine
        gameEngine = new GameEngine(gameState);
        gameEngine.setCurrencyListener(controller::updateDisplay);
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