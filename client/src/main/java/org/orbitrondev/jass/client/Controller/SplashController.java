package org.orbitrondev.jass.client.Controller;

import javafx.concurrent.Worker;
import org.orbitrondev.jass.client.Main;
import org.orbitrondev.jass.client.Model.SplashModel;
import org.orbitrondev.jass.client.View.SplashView;
import org.orbitrondev.jass.lib.MVC.Controller;

public class SplashController extends Controller<SplashModel, SplashView> {
    public SplashController(final Main main, SplashModel model, SplashView view) {
        super(model, view);

        view.progress.progressProperty().bind(model.initializer.progressProperty());
        model.initializer.stateProperty().addListener((o, oldValue, newValue) -> {
            if (newValue == Worker.State.SUCCEEDED) main.startApp();
        });
    }
}
