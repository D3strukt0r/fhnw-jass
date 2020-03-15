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

package jass.client.controller;

import jass.client.entity.LoginEntity;
import jass.client.mvc.Controller;
import jass.client.message.ChangePassword;
import jass.client.model.ChangePasswordModel;
import jass.client.utils.SocketUtil;
import jass.client.view.ChangePasswordView;
import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import jass.client.utils.WindowUtil;
import jass.client.utils.ViewUtil;
import jass.lib.message.ChangePasswordData;
import jass.lib.servicelocator.ServiceLocator;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * The controller for the change password view.
 *
 * @author Manuele Vaccari
 * @version %I%, %G%
 * @since 0.0.1
 */
public class ChangePasswordController extends Controller<ChangePasswordModel, ChangePasswordView> {
    /**
     * Initializes all event listeners for the view.
     *
     * @since 0.0.1
     */
    public ChangePasswordController(ChangePasswordModel model, ChangePasswordView view) {
        super(model, view);

        // Register ourselves to listen for button clicks
        view.getBtnChange().setOnAction(event -> clickOnChange());
        view.getBtnCancel().setOnAction(event -> {
            WindowUtil.switchToDashboardWindow();
            view.stop();
        });

        // Disable/Enable the login button depending on if the inputs are valid
        AtomicBoolean oldPasswordValid = new AtomicBoolean(false);
        AtomicBoolean newPasswordValid = new AtomicBoolean(false);
        AtomicBoolean repeatNewPasswordValid = new AtomicBoolean(false);
        Runnable updateButtonClickable = () -> {
            if (!oldPasswordValid.get() || !newPasswordValid.get() || !repeatNewPasswordValid.get()) {
                view.getBtnChange().setDisable(true);
            } else {
                view.getBtnChange().setDisable(false);
            }
        };
        view.getOldPassword().textProperty().addListener((o, oldVal, newVal) -> {
            if (!oldVal.equals(newVal)) {
                oldPasswordValid.set(view.getOldPassword().validate());
                updateButtonClickable.run();
            }
        });
        view.getNewPassword().textProperty().addListener((o, oldVal, newVal) -> {
            if (!oldVal.equals(newVal)) {
                newPasswordValid.set(view.getNewPassword().validate());
                updateButtonClickable.run();
            }
        });
        view.getRepeatNewPassword().textProperty().addListener((o, oldVal, newVal) -> {
            if (!oldVal.equals(newVal)) {
                repeatNewPasswordValid.set(view.getRepeatNewPassword().validate());
                updateButtonClickable.run();
            }
        });

        // Register ourselves to handle window-closing event
        view.getStage().setOnCloseRequest(event -> Platform.exit());
    }

    /**
     * Disables all the input fields in the view.
     *
     * @since 0.0.1
     */
    public void disableInputs() {
        view.getOldPassword().setDisable(true);
        view.getNewPassword().setDisable(true);
        view.getRepeatNewPassword().setDisable(true);
    }

    /**
     * Disables all the form fields in the view.
     *
     * @since 0.0.1
     */
    public void disableAll() {
        disableInputs();
        view.getBtnChange().setDisable(true);
        view.getBtnCancel().setDisable(true);
    }

    /**
     * Enables all the input fields in the view.
     *
     * @since 0.0.1
     */
    public void enableInputs() {
        view.getOldPassword().setDisable(false);
        view.getNewPassword().setDisable(false);
        view.getRepeatNewPassword().setDisable(false);
    }

    /**
     * Enables all the form fields in the view.
     *
     * @since 0.0.1
     */
    public void enableAll() {
        enableInputs();
        view.getBtnChange().setDisable(false);
        view.getBtnCancel().setDisable(false);
    }

    /**
     * As the view contains an error message field, this updates the text and the window appropriately.
     *
     * @since 0.0.1
     */
    public void setErrorMessage(String translatorKey) {
        Platform.runLater(() -> {
            if (view.getErrorMessage().getChildren().size() == 0) {
                // Make window larger, so it doesn't become crammed, only if we haven't done so yet
                view.getStage().setHeight(view.getStage().getHeight() + 30);
            }
            Text text = ViewUtil.useText(translatorKey);
            text.setFill(Color.RED);
            view.getErrorMessage().getChildren().clear();
            view.getErrorMessage().getChildren().addAll(text, ViewUtil.useSpacer(20));
        });
    }

    /**
     * Handles the click on the change button. Inputs should already be checked. This will send it to the server, and
     * update local values if successful.
     *
     * @since 0.0.1
     */
    public void clickOnChange() {
        // Disable everything to prevent changing data while working on the data
        disableAll();

        // Connection would freeze window (and the animations) so do it in a different thread.
        new Thread(() -> {
            LoginEntity login = (LoginEntity) ServiceLocator.get("login");
            LoginEntity newLogin = new LoginEntity(login.getUsername(), view.getNewPassword().getText(), login.getToken());

            SocketUtil backend = (SocketUtil) ServiceLocator.get("backend");
            ChangePassword changePasswordMsg = new ChangePassword(new ChangePasswordData(login.getToken(), newLogin.getPassword()));

            // Send the change password request to the server. Update locally if successful.
            if (changePasswordMsg.process(backend)) {
                ServiceLocator.remove("login");
                ServiceLocator.add(newLogin);
                WindowUtil.switchToDashboardWindow();
                view.stop();
            } else {
                enableAll();
                setErrorMessage("gui.changePassword.changeFailed");
            }
        }).start();
    }
}
