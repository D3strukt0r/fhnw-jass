package org.orbitrondev.jass.server.Message;

import org.orbitrondev.jass.server.Client;

public class MessageError extends Message {
    public MessageError() {
        super(new String[] {"MessageError", "Invalid command"});
    }

    /**
     * This message type does no processing at all
     */
    @Override
    public void process(Client client) {}
}
