package jass.client.message;


/**
 *  Search for a game
 *
 * @author Thomas Weber
 * @version %I%, %G%
 * @since 0.0.1
 */

import jass.client.util.SocketUtil;
import jass.lib.message.MessageData;
import jass.lib.message.MessageData;
import jass.lib.message.ResultData;
import jass.lib.message.SearchGameData;

public class SearchGame extends Message {
    private SearchGameData data;

    public SearchGame(MessageData rawData) {
        super(rawData);
        data = (SearchGameData) rawData;
    }

    @Override
    public boolean process(SocketUtil socket) {
        socket.send(this);

        Message result = socket.waitForResultResponse(data.getId());
        ResultData resultData = (ResultData) result.getRawData();

        return resultData.getResult();
    }

}
