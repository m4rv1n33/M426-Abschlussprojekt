package nusextended.m426;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import nusextended.m426.game.GameEngine;
import nusextended.m426.game.GameState;
import nusextended.m426.game.NumberFormatter;
import nusextended.m426.game.PrestigeStateManager;
import nusextended.m426.game.UpgradeStateManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

// Issue #54: boots JavaFX, loads the real FXML, wires it like NusianApplication and checks the UI tracks the backend.
@DisplayName("Frontend-backend integration smoke test (#54)")
class FrontendBackendSmokeTest {

    private static boolean toolkitStarted;

    @BeforeAll
    static void startToolkit() {
        try {
            CountDownLatch latch = new CountDownLatch(1);
            Platform.startup(latch::countDown);
            assertTrue(latch.await(15, TimeUnit.SECONDS), "JavaFX toolkit must start");
            toolkitStarted = true;
        } catch (IllegalStateException alreadyRunning) {
            // already started in this JVM
            toolkitStarted = true;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("Wired FXML + controller + engine produce currency and update the UI display")
    void fullStackSmoke() throws Exception {
        assertTrue(toolkitStarted, "JavaFX toolkit not available");

        AtomicReference<Throwable> failure = new AtomicReference<>();
        AtomicReference<String> displayedCurrency = new AtomicReference<>();
        AtomicReference<Double> backendCurrency = new AtomicReference<>();
        CountDownLatch done = new CountDownLatch(1);

        Platform.runLater(() -> {
            try {
                // wire like NusianApplication.start()
                GameState gameState = new GameState();
                UpgradeStateManager upgradeManager = new UpgradeStateManager(gameState);
                PrestigeStateManager prestigeManager = new PrestigeStateManager(gameState);

                FXMLLoader fxmlLoader =
                        new FXMLLoader(NusianApplication.class.getResource("nusian-view.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
                assertNotNull(scene, "Scene must load from FXML");

                NusianController controller = fxmlLoader.getController();
                assertNotNull(controller, "Controller must be instantiated by the FXMLLoader");
                controller.setGameState(gameState);
                controller.setUpgradeManager(upgradeManager);
                controller.setPrestigeManager(prestigeManager);

                GameEngine engine = new GameEngine(gameState, upgradeManager);
                engine.setCurrencyListener(controller::updateCurrencyDisplay);

                // drive a few engine ticks
                long t = 1_000_000_000L;
                engine.handle(t); // primes lastUpdateTime
                for (int i = 0; i < 5; i++) {
                    engine.handle(t += 1_000_000_000L);
                }

                backendCurrency.set(gameState.getCurrency());
                displayedCurrency.set(controller.currencyDisplay.getText());
            } catch (Throwable ex) {
                failure.set(ex);
            } finally {
                done.countDown();
            }
        });

        assertTrue(done.await(15, TimeUnit.SECONDS), "Smoke run must finish on the FX thread");
        if (failure.get() != null) {
            throw new AssertionError("Frontend-backend smoke run threw on the FX thread", failure.get());
        }

        assertTrue(backendCurrency.get() > 0.0,
                "Engine must have produced currency over the simulated ticks");
        assertEquals(NumberFormatter.formatCurrency(backendCurrency.get()), displayedCurrency.get(),
                "Currency display must reflect the backend currency after the wired ticks");
    }
}
