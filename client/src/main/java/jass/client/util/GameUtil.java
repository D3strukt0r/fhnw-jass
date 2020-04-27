package jass.client.util;

import jass.client.controller.GameController;
import jass.client.eventlistener.BroadcastDeckEventListener;
import jass.lib.message.BroadcastDeckData;
import jass.lib.message.CardData;
import jass.lib.message.GameFoundData;
import jass.lib.servicelocator.ServiceLocator;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import jass.lib.servicelocator.Service;

import java.util.ArrayList;

/**
 * @author Victor Hargrave
 * @version %I%, %G%
 * @since 0.0.1
 */
public final class GameUtil implements Service, BroadcastDeckEventListener {
    /**
     * The logger to print to console and save in a .log file.
     */
    private static final Logger logger = LogManager.getLogger(GameController.class);

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


    //TODO DUMMY DATA - delete when not used anymore
    private String gameMode = "Trumpf";
    private int pointsRound = 14;
    private int pointsTotal = 15;

    /**
     * An object for a running game.
     */
    public GameUtil() {
        SocketUtil socket = (SocketUtil) ServiceLocator.get(SocketUtil.class);
        if (socket != null) { // Not necessary but keeps IDE happy
            socket.setBroadcastDeckEventListener(this);
        }
    }

    @Override
    public void onDeckBroadcasted(final BroadcastDeckData data) {
        logger.info("Successfully received cards!");
        this.setDeckId(data.getDeckId());
        this.setPlayerDeck(data.getCardsClient());

        // TODO - get rid of this alert, just for demonstration purposes at the moment
        Platform.runLater(() -> {
            CardData c = data.getCardsClient().get(0);
            Alert alert = new Alert(Alert.AlertType.INFORMATION, data.getCardsClient().get(0).getRank() + " " + data.getCardsClient().get(0).getSuit());
            alert.showAndWait();
        });
    }

    /**
     * @param msgData The game.
     */
    public void setGame(final GameFoundData msgData) {
        this.game = msgData;
    }

    /**
     * @return Returns the game.
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
     * @return Returns the deck of the player.
     */
    public ObservableList<CardData> getPlayerDeck() {
        return playerDeck;
    }

    /**
     * @param playerDeck The deck of the player.
     */
    public void setPlayerDeck(final ArrayList<CardData> playerDeck) {
        this.playerDeck = FXCollections.observableArrayList(playerDeck);
    }
}
