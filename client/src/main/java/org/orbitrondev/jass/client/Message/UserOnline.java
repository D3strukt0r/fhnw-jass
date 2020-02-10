package org.orbitrondev.jass.client.Message;

import org.orbitrondev.jass.client.Utils.BackendUtil;
import org.orbitrondev.jass.lib.Message.MessageData;
import org.orbitrondev.jass.lib.Message.ResultData;
import org.orbitrondev.jass.lib.Message.UserOnlineData;

/**
 * Checks whether the user is currently logged in.
 */
public class UserOnline extends Message {
    private UserOnlineData data;

    public UserOnline(MessageData rawData) {
        super(rawData);
        data = (UserOnlineData) rawData;
    }

    @Override
    public boolean process(BackendUtil backendUtil) {
        backendUtil.send(this);

        Message result = backendUtil.waitForResultResponse(data.getId());
        ResultData resultData = (ResultData) result.getRawData();

        return resultData.getResult();
    }
}
