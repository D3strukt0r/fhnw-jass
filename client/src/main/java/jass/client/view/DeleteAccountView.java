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

import jass.client.controller.DeleteAccountController;
import jass.client.mvc.View;
import jass.client.util.I18nUtil;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The delete account view.
 *
 * @author Manuele Vaccari
 * @version %I%, %G%
 * @since 0.0.1
 */
public final class DeleteAccountView extends View {
    /**
     * @param stage The stage of the window.
     */
    public DeleteAccountView(final Stage stage) {
        super(stage);
        stage.titleProperty().bind(I18nUtil.createStringBinding("gui.deleteAccount.title"));
        stage.setResizable(false);
        stage.setWidth(300);

        // Register ourselves to handle window-closing event
        stage.setOnCloseRequest(event -> Platform.exit());
    }

    @Override
    protected Scene create_GUI() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/delete_account.fxml"));
            Parent root = loader.load();
            DeleteAccountController controller = loader.getController();
            controller.setView(this);

            Scene scene = new Scene(root);
            scene.setOnKeyPressed(event -> {
                // Click the delete button by clicking ENTER
                if (event.getCode() == KeyCode.ENTER) {
                    if (!controller.getDelete().isDisable()) {
                        controller.getDelete().fire();
                    }
                }
            });
            return scene;
        } catch (IOException e) {
            return null;
        }
    }
}
