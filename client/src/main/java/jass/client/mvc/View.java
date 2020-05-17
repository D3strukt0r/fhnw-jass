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
 * @since 1.0.0
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
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    protected View(final Stage stage) {
        this.stage = stage;

        scene = createGUI(); // Create all controls within "root"

        // Add icon to window
        stage.getIcons().add(new Image(getClass().getResource("/images/icon.png").toString()));

        stage.setScene(scene);
    }

    /**
     * @return Returns a scene with the contents.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    protected abstract Scene createGUI();

    /**
     * Display the view.
     *
     * @author Manuele Vaccari & Victor Hargrave
     * @since 1.0.0
     */
    public void start() {
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Hide the view.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    public void stop() {
        stage.hide();
    }

    /**
     * @return Returns the scene.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    public Scene getScene() {
        return scene;
    }

    /**
     * Getter for the stage, so that the controller can access window events.
     *
     * @return Returns the stage.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    public Stage getStage() {
        return stage;
    }
}
