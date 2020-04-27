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

import jass.client.controller.RegisterController;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import jass.client.mvc.View;
import jass.client.util.I18nUtil;

import java.io.IOException;

/**
 * The server connection view.
 *
 * @author Manuele Vaccari
 * @version %I%, %G%
 * @since 0.0.1
 */
public final class RegisterView extends View {
    /**
     * @param stage The stage of the window.
     */
    public RegisterView(final Stage stage) {
        super(stage);
        stage.titleProperty().bind(I18nUtil.createStringBinding("gui.login.title"));
        stage.setResizable(false);
        stage.setWidth(350);

        // Register ourselves to handle window-closing event
        stage.setOnCloseRequest(event -> Platform.exit());
    }

    @Override
    protected Scene createGUI() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/register.fxml"));
            Parent root = loader.load();
            RegisterController controller = loader.getController();
            controller.setView(this);

            Scene scene = new Scene(root);
            scene.setOnKeyPressed(event -> {
                // Click the register button by clicking ENTER
                if (event.getCode() == KeyCode.ENTER) {
                    if (!controller.getRegister().isDisable()) {
                        controller.getRegister().fire();
                    }
                }
            });
            return scene;
        } catch (IOException e) {
            return null;
        }
    }
}
