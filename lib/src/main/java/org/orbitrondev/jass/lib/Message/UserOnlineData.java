package org.orbitrondev.jass.lib.Message;

import org.json.JSONObject;

public class UserOnlineData extends MessageData {
    private final String token;
    private final String username;

    public UserOnlineData(String token, String username) {
        super("UserOnline");
        this.token = token;
        this.username = username;
    }

    public UserOnlineData(JSONObject data) {
        super(data);
        token = data.getString("token");
        username = data.getString("username");
    }

    public String getToken() {
        return token;
    }

    public String getUsername() {
        return username;
    }
}
