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
public class GameUtil implements Service, BroadcastDeckEventListener {
    private static final Logger logger = LogManager.getLogger(GameController.class);
    private GameFoundData game;
    private int deckId;
    private ObservableList<CardData> playerDeck;


    //TODO DUMMY DATA - delete when not used anymore
    private String gameMode = "Trumpf";
    private int pointsRound = 14;
    private int pointsTotal = 15;

    public GameUtil() {
        SocketUtil socket = (SocketUtil) ServiceLocator.get("backend");
        if (socket != null) { // Not necessary but keeps IDE happy
            socket.setBroadcastDeckEventListener(this);
        }
    }

    @Override
    public void onDeckBroadcasted(BroadcastDeckData msgData) {
        BroadcastDeckData data = msgData;
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

    public void setGame(GameFoundData msgData) {
        this.game = msgData;
    }

    public GameFoundData getGame() {
        return game;
    }

    public int getDeckId() {
        return deckId;
    }

    public void setDeckId(int deckId) {
        this.deckId = deckId;
    }

    public ObservableList<CardData> getPlayerDeck() {
        return playerDeck;
    }

    public void setPlayerDeck(ArrayList<CardData> playerDeck) {
        this.playerDeck = FXCollections.observableArrayList(playerDeck);
    }

    @Override
    public String getServiceName() {
        return "GameUtil";
    }
}
