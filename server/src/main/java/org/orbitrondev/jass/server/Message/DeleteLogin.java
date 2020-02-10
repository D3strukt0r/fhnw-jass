package org.orbitrondev.jass.server.Message;

import org.orbitrondev.jass.lib.Message.DeleteLoginData;
import org.orbitrondev.jass.lib.Message.MessageData;
import org.orbitrondev.jass.lib.Message.ResultData;
import org.orbitrondev.jass.server.Client;
import org.orbitrondev.jass.server.Entity.UserRepository;

public class DeleteLogin extends Message {
    private DeleteLoginData data;

    public DeleteLogin(MessageData rawData) {
        super(rawData);
        data = (DeleteLoginData) rawData;
    }

    @Override
    public void process(Client client) {
        boolean result = false;
        if (client.getToken().equals(data.getToken())) {
            UserRepository.remove(client.getUser());
            client.setToken(null);
            client.setUser(null);
            result = true;
        }
        client.send(new Result(new ResultData(result)));
    }
}
