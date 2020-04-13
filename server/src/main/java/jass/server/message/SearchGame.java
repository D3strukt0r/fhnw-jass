package jass.server.message;

import jass.lib.message.SearchGameData;

import jass.lib.message.MessageData;
import jass.lib.message.ResultData;
import jass.lib.message.SearchGameData;
import jass.server.util.ClientUtil;

/**
 * Adds a client to the lobby waiting list
 *
 * @author Thomas Weber
 * @version %I%, %G%
 * @since 0.0.1
 */

public class SearchGame extends Message {
    private final SearchGameData data;

    public SearchGame(final MessageData rawData) {
        super(rawData);
        data = (SearchGameData) rawData;
    }

    @Override
    public void process(final ClientUtil client) {
        boolean result = false;

        // Only continue if the user has the right token.
        if (client.getToken() != null && client.getToken().equals(data.getToken())) {
            // Check if there is anyone connected with the given username.
            System.out.println(client.getToken());
        }

        client.send(new Result(new ResultData(data.getId(), result)));
    }
}
