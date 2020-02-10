package org.orbitrondev.jass.lib.Message;

import org.json.JSONObject;

public class ChangePasswordData extends MessageData {
    private final String token;
    private final String newPassword;

    public ChangePasswordData(String token, String newPassword) {
        super("ChangePassword");
        this.token = token;
        this.newPassword = newPassword;
    }

    public ChangePasswordData(JSONObject data) {
        super(data);
        token = data.getString("token");
        newPassword = data.getString("newPassword");
    }

    public String getToken() {
        return token;
    }

    public String getNewPassword() {
        return newPassword;
    }
}
