package org.orbitrondev.jass.client.View;

import com.jfoenix.controls.JFXProgressBar;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.orbitrondev.jass.client.Model.SplashModel;
import org.orbitrondev.jass.lib.MVC.View;

public class SplashView extends View<SplashModel> {
    public JFXProgressBar progress;

    public SplashView(Stage stage, SplashModel model) {
        super(stage, model);
        stage.initStyle(StageStyle.TRANSPARENT); // Also undecorated
    }

    @Override
    protected Scene create_GUI() {
        BorderPane root = new BorderPane();
        root.getStyleClass().add("splash");

        HBox iconContainer = new HBox();
        iconContainer.getStyleClass().add("icon-container");
        root.setCenter(iconContainer);

        progress = new JFXProgressBar();
        progress.setMaxWidth(Double.MAX_VALUE);
        root.setBottom(progress);

        Scene scene = new Scene(root, 300, 300, Color.TRANSPARENT);
        scene.getStylesheets().addAll(this.getClass().getResource("/css/splash.css").toExternalForm());

        return scene;
    }
}
