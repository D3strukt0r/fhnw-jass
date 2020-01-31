package org.orbitrondev.jass.server.Message;

import org.orbitrondev.jass.server.Client;
import org.orbitrondev.jass.server.Entity.User;

public class ChangePassword extends Message {
	private String token;
	private String password;
	
	public ChangePassword(String[] data) {
		super(data);
		this.token = data[1];
		this.password = data[2];
	}

	@Override
	public void process(Client client) {
		boolean result = false;
		if (client.getToken().equals(token)) {
			User user = client.getUser();
            user.changePassword(password);
			result = true;
		}
		client.send(new Result(result));
	}

}
