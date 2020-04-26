package jass.client.eventlistener;

import jass.lib.message.GameFoundData;

public interface GameFoundEventListener {
    /**
     * Executes when a game is found.
     * @param msgData
     */
    void onGameFound(GameFoundData msgData);
}
