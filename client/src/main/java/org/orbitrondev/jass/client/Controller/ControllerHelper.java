package org.orbitrondev.jass.client.Controller;

import javafx.application.Platform;
import javafx.stage.Stage;
import org.orbitrondev.jass.client.Model.DashboardModel;
import org.orbitrondev.jass.client.Model.LoginModel;
import org.orbitrondev.jass.client.Model.RegisterModel;
import org.orbitrondev.jass.client.Model.ServerConnectionModel;
import org.orbitrondev.jass.client.View.DashboardView;
import org.orbitrondev.jass.client.View.LoginView;
import org.orbitrondev.jass.client.View.RegisterView;
import org.orbitrondev.jass.client.View.ServerConnectionView;
import org.orbitrondev.jass.lib.MVC.View;

public class ControllerHelper {
    static void switchToServerConnectionWindow(final View<?> oldView) {
        Platform.runLater(() -> {
            Stage appStage = new Stage();
            ServerConnectionModel model = new ServerConnectionModel();
            ServerConnectionView newView = new ServerConnectionView(appStage, model);
            new ServerConnectionController(model, newView);

            oldView.stop();
            newView.start();
        });
    }

    static void switchToDashboardWindow(final View<?> oldView) {
        Platform.runLater(() -> {
            Stage appStage = new Stage();
            DashboardModel model = new DashboardModel();
            DashboardView newView = new DashboardView(appStage, model);
            new DashboardController(model, newView);

            oldView.stop();
            newView.start();
        });
    }

    static void switchToLoginWindow(final View<?> oldView) {
        Platform.runLater(() -> {
            Stage appStage = new Stage();
            LoginModel model = new LoginModel();
            LoginView newView = new LoginView(appStage, model);
            new LoginController(model, newView);

            oldView.stop();
            newView.start();
        });
    }

    static void switchToRegisterWindow(final View<?> oldView) {
        Platform.runLater(() -> {
            Stage appStage = new Stage();
            RegisterModel model = new RegisterModel();
            RegisterView newView = new RegisterView(appStage, model);
            new RegisterController(model, newView);

            oldView.stop();
            newView.start();
        });
    }
}
