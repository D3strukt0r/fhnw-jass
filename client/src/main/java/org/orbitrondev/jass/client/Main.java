package org.orbitrondev.jass.client;

import javafx.application.Application;
import javafx.stage.Stage;
import org.orbitrondev.jass.client.Controller.ServerConnectionController;
import org.orbitrondev.jass.client.Controller.SplashController;
import org.orbitrondev.jass.client.Model.ServerConnectionModel;
import org.orbitrondev.jass.client.Model.SplashModel;
import org.orbitrondev.jass.client.View.ServerConnectionView;
import org.orbitrondev.jass.client.View.SplashView;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    private SplashView splashView;

    @Override
    public void start(Stage primaryStage) {
        SplashModel splashModel = new SplashModel();
        splashView = new SplashView(primaryStage, splashModel);
        new SplashController(this, splashModel, splashView);
        splashView.start();
        splashModel.initialize();
    }

    public void startApp() {
        Stage appStage = new Stage();

        ServerConnectionModel model = new ServerConnectionModel();
        ServerConnectionView view = new ServerConnectionView(appStage, model);
        new ServerConnectionController(model, view);

        splashView.stop();
        splashView = null;
        view.start();
    }
}
