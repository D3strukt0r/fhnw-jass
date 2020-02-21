package org.orbitrondev.jass.client.Controller;

import com.jfoenix.controls.JFXProgressBar;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import org.orbitrondev.jass.client.FXML.FXMLController;
import org.orbitrondev.jass.client.Model.SplashModel;
import org.orbitrondev.jass.client.View.SplashView;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * The controller for the splash view.
 *
 * @author Brad Richards
 */
public class SplashController extends FXMLController {
    private SplashModel model;
    private SplashView view;

    @FXML
    private JFXProgressBar loadingProgress;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        model = new SplashModel();

        loadingProgress.progressProperty().bind(model.initializer.progressProperty());
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
                view.getStage().hide();
            }
        });

        model.initialize();
    }

    public void setView(SplashView view) {
        this.view = view;
    }
}
