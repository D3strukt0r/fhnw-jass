package jass.client.controller;

import jass.client.entity.LoginEntity;
import jass.client.mvc.Controller;
import jass.client.util.*;
import jass.client.view.GameView;
import jass.client.view.LoginView;
import jass.client.view.ServerConnectionView;
import jass.lib.message.CardData;
import jass.lib.servicelocator.ServiceLocator;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.*;
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
public final class GameController extends Controller {
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

    private GameUtil gameUtil;

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        this.gameUtil = (GameUtil) ServiceLocator.get(GameUtil.class);

        enableButtons();
        updateUserNames();
        updateCardImages();

        this.gameUtil.getPlayerDeck().addListener(new ListChangeListener() {
            @Override
            public void onChanged(Change c) {
                updateCardImages();
            }
        });

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
    }

    /**
     * Display card images in the right player pane
     */
    private void updateCardImages() {
        if (this.gameUtil.getPlayerDeck().stream().count() == 9) {

            LoginEntity login = (LoginEntity) ServiceLocator.get(LoginEntity.class);

            assert login != null;
            if (gameUtil.getGame().getPlayerOne().equals(login.getUsername())) {
                CardData card1 = this.gameUtil.getPlayerDeck().get(0);
                setImage("/images/cards/" + card1.getRank() + "_of_" + card1.getSuit() + ".png", user1b1);

                CardData card2 = this.gameUtil.getPlayerDeck().get(1);
                setImage("/images/cards/" + card2.getRank() + "_of_" + card2.getSuit() + ".png", user1b2);

                CardData card3 = this.gameUtil.getPlayerDeck().get(2);
                setImage("/images/cards/" + card3.getRank() + "_of_" + card3.getSuit() + ".png", user1b3);

                CardData card4 = this.gameUtil.getPlayerDeck().get(3);
                setImage("/images/cards/" + card4.getRank() + "_of_" + card4.getSuit() + ".png", user1b4);

                CardData card5 = this.gameUtil.getPlayerDeck().get(4);
                setImage("/images/cards/" + card5.getRank() + "_of_" + card5.getSuit() + ".png", user1b5);

                CardData card6 = this.gameUtil.getPlayerDeck().get(5);
                setImage("/images/cards/" + card6.getRank() + "_of_" + card6.getSuit() + ".png", user1b6);

                CardData card7 = this.gameUtil.getPlayerDeck().get(6);
                setImage("/images/cards/" + card7.getRank() + "_of_" + card7.getSuit() + ".png", user1b7);

                CardData card8 = this.gameUtil.getPlayerDeck().get(7);
                setImage("/images/cards/" + card8.getRank() + "_of_" + card8.getSuit() + ".png", user1b8);

                CardData card9 = this.gameUtil.getPlayerDeck().get(8);
                setImage("/images/cards/" + card9.getRank() + "_of_" + card9.getSuit() + ".png", user1b9);
            }

            if (gameUtil.getGame().getPlayerTwo().equals(login.getUsername())) {
                CardData card1 = this.gameUtil.getPlayerDeck().get(0);
                setImage("/images/cards/" + card1.getRank() + "_of_" + card1.getSuit() + ".png", user2b1);

                CardData card2 = this.gameUtil.getPlayerDeck().get(1);
                setImage("/images/cards/" + card2.getRank() + "_of_" + card2.getSuit() + ".png", user2b2);

                CardData card3 = this.gameUtil.getPlayerDeck().get(2);
                setImage("/images/cards/" + card3.getRank() + "_of_" + card3.getSuit() + ".png", user2b3);

                CardData card4 = this.gameUtil.getPlayerDeck().get(3);
                setImage("/images/cards/" + card4.getRank() + "_of_" + card4.getSuit() + ".png", user2b4);

                CardData card5 = this.gameUtil.getPlayerDeck().get(4);
                setImage("/images/cards/" + card5.getRank() + "_of_" + card5.getSuit() + ".png", user2b5);

                CardData card6 = this.gameUtil.getPlayerDeck().get(5);
                setImage("/images/cards/" + card6.getRank() + "_of_" + card6.getSuit() + ".png", user2b6);

                CardData card7 = this.gameUtil.getPlayerDeck().get(6);
                setImage("/images/cards/" + card7.getRank() + "_of_" + card7.getSuit() + ".png", user2b7);

                CardData card8 = this.gameUtil.getPlayerDeck().get(7);
                setImage("/images/cards/" + card8.getRank() + "_of_" + card8.getSuit() + ".png", user2b8);

                CardData card9 = this.gameUtil.getPlayerDeck().get(8);
                setImage("/images/cards/" + card9.getRank() + "_of_" + card9.getSuit() + ".png", user2b9);
            }

            if (gameUtil.getGame().getPlayerThree().equals(login.getUsername())) {
                CardData card1 = this.gameUtil.getPlayerDeck().get(0);
                setImage("/images/cards/" + card1.getRank() + "_of_" + card1.getSuit() + ".png", user3b1);

                CardData card2 = this.gameUtil.getPlayerDeck().get(1);
                setImage("/images/cards/" + card2.getRank() + "_of_" + card2.getSuit() + ".png", user3b2);

                CardData card3 = this.gameUtil.getPlayerDeck().get(2);
                setImage("/images/cards/" + card3.getRank() + "_of_" + card3.getSuit() + ".png", user3b3);

                CardData card4 = this.gameUtil.getPlayerDeck().get(3);
                setImage("/images/cards/" + card4.getRank() + "_of_" + card4.getSuit() + ".png", user3b4);

                CardData card5 = this.gameUtil.getPlayerDeck().get(4);
                setImage("/images/cards/" + card5.getRank() + "_of_" + card5.getSuit() + ".png", user3b5);

                CardData card6 = this.gameUtil.getPlayerDeck().get(5);
                setImage("/images/cards/" + card6.getRank() + "_of_" + card6.getSuit() + ".png", user3b6);

                CardData card7 = this.gameUtil.getPlayerDeck().get(6);
                setImage("/images/cards/" + card7.getRank() + "_of_" + card7.getSuit() + ".png", user3b7);

                CardData card8 = this.gameUtil.getPlayerDeck().get(7);
                setImage("/images/cards/" + card8.getRank() + "_of_" + card8.getSuit() + ".png", user3b8);

                CardData card9 = this.gameUtil.getPlayerDeck().get(8);
                setImage("/images/cards/" + card9.getRank() + "_of_" + card9.getSuit() + ".png", user3b9);
            }

            if (gameUtil.getGame().getPlayerFour().equals(login.getUsername())) {
                CardData card1 = this.gameUtil.getPlayerDeck().get(0);
                setImage("/images/cards/" + card1.getRank() + "_of_" + card1.getSuit() + ".png", user4b1);

                CardData card2 = this.gameUtil.getPlayerDeck().get(1);
                setImage("/images/cards/" + card2.getRank() + "_of_" + card2.getSuit() + ".png", user4b2);

                CardData card3 = this.gameUtil.getPlayerDeck().get(2);
                setImage("/images/cards/" + card3.getRank() + "_of_" + card3.getSuit() + ".png", user4b3);

                CardData card4 = this.gameUtil.getPlayerDeck().get(3);
                setImage("/images/cards/" + card4.getRank() + "_of_" + card4.getSuit() + ".png", user4b4);

                CardData card5 = this.gameUtil.getPlayerDeck().get(4);
                setImage("/images/cards/" + card5.getRank() + "_of_" + card5.getSuit() + ".png", user4b5);

                CardData card6 = this.gameUtil.getPlayerDeck().get(5);
                setImage("/images/cards/" + card6.getRank() + "_of_" + card6.getSuit() + ".png", user4b6);

                CardData card7 = this.gameUtil.getPlayerDeck().get(6);
                setImage("/images/cards/" + card7.getRank() + "_of_" + card7.getSuit() + ".png", user4b7);

                CardData card8 = this.gameUtil.getPlayerDeck().get(7);
                setImage("/images/cards/" + card8.getRank() + "_of_" + card8.getSuit() + ".png", user4b8);

                CardData card9 = this.gameUtil.getPlayerDeck().get(8);
                setImage("/images/cards/" + card9.getRank() + "_of_" + card9.getSuit() + ".png", user4b9);
            }
        }
    }

    private void setImage(String pathToImage, Button button) {
        BackgroundImage backgroundImage = new BackgroundImage(new Image(getClass().getResource(pathToImage).toExternalForm()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(74, 113, true, true, true, false));
        Background background = new Background(backgroundImage);
        button.setBackground(background);

    }

    /**
     * Disconnect from the server and returns to the server connection window.
     */
    @FXML
    private void clickOnDisconnect() {
        SocketUtil socket = (SocketUtil) ServiceLocator.get(SocketUtil.class);
        if (socket != null) { // Not necessary but keeps IDE happy
            socket.close();
        }
        ServiceLocator.remove(SocketUtil.class);
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


    /**
     * @author Sasa Trajkova
     * Method to only enable buttons for those cards that are legal for a specific round
     */
    public void enableButtons() {
        //TODO enable buttons for the cards that could be played in the round based on game mode

        LoginEntity login = (LoginEntity) ServiceLocator.get(LoginEntity.class);

        if (gameUtil.getGame().getPlayerOne().equals(login.getUsername())) {
            user1b1.setDisable(false);
            user1b2.setDisable(false);
            user1b3.setDisable(false);
            user1b4.setDisable(false);
            user1b5.setDisable(false);
            user1b6.setDisable(false);
            user1b7.setDisable(false);
            user1b8.setDisable(false);
            user1b9.setDisable(false);
        }

        if (gameUtil.getGame().getPlayerTwo().equals(login.getUsername())) {
            user2b1.setDisable(false);
            user2b2.setDisable(false);
            user2b3.setDisable(false);
            user2b4.setDisable(false);
            user2b5.setDisable(false);
            user2b6.setDisable(false);
            user2b7.setDisable(false);
            user2b8.setDisable(false);
            user2b9.setDisable(false);
        }

        if (gameUtil.getGame().getPlayerThree().equals(login.getUsername())) {
            user3b1.setDisable(false);
            user3b2.setDisable(false);
            user3b3.setDisable(false);
            user3b4.setDisable(false);
            user3b5.setDisable(false);
            user3b6.setDisable(false);
            user3b7.setDisable(false);
            user3b8.setDisable(false);
            user3b9.setDisable(false);
        }

        if (gameUtil.getGame().getPlayerFour().equals(login.getUsername())) {
            user4b1.setDisable(false);
            user4b2.setDisable(false);
            user4b3.setDisable(false);
            user4b4.setDisable(false);
            user4b5.setDisable(false);
            user4b6.setDisable(false);
            user4b7.setDisable(false);
            user4b8.setDisable(false);
            user4b9.setDisable(false);
        }
    }

    /**
     * @author Sasa Trajkova
     * Change background color in the player pane if it's the player's turn to play
     */
    @FXML
    public void changePlayerPaneBackground() {
        //TODO change player pane background
    }

    /**
     * @author Sasa Trajkova
     * Fatch usernames and match them with the right label
     */
    @FXML
    public void updateUserNames() {

        if (gameUtil.getGame().getPlayerOne() != null) {
            user1.setText(gameUtil.getGame().getPlayerOne());
        } else {
            user1.setText("--");
        }

        if (gameUtil.getGame().getPlayerTwo() != null) {
            user2.setText(gameUtil.getGame().getPlayerTwo());
        } else {
            user2.setText("--");
        }

        if (gameUtil.getGame().getPlayerThree() != null) {
            user3.setText(gameUtil.getGame().getPlayerThree());
        } else {
            user3.setText("--");
        }

        if (gameUtil.getGame().getPlayerFour() != null) {
            user4.setText(gameUtil.getGame().getPlayerFour());
        } else {
            user4.setText("--");
        }
    }
}

