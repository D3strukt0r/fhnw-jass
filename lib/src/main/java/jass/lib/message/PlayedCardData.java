package jass.lib.message;

import org.json.JSONObject;

/**
 * @author Victor Hargrave & Manuele Vaccari
 * @version %I%, %G%
 * @since 0.0.1
 */
public final class PlayedCardData extends MessageData {
    /**
     * The game ID.
     */
    private final boolean playedCardValid;

    /**
     * This is used by the server to notify the client to give an appropriate
     * answer back.
     */
    public PlayedCardData(boolean playedCardValid) {
        super("PlayedCardValid");
        this.playedCardValid = playedCardValid;
    }

    /**
     * @param data The message containing all the data.
     */
    public PlayedCardData(final JSONObject data) {
        super(data);
        this.playedCardValid = data.getBoolean("playedCardValid");
    }

    /**
     * @return Returns the game ID.
     */
    public boolean getplayedCardValid() {
        return playedCardValid;
    }
}
