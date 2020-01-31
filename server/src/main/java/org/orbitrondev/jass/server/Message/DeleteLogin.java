package org.orbitrondev.jass.server.Message;

import org.orbitrondev.jass.server.Client;
import org.orbitrondev.jass.server.Entity.UserRepository;

public class DeleteLogin extends Message {
	private String token;
	
	public DeleteLogin(String[] data) {
		super(data);
		this.token = data[1];
	}

	@Override
	public void process(Client client) {
		boolean result = false;
		if (client.getToken().equals(token)) {
			UserRepository.remove(client.getUser());
			client.setToken(null);
			client.setUser(null);
			result = true;
		}
		client.send(new Result(result));
	}
}
