package org.orbitrondev.jass.lib.Message;

import org.json.JSONObject;

public class DeleteLoginData extends MessageData {
    private final String token;

    public DeleteLoginData(String token) {
        super("DeleteLogin");
        this.token = token;
    }

    public DeleteLoginData(JSONObject data) {
        super(data);
        token = data.getString("token");
    }

    public String getToken() {
        return token;
    }
}
