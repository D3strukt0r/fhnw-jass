package org.orbitrondev.jass.client;

import javafx.application.Application;
import javafx.stage.Stage;
import org.orbitrondev.jass.client.Controller.SplashController;
import org.orbitrondev.jass.client.Model.SplashModel;
import org.orbitrondev.jass.client.View.SplashView;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        SplashModel splashModel = new SplashModel();
        SplashView splashView = new SplashView(primaryStage, splashModel);
        new SplashController(splashModel, splashView);
        splashView.start();
        splashModel.initialize();
    }
}
