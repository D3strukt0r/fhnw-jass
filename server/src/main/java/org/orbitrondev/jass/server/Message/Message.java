package org.orbitrondev.jass.server.Message;

import org.orbitrondev.jass.lib.Message.MessageData;
import org.orbitrondev.jass.server.Client;

public abstract class Message {
    protected final MessageData rawData;

    public Message(MessageData rawData) {
        this.rawData = rawData;
    }

    /**
     * Perform whatever actions are required for this particular type of message.
     */
    public abstract void process(Client client);

    /**
     * A message is really just a bunch of strings separated by vertical bars
     */
    @Override
    public String toString() {
        return MessageData.serialize(rawData);
    }
}