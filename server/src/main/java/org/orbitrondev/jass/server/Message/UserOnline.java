package org.orbitrondev.jass.server.Message;

import org.orbitrondev.jass.lib.Message.MessageData;
import org.orbitrondev.jass.lib.Message.ResultData;
import org.orbitrondev.jass.lib.Message.UserOnlineData;
import org.orbitrondev.jass.server.Client;
import org.orbitrondev.jass.server.Listener;

public class UserOnline extends Message {
    private UserOnlineData data;

    public UserOnline(MessageData rawData) {
        super(rawData);
        data = (UserOnlineData) rawData;
    }

    /**
     * Anyone can query a specific user: are they currently logged in?
     * <p>
     * Note that "false" can also mean that the user asking the question is not logged in, and therefore cannot ask this
     * question.
     */
    @Override
    public void process(Client client) {
        boolean result = false;
        if (client.getToken() != null && client.getToken().equals(data.getToken())) {
            result = Listener.exists(data.getUsername());
        }
        client.send(new Result(new ResultData(result)));
    }
}
