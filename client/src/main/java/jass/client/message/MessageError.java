/*
 * fhnw-jass is jass game programmed in java for a school project.
 * Copyright (C) 2020 Manuele Vaccari & Victor Hargrave & Thomas Weber & Sasa
 * Trajkova
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

package jass.client.message;

import jass.client.util.SocketUtil;
import jass.lib.message.MessageData;
import jass.lib.message.MessageErrorData;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Login to the server.
 *
 * @author Manuele Vaccari
 * @version %I%, %G%
 * @since 1.0.0
 */
public final class MessageError extends Message {
    /**
     * The data of the message.
     */
    private final MessageErrorData data;

    /**
     * @param rawData The data (still not casted)
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    public MessageError(final MessageData rawData) {
        super(rawData);
        data = (MessageErrorData) rawData;
    }

    /**
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    @Override
    public boolean process(final SocketUtil socket) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Oops, the server sent an error message!");

            switch (data.getErrorMessage()) {
                case INVALID_COMMAND:
                    alert.setContentText("The client sent a command unknown to the server");
                    break;
                default:
                    alert.setContentText("Unknown error message received.");
                    break;
            }
            alert.setContentText("Oops, there was an error!");

            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(getClass().getResource("/images/icon.png").toString()));
            alert.showAndWait();
        });
        return true;
    }
}
