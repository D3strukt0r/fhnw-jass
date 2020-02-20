package org.orbitrondev.jass.client.Controller;

import javafx.application.Platform;
import javafx.concurrent.Worker;
import javafx.stage.Stage;
import org.orbitrondev.jass.client.Model.ServerConnectionModel;
import org.orbitrondev.jass.client.Model.SplashModel;
import org.orbitrondev.jass.client.View.ServerConnectionView;
import org.orbitrondev.jass.client.View.SplashView;
import org.orbitrondev.jass.client.MVC.Controller;

/**
 * The controller for the splash view.
 *
 * @author Brad Richards
 */
public class SplashController extends Controller<SplashModel, SplashView> {
    /**
     * Initializes all event listeners for the view.
     *
     * @since 0.0.1
     */
    public SplashController(SplashModel model, SplashView view) {
        super(model, view);

        view.progress.progressProperty().bind(model.initializer.progressProperty());
        model.initializer.stateProperty().addListener((o, oldValue, newValue) -> {
            if (newValue == Worker.State.SUCCEEDED) {
                // If already logged in go to the game directly, if at least connected, go to login screen, otherwise
                // to server connection
                if (model.isLoggedIn()) {
                    ControllerHelper.switchToDashboardWindow();
                } else if (model.isConnected()) {
                    ControllerHelper.switchToLoginWindow();
                } else {
                    ControllerHelper.switchToServerConnectionWindow();
                }
                Platform.runLater(view::stop);
            }
        });
    }
}
