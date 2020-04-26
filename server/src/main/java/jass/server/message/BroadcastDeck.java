package jass.server.message;

import jass.lib.message.BroadcastDeckData;
import jass.lib.message.MessageData;
import jass.server.util.ClientUtil;

/**
 * Sends message to client for game found.
 *
 * @author Victor Hargrave
 * @version %I%, %G%
 * @since 0.0.1
 */
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
    public void process(final ClientUtil client) {
        client.send(this);

        // TODO - Validation if client received message successfully?
    }
}
