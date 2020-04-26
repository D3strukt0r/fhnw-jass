package jass.client.eventlistener;

import jass.lib.message.MessageData;

public interface BroadcastDeckEventListener {
    /**
     * Executes when a game is found.
     * @param msgData
     */
    void onDeckBroadcasted(MessageData msgData);
}
