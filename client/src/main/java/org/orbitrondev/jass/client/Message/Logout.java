package org.orbitrondev.jass.client.Message;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.orbitrondev.jass.client.Utils.BackendUtil;
import org.orbitrondev.jass.lib.Message.MessageData;
import org.orbitrondev.jass.lib.Message.ResultData;

/**
 * Logs the current user out from the server. After successful logout, token becomes invalid.
 */
public class Logout extends Message {
    private static final Logger logger = LogManager.getLogger(Logout.class);

    public Logout(MessageData rawData) {
        super(rawData);
    }

    @Override
    public boolean process(BackendUtil backendUtil) {
        backendUtil.send(this);

        Message result = backendUtil.waitForResultResponse(rawData.getId());
        ResultData resultData = (ResultData) result.getRawData();

        return resultData.getResult();
    }
}
