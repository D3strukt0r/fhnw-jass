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

package jass.client.mvc;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * The abstract View for the MVC.
 *
 * @author Manuele Vaccari
 * @version %I%, %G%
 * @since 0.0.1
 */
public abstract class View {
    /**
     * The stage.
     */
    private final Stage stage;

    /**
     * The scene.
     */
    private final Scene scene;

    /**
     * Set any options for the stage in the subclass constructor.
     *
     * @param stage An object containing a Stage.
     */
    protected View(final Stage stage) {
        this.stage = stage;

        scene = createGUI(); // Create all controls within "root"
        stage.getIcons().add(new Image(getClass().getResource("/images/icon.png").toString())); // Add icon to window
        stage.setScene(scene);
    }

    /**
     * @return Returns a scene with the contents.
     */
    protected abstract Scene createGUI();

    /**
     * Display the view.
     */
    public void start() {
        stage.show();
    }

    /**
     * Hide the view.
     */
    public void stop() {
        stage.hide();
    }

    /**
     * @return Returns the scene.
     */
    public Scene getScene() {
        return scene;
    }

    /**
     * Getter for the stage, so that the controller can access window events.
     *
     * @return Returns the stage.
     */
    public Stage getStage() {
        return stage;
    }
}
