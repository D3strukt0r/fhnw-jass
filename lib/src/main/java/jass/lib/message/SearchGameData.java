package jass.lib.message;

import org.json.JSONObject;

/**
 * The data model for the Search Game message.
 *
 * @author Thomas Weber
 */

public class SearchGameData extends MessageData {
    private final String token;
    private final String username;

    public SearchGameData(String token, String username) {
        super("SearchGame");
        this.token = token;
        this.username = username;
    }

    public SearchGameData(JSONObject data) {
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
