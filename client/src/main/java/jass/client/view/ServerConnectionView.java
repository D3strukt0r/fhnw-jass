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

import jass.client.controller.ServerConnectionController;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import jass.client.mvc.View;
import jass.client.utils.I18nUtil;

import java.io.IOException;

/**
 * The server connection view.
 *
 * @author Manuele Vaccari
 * @version %I%, %G%
 * @since 0.0.1
 */
public class ServerConnectionView extends View {
    public ServerConnectionView(Stage stage) {
        super(stage);
        stage.titleProperty().bind(I18nUtil.createStringBinding("gui.serverConnection.title"));
        stage.setResizable(false);
        stage.setWidth(scene.getWidth()); // Fixes weird width when returning to server connection windows

        // Register ourselves to handle window-closing event
        stage.setOnCloseRequest(event -> Platform.exit());
    }

    @Override
    protected Scene create_GUI() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/server_connection.fxml"));
            Parent root = loader.load();
            ServerConnectionController controller = loader.getController();
            controller.setView(this);

            Scene scene = new Scene(root);
            scene.setOnKeyPressed(event -> {
                // Click the connect button by clicking ENTER
                if (event.getCode() == KeyCode.ENTER) {
                    if (!controller.getConnect().isDisable()) {
                        controller.getConnect().fire();
                    }
                }
            });
            return scene;
        } catch (IOException e) {
            return null;
        }
    }
}
