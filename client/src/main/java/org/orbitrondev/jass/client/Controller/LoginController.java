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

package org.orbitrondev.jass.client.Controller;

import com.jfoenix.controls.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.orbitrondev.jass.client.Entity.LoginEntity;
import org.orbitrondev.jass.client.Entity.LoginRepository;
import org.orbitrondev.jass.client.FXML.FXMLController;
import org.orbitrondev.jass.client.Message.Login;
import org.orbitrondev.jass.client.Utils.DatabaseUtil;
import org.orbitrondev.jass.client.Utils.I18nUtil;
import org.orbitrondev.jass.client.Utils.SocketUtil;
import org.orbitrondev.jass.client.Utils.WindowUtil;
import org.orbitrondev.jass.client.Utils.ViewUtil;
import org.orbitrondev.jass.lib.Message.LoginData;
import org.orbitrondev.jass.lib.ServiceLocator.ServiceLocator;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * The controller for the server connection view.
 *
 * @author Manuele Vaccari
 * @version %I%, %G%
 * @since 0.0.1
 */
public class LoginController extends FXMLController {
    private static final Logger logger = LogManager.getLogger(LoginController.class);

    @FXML
    public Menu mFile;
    @FXML
    public Menu mFileChangeLanguage;
    @FXML
    public MenuItem mFileDisconnect;
    @FXML
    public MenuItem mFileExit;
    @FXML
    public Menu mEdit;
    @FXML
    public MenuItem mEditDelete;
    @FXML
    public Menu mHelp;
    @FXML
    public MenuItem mHelpAbout;

    @FXML
    private Text navbar;
    @FXML
    private VBox errorMessage;
    @FXML
    private JFXTextField username;
    @FXML
    private JFXPasswordField password;
    @FXML
    private JFXCheckBox connectAutomatically;
    @FXML
    private JFXButton login;
    @FXML
    private JFXButton register;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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

        connectAutomatically.textProperty().bind(I18nUtil.createStringBinding(connectAutomatically.getText()));

        login.textProperty().bind(I18nUtil.createStringBinding(login.getText()));
        register.textProperty().bind(I18nUtil.createStringBinding(register.getText()));

        /*
         * Disable/Enable the "Connect"-button depending on if the inputs are valid
         */
        AtomicBoolean usernameValid = new AtomicBoolean(false);
        AtomicBoolean passwordValid = new AtomicBoolean(false);
        Runnable updateButtonClickable = () -> {
            if (!usernameValid.get() || !passwordValid.get()) {
                login.setDisable(true);
            } else {
                login.setDisable(false);
            }
        };
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

        /*
         * Validate input fields
         */
        username.getValidators().addAll(
            ViewUtil.useRequiredValidator("gui.login.username.empty")
        );
        password.getValidators().addAll(
            ViewUtil.useRequiredValidator("gui.login.password.empty")
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
     * As the view contains an error message field, this updates the text and the window appropriately.
     *
     * @since 0.0.1
     */
    public void setErrorMessage(String translatorKey) {
        Platform.runLater(() -> {
            if (errorMessage.getChildren().size() == 0) {
                // Make window larger, so it doesn't become crammed, only if we haven't done so yet
                // TODO: Don't use root, use the stage (view.getStage().setHeight(x))
                //double newHeight = root.getHeight() + 30;
                //root.setMaxHeight(newHeight);
                //root.setPrefHeight(newHeight);
                //root.setMinHeight(newHeight);
                errorMessage.setPrefHeight(50);
            }
            Text text = ViewUtil.useText(translatorKey);
            text.setFill(Color.RED);
            errorMessage.getChildren().clear();
            errorMessage.getChildren().addAll(text, ViewUtil.useSpacer(20));
        });
    }

    @FXML
    private void clickOnDisconnect(ActionEvent event) {
        SocketUtil socket = (SocketUtil) ServiceLocator.get("backend");
        socket.close();
        ServiceLocator.remove("backend");
        WindowUtil.switchToServerConnectionWindow();
    }

    @FXML
    private void clickOnExit(ActionEvent event) {
        Platform.exit();
    }

    /**
     * Handles the click on the login button. Inputs should already be checked. This will send it to the server, and
     * update local values if successful.
     *
     * @since 0.0.1
     */
    @FXML
    private void clickOnLogin(ActionEvent event) {
        // Disable everything to prevent something while working on the data
        disableAll();

        // Connection would freeze window (and the animations) so do it in a different thread.
        new Thread(() -> {
            LoginEntity login = new LoginEntity(
                username.getText(),
                password.getText(),
                connectAutomatically.isSelected()
            );
            SocketUtil backend = (SocketUtil) ServiceLocator.get("backend");
            Login loginMsg = new Login(new LoginData(login.getUsername(), login.getPassword()));

            // Send the login request to the server. Update locally if successful.
            if (loginMsg.process(backend)) {
                login.setToken(loginMsg.getToken());

                // Save the login in the db
                DatabaseUtil db = (DatabaseUtil) ServiceLocator.get("db");
                try {
                    db.getLoginDao().create(login);
                } catch (SQLException e) {
                    logger.error("Couldn't save login data to local database.");
                }

                LoginRepository.setToConnectAutomatically(login); // Make sure it's the only entry
                WindowUtil.switchToDashboardWindow();
                Platform.runLater(() -> this.login.getScene().getWindow().hide()); // Dashboard is still MVC
            } else {
                enableAll();
                setErrorMessage("gui.login.login.failed");
            }
        }).start();
    }

    @FXML
    private void clickOnRegister(ActionEvent event) {
        WindowUtil.switchToRegisterWindow();
    }

    public JFXButton getLogin() {
        return login;
    }
}
