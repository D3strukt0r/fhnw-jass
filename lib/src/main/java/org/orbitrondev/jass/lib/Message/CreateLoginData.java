package org.orbitrondev.jass.lib.Message;

import org.json.JSONObject;

public class CreateLoginData extends MessageData {
    private final String username;
    private final String password;

    public CreateLoginData(String username, String password) {
        super("CreateLogin");
        this.username = username;
        this.password = password;
    }

    public CreateLoginData(JSONObject data) {
        super(data);
        username = data.getString("username");
        password = data.getString("password");
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
