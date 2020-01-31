package org.orbitrondev.jass.client.View;

import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.orbitrondev.jass.client.Model.DashboardModel;
import org.orbitrondev.jass.client.Utils.I18nUtil;
import org.orbitrondev.jass.lib.MVC.View;

public class DashboardView extends View<DashboardModel> {
    public DashboardView(Stage stage, DashboardModel model) {
        super(stage, model);
        stage.titleProperty().bind(I18nUtil.createStringBinding("gui.dashboard.title"));
        stage.setMinHeight(300);
        stage.setMinWidth(400);
    }

    @Override
    protected Scene create_GUI() {
        VBox root = new VBox();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/css/app.css").toExternalForm());
        return scene;
    }
}
