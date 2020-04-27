package jass.client.eventlistener;

import jass.lib.message.BroadcastDeckData;

public interface BroadcastDeckEventListener {
    /**
     * Executes when a game is found.
     *
     * @param msgData The deck of the user.
     */
    void onDeckBroadcasted(BroadcastDeckData msgData);
}
