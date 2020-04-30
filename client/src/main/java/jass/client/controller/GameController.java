package jass.client.controller;

import jass.client.entity.LoginEntity;
import jass.client.eventlistener.DisconnectEventListener;
import jass.client.message.Logout;
import jass.client.mvc.Controller;
import jass.client.util.GameUtil;
import jass.client.util.I18nUtil;
import jass.client.util.SocketUtil;
import jass.client.util.ViewUtil;
import jass.client.util.WindowUtil;
import jass.client.view.GameView;
import jass.client.view.LoginView;
import jass.client.view.ServerConnectionView;
import jass.lib.Card;
import jass.lib.GameMode;
import jass.lib.message.LogoutData;
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
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
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
public final class GameController extends Controller implements DisconnectEventListener {
    /**
     * The logger to print to console and save in a .log file.
     */
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
     * The Username label for user one.
     */
    @FXML
    private Label user1;

    /**
     * The Username label for user two.
     */
    @FXML
    private Label user2;

    /**
     * The Username label for user three.
     */
    @FXML
    private Label user3;

    /**
     * The Username label for user four.
     */
    @FXML
    private Label user4;

    /**
     * The user one card button one.
     */
    @FXML
    private Button user1b1;

    /**
     * The user one card button two.
     */
    @FXML
    private Button user1b2;

    /**
     * The user one card button three.
     */
    @FXML
    private Button user1b3;

    /**
     * The user one card button four.
     */
    @FXML
    private Button user1b4;

    /**
     * The user one card button five.
     */
    @FXML
    private Button user1b5;

    /**
     * The user one card button six.
     */
    @FXML
    private Button user1b6;

    /**
     * The user one card button seven.
     */
    @FXML
    private Button user1b7;

    /**
     * The user one card button eight.
     */
    @FXML
    private Button user1b8;

    /**
     * The user one card button nine.
     */
    @FXML
    private Button user1b9;

    /**
     * The card that user one played in this round.
     */
    @FXML
    private Button user1played;

    /**
     * The user two card button one.
     */
    @FXML
    private Button user2b1;

    /**
     * The user two card button two.
     */
    @FXML
    private Button user2b2;

    /**
     * The user two card button three.
     */
    @FXML
    private Button user2b3;

    /**
     * The user two card button four.
     */
    @FXML
    private Button user2b4;

    /**
     * The user two card button five.
     */
    @FXML
    private Button user2b5;

    /**
     * The user two card button six.
     */
    @FXML
    private Button user2b6;

    /**
     * The user two card button seven.
     */
    @FXML
    private Button user2b7;

    /**
     * The user two card button eight.
     */
    @FXML
    private Button user2b8;

    /**
     * The user two card button nine.
     */
    @FXML
    private Button user2b9;

    /**
     * The card that user two played in this round.
     */
    @FXML
    private Button user2played;

    /**
     * The user three card button one.
     */
    @FXML
    private Button user3b1;

    /**
     * The user three card button two.
     */
    @FXML
    private Button user3b2;

    /**
     * The user three card button three.
     */
    @FXML
    private Button user3b3;

    /**
     * The user three card button four.
     */
    @FXML
    private Button user3b4;

    /**
     * The user three card button five.
     */
    @FXML
    private Button user3b5;

    /**
     * The user three card button six.
     */
    @FXML
    private Button user3b6;

    /**
     * The user three card button seven.
     */
    @FXML
    private Button user3b7;

    /**
     * The user three card button eight.
     */
    @FXML
    private Button user3b8;

    /**
     * The user three card button nine.
     */
    @FXML
    private Button user3b9;

    /**
     * The card that user three played in this round.
     */
    @FXML
    private Button user3played;

    /**
     * The user four card button one.
     */
    @FXML
    private Button user4b1;

    /**
     * The user four card button two.
     */
    @FXML
    private Button user4b2;

    /**
     * The user four card button three.
     */
    @FXML
    private Button user4b3;

    /**
     * The user four card button four.
     */
    @FXML
    private Button user4b4;

    /**
     * The user four card button five.
     */
    @FXML
    private Button user4b5;

    /**
     * The user four card button six.
     */
    @FXML
    private Button user4b6;

    /**
     * The user four card button seven.
     */
    @FXML
    private Button user4b7;

    /**
     * The user four card button eight.
     */
    @FXML
    private Button user4b8;

    /**
     * The user four card button nine.
     */
    @FXML
    private Button user4b9;

    /**
     * The card that user four played in this round.
     */
    @FXML
    private Button user4played;

    /**
     * The running game.
     */
    private GameUtil gameUtil;

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        gameUtil = ServiceLocator.get(GameUtil.class);

        logger.info("initialising");
        gameUtil.getPlayerDeck().addListener((ListChangeListener<Card>) c -> {
            logger.info("listener was activated. Now updating cards");
            logger.info("here's a card" + gameUtil.getPlayerDeck().get(0).getSuit());
            if (gameUtil.getPlayerDeck().size() == 9) {
                updateCardImages();
            }
        });
        logger.info("listener created for cards");
        enableButtons();
        logger.info("buttons enabled");
        updateUserNames();
        logger.info("updated user names");

        if (gameUtil.getPlayerDeck() != null) {
            updateCardImages();
            logger.info("updated card images");
        }

        gameUtil.getGameModeProperty().addListener((obs, oldGameMode, newGameMode) -> {
            Platform.runLater(() -> {
                // TODO Make this more beautiful
                if (newGameMode == GameMode.TRUMPF) {
                    String rawTrumpf = gameUtil.getTrumpfProperty().getValue().toString();
                    String trumpf = rawTrumpf.substring(0, 1).toUpperCase() + rawTrumpf.substring(1);
                    mode.setText("Mode: " + newGameMode.toString() + " | Card: " + trumpf);
                } else {
                    mode.setText("Mode: " + newGameMode.toString());
                }
            });
        });

        SocketUtil socket = ServiceLocator.get(SocketUtil.class);
        assert socket != null;
        socket.addDisconnectListener(this);

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
     * Display card images in the right player pane.
     */
    private void updateCardImages() {
        if (gameUtil.getPlayerDeck().size() == 9) {
            Card card1 = gameUtil.getPlayerDeck().get(0);
            Card card2 = gameUtil.getPlayerDeck().get(1);
            Card card3 = gameUtil.getPlayerDeck().get(2);
            Card card4 = gameUtil.getPlayerDeck().get(3);
            Card card5 = gameUtil.getPlayerDeck().get(4);
            Card card6 = gameUtil.getPlayerDeck().get(5);
            Card card7 = gameUtil.getPlayerDeck().get(6);
            Card card8 = gameUtil.getPlayerDeck().get(7);
            Card card9 = gameUtil.getPlayerDeck().get(8);

            LoginEntity login = ServiceLocator.get(LoginEntity.class);
            assert login != null;
            if (gameUtil.getGame().getPlayerOne().equals(login.getUsername())) {
                setImage(getCardPath(card1.getRank().toString(), card1.getSuit().toString()), user1b1);
                setImage(getCardPath(card2.getRank().toString(), card2.getSuit().toString()), user1b2);
                setImage(getCardPath(card3.getRank().toString(), card3.getSuit().toString()), user1b3);
                setImage(getCardPath(card4.getRank().toString(), card4.getSuit().toString()), user1b4);
                setImage(getCardPath(card5.getRank().toString(), card5.getSuit().toString()), user1b5);
                setImage(getCardPath(card6.getRank().toString(), card6.getSuit().toString()), user1b6);
                setImage(getCardPath(card7.getRank().toString(), card7.getSuit().toString()), user1b7);
                setImage(getCardPath(card8.getRank().toString(), card8.getSuit().toString()), user1b8);
                setImage(getCardPath(card9.getRank().toString(), card9.getSuit().toString()), user1b9);
            } else if (gameUtil.getGame().getPlayerTwo().equals(login.getUsername())) {
                setImage(getCardPath(card1.getRank().toString(), card1.getSuit().toString()), user2b1);
                setImage(getCardPath(card2.getRank().toString(), card2.getSuit().toString()), user2b2);
                setImage(getCardPath(card3.getRank().toString(), card3.getSuit().toString()), user2b3);
                setImage(getCardPath(card4.getRank().toString(), card4.getSuit().toString()), user2b4);
                setImage(getCardPath(card5.getRank().toString(), card5.getSuit().toString()), user2b5);
                setImage(getCardPath(card6.getRank().toString(), card6.getSuit().toString()), user2b6);
                setImage(getCardPath(card7.getRank().toString(), card7.getSuit().toString()), user2b7);
                setImage(getCardPath(card8.getRank().toString(), card8.getSuit().toString()), user2b8);
                setImage(getCardPath(card9.getRank().toString(), card9.getSuit().toString()), user2b9);
            } else if (gameUtil.getGame().getPlayerThree().equals(login.getUsername())) {
                setImage(getCardPath(card1.getRank().toString(), card1.getSuit().toString()), user3b1);
                setImage(getCardPath(card2.getRank().toString(), card2.getSuit().toString()), user3b2);
                setImage(getCardPath(card3.getRank().toString(), card3.getSuit().toString()), user3b3);
                setImage(getCardPath(card4.getRank().toString(), card4.getSuit().toString()), user3b4);
                setImage(getCardPath(card5.getRank().toString(), card5.getSuit().toString()), user3b5);
                setImage(getCardPath(card6.getRank().toString(), card6.getSuit().toString()), user3b6);
                setImage(getCardPath(card7.getRank().toString(), card7.getSuit().toString()), user3b7);
                setImage(getCardPath(card8.getRank().toString(), card8.getSuit().toString()), user3b8);
                setImage(getCardPath(card9.getRank().toString(), card9.getSuit().toString()), user3b9);
            } else if (gameUtil.getGame().getPlayerFour().equals(login.getUsername())) {
                setImage(getCardPath(card1.getRank().toString(), card1.getSuit().toString()), user4b1);
                setImage(getCardPath(card2.getRank().toString(), card2.getSuit().toString()), user4b2);
                setImage(getCardPath(card3.getRank().toString(), card3.getSuit().toString()), user4b3);
                setImage(getCardPath(card4.getRank().toString(), card4.getSuit().toString()), user4b4);
                setImage(getCardPath(card5.getRank().toString(), card5.getSuit().toString()), user4b5);
                setImage(getCardPath(card6.getRank().toString(), card6.getSuit().toString()), user4b6);
                setImage(getCardPath(card7.getRank().toString(), card7.getSuit().toString()), user4b7);
                setImage(getCardPath(card8.getRank().toString(), card8.getSuit().toString()), user4b8);
                setImage(getCardPath(card9.getRank().toString(), card9.getSuit().toString()), user4b9);
            }
        }
    }

    /**
     * @param rank The rank of the card.
     * @param suit The suit of the card.
     *
     * @return Returns the image path to the corresponding card.
     */
    private String getCardPath(final String rank, final String suit) {
        return "/images/cards/" + rank + "_of_" + suit + ".png";
    }

    /**
     * @param pathToImage The image path.
     * @param button      The button to assign a card image.
     */
    private void setImage(final String pathToImage, final Button button) {
        BackgroundImage backgroundImage = new BackgroundImage(new Image(
            getClass().getResource(pathToImage).toExternalForm()),
            BackgroundRepeat.NO_REPEAT,
            BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.CENTER,
            new BackgroundSize(74, 113, true, true, true, false));
        Background background = new Background(backgroundImage);
        button.setBackground(background);
    }

    /**
     * Disconnect from the server and returns to the server connection window.
     */
    @FXML
    private void clickOnDisconnect() {
        ServiceLocator.remove(LoginEntity.class);
        SocketUtil socket = ServiceLocator.get(SocketUtil.class);
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
        SocketUtil socket = ServiceLocator.get(SocketUtil.class);
        assert socket != null;

        Logout logoutMsg = new Logout(new LogoutData());
        socket.send(logoutMsg);
        ServiceLocator.remove(LoginEntity.class);
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
     * Method to only enable buttons for those cards that are legal for a
     * specific round.
     *
     * @author Sasa Trajkova
     */
    public void enableButtons() {
        //TODO enable buttons for the cards that could be played in the round based on game mode

        LoginEntity login = ServiceLocator.get(LoginEntity.class);
        assert login != null;
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
        } else if (gameUtil.getGame().getPlayerTwo().equals(login.getUsername())) {
            user2b1.setDisable(false);
            user2b2.setDisable(false);
            user2b3.setDisable(false);
            user2b4.setDisable(false);
            user2b5.setDisable(false);
            user2b6.setDisable(false);
            user2b7.setDisable(false);
            user2b8.setDisable(false);
            user2b9.setDisable(false);
        } else if (gameUtil.getGame().getPlayerThree().equals(login.getUsername())) {
            user3b1.setDisable(false);
            user3b2.setDisable(false);
            user3b3.setDisable(false);
            user3b4.setDisable(false);
            user3b5.setDisable(false);
            user3b6.setDisable(false);
            user3b7.setDisable(false);
            user3b8.setDisable(false);
            user3b9.setDisable(false);
        } else if (gameUtil.getGame().getPlayerFour().equals(login.getUsername())) {
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
     * Change background color in the player pane if it's the player's turn to
     * play.
     *
     * @author Sasa Trajkova
     */
    public void changePlayerPaneBackground() {
        //TODO change player pane background
    }

    /**
     * Fetch usernames and match them with the right label.
     *
     * @author Sasa Trajkova
     */
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

    @Override
    public void onDisconnectEvent() {
        ServiceLocator.remove(LoginEntity.class);
        SocketUtil socket = ServiceLocator.get(SocketUtil.class);
        if (socket != null) { // Not necessary but keeps IDE happy
            socket.close();
        }
        ServiceLocator.remove(SocketUtil.class);
        WindowUtil.switchToNewWindow(view, ServerConnectionView.class);
    }
}
