package org.orbitrondev.jass.client.Message;

import org.orbitrondev.jass.client.Utils.BackendUtil;
import org.orbitrondev.jass.lib.Message.MessageData;
import org.orbitrondev.jass.lib.Message.MessageErrorData;

/**
 * Login to the server.
 */
public class MessageError extends Message {
    private MessageErrorData data;

    public MessageError(MessageData rawData) {
        super(rawData);
        data = (MessageErrorData) rawData;
    }

    /**
     * This message type does no processing at all (only the server)
     */
    @Override
    public boolean process(BackendUtil backendUtil) {
        // TODO: Somehow show error popup when we get such a message
        return true;
    }
}
