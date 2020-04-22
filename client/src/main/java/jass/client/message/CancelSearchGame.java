package jass.client.message;

import jass.client.util.SocketUtil;
import jass.lib.message.CancelSearchGameData;
import jass.lib.message.MessageData;
import jass.lib.message.ResultData;

public class CancelSearchGame extends Message {
    private CancelSearchGameData data;

    public CancelSearchGame(MessageData rawData) {
        super(rawData);
        data = (CancelSearchGameData) rawData;
    }

    @Override
    public boolean process(SocketUtil socket) {
        socket.send(this);

        Message result = socket.waitForResultResponse(data.getId());
        ResultData resultData = (ResultData) result.getRawData();

        return resultData.getResult();
    }

}
