package jass.lib.message;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Victor Hargrave
 * @version %I%, %G%
 * @since 0.0.1
 */
public final class BroadcastTurnData extends MessageData {

    private final int turnId;

    private final String startingPlayer;

    private final String winningPlayer;

    private final JSONArray playedCards;

    private ArrayList<CardData> playedCardsClient = null;

    public BroadcastTurnData(final int turnId,
                             final String startingPlayer,
                             final String winningPlayer,
                             final List<CardData> playedCards) {
        super("BroadcastTurn");
        this.turnId = turnId;
        this.startingPlayer = startingPlayer;
        this.winningPlayer = winningPlayer;
        this.playedCards = new JSONArray(playedCards);
    }

    /**
     * @param data The message containing all the data.
     */
    public BroadcastTurnData(final JSONObject data) {
        super(data);
        turnId = data.getInt("turnId");
        startingPlayer = data.getString("startingPlayer");
        winningPlayer = data.getString("winningPlayer");
        playedCards = data.getJSONArray("playedCards");
        playedCardsClient = new ArrayList<>();
        for (int i = 0; i < this.playedCards.length(); i++) {
            JSONObject jsonobject = this.playedCards.getJSONObject(i);
            int cardId = jsonobject.getInt("cardId");
            int suitId = jsonobject.getInt("suitId");
            String suit = jsonobject.getString("suit");
            int rankId = jsonobject.getInt("rankId");
            String rank = jsonobject.getString("rank");

            playedCardsClient.add(new CardData(cardId, suitId, suit, rankId, rank));
        }
    }

    public int getTurnId() {
        return turnId;
    }

    public String getStartingPlayer() {
        return startingPlayer;
    }

    public String getWinningPlayer() {
        return winningPlayer;
    }

    public JSONArray getPlayedCards() {
        return playedCards;
    }

    public ArrayList<CardData> getPlayedCardsClient() {
        return playedCardsClient;
    }
}
