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

import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import org.orbitrondev.jass.client.Entity.LoginEntity;
import org.orbitrondev.jass.client.Message.CreateLogin;
import org.orbitrondev.jass.client.Message.Login;
import org.orbitrondev.jass.client.Model.RegisterModel;
import org.orbitrondev.jass.client.Utils.SocketUtil;
import org.orbitrondev.jass.client.View.ViewHelper;
import org.orbitrondev.jass.client.View.RegisterView;
import org.orbitrondev.jass.client.MVC.Controller;
import org.orbitrondev.jass.lib.Message.CreateLoginData;
import org.orbitrondev.jass.lib.Message.LoginData;
import org.orbitrondev.jass.lib.ServiceLocator.ServiceLocator;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * The controller for the register view.
 *
 * @author Manuele Vaccari
 * @version %I%, %G%
 * @since 0.0.1
 */
public class RegisterController extends Controller<RegisterModel, RegisterView> {
    /**
     * Initializes all event listeners for the view.
     *
     * @since 0.0.1
     */
    protected RegisterController(RegisterModel model, RegisterView view) {
        super(model, view);

        // Register ourselves to listen for button clicks
        view.getBtnRegister().setOnAction(event -> clickOnRegister());
        view.getBtnLogin().setOnAction(event -> ControllerHelper.switchToLoginWindow(view));

        // Disable/Enable the login button depending on if the inputs are valid
        AtomicBoolean usernameValid = new AtomicBoolean(false);
        AtomicBoolean passwordValid = new AtomicBoolean(false);
        AtomicBoolean repeatPasswordValid = new AtomicBoolean(false);
        Runnable updateButtonClickable = () -> {
            if (!usernameValid.get() || !passwordValid.get() || !repeatPasswordValid.get()) {
                view.getBtnRegister().setDisable(true);
            } else {
                view.getBtnRegister().setDisable(false);
            }
        };
        view.getUsername().textProperty().addListener((o, oldVal, newVal) -> {
            if (!oldVal.equals(newVal)) {
                usernameValid.set(view.getUsername().validate());
                updateButtonClickable.run();
            }
        });
        view.getPassword().textProperty().addListener((o, oldVal, newVal) -> {
            if (!oldVal.equals(newVal)) {
                passwordValid.set(view.getPassword().validate());
                updateButtonClickable.run();
            }
        });
        view.getRepeatPassword().textProperty().addListener((o, oldVal, newVal) -> {
            if (!oldVal.equals(newVal)) {
                repeatPasswordValid.set(view.getRepeatPassword().validate());
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
        view.getUsername().setDisable(true);
        view.getPassword().setDisable(true);
        view.getRepeatPassword().setDisable(true);
    }

    /**
     * Disables all the form fields in the view.
     *
     * @since 0.0.1
     */
    public void disableAll() {
        disableInputs();
        view.getBtnRegister().setDisable(true);
        view.getBtnLogin().setDisable(true);
    }

    /**
     * Enables all the input fields in the view.
     *
     * @since 0.0.1
     */
    public void enableInputs() {
        view.getUsername().setDisable(false);
        view.getPassword().setDisable(false);
        view.getRepeatPassword().setDisable(false);
    }

    /**
     * Enables all the form fields in the view.
     *
     * @since 0.0.1
     */
    public void enableAll() {
        enableInputs();
        view.getBtnRegister().setDisable(false);
        view.getBtnLogin().setDisable(false);
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
            Text text = ViewHelper.useText(translatorKey);
            text.setFill(Color.RED);
            view.getErrorMessage().getChildren().clear();
            view.getErrorMessage().getChildren().addAll(text, ViewHelper.useSpacer(20));
        });
    }

    /**
     * Handles the click on the register button. Inputs should already be checked. This will send it to the server, and
     * update local values if successful.
     *
     * @since 0.0.1
     */
    public void clickOnRegister() {
        // Disable everything to prevent something while working on the data
        disableAll();

        // Connection would freeze window (and the animations) so do it in a different thread.
        new Thread(() -> {
            LoginEntity login = new LoginEntity(view.getUsername().getText(), view.getPassword().getText());

            SocketUtil backend = (SocketUtil) ServiceLocator.get("backend");
            CreateLogin createLoginMsg = new CreateLogin(new CreateLoginData(login.getUsername(), login.getPassword()));

            // Try sending the register command.
            if (createLoginMsg.process(backend)) {
                Login loginMsg = new Login(new LoginData(login.getUsername(), login.getPassword()));

                // If registered, try logging in now.
                if (loginMsg.process(backend)) {
                    login.setToken(loginMsg.getToken());
                    ServiceLocator.add(login);
                    ControllerHelper.switchToDashboardWindow(view);
                } else {
                    enableAll();
                    setErrorMessage("gui.login.loginFailed");
                }
            } else {
                enableAll();
                setErrorMessage("gui.register.registerFailed");
            }
        }).start();
    }
}
