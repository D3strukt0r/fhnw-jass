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
import org.orbitrondev.jass.client.Message.Login;
import org.orbitrondev.jass.client.Model.LoginModel;
import org.orbitrondev.jass.client.Utils.BackendUtil;
import org.orbitrondev.jass.client.View.ViewHelper;
import org.orbitrondev.jass.client.View.LoginView;
import org.orbitrondev.jass.lib.MVC.Controller;
import org.orbitrondev.jass.lib.Message.LoginData;
import org.orbitrondev.jass.lib.ServiceLocator.ServiceLocator;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * The controller for the login view.
 *
 * @author Manuele Vaccari
 * @version %I%, %G%
 * @since 0.0.1
 */
public class LoginController extends Controller<LoginModel, LoginView> {
    /**
     * Initializes all event listeners for the view.
     *
     * @since 0.0.1
     */
    protected LoginController(LoginModel model, LoginView view) {
        super(model, view);

        // Register ourselves to listen for button clicks
        view.getBtnLogin().setOnAction(event -> clickOnLogin());
        view.getBtnRegister().setOnAction(event -> ControllerHelper.switchToRegisterWindow(view));

        // Disable/Enable the login button depending on if the inputs are valid
        AtomicBoolean usernameValid = new AtomicBoolean(false);
        AtomicBoolean passwordValid = new AtomicBoolean(false);
        Runnable updateButtonClickable = () -> {
            if (!usernameValid.get() || !passwordValid.get()) {
                view.getBtnLogin().setDisable(true);
            } else {
                view.getBtnLogin().setDisable(false);
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
        view.getConnectAutomatically().setDisable(true);
    }

    /**
     * Disables all the form fields in the view.
     *
     * @since 0.0.1
     */
    public void disableAll() {
        disableInputs();
        view.getBtnLogin().setDisable(true);
        view.getBtnRegister().setDisable(true);
    }

    /**
     * Enables all the input fields in the view.
     *
     * @since 0.0.1
     */
    public void enableInputs() {
        view.getUsername().setDisable(false);
        view.getPassword().setDisable(false);
        view.getConnectAutomatically().setDisable(false);
    }

    /**
     * Enables all the form fields in the view.
     *
     * @since 0.0.1
     */
    public void enableAll() {
        enableInputs();
        view.getBtnLogin().setDisable(false);
        view.getBtnRegister().setDisable(false);
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
     * Handles the click on the login button. Inputs should already be checked. This will send it to the server, and
     * update local values if successful.
     *
     * @since 0.0.1
     */
    public void clickOnLogin() {
        // Disable everything to prevent something while working on the data
        disableAll();

        // Connection would freeze window (and the animations) so do it in a different thread.
        new Thread(() -> {
            LoginEntity login = new LoginEntity(
                view.getUsername().getText(),
                view.getPassword().getText(),
                view.getConnectAutomatically().isSelected()
            );
            BackendUtil backend = (BackendUtil) ServiceLocator.get("backend");
            Login loginMsg = new Login(new LoginData(login.getUsername(), login.getPassword()));

            // Send the login request to the server. Update locally if successful.
            if (loginMsg.process(backend)) {
                login.setToken(loginMsg.getToken());
                ControllerHelper.switchToDashboardWindow(view);
            } else {
                enableAll();
                setErrorMessage("gui.login.loginFailed");
            }
        }).start();
    }
}
