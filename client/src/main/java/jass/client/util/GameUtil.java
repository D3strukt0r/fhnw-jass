package jass.client.util;

import jass.client.entity.LoginEntity;
import jass.client.eventlistener.BroadcastDeckEventListener;
import jass.client.eventlistener.BroadcastGameModeEventListener;
import jass.client.eventlistener.BroadcastTurnEventListener;
import jass.client.eventlistener.ChooseGameModeEventListener;
import jass.client.eventlistener.PlayCardEventListener;
import jass.client.message.ChosenGameMode;
import jass.client.message.PlayCard;
import jass.lib.Card;
import jass.lib.GameMode;
import jass.lib.message.*;
import jass.lib.servicelocator.ServiceLocator;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
public final class GameUtil implements Service, BroadcastDeckEventListener, ChooseGameModeEventListener, BroadcastGameModeEventListener, PlayCardEventListener, BroadcastTurnEventListener {
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

    private int turnId;

    private SimpleStringProperty startingPlayerUsername = new SimpleStringProperty();

    private SimpleStringProperty winningPlayerUsername = new SimpleStringProperty();

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
     * An object for a running game.
     */
    public GameUtil() {
        SocketUtil socket = ServiceLocator.get(SocketUtil.class);
        assert socket != null;

        socket.setBroadcastDeckEventListener(this);
        socket.addChooseGameModeEventListener(this);
        socket.addBroadcastGameModeEventListener(this);
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
        if(data.getplayedCardValid()) {
            // TODO show error
        };
    }

    @Override
    public void onBroadcastTurn(final BroadcastTurnData data) {
        logger.info("Successfully received turn!");
        turnId = data.getTurnId();
        setStartingPlayerUsername(data.getStartingPlayer());
        setWinningPlayerUsername(data.getWinningPlayer());
        playedCards.clear();
        playerDeck.addAll(data.getPlayedCardsClient());
    }

    public void playCard(int cardId) {
        PlayCard playedCard = new PlayCard(new PlayCardData(this.turnId, cardId));

        SocketUtil socket = ServiceLocator.get(SocketUtil.class);
        assert socket != null;
        socket.send(playedCard);
        logger.info("Sent played card!");
    }

    public void setGame(final GameFoundData msgData) {
        this.game = msgData;
    }

    public GameFoundData getGame() {
        return game;
    }

    public int getDeckId() {
        return deckId;
    }

    public void setDeckId(final int deckId) {
        this.deckId = deckId;
    }

    public ObservableList<CardData> getPlayerDeck() {
        return playerDeck;
    }

    public void setPlayerDeck(final ArrayList<CardData> playerDeck) {
        this.playerDeck = FXCollections.observableArrayList(playerDeck);
    }

    public SimpleObjectProperty<GameMode> getGameModeProperty() {
        return gameMode;
    }

    public SimpleObjectProperty<Card.Suit> getTrumpfProperty() {
        return trumpf;
    }

    public int getTurnId() {
        return turnId;
    }

    public void setTurnId(int turnId) {
        this.turnId = turnId;
    }

    public SimpleStringProperty getStartingPlayerUsername() {
        return startingPlayerUsername;
    }

    public void setStartingPlayerUsername(String startingPlayerUsername) {
        this.startingPlayerUsername.set(startingPlayerUsername);
    }

    public SimpleStringProperty getWinningPlayerUsername() {
        return winningPlayerUsername;
    }

    public void setWinningPlayerUsername(String winningPlayerUsername) {
        this.winningPlayerUsername.set(winningPlayerUsername);
    }

    public ObservableList<CardData> getPlayedCards() {
        return playedCards;
    }
}
