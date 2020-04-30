package jass.lib.message;

import jass.lib.Card;
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
    private final ArrayList<Card> cardsClient = new ArrayList<>();

    /**
     * @param deckId The deck ID.
     * @param cards  The cards.
     */
    public BroadcastDeckData(final int deckId, final List<Card> cards) {
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
        for (int i = 0; i < this.cards.length(); i++) {
            JSONObject jsonobject = this.cards.getJSONObject(i);
            Card.Suit suit = jsonobject.getEnum(Card.Suit.class, "suit");
            Card.Rank rank = jsonobject.getEnum(Card.Rank.class, "rank");
            cardsClient.add(new Card(suit, rank));
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
    public ArrayList<Card> getCardsClient() {
        return cardsClient;
    }
}
