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

package jass.client.controller;

import com.jfoenix.controls.JFXProgressBar;
import jass.client.view.LobbyView;
import jass.client.view.LoginView;
import jass.client.view.ServerConnectionView;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import jass.client.mvc.Controller;
import jass.client.model.SplashModel;
import jass.client.util.WindowUtil;
import jass.client.view.SplashView;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * The controller for the splash view.
 *
 * @author Brad Richards
 */
public final class SplashController extends Controller {
    /**
     * The model.
     */
    private SplashModel model;

    /**
     * The view.
     */
    private SplashView view;

    /**
     * The loading bar.
     */
    @FXML
    private JFXProgressBar loadingProgress;

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        model = new SplashModel();

        loadingProgress.progressProperty().bind(model.getInitializer().progressProperty());
        model.getInitializer().stateProperty().addListener((o, oldValue, newValue) -> {
            if (newValue == Worker.State.SUCCEEDED) {
                // If already logged in go to the game directly, if at least connected, go to login screen, otherwise
                // to server connection
                if (model.isLoggedIn()) {
                    WindowUtil.switchToNewWindow(view, LobbyView.class);
                } else if (model.isConnected()) {
                    WindowUtil.switchToNewWindow(view, LoginView.class);
                } else {
                    WindowUtil.switchToNewWindow(view, ServerConnectionView.class);
                }
                view.getStage().hide();
            }
        });

        model.initialize();
    }

    /**
     * @param view The view.
     */
    public void setView(final SplashView view) {
        this.view = view;
    }
}
