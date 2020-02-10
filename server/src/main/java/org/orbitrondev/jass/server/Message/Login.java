package org.orbitrondev.jass.server.Message;

import org.json.JSONObject;
import org.orbitrondev.jass.lib.Message.LoginData;
import org.orbitrondev.jass.lib.Message.MessageData;
import org.orbitrondev.jass.lib.Message.ResultData;
import org.orbitrondev.jass.server.Client;
import org.orbitrondev.jass.server.Entity.User;
import org.orbitrondev.jass.server.Entity.UserRepository;

/**
 * Login to an existing account. If successful, return an authentication token to the client.
 */
public class Login extends Message {
    private LoginData data;

    public Login(MessageData rawData) {
        super(rawData);
        data = (LoginData) rawData;
    }

    @Override
    public void process(Client client) {
        Message reply;
        // Find existing login matching the username
        User user = null;
        if (UserRepository.usernameExists(data.getUsername())) {
            user = UserRepository.getByUsername(data.getUsername());
        }
        if (user != null && user.checkPassword(data.getPassword())) {
            client.setUser(user);
            String token = User.createToken();
            client.setToken(token);

            JSONObject resultData = new JSONObject();
            resultData.put("token", token);
            reply = new Result(new ResultData(true, resultData));
        } else {
            reply = new Result(new ResultData(false));
        }
        client.send(reply);
    }
}
