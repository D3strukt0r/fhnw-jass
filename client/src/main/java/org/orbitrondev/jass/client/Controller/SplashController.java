package org.orbitrondev.jass.client.Controller;

import javafx.concurrent.Worker;
import org.orbitrondev.jass.client.Model.SplashModel;
import org.orbitrondev.jass.client.View.SplashView;
import org.orbitrondev.jass.lib.MVC.Controller;

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
                ControllerHelper.switchToServerConnectionWindow(view);
            }
        });
    }
}
