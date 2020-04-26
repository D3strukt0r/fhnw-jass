package jass.client.controller;
import com.jfoenix.controls.JFXButton;


import jass.client.eventlistener.BroadcastDeckEventListener;
import jass.client.mvc.Controller;
import jass.client.util.I18nUtil;
import jass.client.util.SocketUtil;
import jass.client.util.ViewUtil;
import jass.client.util.WindowUtil;
import jass.client.view.GameView;
import jass.client.view.LoginView;
import jass.client.view.ServerConnectionView;
import jass.lib.message.BroadcastDeckData;
import jass.lib.message.MessageData;
import jass.lib.servicelocator.ServiceLocator;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;
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
     * The Mode label.
     */
    @FXML
    private Label mode;

    /**
     * The Score(round) label.
     */
    @FXML
    private Label scoreR;

    /**
     * The Score(total) label.
     */
    @FXML
    private Label scoreT;

    /**
     * The Username labels.
     */
    @FXML
    private Label user1;
    @FXML
    private Label user2;
    @FXML
    private Label user3;
    @FXML
    private Label user4;

    /**
     * The User1 card buttons.
     */
    @FXML
    private Button user1b1;
    @FXML
    private Button user1b2;
    @FXML
    private Button user1b3;
    @FXML
    private Button user1b4;
    @FXML
    private Button user1b5;
    @FXML
    private Button user1b6;
    @FXML
    private Button user1b7;
    @FXML
    private Button user1b8;
    @FXML
    private Button user1b9;
    @FXML
    private Button user1played;

    /**
     * The User2 card buttons.
     */
    @FXML
    private Button user2b1;
    @FXML
    private Button user2b2;
    @FXML
    private Button user2b3;
    @FXML
    private Button user2b4;
    @FXML
    private Button user2b5;
    @FXML
    private Button user2b6;
    @FXML
    private Button user2b7;
    @FXML
    private Button user2b8;
    @FXML
    private Button user2b9;
    @FXML
    private Button user2played;

    /**
     * The User3 card buttons.
     */
    @FXML
    private Button user3b1;
    @FXML
    private Button user3b2;
    @FXML
    private Button user3b3;
    @FXML
    private Button user3b4;
    @FXML
    private Button user3b5;
    @FXML
    private Button user3b6;
    @FXML
    private Button user3b7;
    @FXML
    private Button user3b8;
    @FXML
    private Button user3b9;
    @FXML
    private Button user3played;

    /**
     * The User4 card buttons.
     */
    @FXML
    private Button user4b1;
    @FXML
    private Button user4b2;
    @FXML
    private Button user4b3;
    @FXML
    private Button user4b4;
    @FXML
    private Button user4b5;
    @FXML
    private Button user4b6;
    @FXML
    private Button user4b7;
    @FXML
    private Button user4b8;
    @FXML
    private Button user4b9;
    @FXML
    private Button user4played;


    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        SocketUtil socket = (SocketUtil) ServiceLocator.get("backend");
        if (socket != null) { // Not necessary but keeps IDE happy
            socket.setBroadcastDeckEventListener(this);
        }
        // TODO: Do something

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
        WindowUtil.switchToNewWindow(view, ServerConnectionView.class);
    }

    /**
     * Keeps the server connection but returns to the login window.
     */
    @FXML
    public void clickOnLogout() {
        //TODO handle logout properly
        WindowUtil.switchToNewWindow(view, LoginView.class);
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

    /**
     * @author Sasa Trajkova
     * Method to only enable buttons for those cards that are legal for a specific round
     */
    public void enableButtons() {
        //TODO enable buttons for the cards that could be played in the round
    }

    /**
     * @author Sasa Trajkova
     * Method to display card images based on the players hand
     */
    public void updateCardImage() {
        //TODO return card image (calculate what the players cards are)
    }

    /**
     * @author Sasa Trajkova
     * Change background color in the player pane if it's the player's turn to play
     */
    @FXML
    public void changePlayerPaneBackground(){
        //TODO change player pane background
    }

    /**
     * @author Sasa Trajkova
     * Fatch usernames and match them with the right label
     */
    @FXML
    public void updateUserNames(){
        //TODO update usernames;
    }
}

