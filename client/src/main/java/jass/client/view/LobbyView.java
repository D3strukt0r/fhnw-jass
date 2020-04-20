package jass.client.view;

import jass.client.controller.LobbyController;
import jass.client.controller.LoginController;
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
public class LobbyView extends View {
    /**
     * @param stage The stage of the window.
     */
    public LobbyView(final Stage stage) {
        super(stage);
        stage.titleProperty().bind(I18nUtil.createStringBinding("gui.lobby.title"));
        stage.setResizable(false);
        stage.setWidth(350);

        // Register ourselves to handle window-closing event
        stage.setOnCloseRequest(event -> Platform.exit());
    }

    @Override
    protected Scene create_GUI() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/lobby.fxml"));
            Parent root = loader.load();
            LobbyController controller = loader.getController();
            controller.setView(this);

            return new Scene(root);
        } catch (IOException e) {
            return null;
        }
    }
}

