package jass.lib.message;

import jass.lib.Card;
import jass.lib.GameMode;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * The data model for the BroadcastDeckData message.
 *
 * @author Victor Hargrave
 */
public final class BroadcastGameModeData extends MessageData {
    /**
     * The token for the current session.
     */
    private final GameMode gameMode;

    /**
     * The suit to be the trumpf, if game mode trumpf is chosen.
     */
    private final Card.Suit trumpfSuit;

    /**
     * @param gameMode The game mode.
     */
    public BroadcastGameModeData(final GameMode gameMode) {
        super("BroadcastGameMode");
        this.gameMode = gameMode;
        trumpfSuit = null;
    }

    /**
     * @param gameMode The game mode.
     */
    public BroadcastGameModeData(final GameMode gameMode, final Card.Suit trumpfSuit) {
        super("BroadcastGameMode");
        this.gameMode = gameMode;
        this.trumpfSuit = trumpfSuit;
    }

    /**
     * @param data The message containing all the data.
     */
    public BroadcastGameModeData(final JSONObject data) {
        super(data);
        gameMode = data.getEnum(GameMode.class, "gameMode");
        Card.Suit trumpfSuitTemp;
        try {
            trumpfSuitTemp = data.getEnum(Card.Suit.class, "trumpfSuit");
        } catch (JSONException exception) {
            trumpfSuitTemp = null;
        }
        trumpfSuit = trumpfSuitTemp;
    }

    /**
     * @return Returns the game mode.
     */
    public GameMode getGameMode() {
        return gameMode;
    }

    /**
     * @return Returns the trumpf suit.
     */
    public Card.Suit getTrumpfSuit() {
        return trumpfSuit;
    }
}
