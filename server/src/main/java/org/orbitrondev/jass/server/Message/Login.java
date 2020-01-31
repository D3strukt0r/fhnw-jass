package org.orbitrondev.jass.server.Message;

import org.orbitrondev.jass.server.Client;
import org.orbitrondev.jass.server.Entity.User;
import org.orbitrondev.jass.server.Entity.UserRepository;

/**
 * Login to an existing account. If successful, return an authentication token
 * to the client.
 */
public class Login extends Message {
	private String username;
	private String password;

	public Login(String[] data) {
		super(data);
		this.username = data[1];
		this.password = data[2];
	}

	@Override
	public void process(Client client) {
		Message reply;
		// Find existing login matching the username
        User user = null;
        if (UserRepository.usernameExists(username)) {
            user = UserRepository.getByUsername(username);
        }
		if (user != null && user.checkPassword(password)) {
			client.setUser(user);
			String token = User.createToken();
			client.setToken(token);
			reply = new Result(true, token);
		} else {
			reply = new Result(false);
		}
		client.send(reply);
	}
}
