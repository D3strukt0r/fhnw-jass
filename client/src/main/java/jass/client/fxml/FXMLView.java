package jass.client.fxml;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public abstract class FXMLView {
    protected Stage stage;
    protected Scene scene;

    /**
     * Set any options for the stage in the subclass constructor
     *
     * @param stage An object containing a Stage
     */
    protected FXMLView(Stage stage) {
        this.stage = stage;

        scene = create_GUI(); // Create all controls within "root"
        stage.getIcons().add(new Image(getClass().getResource("/images/icon.png").toString())); // Add icon to window
        stage.setScene(scene);
    }

    protected abstract Scene create_GUI();

    /**
     * Display the view
     */
    public void start() {
        stage.show();
    }

    /**
     * Hide the view
     */
    public void stop() {
        stage.hide();
    }

    /**
     * Getter for the stage, so that the controller can access window events
     */
    public Stage getStage() {
        return stage;
    }
}
