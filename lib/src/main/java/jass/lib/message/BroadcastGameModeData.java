package jass.lib.message;

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
    private final ChooseGameModeData.GameMode gameMode;

    /**
     * @param gameMode The game mode.
     */
    public BroadcastGameModeData(final ChooseGameModeData.GameMode gameMode) {
        super("BroadcastGameMode");
        this.gameMode = gameMode;
    }

    /**
     * @param data The message containing all the data.
     */
    public BroadcastGameModeData(final JSONObject data) {
        super(data);
        gameMode = data.getEnum(ChooseGameModeData.GameMode.class, "gameMode");
    }

    /**
     * @return Returns the game mode.
     */
    public ChooseGameModeData.GameMode getGameMode() {
        return gameMode;
    }
}
