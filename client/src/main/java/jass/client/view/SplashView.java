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

import jass.client.controller.SplashController;
import jass.client.mvc.View;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
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
        stage.initStyle(StageStyle.TRANSPARENT); // Also undecorated
    }

    /**
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    @Override
    protected Scene createGUI() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/splash_screen.fxml"));
            Parent root = loader.load();
            SplashController controller = loader.getController();
            controller.setView(this);

            return new Scene(root);
        } catch (IOException e) {
            return null;
        }
    }
}
