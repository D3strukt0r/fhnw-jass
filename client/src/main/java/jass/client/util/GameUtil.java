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

package jass.client.util;

import jass.client.entity.LoginEntity;
import jass.client.eventlistener.BroadcastDeckEventListener;
import jass.client.eventlistener.BroadcastGameModeEventListener;
import jass.client.eventlistener.BroadcastPointsEventListener;
import jass.client.eventlistener.BroadcastTurnEventListener;
import jass.client.eventlistener.ChooseGameModeEventListener;
import jass.client.eventlistener.PlayedCardEventListener;
import jass.client.message.ChosenGameMode;
import jass.client.message.ContinuePlaying;
import jass.client.message.PlayCard;
import jass.client.message.StopPlaying;
import jass.lib.Card;
import jass.lib.GameMode;
import jass.lib.message.BroadcastDeckData;
import jass.lib.message.BroadcastGameModeData;
import jass.lib.message.BroadcastPointsData;
import jass.lib.message.BroadcastTurnData;
import jass.lib.message.CardData;
import jass.lib.message.ChooseGameModeData;
import jass.lib.message.ChosenGameModeData;
import jass.lib.message.ContinuePlayingData;
import jass.lib.message.GameFoundData;
import jass.lib.message.PlayCardData;
import jass.lib.message.PlayedCardData;
import jass.lib.message.StopPlayingData;
import jass.lib.servicelocator.Service;
import jass.lib.servicelocator.ServiceLocator;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Closeable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Victor Hargrave & Manuele Vaccari
 * @version %I%, %G%
 * @since 1.0.0
 */
public final class GameUtil implements Service, Closeable, BroadcastDeckEventListener, ChooseGameModeEventListener, BroadcastGameModeEventListener, PlayedCardEventListener, BroadcastTurnEventListener, BroadcastPointsEventListener {
    /**
     * The logger to print to console and save in a .log file.
     */
    private static final Logger logger = LogManager.getLogger(GameUtil.class);

    /**
     * The game.
     */
    private GameFoundData game;

    /**
     * The deck of the player.
     */
    private final ObservableList<CardData> playerDeck = FXCollections.observableArrayList(new ArrayList<>());

    /**
     * The current turn ID.
     */
    private int turnId;

    /**
     * The username of the user who starts to play.
     */
    private final SimpleStringProperty startingPlayerUsername = new SimpleStringProperty();

    /**
     * The username of the user who won.
     */
    private final SimpleStringProperty winningPlayerUsername = new SimpleStringProperty();

    /**
     * Whether to disable the buttons at the moment.
     */
    private final SimpleBooleanProperty disableButtons = new SimpleBooleanProperty();

    /**
     * The played cards so far.
     */
    private final ObservableList<CardData> playedCards = FXCollections.observableArrayList(new ArrayList<>());

    /**
     * The game mode data.
     */
    private final SimpleObjectProperty<GameMode> gameMode = new SimpleObjectProperty<>();

    /**
     * The trumpf.
     */
    private final SimpleObjectProperty<Card.Suit> trumpf = new SimpleObjectProperty<>();

    /**
     * The error message when a move is invalid.
     */
    private String moveInvalidErrorMessage = "";

    /**
     * The card ID to be removed.
     */
    private int cardIdToRemove = 0;

    /**
     * The current points of the round.
     */
    private final SimpleIntegerProperty pointsRound = new SimpleIntegerProperty(-1);

    /**
     * The current points of the game.
     */
    private final SimpleIntegerProperty pointsTotal = new SimpleIntegerProperty(-1);

    /**
     * Whether the player has left.
     */
    private boolean aPlayerLeft;

    /**
     * Whether the user has decided to leave the game.
     */
    private boolean decidedToLeaveGame;

    /**
     * Initialize GameUtil before a game starts.
     *
     * @author Manuele Vaccari & Victor Hargrave
     * @since 1.0.0
     */
    public GameUtil() {
        EventUtil.addBroadcastDeckEventListener(this);
        EventUtil.addChooseGameModeEventListener(this);
        EventUtil.addBroadcastGameModeEventListener(this);
        EventUtil.addPlayedCardEventListener(this);
        EventUtil.addBroadcastedTurnEventListener(this);
        EventUtil.addBroadcastPointsEventListener(this);
    }

    /**
     * @author Victor Hargrave & Manuele Vaccari
     * @since 1.0.0
     */
    @Override
    public void close() {
        game = null;
        playerDeck.clear();
        turnId = 0;
        startingPlayerUsername.setValue("");
        winningPlayerUsername.setValue("");
        disableButtons.set(true);
        playedCards.clear();
        moveInvalidErrorMessage = "";
        cardIdToRemove = 0;
        pointsRound.set(0);
        pointsTotal.set(0);
        aPlayerLeft = false;
        decidedToLeaveGame = false;

        EventUtil.removeBroadcastDeckEventListener(this);
        EventUtil.removeChooseGameModeEventListener(this);
        EventUtil.removeBroadcastGameModeEventListener(this);
        EventUtil.removePlayedCardEventListener(this);
        EventUtil.removeBroadcastedTurnEventListener(this);
        EventUtil.removeBroadcastPointsEventListener(this);
    }

    /**
     * @author Victor Hargrave
     * @since 1.0.0
     */
    @Override
    public void onBroadcastDeck(final BroadcastDeckData data) {
        logger.info("Successfully received cards!");
        playerDeck.clear();
        playerDeck.addAll(data.getCardsClient());
    }

    /**
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    @Override
    public void onChooseGameMode(final ChooseGameModeData data) {
        Platform.runLater(() -> {
            // Save all the possible game modes...
            List<String> choices = new ArrayList<>();
            Arrays.stream(GameMode.values()).forEach(gameMode -> choices.add(gameMode.toString()));

            // Preparing the dialog...
            ChoiceDialog<String> dialog = new ChoiceDialog<>("", choices);
            dialog.setTitle(I18nUtil.get("gui.game.dialog.chooseGameMode.title"));
            dialog.setHeaderText(null);
            dialog.setContentText(I18nUtil.get("gui.game.dialog.chooseGameMode.content"));
            Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(getClass().getResource("/images/icon.png").toString()));

            // Show the dialog until the player chooses something
            String result = "";
            while (result == null || result.length() == 0) {
                result = dialog.showAndWait().orElse(null);
            }

            GameMode gameMode = GameMode.fromString(result);
            LoginEntity login = ServiceLocator.get(LoginEntity.class);
            assert login != null;

            // If trumpf, choose which card to be trumpf
            ChosenGameMode chosenGameMode;
            if (gameMode == GameMode.TRUMPF) {
                // Save all the possible card types...
                List<String> choices2 = new ArrayList<>();
                Arrays.stream(Card.Suit.values()).forEach(suit -> choices2.add(suit.toString()));

                // Preparing the dialog...
                ChoiceDialog<String> dialog2 = new ChoiceDialog<>("", choices2);
                dialog2.setTitle(I18nUtil.get("gui.game.dialog.chooseTrumpf.title"));
                dialog2.setHeaderText(null);
                dialog2.setContentText(I18nUtil.get("gui.game.dialog.chooseTrumpf.content"));
                Stage stage2 = (Stage) dialog2.getDialogPane().getScene().getWindow();
                stage2.getIcons().add(new Image(getClass().getResource("/images/icon.png").toString()));

                // Show the dialog until the player chooses something
                String result2 = "";
                while (result2 == null || result2.length() == 0) {
                    result2 = dialog2.showAndWait().orElse(null);
                }

                Card.Suit suit = Card.Suit.fromString(result2);
                chosenGameMode = new ChosenGameMode(new ChosenGameModeData(data.getId(), login.getToken(), gameMode, suit));
            } else {
                chosenGameMode = new ChosenGameMode(new ChosenGameModeData(data.getId(), login.getToken(), gameMode));
            }

            // Return the chosen game mode back to the server.
            SocketUtil socket = ServiceLocator.get(SocketUtil.class);
            assert socket != null;
            socket.send(chosenGameMode);
            logger.info("Sent game mode!");
        });
    }

    /**
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    @Override
    public void onBroadcastGameMode(final BroadcastGameModeData data) {
        logger.info("Received Game mode!");
        if (data.getGameMode() == GameMode.TRUMPF) {
            trumpf.setValue(data.getTrumpfSuit());
        }
        gameMode.setValue(data.getGameMode());
    }

    /**
     * @author Victor Hargrave
     * @since 1.0.0
     */
    @Override
    public void onPlayedCard(final PlayedCardData data) {
        if (!data.getPlayedCardValid()) {
            Platform.runLater(() -> {
                moveInvalidErrorMessage = I18nUtil.get("gui.game.error.moveInvalid");
                Alert alert = new Alert(Alert.AlertType.ERROR, moveInvalidErrorMessage, ButtonType.YES);
                alert.showAndWait();

                if (alert.getResult() == ButtonType.YES) {
                    this.setDisableButtons(false);
                    alert.close();
                }
            });
        }
    }

    /**
     * @author Victor Hargrave
     * @since 1.0.0
     */
    @Override
    public void onBroadcastTurn(final BroadcastTurnData data) {
        logger.info("Successfully received turn!");
        turnId = data.getTurnId();
        setStartingPlayerUsername(data.getStartingPlayer());
        setWinningPlayerUsername(data.getWinningPlayer());
        if (isCurrentPlayersTurn(data.getPlayedCardsClient().size())) {
            this.setDisableButtons(false);
        }
        playedCards.clear();
        playedCards.addAll(data.getPlayedCardsClient());
    }

    /**
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    @Override
    public void onBroadcastPoints(final BroadcastPointsData data) {
        logger.info("Gained points in this round: " + data.getPoints());
        pointsRound.setValue(pointsRound.getValue() + data.getPoints());
        logger.info("Total points round: " + pointsRound.getValue());
        pointsTotal.setValue(pointsTotal.getValue() + data.getPoints());
        logger.info("Total points game: " + pointsTotal.getValue());
    }

    /**
     * @author Victor Hargrave
     * @since 1.0.0
     */
    public void stopPlaying() {
        StopPlaying stopPlayingMessage = new StopPlaying(new StopPlayingData());

        SocketUtil socket = ServiceLocator.get(SocketUtil.class);
        assert socket != null;
        socket.send(stopPlayingMessage);
        logger.info("Sent stop playing message!");
    }

    /**
     * @author Thomas Weber
     * @since 1.0.0
     */
    public void continuePlaying() {
        ContinuePlaying continuePlayingMessage = new ContinuePlaying(new ContinuePlayingData());

        SocketUtil socket = ServiceLocator.get(SocketUtil.class);
        assert socket != null;
        socket.send(continuePlayingMessage);
        logger.info("Sent continue playing message!");
    }

    /**
     * @param cardId The card ID to be played and sent to the server.
     *
     * @author Victor Hargrave
     * @since 1.0.0
     */
    public void playCard(final int cardId) {
        cardIdToRemove = cardId;
        PlayCard playedCard = new PlayCard(new PlayCardData(this.turnId, cardId));

        SocketUtil socket = ServiceLocator.get(SocketUtil.class);
        assert socket != null;
        socket.send(playedCard);
        logger.info("Sent played card!");
    }

    /**
     * @param numberOfPlayedCards How many cards have been played so far.
     *
     * @return Returns true if it's this player's turn, otherwise false.
     *
     * @author Victor Hargrave
     * @since 1.0.0
     */
    private boolean isCurrentPlayersTurn(final int numberOfPlayedCards) {
        String player = "";
        if (getGame().getPlayerOne().equals(getStartingPlayerUsername().getValue())) {
            if (numberOfPlayedCards == 0) {
                player = getGame().getPlayerOne();
            } else if (numberOfPlayedCards == 1) {
                player = getGame().getPlayerTwo();
            } else if (numberOfPlayedCards == 2) {
                player = getGame().getPlayerThree();
            } else if (numberOfPlayedCards == 3) {
                player = getGame().getPlayerFour();
            }
        }
        if (getGame().getPlayerTwo().equals(getStartingPlayerUsername().getValue())) {
            if (numberOfPlayedCards == 0) {
                player = getGame().getPlayerTwo();
            } else if (numberOfPlayedCards == 1) {
                player = getGame().getPlayerThree();
            } else if (numberOfPlayedCards == 2) {
                player = getGame().getPlayerFour();
            } else if (numberOfPlayedCards == 3) {
                player = getGame().getPlayerOne();
            }
        }
        if (getGame().getPlayerThree().equals(getStartingPlayerUsername().getValue())) {
            if (numberOfPlayedCards == 0) {
                player = getGame().getPlayerThree();
            } else if (numberOfPlayedCards == 1) {
                player = getGame().getPlayerFour();
            } else if (numberOfPlayedCards == 2) {
                player = getGame().getPlayerOne();
            } else if (numberOfPlayedCards == 3) {
                player = getGame().getPlayerTwo();
            }
        }
        if (getGame().getPlayerFour().equals(getStartingPlayerUsername().getValue())) {
            if (numberOfPlayedCards == 0) {
                player = getGame().getPlayerFour();
            } else if (numberOfPlayedCards == 1) {
                player = getGame().getPlayerOne();
            } else if (numberOfPlayedCards == 2) {
                player = getGame().getPlayerTwo();
            } else if (numberOfPlayedCards == 3) {
                player = getGame().getPlayerThree();
            }
        }
        LoginEntity login = ServiceLocator.get(LoginEntity.class);
        assert login != null;
        return login.getUsername().equals(player);
    }

    /**
     * @author Thomas Weber
     * @since 1.0.0
     */
    public void prepareForNewRound() {
        playedCards.clear();
        playerDeck.clear();
        startingPlayerUsername.setValue("");
        winningPlayerUsername.setValue("");
        disableButtons.set(true);
        cardIdToRemove = 0;
        pointsRound.set(0);
    }

    /**
     * @param msgData The game data.
     *
     * @author Victor Hargrave
     * @since 1.0.0
     */
    public void setGame(final GameFoundData msgData) {
        this.game = msgData;
    }

    /**
     * @return Returns the game data.
     *
     * @author Victor Hargrave
     * @since 1.0.0
     */
    public GameFoundData getGame() {
        return game;
    }

    /**
     * @return Returns the player's deck.
     *
     * @author Victor Hargrave
     * @since 1.0.0
     */
    public ObservableList<CardData> getPlayerDeck() {
        return playerDeck;
    }

    /**
     * @param startingPlayerUsername The username of the player who starts
     *                               playing.
     *
     * @author Victor Hargrave
     * @since 1.0.0
     */
    public void setStartingPlayerUsername(final String startingPlayerUsername) {
        this.startingPlayerUsername.setValue(startingPlayerUsername);
    }

    /**
     * @return Returns the username of the player who starts playing.
     *
     * @author Victor Hargrave
     * @since 1.0.0
     */
    public SimpleStringProperty getStartingPlayerUsername() {
        return startingPlayerUsername;
    }

    /**
     * @param winningPlayerUsername The winnings player's username.
     *
     * @author Victor Hargrave
     * @since 1.0.0
     */
    public void setWinningPlayerUsername(final String winningPlayerUsername) {
        this.winningPlayerUsername.setValue(winningPlayerUsername);
    }

    /**
     * @return Returns the winning player's username.
     *
     * @author Victor Hargrave
     * @since 1.0.0
     */
    public SimpleStringProperty getWinningPlayerUsername() {
        return winningPlayerUsername;
    }

    /**
     * @param disableButtons Whether to disable the user's cards or not.
     *
     * @author Victor Hargrave
     * @since 1.0.0
     */
    public void setDisableButtons(final boolean disableButtons) {
        this.disableButtons.setValue(disableButtons);
    }

    /**
     * @return Returns the property of whether to disable the user's cards.
     *
     * @author Victor Hargrave
     * @since 1.0.0
     */
    public SimpleBooleanProperty getDisableButtonsProperty() {
        return disableButtons;
    }

    /**
     * @return Returns a list of the user's cards.
     *
     * @author Victor Hargrave
     * @since 1.0.0
     */
    public ObservableList<CardData> getPlayedCards() {
        return playedCards;
    }

    /**
     * @return Returns the property of the game mode variable.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    public SimpleObjectProperty<GameMode> getGameModeProperty() {
        return gameMode;
    }

    /**
     * @return Returns the property of the trumpf variable.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    public SimpleObjectProperty<Card.Suit> getTrumpfProperty() {
        return trumpf;
    }

    /**
     * @return Returns card ID which is supposed to be removed.
     *
     * @author Victor Hargrave
     * @since 1.0.0
     */
    public int getCardIdToRemove() {
        return cardIdToRemove;
    }

    /**
     * @return Returns the points of the round property.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    public SimpleIntegerProperty getPointsRoundProperty() {
        return pointsRound;
    }

    /**
     * @return Returns the points of the game property.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    public SimpleIntegerProperty getPointsTotalProperty() {
        return pointsTotal;
    }

    /**
     * @param aPlayerLeft The player who is left.
     *
     * @author Victor Hargrave
     * @since 1.0.0
     */
    public void setAPlayerLeft(final boolean aPlayerLeft) {
        this.aPlayerLeft = aPlayerLeft;
    }

    /**
     * @return Returns player who is left.
     *
     * @author Victor Hargrave
     * @since 1.0.0
     */
    public boolean getAPlayerLeft() {
        return aPlayerLeft;
    }

    /**
     * @param decidedToLeaveGame Whether the user decided to leave
     *
     * @author Victor Hargrave
     * @since 1.0.0
     */
    public void setDecidedToLeaveGame(final boolean decidedToLeaveGame) {
        this.decidedToLeaveGame = decidedToLeaveGame;
    }

    /**
     * @return Returns whether the user decided to leave
     *
     * @author Victor Hargrave
     * @since 1.0.0
     */
    public boolean getDecidedToLeaveGame() {
        return decidedToLeaveGame;
    }
}
