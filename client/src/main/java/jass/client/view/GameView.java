package jass.client.view;

import jass.client.controller.GameController;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jass.client.mvc.View;
import jass.client.util.I18nUtil;

import java.io.IOException;

/**
 * The game view.
 *
 * @author Sasa Trajkova
 * @version %I%, %G%
 * @since 0.0.1
 */
public final class GameView extends View {
    /**
     * @param stage The stage of the window.
     */
    public GameView(final Stage stage) {
        super(stage);
        stage.titleProperty().bind(I18nUtil.createStringBinding("gui.game.title"));
        stage.setResizable(false);
        stage.setMinHeight(715);
        stage.setMinWidth(1220);
        stage.centerOnScreen();

        // Register ourselves to handle window-closing event
        stage.setOnCloseRequest(event -> Platform.exit());
    }

    @Override
    protected Scene createGUI() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/game.fxml"));
            Parent root = loader.load();
            GameController controller = loader.getController();
            controller.setView(this);
            return new Scene(root);
        } catch (IOException e) {
            return null;
        }
    }
}
