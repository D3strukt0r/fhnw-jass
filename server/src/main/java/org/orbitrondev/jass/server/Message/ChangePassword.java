package org.orbitrondev.jass.server.Message;

import org.orbitrondev.jass.lib.Message.ChangePasswordData;
import org.orbitrondev.jass.lib.Message.MessageData;
import org.orbitrondev.jass.lib.Message.ResultData;
import org.orbitrondev.jass.server.Client;
import org.orbitrondev.jass.server.Entity.User;

public class ChangePassword extends Message {
    private ChangePasswordData data;

    public ChangePassword(MessageData rawData) {
        super(rawData);
        data = (ChangePasswordData) rawData;
    }

    @Override
    public void process(Client client) {
        boolean result = false;
        if (client.getToken().equals(data.getToken())) {
            User user = client.getUser();
            user.changePassword(data.getNewPassword());
            result = true;
        }
        client.send(new Result(new ResultData(result)));
    }

}
