package jass.lib.message;

import org.json.JSONObject;

/**
 * @author Victor Hargrave & Manuele Vaccari
 * @version %I%, %G%
 * @since 0.0.1
 */
public final class PlayCardData extends MessageData {

    private final int turnId;

    private final int cardId;

    public PlayCardData(final int turnId, final int cardId) {
        super("PlayCard");
        this.turnId = turnId;
        this.cardId = cardId;
    }

    /**
     * @param data The message containing all the data.
     */
    public PlayCardData(final JSONObject data) {
        super(data);
        turnId = data.getInt("turnId");
        cardId = data.getInt("cardId");
    }


    public int getTurnId() {
        return turnId;
    }

    public int getCardId() {
        return cardId;
    }
}
