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
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import jass.client.entity.LoginEntity;
import jass.client.eventlistener.DisconnectEventListener;
import jass.client.mvc.Controller;
import jass.client.message.Register;
import jass.client.message.Login;
import jass.client.util.I18nUtil;
import jass.client.util.SocketUtil;
import jass.client.util.WindowUtil;
import jass.client.util.ViewUtil;
import jass.client.view.RegisterView;
import jass.lib.message.RegisterData;
import jass.lib.message.LoginData;
import jass.lib.servicelocator.ServiceLocator;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * The controller for the server connection view.
 *
 * @author Manuele Vaccari
 * @version %I%, %G%
 * @since 0.0.1
 */
public final class RegisterController extends Controller implements DisconnectEventListener {
    /**
     * The view.
     */
    private RegisterView view;

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
     * The username text field.
     */
    @FXML
    private JFXTextField username;

    /**
     * The password field.
     */
    @FXML
    private JFXPasswordField password;

    /**
     * The repeat password field.
     */
    @FXML
    private JFXPasswordField repeatPassword;

    /**
     * The "remember me" checkbox.
     */
    @FXML
    private JFXCheckBox connectAutomatically;

    /**
     * The register button.
     */
    @FXML
    private JFXButton register;

    /**
     * The login button.
     */
    @FXML
    private JFXButton login;

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        /*
         * Register oneself for disconnect events
         */
        SocketUtil socket = (SocketUtil) ServiceLocator.get("backend");
        if (socket != null) { // Not necessary but keeps IDE happy
            socket.addDisconnectListener(this);
        }

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

        username.promptTextProperty().bind(I18nUtil.createStringBinding(username.getPromptText()));
        password.promptTextProperty().bind(I18nUtil.createStringBinding(password.getPromptText()));
        repeatPassword.promptTextProperty().bind(I18nUtil.createStringBinding(repeatPassword.getPromptText()));

        connectAutomatically.textProperty().bind(I18nUtil.createStringBinding(connectAutomatically.getText()));

        register.textProperty().bind(I18nUtil.createStringBinding(register.getText()));
        login.textProperty().bind(I18nUtil.createStringBinding(login.getText()));

        /*
         * Disable/Enable the Connect button depending on if the inputs are valid
         */
        AtomicBoolean usernameValid = new AtomicBoolean(false);
        AtomicBoolean passwordValid = new AtomicBoolean(false);
        AtomicBoolean repeatPasswordValid = new AtomicBoolean(false);
        Runnable updateButtonClickable = () -> register.setDisable(!usernameValid.get() || !passwordValid.get() || !repeatPasswordValid.get());
        username.textProperty().addListener((o, oldVal, newVal) -> {
            if (!oldVal.equals(newVal)) {
                usernameValid.set(username.validate());
                updateButtonClickable.run();
            }
        });
        password.textProperty().addListener((o, oldVal, newVal) -> {
            if (!oldVal.equals(newVal)) {
                passwordValid.set(password.validate());
                updateButtonClickable.run();
            }
        });
        repeatPassword.textProperty().addListener((o, oldVal, newVal) -> {
            if (!oldVal.equals(newVal)) {
                repeatPasswordValid.set(repeatPassword.validate());
                updateButtonClickable.run();
            }
        });

        /*
         * Validate input fields
         */
        username.getValidators().addAll(
            ViewUtil.useRequiredValidator("gui.register.username.empty")
        );
        password.getValidators().addAll(
            ViewUtil.useRequiredValidator("gui.register.password.empty")
        );
        repeatPassword.getValidators().addAll(
            ViewUtil.useRequiredValidator("gui.register.repeatPassword.empty"),
            ViewUtil.useIsSameValidator(password, "gui.register.repeatPassword.notSame")
        );
    }

    /**
     * Disables all the input fields in the view.
     *
     * @since 0.0.1
     */
    public void disableInputs() {
        username.setDisable(true);
        password.setDisable(true);
        connectAutomatically.setDisable(true);
    }

    /**
     * Disables all the form fields in the view.
     *
     * @since 0.0.1
     */
    public void disableAll() {
        disableInputs();
        login.setDisable(true);
        register.setDisable(true);
    }

    /**
     * Enables all the input fields in the view.
     *
     * @since 0.0.1
     */
    public void enableInputs() {
        username.setDisable(false);
        password.setDisable(false);
        connectAutomatically.setDisable(false);
    }

    /**
     * Enables all the form fields in the view.
     *
     * @since 0.0.1
     */
    public void enableAll() {
        enableInputs();
        login.setDisable(false);
        register.setDisable(false);
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
                // TODO: This keeps the window size even after switching to e.g. login
                //view.getStage().setHeight(view.getStage().getHeight() + 30);
                errorMessage.setPrefHeight(40);
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
        SocketUtil socket = (SocketUtil) ServiceLocator.get("backend");
        if (socket != null) { // Not necessary but keeps IDE happy
            socket.close();
        }
        ServiceLocator.remove("backend");
        WindowUtil.switchToServerConnectionWindow();
    }

    /**
     * Shuts down the application.
     */
    @FXML
    private void clickOnExit() {
        Platform.exit();
    }

    /**
     * Handles the click on the register button. Inputs should already be
     * checked. This will send it to the server, and update local values if
     * successful.
     *
     * @since 0.0.1
     */
    @FXML
    private void clickOnRegister() {
        // Disable everything to prevent something while working on the data
        disableAll();

        // Connection would freeze window (and the animations) so do it in a different thread.
        new Thread(() -> {
            LoginEntity login = new LoginEntity(username.getText(), password.getText());

            SocketUtil backend = (SocketUtil) ServiceLocator.get("backend");
            Register registerMsg = new Register(new RegisterData(login.getUsername(), login.getPassword()));

            // Try sending the register command.
            if (registerMsg.process(backend)) {
                Login loginMsg = new Login(new LoginData(login.getUsername(), login.getPassword()));

                // If registered, try logging in now.
                if (loginMsg.process(backend)) {
                    login.setToken(loginMsg.getToken());
                    ServiceLocator.add(login);
                    WindowUtil.switchToLobbyWindow();
                    Platform.runLater(() -> this.login.getScene().getWindow().hide()); // Dashboard is still MVC
                } else {
                    enableAll();
                    setErrorMessage("gui.login.login.failed");
                }
            } else {
                enableAll();
                setErrorMessage("gui.register.register.failed");
            }
        }).start();
    }

    /**
     * @return Returns the register button
     */
    public JFXButton getRegister() {
        return register;
    }

    /**
     * After clicking on login, switch to the login window.
     */
    @FXML
    private void clickOnLogin() {
        WindowUtil.switchToLoginWindow();
    }

    @Override
    public void onDisconnectEvent() {
        ServiceLocator.remove("backend");
        WindowUtil.switchToServerConnectionWindow();
    }

    /**
     * @param view The view.
     */
    public void setView(final RegisterView view) {
        this.view = view;
    }
}