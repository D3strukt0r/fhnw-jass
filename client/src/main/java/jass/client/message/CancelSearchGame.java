package jass.client.message;

import jass.client.util.SocketUtil;
import jass.lib.message.CancelSearchGameData;
import jass.lib.message.MessageData;
import jass.lib.message.ResultData;

public final class CancelSearchGame extends Message {
    /**
     * The data of the message.
     */
    private final CancelSearchGameData data;

    /**
     * @param rawData The data (still not casted)
     */
    public CancelSearchGame(final MessageData rawData) {
        super(rawData);
        data = (CancelSearchGameData) rawData;
    }

    @Override
    public boolean process(final SocketUtil socket) {
        socket.send(this);

        Message result = socket.waitForResultResponse(data.getId());
        ResultData resultData = (ResultData) result.getRawData();

        return resultData.getResult();
    }
}
