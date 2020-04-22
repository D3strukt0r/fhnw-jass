package jass.client.message;

import jass.client.util.SocketUtil;
import jass.lib.message.GameFoundData;
import jass.lib.message.MessageData;

public final class GameFound extends Message {
    /**
     * The data of the message.
     */
    private final GameFoundData data;

    /**
     * @param rawData The data (still not casted)
     */
    public GameFound(final MessageData rawData) {
        super(rawData);
        data = (GameFoundData) rawData;
    }

    @Override
    public boolean process(final SocketUtil socket) {
        return false;
    }
}
