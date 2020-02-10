package org.orbitrondev.jass.lib.Message;

import org.json.JSONObject;

public class LoginData extends MessageData {
    private final String username;
    private final String password;

    public LoginData(String username, String password) {
        super("Login");
        this.username = username;
        this.password = password;
    }

    public LoginData(JSONObject data) {
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
