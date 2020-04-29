package jass.server.message;

import jass.lib.message.MessageData;
import jass.server.util.ClientUtil;

/**
 * Sends message to client for game found.
 *
 * @author Thomas Weber
 * @version %I%, %G%
 * @since 0.0.1
 */
public final class GameFound extends Message {
    /**
     * @param rawData The data (still not casted)
     */
    public GameFound(final MessageData rawData) {
        super(rawData);
    }

    @Override
    public void process(final ClientUtil client) {
        client.send(this);
    }
}
