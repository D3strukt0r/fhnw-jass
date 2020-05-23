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

package jass.client.view;

import jass.client.mvc.Controller;
import jass.client.mvc.View;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

/**
 * The splash view.
 *
 * @author Manuele Vaccari
 * @version %I%, %G%
 * @since 1.0.0
 */
public final class SplashView extends View {
    /**
     * @param stage The stage of the window.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    public SplashView(final Stage stage) {
        super(stage);

        // Also undecorated
        stage.initStyle(StageStyle.TRANSPARENT);
    }

    /**
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    @Override
    protected Scene createGUI() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/splash_screen.fxml"));
            setRoot(loader.load());
            ((Controller) loader.getController()).setView(this);
            return new Scene(getRoot());
        } catch (IOException e) {
            return null;
        }
    }
}
