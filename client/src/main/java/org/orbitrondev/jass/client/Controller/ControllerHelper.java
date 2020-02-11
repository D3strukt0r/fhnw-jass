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
import javafx.stage.Stage;
import org.orbitrondev.jass.client.Model.DashboardModel;
import org.orbitrondev.jass.client.Model.LoginModel;
import org.orbitrondev.jass.client.Model.RegisterModel;
import org.orbitrondev.jass.client.Model.ServerConnectionModel;
import org.orbitrondev.jass.client.View.DashboardView;
import org.orbitrondev.jass.client.View.LoginView;
import org.orbitrondev.jass.client.View.RegisterView;
import org.orbitrondev.jass.client.View.ServerConnectionView;
import org.orbitrondev.jass.lib.MVC.View;

/**
 * A helper class for the controllers to switch between windows easily.
 *
 * @author Manuele Vaccari
 * @version %I%, %G%
 * @since 0.0.1
 */
public class ControllerHelper {
    /**
     * Switch to the server connection window, and close the current window.
     *
     * @since 0.0.1
     */
    static void switchToServerConnectionWindow(final View<?> oldView) {
        Platform.runLater(() -> {
            Stage stage = new Stage();
            ServerConnectionModel model = new ServerConnectionModel();
            ServerConnectionView newView = new ServerConnectionView(stage, model);
            new ServerConnectionController(model, newView);

            oldView.stop();
            newView.start();
        });
    }

    /**
     * Switch to the dashboard window, and close the current window.
     *
     * @since 0.0.1
     */
    static void switchToDashboardWindow(final View<?> oldView) {
        Platform.runLater(() -> {
            Stage stage = new Stage();
            DashboardModel model = new DashboardModel();
            DashboardView newView = new DashboardView(stage, model);
            new DashboardController(model, newView);

            oldView.stop();
            newView.start();
        });
    }

    /**
     * Switch to the login window, and close the current window.
     *
     * @since 0.0.1
     */
    static void switchToLoginWindow(final View<?> oldView) {
        Platform.runLater(() -> {
            Stage stage = new Stage();
            LoginModel model = new LoginModel();
            LoginView newView = new LoginView(stage, model);
            new LoginController(model, newView);

            oldView.stop();
            newView.start();
        });
    }

    /**
     * Switch to the register window, and close the current window.
     *
     * @since 0.0.1
     */
    static void switchToRegisterWindow(final View<?> oldView) {
        Platform.runLater(() -> {
            Stage stage = new Stage();
            RegisterModel model = new RegisterModel();
            RegisterView newView = new RegisterView(stage, model);
            new RegisterController(model, newView);

            oldView.stop();
            newView.start();
        });
    }
}
