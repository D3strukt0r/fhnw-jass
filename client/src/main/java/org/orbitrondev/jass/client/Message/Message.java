package org.orbitrondev.jass.client.Message;

import org.orbitrondev.jass.client.Utils.BackendUtil;
import org.orbitrondev.jass.lib.Message.MessageData;

public abstract class Message {
    protected final MessageData rawData;

    public Message(MessageData data) {
        this.rawData = data;
    }

    public int getId() {
        return rawData.getId();
    }

    public MessageData getRawData() {
        return rawData;
    }

    /**
     * Perform whatever actions are required for this particular type of message.
     */
    public abstract boolean process(BackendUtil backendUtil);

    /**
     * A message is really just a bunch of strings separated by vertical bars
     */
    @Override
    public String toString() {
        return MessageData.serialize(rawData);
    }
}
