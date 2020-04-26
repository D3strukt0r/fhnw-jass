package jass.client.eventlistener;

import jass.lib.message.BroadcastDeckData;

public interface BroadcastDeckEventListener {
    /**
     * Executes when a game is found.
     * @param msgData
     */
    void onDeckBroadcasted(BroadcastDeckData msgData);
}
