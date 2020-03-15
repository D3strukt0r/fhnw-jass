/*
 * fhnw-jass is jass game programmed in java for a school project.
 * Copyright (C) 2020 Manuele Vaccari
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package jass.client.view;

import jass.client.mvc.View;
import jass.client.model.DashboardModel;
import jass.client.utils.I18nUtil;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * The dashboard view.
 *
 * @author Manuele Vaccari
 * @version %I%, %G%
 * @since 0.0.1
 */
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
        Text text = new Text("Here will be the game...");
        root.getChildren().addAll(text);
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/css/app.css").toExternalForm());
        return scene;
    }
}
