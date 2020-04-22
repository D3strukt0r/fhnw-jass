package jass.client.message;

import jass.client.util.SocketUtil;
import jass.lib.message.MessageData;
import jass.lib.message.ResultData;
import jass.lib.message.SearchGameData;

/**
 * Search for a game.
 *
 * @author Thomas Weber
 * @version %I%, %G%
 * @since 0.0.1
 */
public final class SearchGame extends Message {
    /**
     * The data of the message.
     */
    private final SearchGameData data;

    /**
     * @param rawData The data (still not casted)
     */
    public SearchGame(final MessageData rawData) {
        super(rawData);
        data = (SearchGameData) rawData;
    }

    @Override
    public boolean process(final SocketUtil socket) {
        socket.send(this);

        Message result = socket.waitForResultResponse(data.getId());
        ResultData resultData = (ResultData) result.getRawData();

        return resultData.getResult();
    }
}
