package jass.lib.message;

import org.json.JSONObject;

/**
 * @author Manuele Vaccari
 * @version %I%, %G%
 * @since 0.0.1
 */
public final class BroadcastPlayedCardData extends MessageData {
    /**
     * The username of the user who played the card.
     */
    private final int username;

    /**
     * The card ID which was played.
     */
    private final int cardId;

    /**
     * @param username The username of the user who played the card.
     * @param cardId   The card ID which was played.
     */
    public BroadcastPlayedCardData(final int username, final int cardId) {
        super("BroadcastPlayedCard");
        this.username = username;
        this.cardId = cardId;
    }

    /**
     * @param data The message containing all the data.
     */
    public BroadcastPlayedCardData(final JSONObject data) {
        super(data);
        username = data.getInt("username");
        cardId = data.getInt("cardId");
    }

    /**
     * @return Returns the username.
     */
    public int getUsername() {
        return username;
    }

    /**
     * @return Returns the card ID.
     */
    public int getCardId() {
        return cardId;
    }
}
