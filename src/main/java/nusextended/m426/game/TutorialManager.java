package nusextended.m426.game;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

public class TutorialManager {
    private static final String TUTORIAL_FILE = "/tutorial.json";

    // Reihenfolge, in der die Tutorial-Schritte angezeigt werden
    private static final String[] STEP_ORDER = {
        "tutorial-0-welcome",
        "tutorial-1-production",
        "tutorial-2-upgrades",
        "tutorial-3-prestige",
        "tutorial-4-prestige-upgrades"
    };

    private final Map<String, String> texts;

    public TutorialManager() {
        this.texts = loadTexts();
    }

    private Map<String, String> loadTexts() {
        try (Reader reader = new InputStreamReader(
                getClass().getResourceAsStream(TUTORIAL_FILE), StandardCharsets.UTF_8)) {
            Type type = new TypeToken<LinkedHashMap<String, String>>() { }.getType();
            return new Gson().fromJson(reader, type);
        } catch (Exception e) {
            System.err.println("[TutorialManager] Could not load tutorial.json: " + e.getMessage());
            return Map.of();
        }
    }

    /** Zeigt alle Tutorial-Schritte nacheinander als Popup an. */
    public void showTutorial() {
        for (String stepId : STEP_ORDER) {
            String text = texts.get(stepId);
            if (text == null) {
                continue;
            }
            showStep(text);
        }
    }

    private void showStep(String text) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Tutorial");
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.getButtonTypes().setAll(ButtonType.OK);
        alert.showAndWait();
    }
}