package org.orbitrondev.jass.server.Message;

import org.orbitrondev.jass.lib.Message.MessageData;
import org.orbitrondev.jass.lib.Message.ResultData;
import org.orbitrondev.jass.server.Client;

public class Logout extends Message {
    public Logout(MessageData rawData) {
        super(rawData);
    }

    @Override
    public void process(Client client) {
        client.setToken(null); // Destroy authentication token
        client.setUser(null); // Destroy account information
        client.send(new Result(new ResultData(true)));
    }
}
