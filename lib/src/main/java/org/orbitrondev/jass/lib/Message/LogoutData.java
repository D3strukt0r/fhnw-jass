package org.orbitrondev.jass.lib.Message;

import org.json.JSONObject;

public class LogoutData extends MessageData {
    public LogoutData() {
        super("Logout");
    }

    public LogoutData(JSONObject data) {
        super(data);
    }
}
