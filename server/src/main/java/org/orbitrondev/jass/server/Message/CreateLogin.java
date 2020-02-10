package org.orbitrondev.jass.server.Message;

import org.orbitrondev.jass.lib.Message.CreateLoginData;
import org.orbitrondev.jass.lib.Message.MessageData;
import org.orbitrondev.jass.lib.Message.ResultData;
import org.orbitrondev.jass.server.Client;
import org.orbitrondev.jass.server.Entity.User;
import org.orbitrondev.jass.server.Entity.UserRepository;

/**
 * Create a completely new login. After creating an account, the user must still login!
 */
public class CreateLogin extends Message {
    private CreateLoginData data;

    public CreateLogin(MessageData rawData) {
        super(rawData);
        data = (CreateLoginData) rawData;
    }

    /**
     * We can only create a new account if the name is at least 3 characters, and is not in use either as a user or as a
     * chatroom
     */
    @Override
    public void process(Client client) {
        boolean result = false;
        if (data.getUsername() != null && data.getUsername().length() >= 3) {
            if (data.getPassword() != null && data.getPassword().length() >= 3) { // lax password requirements
                if (!UserRepository.usernameExists(data.getUsername())) {
                    User newUser = new User(data.getUsername(), data.getPassword());
                    UserRepository.create(newUser);
                    result = true;
                }
            }
        }
        client.send(new Result(new ResultData(result)));
    }

}
