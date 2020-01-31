package org.orbitrondev.jass.server.Message;

import org.orbitrondev.jass.server.Client;

import java.util.ArrayList;

public abstract class Message {
    private String[] data;

    public Message(String[] data) {
        this.data = data;
    }

    // Special constructor for variable-length messages
    public Message(String[] data, ArrayList<String> elements) {
        this.data = new String[data.length + elements.size()];
        for (int i = 0; i < data.length; i++)
            this.data[i] = data[i];
        for (int i = 0; i < elements.size(); i++)
            this.data[i + data.length] = elements.get(i);
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
        return String.join("|", data);
    }
}
