package jass.client.eventlistener;

import jass.lib.message.BroadcastGameModeData;

/**
 * @author Manuele Vaccari
 * @version %I%, %G%
 * @since 0.0.1
 */
public interface BroadcastGameModeEventListener {
    /**
     * Executes when a user is requested to choose a game mode.
     *
     * @param data The data (basically the ID).
     */
    void onBroadcastGameMode(BroadcastGameModeData data);
}
