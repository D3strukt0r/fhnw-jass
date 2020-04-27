package jass.server.eventlistener;

import jass.lib.message.ChosenGameModeData;

/**
 * @author Manuele Vaccari
 * @version %I%, %G%
 * @since 0.0.1
 */
public interface ChosenGameModeEventListener {
    /**
     * Executes when a user is requested to choose a game mode.
     *
     * @param data The data (basically the ID).
     */
    void onChosenGameMode(ChosenGameModeData data);
}
