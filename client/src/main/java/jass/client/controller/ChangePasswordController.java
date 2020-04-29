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

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import jass.client.entity.LoginEntity;
import jass.client.mvc.Controller;
import jass.client.message.ChangePassword;
import jass.client.util.I18nUtil;
import jass.client.util.SocketUtil;
import jass.client.view.ChangePasswordView;
import jass.client.view.LobbyView;
import jass.client.view.ServerConnectionView;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import jass.client.util.WindowUtil;
import jass.client.util.ViewUtil;
import jass.lib.message.ChangePasswordData;
import jass.lib.servicelocator.ServiceLocator;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * The controller for the change password view.
 *
 * @author Manuele Vaccari
 * @version %I%, %G%
 * @since 0.0.1
 */
public final class ChangePasswordController extends Controller {
    /**
     * The view.
     */
    private ChangePasswordView view;

    /**
     * The "File" element.
     */
    @FXML
    private Menu mFile;

    /**
     * The "File -> Change Language" element.
     */
    @FXML
    private Menu mFileChangeLanguage;

    /**
     * The "File -> Disconnect" element.
     */
    @FXML
    private MenuItem mFileDisconnect;

    /**
     * The "File -> Exit" element.
     */
    @FXML
    private MenuItem mFileExit;

    /**
     * The "Edit" element.
     */
    @FXML
    private Menu mEdit;

    /**
     * The "Edit -> Delete" element.
     */
    @FXML
    private MenuItem mEditDelete;

    /**
     * The "Help" element.
     */
    @FXML
    private Menu mHelp;

    /**
     * The "Help -> About" element.
     */
    @FXML
    private MenuItem mHelpAbout;

    /**
     * The navbar.
     */
    @FXML
    private Text navbar;

    /**
     * The error message.
     */
    @FXML
    private VBox errorMessage;

    /**
     * The old password field.
     */
    @FXML
    private JFXPasswordField oldPassword;

    /**
     * The new password field.
     */
    @FXML
    private JFXPasswordField newPassword;

    /**
     * The repeat new password field.
     */
    @FXML
    private JFXPasswordField repeatNewPassword;

    /**
     * The change button.
     */
    @FXML
    private JFXButton change;

    /**
     * The cancel button.
     */
    @FXML
    private JFXButton cancel;

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        /*
         * Bind all texts
         */
        mFile.textProperty().bind(I18nUtil.createStringBinding(mFile.getText()));
        mFileChangeLanguage.textProperty().bind(I18nUtil.createStringBinding(mFileChangeLanguage.getText()));
        ViewUtil.useLanguageMenuContent(mFileChangeLanguage);
        mFileDisconnect.textProperty().bind(I18nUtil.createStringBinding(mFileDisconnect.getText()));
        mFileExit.textProperty().bind(I18nUtil.createStringBinding(mFileExit.getText()));
        mFileExit.setAccelerator(KeyCombination.keyCombination("Alt+F4"));

        mEdit.textProperty().bind(I18nUtil.createStringBinding(mEdit.getText()));
        mEditDelete.textProperty().bind(I18nUtil.createStringBinding(mEditDelete.getText()));

        mHelp.textProperty().bind(I18nUtil.createStringBinding(mHelp.getText()));
        mHelpAbout.textProperty().bind(I18nUtil.createStringBinding(mHelpAbout.getText()));

        navbar.textProperty().bind(I18nUtil.createStringBinding(navbar.getText()));

        oldPassword.promptTextProperty().bind(I18nUtil.createStringBinding(oldPassword.getPromptText()));
        newPassword.promptTextProperty().bind(I18nUtil.createStringBinding(newPassword.getPromptText()));
        repeatNewPassword.promptTextProperty().bind(I18nUtil.createStringBinding(repeatNewPassword.getPromptText()));

        change.textProperty().bind(I18nUtil.createStringBinding(change.getText()));
        cancel.textProperty().bind(I18nUtil.createStringBinding(cancel.getText()));

        /*
         * Disable/Enable the "Change"-button depending on if the inputs are valid
         */
        AtomicBoolean oldPasswordValid = new AtomicBoolean(false);
        AtomicBoolean newPasswordValid = new AtomicBoolean(false);
        AtomicBoolean repeatNewPasswordValid = new AtomicBoolean(false);
        Runnable updateButtonClickable = () -> change.setDisable(!oldPasswordValid.get() || !newPasswordValid.get() || !repeatNewPasswordValid.get());
        oldPassword.textProperty().addListener((o, oldVal, newVal) -> {
            if (!oldVal.equals(newVal)) {
                oldPasswordValid.set(oldPassword.validate());
                updateButtonClickable.run();
            }
        });
        newPassword.textProperty().addListener((o, oldVal, newVal) -> {
            if (!oldVal.equals(newVal)) {
                newPasswordValid.set(newPassword.validate());
                updateButtonClickable.run();
            }
        });
        repeatNewPassword.textProperty().addListener((o, oldVal, newVal) -> {
            if (!oldVal.equals(newVal)) {
                repeatNewPasswordValid.set(repeatNewPassword.validate());
                updateButtonClickable.run();
            }
        });

        /*
         * Validate input fields
         */
        oldPassword.getValidators().addAll(
            ViewUtil.useRequiredValidator("gui.changePassword.oldPassword.empty")
        );
        newPassword.getValidators().addAll(
            ViewUtil.useRequiredValidator("gui.changePassword.newPassword.empty")
        );
        repeatNewPassword.getValidators().addAll(
            ViewUtil.useRequiredValidator("gui.changePassword.repeatNewPassword.empty"),
            ViewUtil.useIsSameValidator(newPassword, "gui.changePassword.repeatNewPassword.notSame")
        );
    }

    /**
     * Disables all the input fields in the view.
     *
     * @since 0.0.1
     */
    public void disableInputs() {
        oldPassword.setDisable(true);
        newPassword.setDisable(true);
        repeatNewPassword.setDisable(true);
    }

    /**
     * Disables all the form fields in the view.
     *
     * @since 0.0.1
     */
    public void disableAll() {
        disableInputs();
        change.setDisable(true);
        cancel.setDisable(true);
    }

    /**
     * Enables all the input fields in the view.
     *
     * @since 0.0.1
     */
    public void enableInputs() {
        oldPassword.setDisable(false);
        newPassword.setDisable(false);
        repeatNewPassword.setDisable(false);
    }

    /**
     * Enables all the form fields in the view.
     *
     * @since 0.0.1
     */
    public void enableAll() {
        enableInputs();
        change.setDisable(false);
        cancel.setDisable(false);
    }

    /**
     * As the view contains an error message field, this updates the text and
     * the window appropriately.
     *
     * @param translatorKey The key of the translation.
     *
     * @since 0.0.1
     */
    public void setErrorMessage(final String translatorKey) {
        Platform.runLater(() -> {
            if (errorMessage.getChildren().size() == 0) {
                // Make window larger, so it doesn't become crammed, only if we haven't done so yet
                errorMessage.setPrefHeight(50);
            }
            Text text = ViewUtil.useText(translatorKey);
            text.setFill(Color.RED);
            errorMessage.getChildren().clear();
            errorMessage.getChildren().addAll(text, ViewUtil.useSpacer(20));
        });
    }

    /**
     * Disconnect from the server and returns to the server connection window.
     */
    @FXML
    private void clickOnDisconnect() {
        ServiceLocator.remove(LoginEntity.class);
        SocketUtil socket = ServiceLocator.get(SocketUtil.class);
        if (socket != null) { // Not necessary but keeps IDE happy
            socket.close();
        }
        ServiceLocator.remove(SocketUtil.class);
        WindowUtil.switchTo(view, ServerConnectionView.class);
    }

    /**
     * Shuts down the application.
     */
    @FXML
    private void clickOnExit() {
        Platform.exit();
    }

    /**
     * Handles the click on the change button. Inputs should already be checked.
     * This will send it to the server, and update local values if successful.
     *
     * @since 0.0.1
     */
    @FXML
    public void clickOnChange() {
        // Disable everything to prevent changing data while working on the data
        disableAll();

        // Connection would freeze window (and the animations) so do it in a different thread.
        new Thread(() -> {
            LoginEntity login = ServiceLocator.get(LoginEntity.class);
            assert login != null;
            LoginEntity newLogin = (new LoginEntity())
                .setUsername(login.getUsername())
                .setPassword(newPassword.getText())
                .setToken(login.getToken());

            SocketUtil backend = ServiceLocator.get(SocketUtil.class);
            assert backend != null;
            ChangePassword changePasswordMsg = new ChangePassword(new ChangePasswordData(login.getToken(), newLogin.getPassword()));

            // Send the change password request to the server. Update locally if successful.
            if (changePasswordMsg.process(backend)) {
                ServiceLocator.remove(LoginEntity.class);
                ServiceLocator.add(newLogin);
                WindowUtil.switchTo(view, LobbyView.class);
            } else {
                enableAll();
                setErrorMessage("gui.changePassword.changeFailed");
            }
        }).start();
    }

    /**
     * After clicking on cancel, return to the lobby.
     */
    @FXML
    public void clickOnCancel() {
        WindowUtil.switchTo(view, LobbyView.class);
    }

    /**
     * @param view The view.
     */
    public void setView(final ChangePasswordView view) {
        this.view = view;
    }

    /**
     * @return Returns the change button
     */
    public JFXButton getChange() {
        return change;
    }
}
