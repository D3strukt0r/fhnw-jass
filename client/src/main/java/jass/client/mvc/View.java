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

import javafx.scene.Parent;
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
     * The root element of the document.
     */
    private Parent root;

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

        // Create all controls within "root"
        scene = createGUI();
        stage.setScene(scene);

        // Add icon to window
        stage.getIcons().add(new Image(getClass().getResource("/images/icon.png").toString()));

        // Use the minWidth and minHeight given from the FXML file
        stage.setResizable(true);
        stage.setMinHeight(root.minHeight(-1));
        stage.setHeight(root.prefHeight(-1));
        stage.setMinWidth(root.minWidth(-1));
        stage.setWidth(root.prefWidth(-1));
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

    /**
     * @param root The root of the document.
     *
     * @return Returns the object for further processing.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    public View setRoot(final Parent root) {
        this.root = root;
        return this;
    }

    /**
     * @return Returns the root of the document.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    public Parent getRoot() {
        return root;
    }
}
