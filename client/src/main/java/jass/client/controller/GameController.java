package jass.client.controller;

import jass.client.eventlistener.BroadcastDeckEventListener;
import jass.client.mvc.Controller;
import jass.client.util.SocketUtil;
import jass.client.util.WindowUtil;
import jass.client.view.GameView;
import jass.client.view.ServerConnectionView;
import jass.lib.message.BroadcastDeckData;
import jass.lib.message.MessageData;
import jass.lib.servicelocator.ServiceLocator;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * The controller for the dashboard (game) view.
 *
 * @author Sasa Trajkova
 * @version %I%, %G%
 * @since 0.0.1
 */
public final class GameController extends Controller implements BroadcastDeckEventListener {
    private static final Logger logger = LogManager.getLogger(GameController.class);
    /**
     * The view.
     */
    private GameView view;

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        SocketUtil socket = (SocketUtil) ServiceLocator.get("backend");
        if (socket != null) { // Not necessary but keeps IDE happy
            socket.setBroadcastDeckEventListener(this);
        }
    }

    /**
     * Disconnect from the server and returns to the server connection window.
     */
    @FXML
    private void clickOnDisconnect() {
        SocketUtil socket = (SocketUtil) ServiceLocator.get(SocketUtil.SERVICE_NAME);
        if (socket != null) { // Not necessary but keeps IDE happy
            socket.close();
        }
        ServiceLocator.remove("backend");
        WindowUtil.switchTo(view, ServerConnectionView.class);
    }

    /**
     * Shuts down the application.
     */
    @FXML
    private void clickOnExit() {
        Platform.exit();
    }

    /**
     * @param view The view.
     */
    public void setView(final GameView view) {
        this.view = view;
    }

    @Override
    public void onDeckBroadcasted(MessageData msgData) {
        BroadcastDeckData data = (BroadcastDeckData) msgData;
        logger.info("Successfully received cards!");

        // TODO - get rid of this alert, just for demonstration purposes at the moment
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Cards Received!");
            alert.showAndWait();
        });

    }
}
