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

package org.orbitrondev.jass.client.FXML;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.orbitrondev.jass.client.Utils.I18nUtil;

import java.io.IOException;

/**
 * The server connection view.
 *
 * @author Manuele Vaccari
 * @version %I%, %G%
 * @since 0.0.1
 */
public class ServerConnectionView extends FXMLView {
    public ServerConnectionView(Stage stage) {
        super(stage);
        stage.titleProperty().bind(I18nUtil.createStringBinding("gui.serverConnection.title"));
        stage.setResizable(false);

        // Register ourselves to handle window-closing event
        stage.setOnCloseRequest(event -> Platform.exit());
    }

    @Override
    protected Scene create_GUI() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/server_connection.fxml"));

            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/css/app.css").toExternalForm());
            return scene;
        } catch (IOException e) {
            return null;
        }
    }
}
