package jass.client.view;

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
public class GameView extends View {
    /**
     * @param stage The stage of the window.
     */
    public GameView(final Stage stage) {
        super(stage);
        stage.titleProperty().bind(I18nUtil.createStringBinding("gui.game.title"));
        stage.setResizable(false);
        stage.setMinHeight(600);
        stage.setMinWidth(800);

        // Register ourselves to handle window-closing event
        stage.setOnCloseRequest(event -> Platform.exit());
    }

    @Override
    protected Scene create_GUI() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/game.fxml"));
            Parent root = loader.load();
            return new Scene(root);
        } catch (IOException e) {
            return null;
        }
    }
}
