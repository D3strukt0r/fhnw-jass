package jass.client.controller;

import com.jfoenix.controls.JFXButton;
import jass.client.entity.LoginEntity;
import jass.client.eventlistener.GameFoundEventListener;
import jass.client.message.CancelSearchGame;
import jass.client.message.Logout;
import jass.client.message.SearchGame;
import jass.client.mvc.Controller;
import jass.client.util.GameUtil;
import jass.client.util.I18nUtil;
import jass.client.util.SocketUtil;
import jass.client.util.ViewUtil;
import jass.client.util.WindowUtil;
import jass.client.view.GameView;
import jass.lib.message.CancelSearchGameData;
import jass.lib.message.GameFoundData;
import jass.lib.message.LogoutData;
import jass.lib.message.SearchGameData;
import jass.lib.servicelocator.ServiceLocator;
import javafx.scene.control.Alert;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import jass.client.view.LobbyView;
import jass.client.view.LoginView;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * The controller for the lobby view.
 *
 * @author Manuele Vaccari & Thomas Weber & Sasa Trajkova
 * @version %I%, %G%
 * @since 0.0.1
 */
public final class LobbyController extends Controller implements GameFoundEventListener {
    /**
     * The logger to print to console and save in a .log file.
     */
    private static final Logger logger = LogManager.getLogger(LobbyController.class);

    // GameService gameService

    /**
     * The view.
     */
    private LobbyView view;

    /**
     * The "File" element.
     */
    @FXML
    private Menu mFile;

    /**
     * The "File -> Change Language" element.
     */
    @FXML
    private Menu mFileChangeLanguage;

    /**
     * The "File -> Disconnect" element.
     */
    @FXML
    private MenuItem mFileDisconnect;

    /**
     * The "File -> Logout" element.
     */
    @FXML
    private MenuItem mFileLogout;

    /**
     * The "File -> Exit" element.
     */
    @FXML
    private MenuItem mFileExit;

    /**
     * The "Help" element.
     */
    @FXML
    private Menu mHelp;

    /**
     * The "Help -> About" element.
     */
    @FXML
    private MenuItem mHelpAbout;

    /**
     * The Find Match button.
     */
    @FXML
    private JFXButton findMatch;

    /**
     * The Cancel Match button.
     */
    @FXML
    private JFXButton cancelMatch;

    /**
     * The searching text.
     */
    @FXML
    private Text searching;


    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        SocketUtil socket = ServiceLocator.get(SocketUtil.class);
        if (socket != null) { // Not necessary but keeps IDE happy
            socket.setGameFoundEventListener(this);
        }

        /*
         * Bind all texts
         */
        mFile.textProperty().bind(I18nUtil.createStringBinding(mFile.getText()));
        mFileChangeLanguage.textProperty().bind(I18nUtil.createStringBinding(mFileChangeLanguage.getText()));
        ViewUtil.useLanguageMenuContent(mFileChangeLanguage);
        mFileDisconnect.textProperty().bind(I18nUtil.createStringBinding(mFileDisconnect.getText()));
        mFileLogout.textProperty().bind(I18nUtil.createStringBinding(mFileLogout.getText()));
        mFileExit.textProperty().bind(I18nUtil.createStringBinding(mFileExit.getText()));
        mFileExit.setAccelerator(KeyCombination.keyCombination("Alt+F4"));

        mHelp.textProperty().bind(I18nUtil.createStringBinding(mHelp.getText()));
        mHelpAbout.textProperty().bind(I18nUtil.createStringBinding(mHelpAbout.getText()));

        findMatch.textProperty().bind(I18nUtil.createStringBinding(findMatch.getText()));
        cancelMatch.textProperty().bind(I18nUtil.createStringBinding(cancelMatch.getText()));
        searching.textProperty().bind(I18nUtil.createStringBinding(searching.getText()));

    }

    /**
     * After clicking on Find match, change the button text to "Cancel" and show
     * text "searching" and the logic for searching for a game (client &
     * server).
     *
     * @author Thomas Weber
     */
    @FXML
    public void clickOnFindMatch() {
        // Get token and initialize SearchGame Message
        LoginEntity login = ServiceLocator.get(LoginEntity.class);
        assert login != null;
        String token = login.getToken();
        String userName = login.getUsername();
        SearchGame searchGameMsg = new SearchGame(new SearchGameData(token, userName));
        SocketUtil backend = ServiceLocator.get(SocketUtil.class);
        assert backend != null;

        // Send SearchGame Message to Server
        if (searchGameMsg.process(backend)) {
            searching.setVisible(true);
            cancelMatch.setVisible(true);
            findMatch.setVisible(false);
        } else {
            logger.error("Error starting search for game");
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Error searching for a game. Please try again!");
                alert.showAndWait();
            });
        }

    }

    /**
     * After clicking on Cancel match, Find match button appears and text
     * "searching" is hidden.
     */
    @FXML
    public void clickOnCancelMatch() {
        // Get token and initialize SearchGame Message
        LoginEntity login = ServiceLocator.get(LoginEntity.class);
        assert login != null;
        String token = login.getToken();
        String userName = login.getUsername();
        CancelSearchGame cancelSearchGameMsg = new CancelSearchGame(new CancelSearchGameData(token, userName));
        SocketUtil backend = ServiceLocator.get(SocketUtil.class);
        assert backend != null;

        // Send SearchGame Message to Server
        if (cancelSearchGameMsg.process(backend)) {
            searching.setVisible(false);
            findMatch.setVisible(true);
            cancelMatch.setVisible(false);
        } else {
            logger.error("Error cancelling search for game");
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Error cancelling search for a game. Please try again!");
                alert.showAndWait();
            });
        }
    }

    /**
     * Work to do after a game was found.
     *
     * @param msgData The game found data.
     */
    public void onGameFound(final GameFoundData msgData) {
        logger.info("Successfully found game!");

        GameUtil gameUtil = ServiceLocator.get(GameUtil.class);
        assert gameUtil != null;
        gameUtil.setGame(msgData);

        WindowUtil.switchToNewWindow(view, GameView.class);
    }

    /**
     * Disconnect from the server and returns to the server connection window.
     */
    @FXML
    private void clickOnDisconnect() {
        SocketUtil socket = ServiceLocator.get(SocketUtil.class);
        assert socket != null;

        socket.close();
        ServiceLocator.remove(SocketUtil.class);
        WindowUtil.switchTo(view, LoginView.class);
    }

    /**
     * Keeps the server connection but returns to the login window.
     */
    @FXML
    public void clickOnLogout() {
        SocketUtil socket = ServiceLocator.get(SocketUtil.class);
        assert socket != null;

        Logout logoutMsg = new Logout(new LogoutData());
        socket.send(logoutMsg);
        ServiceLocator.remove(LoginEntity.class);
        WindowUtil.switchTo(view, LoginView.class);
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
    public void setView(final LobbyView view) {
        this.view = view;
    }
}
