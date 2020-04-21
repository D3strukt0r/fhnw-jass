package jass.client.controller;
import jass.client.mvc.Controller;
import jass.client.util.SocketUtil;
import jass.client.util.WindowUtil;
import jass.lib.servicelocator.ServiceLocator;
import javafx.application.Platform;
import javafx.fxml.FXML;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * The controller for the dashboard (game) view.
 *
 * @author Sasa Trajkova
 * @version %I%, %G%
 * @since 0.0.1
 */
public class GameController extends Controller {
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO: Do something
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
        WindowUtil.switchToServerConnectionWindow();
    }

    /**
     * Shuts down the application.
     */
    @FXML
    private void clickOnExit() {
        Platform.exit();
    }
}
