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
    /**
     * The deck.
     */
    private final int deckId;

    /**
     * The cards.
     */
    private final JSONArray cards;

    /**
     * The cards.
     */
    private ArrayList<CardData> cardsClient = null;

    /**
     * @param deckId The deck ID.
     * @param cards  The cards.
     */
    public BroadcastDeckData(final int deckId, final List<CardData> cards) {
        super("BroadcastDeck");
        this.deckId = deckId;
        this.cards = new JSONArray(cards);
    }

    /**
     * @param data The message containing all the data.
     */
    public BroadcastDeckData(final JSONObject data) {
        super(data);
        deckId = data.getInt("deckId");
        cards = data.getJSONArray("cards");
        cardsClient = new ArrayList<>();
        for (int i = 0; i < this.cards.length(); i++) {
            JSONObject jsonobject = this.cards.getJSONObject(i);
            int cardId = jsonobject.getInt("cardId");
            int suitId = jsonobject.getInt("suitId");
            String suit = jsonobject.getString("suit");
            int rankId = jsonobject.getInt("rankId");
            String rank = jsonobject.getString("rank");

            cardsClient.add(new CardData(cardId, suitId, suit, rankId, rank));
        }
    }

    /**
     * @return Returns the deck ID.
     */
    public int getDeckId() {
        return deckId;
    }

    /**
     * @return Returns the cards.
     */
    public JSONArray getCards() {
        return cards;
    }

    /**
     * @return Returns the cards.
     */
    public ArrayList<CardData> getCardsClient() {
        return cardsClient;
    }
}
