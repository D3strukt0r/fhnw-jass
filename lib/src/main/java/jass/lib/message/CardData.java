package jass.lib.message;

import org.json.JSONObject;

/**
 * The data model for the Card Data.
 *
 * @author Victor Hargrave
 */

public final class CardData{
    private final int cardId;

    private final int suitId;

    private final String suit;

    private final int rankId;

    private final String rank;

    public CardData(final int cardId, final int suitId, final String suit, final int rankId, final String rank) {
        this.cardId = cardId;
        this.suitId = suitId;
        this.suit = suit;
        this.rankId = rankId;
        this.rank = rank;
    }

    public String getSuit() {
        return suit;
    }

    public String getRank() {
        return rank;
    }

    public int getCardId() {
        return cardId;
    }

    public int getSuitId() {
        return suitId;
    }

    public int getRankId() {
        return rankId;
    }
}
