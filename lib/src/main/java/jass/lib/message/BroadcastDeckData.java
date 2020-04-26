

package jass.lib.message;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * The data model for the BroadcastDeckData message.
 *
 * @author Victor Hargrave
 */


public final class BroadcastDeckData extends MessageData {
    private final int deckId;
    private final JSONArray cards;
    private ArrayList<CardData> cardsClient = null;

    public BroadcastDeckData(int deckId, List<CardData> cards) {
        super("BroadcastDeck");
        this.deckId = deckId;
        this.cards = new JSONArray(cards);
    }

    public BroadcastDeckData(final JSONObject data) {
        super(data);
        deckId = data.getInt("deckId");
        cards = data.getJSONArray("cards");
        cardsClient = new ArrayList<CardData>();
        for (int i = 0; i < this.cards.length(); i++) {
            JSONObject jsonobject = this.cards.getJSONObject(i);
            Integer cardId = jsonobject.getInt("cardId");
            Integer suitId = jsonobject.getInt("suitId");
            String suit = jsonobject.getString("suit");
            Integer rankId = jsonobject.getInt("rankId");
            String rank = jsonobject.getString("rank");

            cardsClient.add(new CardData(cardId, suitId, suit, rankId, rank));
        }
    }

    // getters are needed for the client JSON parsing. Do not remove
    public int getDeckId() {
        return deckId;
    }

    public JSONArray getCards() {
        return cards;
    }

    public ArrayList<CardData> getCardsClient() {
        return cardsClient;
    }
}
