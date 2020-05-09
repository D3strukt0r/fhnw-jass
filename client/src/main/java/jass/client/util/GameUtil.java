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
import jass.client.eventlistener.BroadcastTurnEventListener;
import jass.client.eventlistener.ChooseGameModeEventListener;
import jass.client.eventlistener.PlayedCardEventListener;
import jass.client.message.ChosenGameMode;
import jass.client.message.PlayCard;
import jass.lib.Card;
import jass.lib.GameMode;
import jass.lib.message.BroadcastDeckData;
import jass.lib.message.BroadcastGameModeData;
import jass.lib.message.BroadcastTurnData;
import jass.lib.message.CardData;
import jass.lib.message.ChooseGameModeData;
import jass.lib.message.ChosenGameModeData;
import jass.lib.message.GameFoundData;
import jass.lib.message.PlayCardData;
import jass.lib.message.PlayedCardData;
import jass.lib.servicelocator.ServiceLocator;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
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
import jass.lib.servicelocator.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Victor Hargrave, Manuele Vaccari
 * @version %I%, %G%
 * @since 0.0.1
 */
public final class GameUtil implements Service, BroadcastDeckEventListener, ChooseGameModeEventListener, BroadcastGameModeEventListener, PlayedCardEventListener, BroadcastTurnEventListener {
    /**
     * The logger to print to console and save in a .log file.
     */
    private static final Logger logger = LogManager.getLogger(GameUtil.class);

    /**
     * The game.
     */
    private GameFoundData game;

    /**
     * The deck ID.
     */
    private int deckId;

    /**
     * The deck of the player.
     */
    private ObservableList<CardData> playerDeck;

    /**
     * The current turn ID.
     */
    private int turnId;

    /**
     * The username of the user who starts to play.
     */
    private SimpleStringProperty startingPlayerUsername = new SimpleStringProperty();

    /**
     * The username of the user who won.
     */
    private SimpleStringProperty winningPlayerUsername = new SimpleStringProperty();

    /**
     * Whether to disable the buttons at the moment.
     */
    private SimpleBooleanProperty disableButtons = new SimpleBooleanProperty();

    /**
     * The played cards so far.
     */
    private ObservableList<CardData> playedCards;

    /**
     * The game mode data.
     */
    private SimpleObjectProperty<GameMode> gameMode = new SimpleObjectProperty<>();

    /**
     * The trumpf.
     */
    private SimpleObjectProperty<Card.Suit> trumpf = new SimpleObjectProperty<>();

    /**
     * The error message when a move is invalid.
     */
    private String moveInvalidErrorMessage = "";

    /**
     * The card ID to be removed.
     */
    private int cardIdToRemove = 0;

    /**
     * Initialize GameUtil before a game starts.
     */
    public GameUtil() {
        SocketUtil socket = ServiceLocator.get(SocketUtil.class);
        assert socket != null;

        socket.setBroadcastDeckEventListener(this);
        socket.addChooseGameModeEventListener(this);
        socket.addBroadcastGameModeEventListener(this);
        socket.addPlayedCardEventListener(this);
        socket.addBroadcastedTurnEventListener(this);
        playerDeck = FXCollections.observableArrayList(new ArrayList<>());
        playedCards = FXCollections.observableArrayList(new ArrayList<>());
    }

    @Override
    public void onBroadcastDeck(final BroadcastDeckData data) {
        logger.info("Successfully received cards!");
        deckId = data.getDeckId();
        playerDeck.clear();
        playerDeck.addAll(data.getCardsClient());
    }

    @Override
    public void onChooseGameMode(final ChooseGameModeData data) {
        Platform.runLater(() -> {
            // Save all the possible game modes...
            List<String> choices = new ArrayList<>();
            Arrays.stream(GameMode.values()).forEach(gameMode -> choices.add(gameMode.toString()));

            // Preparing the dialog...
            ChoiceDialog<String> dialog = new ChoiceDialog<>("", choices);
            dialog.setTitle("Choose Game Mode");
            dialog.setHeaderText(null);
            dialog.setContentText("Choose the game mode to play:");
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
                dialog2.setTitle("Choose suit");
                dialog2.setHeaderText(null);
                dialog2.setContentText("Choose suit for trumpf:");
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

    @Override
    public void onBroadcastGameMode(final BroadcastGameModeData data) {
        logger.info("Received Game mode!");
        if (data.getGameMode() == GameMode.TRUMPF) {
            trumpf.setValue(data.getTrumpfSuit());
        }
        gameMode.setValue(data.getGameMode());
    }

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
     * @param cardId The card ID to be played and sent to the server.
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
     * @param msgData The game data.
     */
    public void setGame(final GameFoundData msgData) {
        this.game = msgData;
    }

    /**
     * @return Returns the game data.
     */
    public GameFoundData getGame() {
        return game;
    }

    /**
     * @return Returns the deck ID.
     */
    public int getDeckId() {
        return deckId;
    }

    /**
     * @param deckId The deck ID.
     */
    public void setDeckId(final int deckId) {
        this.deckId = deckId;
    }

    /**
     * @return Returns the player's deck.
     */
    public ObservableList<CardData> getPlayerDeck() {
        return playerDeck;
    }

    /**
     * @param playerDeck The player's deck.
     */
    public void setPlayerDeck(final ArrayList<CardData> playerDeck) {
        this.playerDeck = FXCollections.observableArrayList(playerDeck);
    }

    /**
     * @return Returns the property of the game mode variable.
     */
    public SimpleObjectProperty<GameMode> getGameModeProperty() {
        return gameMode;
    }

    /**
     * @return Returns the property of the trumpf variable.
     */
    public SimpleObjectProperty<Card.Suit> getTrumpfProperty() {
        return trumpf;
    }

    /**
     * @return Returns the turn ID.
     */
    public int getTurnId() {
        return turnId;
    }

    /**
     * @param turnId The turn ID.
     */
    public void setTurnId(final int turnId) {
        this.turnId = turnId;
    }

    /**
     * @return Returns the username of the player who starts playing.
     */
    public SimpleStringProperty getStartingPlayerUsername() {
        return startingPlayerUsername;
    }

    /**
     * @param startingPlayerUsername The username of the player who starts
     *                               playing.
     */
    public void setStartingPlayerUsername(final String startingPlayerUsername) {
        this.startingPlayerUsername.setValue(startingPlayerUsername);
    }

    /**
     * @return Returns the winning player's username.
     */
    public SimpleStringProperty getWinningPlayerUsername() {
        return winningPlayerUsername;
    }

    /**
     * @param disableButtons Whether to disable the user's cards or not.
     */
    public void setDisableButtons(final boolean disableButtons) {
        this.disableButtons.setValue(disableButtons);
    }

    /**
     * @return Returns the property of whether to disable the user's cards.
     */
    public SimpleBooleanProperty getDisableButtons() {
        return disableButtons;
    }

    /**
     * @param winningPlayerUsername The winnings player's username.
     */
    public void setWinningPlayerUsername(final String winningPlayerUsername) {
        this.winningPlayerUsername.setValue(winningPlayerUsername);
    }

    /**
     * @return Returns a list of the user's cards.
     */
    public ObservableList<CardData> getPlayedCards() {
        return playedCards;
    }

    /**
     * @return Returns card ID which is supposed to be removed.
     */
    public int getCardIdToRemove() {
        return cardIdToRemove;
    }
}
