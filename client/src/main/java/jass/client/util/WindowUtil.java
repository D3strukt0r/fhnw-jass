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

package jass.client.util;

import jass.client.view.LobbyView;
import jass.client.view.LoginView;
import jass.client.view.RegisterView;
import jass.client.view.ServerConnectionView;
import jass.client.view.SplashView;
import javafx.application.Platform;
import javafx.stage.Stage;

/**
 * A helper class for the controllers to switch between windows easily.
 *
 * @author Manuele Vaccari
 * @version %I%, %G%
 * @since 0.0.1
 */
public class WindowUtil {
    /**
     * A defined stage (window) to be reused.
     */
    private static final Stage stage = new Stage();

    /**
     * Switch to splash screen window.
     */
    public static void switchToSplashScreen() {
        Platform.runLater(() -> {
            Stage stage = new Stage();
            SplashView view = new SplashView(stage);
            view.start();
        });
    }

    /**
     * Switch to server chooser window.
     */
    public static void switchToServerConnectionWindow() {
        Platform.runLater(() -> {
            ServerConnectionView view = new ServerConnectionView(stage);
            view.start();
        });
    }

    /**
     * Switch to login window.
     */
    public static void switchToLoginWindow() {
        Platform.runLater(() -> {
            LoginView view = new LoginView(stage);
            view.start();
        });
    }

    /**
     * Switch to register window.
     */
    public static void switchToRegisterWindow() {
        Platform.runLater(() -> {
            RegisterView view = new RegisterView(stage);
            view.start();
        });
    }

    /**
     * Switch to dashboard window.
     */
    public static void switchToDashboardWindow() {
        Platform.runLater(() -> {
            Stage stage = new Stage();
            LobbyView view = new LobbyView(stage);
            view.start();
        });
    }
}
