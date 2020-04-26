package jass.client.message;

import jass.client.util.SocketUtil;
import jass.lib.message.BroadcastDeckData;
import jass.lib.message.MessageData;

public final class BroadcastDeck extends Message {
    /**
     * The data of the message.
     */
    private final BroadcastDeckData data;

    /**
     * @param rawData The data (still not casted)
     */
    public BroadcastDeck(final MessageData rawData) {
        super(rawData);
        data = (BroadcastDeckData) rawData;
    }

    @Override
    public boolean process(final SocketUtil socket) {
        return false;
    }
}
