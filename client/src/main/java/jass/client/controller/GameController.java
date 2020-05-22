/*
 * fhnw-jass is jass game programmed in java for a school project.
 * Copyright (C) 2020 Manuele Vaccari & Victor Hargrave & Thomas Weber & Sasa
 * Trajkova
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package jass.client.controller;

import jass.client.entity.LoginEntity;
import jass.client.eventlistener.BroadcastAPlayerQuitEventListener;
import jass.client.eventlistener.BroadcastRoundOverEventListener;
import jass.client.eventlistener.DisconnectEventListener;
import jass.client.message.Logout;
import jass.client.mvc.Controller;
import jass.client.util.EventUtil;
import jass.client.util.GameUtil;
import jass.client.util.I18nUtil;
import jass.client.util.SocketUtil;
import jass.client.util.StringUtil;
import jass.client.util.ViewUtil;
import jass.client.util.WindowUtil;
import jass.client.view.AboutView;
import jass.client.view.LobbyView;
import jass.client.view.LoginView;
import jass.client.view.ServerConnectionView;
import jass.lib.GameMode;
import jass.lib.message.BroadcastAPlayerQuitData;
import jass.lib.message.BroadcastRoundOverData;
import jass.lib.message.CardData;
import jass.lib.message.LogoutData;
import jass.lib.servicelocator.ServiceLocator;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.io.Closeable;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * The controller for the dashboard (game) view.
 *
 * @author Sasa Trajkova & Victor Hargrave & Manuele Vaccari
 * @version %I%, %G%
 * @since 1.0.0
 */
public final class GameController extends Controller implements Closeable, DisconnectEventListener, BroadcastAPlayerQuitEventListener, BroadcastRoundOverEventListener {
    /**
     * The logger to print to console and save in a .log file.
     */
    private static final Logger logger = LogManager.getLogger(GameController.class);

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
     * The 9 buttons of the current user inside an array.
     */
    private ArrayList<Button> cardButtons;

    /**
     * The card that user four played in this round.
     */
    @FXML
    private Button user4played;

    /**
     * The user one pane.
     */
    @FXML
    private BorderPane user1pane;

    /**
     * The user two pane.
     */
    @FXML
    private BorderPane user2pane;

    /**
     * The user two pane.
     */
    @FXML
    private BorderPane user3pane;

    /**
     * The user two pane.
     */
    @FXML
    private BorderPane user4pane;

    /**
     * The running game.
     */
    private GameUtil gameUtil;

    /**
     * Whether the dialog that a round is over is currently closed.
     */
    private boolean roundOverDialogClosed;

    /**
     * @author Sasa Trajkova & Victor Hargrave & Manuele Vaccari
     * @since 1.0.0
     */
    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        EventUtil.addDisconnectListener(this);
        EventUtil.addAPlayerQuitEventListener(this);
        EventUtil.addRoundOverEventListener(this);

        gameUtil = ServiceLocator.get(GameUtil.class);

        logger.info("initialising");
        gameUtil.getPlayerDeck().addListener((ListChangeListener<CardData>) c -> {
            if (gameUtil.getPlayerDeck().size() == 9) {
                logger.info("listener was activated. Now updating cards");
                cardButtons = cardButtonsToArray();
                addClickListenerToCardButtons();
                logger.info("button click listeners created");
                updateCardImages();
                updateUserNames();
                indicateTeam();
            }
        });
        gameUtil.getGameModeProperty().addListener((obs, oldGameMode, newGameMode) -> {
            Platform.runLater(() -> {
                // TODO Make this more beautiful
                if (newGameMode == GameMode.TRUMPF) {
                    String rawTrumpf = gameUtil.getTrumpfProperty().getValue().toString();
                    String trumpf = rawTrumpf.substring(0, 1).toUpperCase() + rawTrumpf.substring(1);
                    // TODO: Translate
                    mode.setText("Mode: " + newGameMode.toString() + " | Card: " + trumpf);
                } else {
                    // TODO: Translate
                    mode.setText("Mode: " + newGameMode.toString());
                }
            });
        });
        gameUtil.getPlayedCards().addListener((ListChangeListener<CardData>) c -> {
            updatePlayedCardImages();
            Optional<CardData> card = gameUtil.getPlayerDeck().stream().filter(d -> d.getCardId() == gameUtil.getCardIdToRemove()).findFirst();
            if (card.isPresent()) {
                card.get().setPlayed(true);
            }
            updateCardImages();
        });
        gameUtil.getWinningPlayerUsername().addListener((obs, oldWinningPlayer, newWinningPlayer) -> {
            // when there is a new winning player
            if (!newWinningPlayer.equals(oldWinningPlayer) && !StringUtil.isNullOrEmpty(newWinningPlayer)) {
                // TODO show dialog
                gameUtil.setWinningPlayerUsername("");
            }
        });
        gameUtil.getDisableButtonsProperty().addListener((obs, oldDisableButtons, newDisableButtons) -> {
            // when there is a new winning player
            disableButtons(newDisableButtons);
        });
        // TODO: Translate
        gameUtil.getPointsRoundProperty().addListener(((observable, oldValue, newValue) -> Platform.runLater(() -> scoreR.setText("Points (Round): " + newValue))));
        gameUtil.getPointsRoundProperty().setValue(0);
        // TODO: Translate
        gameUtil.getPointsTotalProperty().addListener(((observable, oldValue, newValue) -> Platform.runLater(() -> scoreT.setText("Points (Total): " + newValue))));
        gameUtil.getPointsTotalProperty().setValue(0);
        logger.info("observable listeners created");

        gameUtil.setDisableButtons(true);
        logger.info("buttons disabled");

        cardButtons = cardButtonsToArray();
        addClickListenerToCardButtons();
        logger.info("button click listeners created");

        updateUserNames();
        logger.info("updated user names");

        if (gameUtil.getPlayerDeck() != null) {
            updateCardImages();
            logger.info("updated card images");
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

        mHelp.textProperty().bind(I18nUtil.createStringBinding(mHelp.getText()));
        mHelpAbout.textProperty().bind(I18nUtil.createStringBinding(mHelpAbout.getText()));
    }

    /**
     * Disconnect from the server and returns to the server connection window.
     *
     * @author Sasa Trajkova & Manuele Vaccari
     * @since 1.0.0
     */
    @FXML
    private void clickOnDisconnect() {
        onDisconnectEvent();
    }

    /**
     * Keeps the server connection but returns to the login window.
     *
     * @author Sasa Trajkova & Manuele Vaccari
     * @since 1.0.0
     */
    @FXML
    public void clickOnLogout() {
        SocketUtil socket = ServiceLocator.get(SocketUtil.class);
        assert socket != null;

        Logout logoutMsg = new Logout(new LogoutData());
        socket.send(logoutMsg);
        ServiceLocator.remove(LoginEntity.class);

        close();
        WindowUtil.switchTo(getView(), LoginView.class);
    }

    /**
     * Shuts down the application.
     *
     * @author Sasa Trajkova
     * @since 1.0.0
     */
    @FXML
    private void clickOnExit() {
        Platform.exit();
    }

    /**
     * Opens the about window.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    @FXML
    public void clickOnAbout() {
        WindowUtil.openInNewWindow(AboutView.class);
    }

    /**
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    @Override
    public void close() {
        gameUtil.close();
        EventUtil.removeDisconnectListener(this);
        EventUtil.removeAPlayerQuitEventListener(this);
        EventUtil.removeRoundOverEventListener(this);
    }

    /**
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    @Override
    public void onDisconnectEvent() {
        ServiceLocator.remove(LoginEntity.class);
        close();
        WindowUtil.switchTo(getView(), ServerConnectionView.class);
    }

    /**
     * @author Victor Hargrave
     * @since 1.0.0
     */
    @Override
    public void onRoundOver(final BroadcastRoundOverData data) {
        // run on separate thread so events can still come in nicely.
        Platform.runLater(() -> {
            String roundOverMessage = "";
            if (data.getTeam1Points() > data.getTeam2Points()) {
                roundOverMessage = I18nUtil.get("gui.game.roundOverWin", data.getTeam1Player1(), data.getTeam1Player2(), data.getTeam1Points());
            } else if (data.getTeam1Points() < data.getTeam2Points()) {
                roundOverMessage = I18nUtil.get("gui.game.roundOverWin", data.getTeam2Player1(), data.getTeam2Player2(), data.getTeam2Points());
            } else {
                // The have the same amount of points.
                roundOverMessage = I18nUtil.get("gui.game.roundOverDraw", data.getTeam1Points());
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION,
                roundOverMessage,
                ButtonType.OK,
                ButtonType.CANCEL);

            alert.setTitle(I18nUtil.get("gui.game.roundOver"));
            alert.showAndWait();
            roundOverDialogClosed = false;


            if (alert.getResult() == ButtonType.OK) {
                roundOverDialogClosed = true;
                if (gameUtil.getAPlayerLeft()) {
                    showNotificationThatPlayerLeft();
                } else {
                    gameUtil.prepareForNewRound();
                    gameUtil.continuePlaying();
                }
            } else if (alert.getResult() == ButtonType.CANCEL) {
                roundOverDialogClosed = true;
                // if a player has already left, then just leave the game
                if (gameUtil.getAPlayerLeft()) {
                    close();
                    WindowUtil.switchTo(getView(), LobbyView.class);
                }
                gameUtil.setDecidedToLeaveGame(true);
                // send message to server
                gameUtil.stopPlaying();
            }
        });
    }

    /**
     * @author Victor Hargrave
     * @since 1.0.0
     */
    @Override
    public void onAPlayerQuit(final BroadcastAPlayerQuitData data) {
        if (gameUtil.getDecidedToLeaveGame()) {
            close();
            WindowUtil.switchTo(getView(), LobbyView.class);
        } else if (roundOverDialogClosed) {
            showNotificationThatPlayerLeft();
        } else {
            gameUtil.setAPlayerLeft(true);
        }
    }

    /**
     * @author Victor Hargrave
     * @since 1.0.0
     */
    private void showNotificationThatPlayerLeft() {
        Platform.runLater(() -> {
            String anotherPlayerLeftMessage = I18nUtil.get("gui.lobby.aPlayerLeft");
            Alert alert = new Alert(Alert.AlertType.ERROR, anotherPlayerLeftMessage, ButtonType.YES);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES) {
                alert.close();
                close();
                WindowUtil.switchTo(getView(), LobbyView.class);
            }
        });
    }

    /**
     * Display card images in the right player pane.
     *
     * @author Sasa Trajkova & Manuele Vaccari & Victor Hargrave
     * @since 1.0.0
     */
    private void updateCardImages() {
        if (gameUtil.getPlayerDeck().size() == 9) {
            CardData card1 = gameUtil.getPlayerDeck().get(0);
            CardData card2 = gameUtil.getPlayerDeck().get(1);
            CardData card3 = gameUtil.getPlayerDeck().get(2);
            CardData card4 = gameUtil.getPlayerDeck().get(3);
            CardData card5 = gameUtil.getPlayerDeck().get(4);
            CardData card6 = gameUtil.getPlayerDeck().get(5);
            CardData card7 = gameUtil.getPlayerDeck().get(6);
            CardData card8 = gameUtil.getPlayerDeck().get(7);
            CardData card9 = gameUtil.getPlayerDeck().get(8);

            CardData[] allCards = new CardData[] {card1,card2,card3,card4,card5,card6,card7,card8,card9};
            Button[] userOneButtons = new Button[]{user1b1, user1b2, user1b3, user1b4, user1b5, user1b6, user1b7, user1b8, user1b9};
            Button[] userTwoButtons = new Button[]{user2b1, user2b2, user2b3, user2b4, user2b5, user2b6, user2b7, user2b8, user2b9};
            Button[] userThreeButtons = new Button[]{user3b1, user3b2, user3b3, user3b4, user3b5, user3b6, user3b7, user3b8, user3b9};
            Button[] userFourButtons = new Button[]{user4b1, user4b2, user4b3, user4b4, user4b5, user4b6, user4b7, user4b8, user4b9};

            LoginEntity login = ServiceLocator.get(LoginEntity.class);
            assert login != null;

            if (gameUtil.getGame().getPlayerOne().equals(login.getUsername())) {
                for (int i = 0; i <=8; i++){
                    setImage(getCardPath(allCards[i]), userOneButtons[i]);
                    setImage(null, userTwoButtons[i]);
                    setImage(null, userThreeButtons[i]);
                    setImage(null, userFourButtons[i]);

                    //clear background if the card has already been played
                    if(allCards[i].isPlayed()){
                        userOneButtons[i].setBackground(null);
                    }
                }
            } else if (gameUtil.getGame().getPlayerTwo().equals(login.getUsername())) {
                for (int i = 0; i <=8; i++){
                    setImage(getCardPath(allCards[i]), userTwoButtons[i]);
                    setImage(null, userOneButtons[i]);
                    setImage(null, userThreeButtons[i]);
                    setImage(null, userFourButtons[i]);

                    //clear background if the card has already been played
                    if(allCards[i].isPlayed()){
                        userTwoButtons[i].setBackground(null);
                    }
                }
            } else if (gameUtil.getGame().getPlayerThree().equals(login.getUsername())) {
                for (int i = 0; i <=8; i++){
                    setImage(getCardPath(allCards[i]), userThreeButtons[i]);
                    setImage(null, userOneButtons[i]);
                    setImage(null, userTwoButtons[i]);
                    setImage(null, userFourButtons[i]);

                    //clear background if the card has already been played
                    if(allCards[i].isPlayed()){
                        userThreeButtons[i].setBackground(null);
                    }
                }
            } else if (gameUtil.getGame().getPlayerFour().equals(login.getUsername())) {
                for (int i = 0; i <=8; i++){
                    setImage(getCardPath(allCards[i]), userFourButtons[i]);
                    setImage(null, userOneButtons[i]);
                    setImage(null, userTwoButtons[i]);
                    setImage(null, userThreeButtons[i]);

                    //clear background if the card has already been played
                    if(allCards[i].isPlayed()){
                        userFourButtons[i].setBackground(null);
                    }
                }
            }
        }
    }

    /**
     * @author Sasa Trajkova & Victor Hargrave
     * @since 1.0.0
     */
    private void updatePlayedCardImages() {
        if (gameUtil.getPlayedCards() == null) {
            return;
        }
        CardData card1 = gameUtil.getPlayedCards().size() > 0 ? gameUtil.getPlayedCards().get(0) : null;
        CardData card2 = gameUtil.getPlayedCards().size() > 1 ? gameUtil.getPlayedCards().get(1) : null;
        CardData card3 = gameUtil.getPlayedCards().size() > 2 ? gameUtil.getPlayedCards().get(2) : null;
        CardData card4 = gameUtil.getPlayedCards().size() > 3 ? gameUtil.getPlayedCards().get(3) : null;

        LoginEntity login = ServiceLocator.get(LoginEntity.class);
        assert login != null;
        if (gameUtil.getGame() == null) {
            return;
        }
        if (gameUtil.getGame().getPlayerOne().equals(gameUtil.getStartingPlayerUsername().getValue())) {
            setImage(getCardPath(card1), user1played);
            setImage(getCardPath(card2), user2played);
            setImage(getCardPath(card3), user3played);
            setImage(getCardPath(card4), user4played);
        }
        if (gameUtil.getGame().getPlayerTwo().equals(gameUtil.getStartingPlayerUsername().getValue())) {
            setImage(getCardPath(card1), user2played);
            setImage(getCardPath(card2), user3played);
            setImage(getCardPath(card3), user4played);
            setImage(getCardPath(card4), user1played);
        }
        if (gameUtil.getGame().getPlayerThree().equals(gameUtil.getStartingPlayerUsername().getValue())) {
            setImage(getCardPath(card1), user3played);
            setImage(getCardPath(card2), user4played);
            setImage(getCardPath(card3), user1played);
            setImage(getCardPath(card4), user2played);
        }
        if (gameUtil.getGame().getPlayerFour().equals(gameUtil.getStartingPlayerUsername().getValue())) {
            setImage(getCardPath(card1), user4played);
            setImage(getCardPath(card2), user1played);
            setImage(getCardPath(card3), user2played);
            setImage(getCardPath(card4), user3played);
        }
    }

    /**
     * @param card The card information.
     *
     * @return Returns the image path to the corresponding card.
     *
     * @author Manuele Vaccari & Victor Hargrave
     * @since 1.0.0
     */
    private String getCardPath(final CardData card) {
        if (card == null || card.isPlayed()) {
            return null;
        }
        return "/images/cards/" + card.getRank() + "_of_" + card.getSuit() + ".png";
    }

    /**
     * @param pathToImage The image path.
     * @param button      The button to assign a card image.
     *
     * @author Sasa Trajkova
     * @since 1.0.0
     */
    private void setImage(final String pathToImage, final Button button) {
        if (StringUtil.isNullOrEmpty(pathToImage)) {
            BackgroundImage cardBackImage = new BackgroundImage(new Image("/images/cards/back@2x.png"),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(74, 113, true, true, true, false));
            Background cardBackground = new Background(cardBackImage);
            button.setBackground(cardBackground);        }
        else {
            BackgroundImage backgroundImage = new BackgroundImage(new Image(
                getClass().getResource(pathToImage).toExternalForm()),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(74, 113, true, true, true, false));
            Background background = new Background(backgroundImage);
            button.setBackground(background);
        }

        //clear background image and set background color for "played cards" pane
        if(StringUtil.isNullOrEmpty(pathToImage) && button == user1played){
            user1played.setBackground(null);
            user1played.setStyle("-fx-background-color: white");
        }
        if(StringUtil.isNullOrEmpty(pathToImage) && button == user2played){
            user2played.setBackground(null);
            user2played.setStyle("-fx-background-color: white");
        }
        if(StringUtil.isNullOrEmpty(pathToImage) && button == user3played){
            user3played.setBackground(null);
            user3played.setStyle("-fx-background-color: white");
        }
        if(StringUtil.isNullOrEmpty(pathToImage) && button == user4played){
            user4played.setBackground(null);
            user4played.setStyle("-fx-background-color: white");
        }
    }

    /**
     * Method to only enable buttons for those cards that are legal for a
     * specific round.
     *
     * @param disable Whether to disable the buttons of the user or not.
     *
     * @author Sasa Trajkova & Victor Hargrave & Manuele Vaccari
     * @since 1.0.0
     */
    public void disableButtons(final boolean disable) {
        //TODO enable buttons for the cards that could be played in the round based on game mode
        CardData card1 = !gameUtil.getPlayerDeck().isEmpty() ? gameUtil.getPlayerDeck().get(0) : null;
        CardData card2 = !gameUtil.getPlayerDeck().isEmpty() ? gameUtil.getPlayerDeck().get(1) : null;
        CardData card3 = !gameUtil.getPlayerDeck().isEmpty() ? gameUtil.getPlayerDeck().get(2) : null;
        CardData card4 = !gameUtil.getPlayerDeck().isEmpty() ? gameUtil.getPlayerDeck().get(3) : null;
        CardData card5 = !gameUtil.getPlayerDeck().isEmpty() ? gameUtil.getPlayerDeck().get(4) : null;
        CardData card6 = !gameUtil.getPlayerDeck().isEmpty() ? gameUtil.getPlayerDeck().get(5) : null;
        CardData card7 = !gameUtil.getPlayerDeck().isEmpty() ? gameUtil.getPlayerDeck().get(6) : null;
        CardData card8 = !gameUtil.getPlayerDeck().isEmpty() ? gameUtil.getPlayerDeck().get(7) : null;
        CardData card9 = !gameUtil.getPlayerDeck().isEmpty() ? gameUtil.getPlayerDeck().get(8) : null;

        LoginEntity login = ServiceLocator.get(LoginEntity.class);
        assert login != null;
        assert gameUtil.getGame() != null;
        if (gameUtil.getGame().getPlayerOne().equals(login.getUsername())) {
            user1b1.setDisable(card1 != null && card1.isPlayed() ? card1.isPlayed() : disable);
            user1b2.setDisable(card2 != null && card2.isPlayed() ? card2.isPlayed() : disable);
            user1b3.setDisable(card3 != null && card3.isPlayed() ? card3.isPlayed() : disable);
            user1b4.setDisable(card4 != null && card4.isPlayed() ? card4.isPlayed() : disable);
            user1b5.setDisable(card5 != null && card5.isPlayed() ? card5.isPlayed() : disable);
            user1b6.setDisable(card6 != null && card6.isPlayed() ? card6.isPlayed() : disable);
            user1b7.setDisable(card7 != null && card7.isPlayed() ? card7.isPlayed() : disable);
            user1b8.setDisable(card8 != null && card8.isPlayed() ? card8.isPlayed() : disable);
            user1b9.setDisable(card9 != null && card9.isPlayed() ? card9.isPlayed() : disable);
        } else if (gameUtil.getGame().getPlayerTwo().equals(login.getUsername())) {
            user2b1.setDisable(card1 != null && card1.isPlayed() ? card1.isPlayed() : disable);
            user2b2.setDisable(card2 != null && card2.isPlayed() ? card2.isPlayed() : disable);
            user2b3.setDisable(card3 != null && card3.isPlayed() ? card3.isPlayed() : disable);
            user2b4.setDisable(card4 != null && card4.isPlayed() ? card4.isPlayed() : disable);
            user2b5.setDisable(card5 != null && card5.isPlayed() ? card5.isPlayed() : disable);
            user2b6.setDisable(card6 != null && card6.isPlayed() ? card6.isPlayed() : disable);
            user2b7.setDisable(card7 != null && card7.isPlayed() ? card7.isPlayed() : disable);
            user2b8.setDisable(card8 != null && card8.isPlayed() ? card8.isPlayed() : disable);
            user2b9.setDisable(card9 != null && card9.isPlayed() ? card9.isPlayed() : disable);
        } else if (gameUtil.getGame().getPlayerThree().equals(login.getUsername())) {
            user3b1.setDisable(card1 != null && card1.isPlayed() ? card1.isPlayed() : disable);
            user3b2.setDisable(card2 != null && card2.isPlayed() ? card2.isPlayed() : disable);
            user3b3.setDisable(card3 != null && card3.isPlayed() ? card3.isPlayed() : disable);
            user3b4.setDisable(card4 != null && card4.isPlayed() ? card4.isPlayed() : disable);
            user3b5.setDisable(card5 != null && card5.isPlayed() ? card5.isPlayed() : disable);
            user3b6.setDisable(card6 != null && card6.isPlayed() ? card6.isPlayed() : disable);
            user3b7.setDisable(card7 != null && card7.isPlayed() ? card7.isPlayed() : disable);
            user3b8.setDisable(card8 != null && card8.isPlayed() ? card8.isPlayed() : disable);
            user3b9.setDisable(card9 != null && card9.isPlayed() ? card9.isPlayed() : disable);
        } else if (gameUtil.getGame().getPlayerFour().equals(login.getUsername())) {
            user4b1.setDisable(card1 != null && card1.isPlayed() ? card1.isPlayed() : disable);
            user4b2.setDisable(card2 != null && card2.isPlayed() ? card2.isPlayed() : disable);
            user4b3.setDisable(card3 != null && card3.isPlayed() ? card3.isPlayed() : disable);
            user4b4.setDisable(card4 != null && card4.isPlayed() ? card4.isPlayed() : disable);
            user4b5.setDisable(card5 != null && card5.isPlayed() ? card5.isPlayed() : disable);
            user4b6.setDisable(card6 != null && card6.isPlayed() ? card6.isPlayed() : disable);
            user4b7.setDisable(card7 != null && card7.isPlayed() ? card7.isPlayed() : disable);
            user4b8.setDisable(card8 != null && card8.isPlayed() ? card8.isPlayed() : disable);
            user4b9.setDisable(card9 != null && card9.isPlayed() ? card9.isPlayed() : disable);
        }
    }
    /**
     * Change border color if it's the player's turn to play
     *
     * @author Sasa Trajkova
     * @since 1.0.0
     */

    public void indicateTurnToPlay() {

    }

    /**
     * Change background color in the player pane depending on which team they belong to
     *
     * @author Sasa Trajkova
     * @since 1.0.0
     */
    public void indicateTeam() {
        LoginEntity login = ServiceLocator.get(LoginEntity.class);
        assert login != null;
        assert gameUtil.getGame() != null;

        if (gameUtil.getGame() == null) {
            return;
        }

        if(gameUtil.getGame().getPlayerOne().equals(login.getUsername())){
            if(gameUtil.getGame().getPlayerOneTeamId() == gameUtil.getGame().getPlayerTwoTeamId()){
                user1pane.getStyleClass().add("team-pane");
                user2pane.getStyleClass().add("team-pane");
            }
            if(gameUtil.getGame().getPlayerOneTeamId() == gameUtil.getGame().getPlayerThreeTeamId()){
                user1pane.getStyleClass().add("team-pane");
                user3pane.getStyleClass().add("team-pane");
            }
            if(gameUtil.getGame().getPlayerOneTeamId() == gameUtil.getGame().getPlayerFourTeamId()){
                user1pane.getStyleClass().add("team-pane");
                user4pane.getStyleClass().add("team-pane");
            }
        }

        if(gameUtil.getGame().getPlayerTwo().equals(login.getUsername())){
            if(gameUtil.getGame().getPlayerTwoTeamId() == gameUtil.getGame().getPlayerOneTeamId()){
                user2pane.getStyleClass().add("team-pane");
                user1pane.getStyleClass().add("team-pane");
            }
            if(gameUtil.getGame().getPlayerTwoTeamId() == gameUtil.getGame().getPlayerThreeTeamId()){
                user2pane.getStyleClass().add("team-pane");
                user3pane.getStyleClass().add("team-pane");
            }
            if(gameUtil.getGame().getPlayerTwoTeamId() == gameUtil.getGame().getPlayerFourTeamId()){
                user2pane.getStyleClass().add("team-pane");
                user4pane.getStyleClass().add("team-pane");
            }
        }

        if(gameUtil.getGame().getPlayerThree().equals(login.getUsername())){
            if(gameUtil.getGame().getPlayerThreeTeamId() == gameUtil.getGame().getPlayerOneTeamId()){
                user3pane.getStyleClass().add("team-pane");
                user1pane.getStyleClass().add("team-pane");
            }
            if(gameUtil.getGame().getPlayerThreeTeamId() == gameUtil.getGame().getPlayerTwoTeamId()){
                user3pane.getStyleClass().add("team-pane");
                user2pane.getStyleClass().add("team-pane");
            }
            if(gameUtil.getGame().getPlayerThreeTeamId() == gameUtil.getGame().getPlayerFourTeamId()){
                user3pane.getStyleClass().add("team-pane");
                user4pane.getStyleClass().add("team-pane");
            }
        }

        if(gameUtil.getGame().getPlayerFour().equals(login.getUsername())){
            if(gameUtil.getGame().getPlayerFourTeamId() == gameUtil.getGame().getPlayerOneTeamId()){
                user4pane.getStyleClass().add("team-pane");
                user1pane.getStyleClass().add("team-pane");
            }
            if(gameUtil.getGame().getPlayerFourTeamId() == gameUtil.getGame().getPlayerTwoTeamId()){
                user4pane.getStyleClass().add("team-pane");
                user2pane.getStyleClass().add("team-pane");
            }
            if(gameUtil.getGame().getPlayerFourTeamId() == gameUtil.getGame().getPlayerThreeTeamId()){
                user4pane.getStyleClass().add("team-pane");
                user3pane.getStyleClass().add("team-pane");
            }
        }
    }

    /**
     * Fetch usernames and match them with the right label.
     *
     * @author Sasa Trajkova & Victor Hargrave
     * @since 1.0.0
     */
    public void updateUserNames() {
        Platform.runLater(() -> {
            if (gameUtil.getGame() != null && gameUtil.getGame().getPlayerOne() != null) {
                user1.setText(gameUtil.getGame().getPlayerOne());
            } else {
                user1.setText("--");
            }

            if (gameUtil.getGame() != null && gameUtil.getGame().getPlayerTwo() != null) {
                user2.setText(gameUtil.getGame().getPlayerTwo());
            } else {
                user2.setText("--");
            }

            if (gameUtil.getGame() != null && gameUtil.getGame().getPlayerThree() != null) {
                user3.setText(gameUtil.getGame().getPlayerThree());
            } else {
                user3.setText("--");
            }

            if (gameUtil.getGame() != null && gameUtil.getGame().getPlayerFour() != null) {
                user4.setText(gameUtil.getGame().getPlayerFour());
            } else {
                user4.setText("--");
            }
        });
    }

    /**
     * @author ...
     * @since 1.0.0
     */
    private ArrayList<Button> cardButtonsToArray() {
        ArrayList<Button> buttons = new ArrayList<>();
        LoginEntity login = ServiceLocator.get(LoginEntity.class);
        assert login != null;
        buttons.clear();
        if (gameUtil.getGame().getPlayerOne().equals(login.getUsername())) {
            buttons.add(user1b1);
            buttons.add(user1b2);
            buttons.add(user1b3);
            buttons.add(user1b4);
            buttons.add(user1b5);
            buttons.add(user1b6);
            buttons.add(user1b7);
            buttons.add(user1b8);
            buttons.add(user1b9);
        }

        if (gameUtil.getGame().getPlayerTwo().equals(login.getUsername())) {
            buttons.add(user2b1);
            buttons.add(user2b2);
            buttons.add(user2b3);
            buttons.add(user2b4);
            buttons.add(user2b5);
            buttons.add(user2b6);
            buttons.add(user2b7);
            buttons.add(user2b8);
            buttons.add(user2b9);
        }

        if (gameUtil.getGame().getPlayerThree().equals(login.getUsername())) {
            buttons.add(user3b1);
            buttons.add(user3b2);
            buttons.add(user3b3);
            buttons.add(user3b4);
            buttons.add(user3b5);
            buttons.add(user3b6);
            buttons.add(user3b7);
            buttons.add(user3b8);
            buttons.add(user3b9);
        }

        if (gameUtil.getGame().getPlayerFour().equals(login.getUsername())) {
            buttons.add(user4b1);
            buttons.add(user4b2);
            buttons.add(user4b3);
            buttons.add(user4b4);
            buttons.add(user4b5);
            buttons.add(user4b6);
            buttons.add(user4b7);
            buttons.add(user4b8);
            buttons.add(user4b9);
        }
        return buttons;
    }

    /**
     * @author ...
     * @since 1.0.0
     */
    private void addClickListenerToCardButtons() {
        for (int i = 0; i < 9; i++) {
            Button button = cardButtons.get(i);
            int finalI = i;
            button.setOnAction(e -> {
                CardData card = gameUtil.getPlayerDeck().get(finalI);
                gameUtil.playCard(card.getCardId());
                gameUtil.setDisableButtons(true);
            });
        }
    }
}
