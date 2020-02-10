package org.orbitrondev.jass.client.Message;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.orbitrondev.jass.client.Utils.BackendUtil;
import org.orbitrondev.jass.lib.Message.CreateLoginData;
import org.orbitrondev.jass.lib.Message.MessageData;
import org.orbitrondev.jass.lib.Message.ResultData;

/**
 * Register a user on the the server.
 */
public class CreateLogin extends Message {
    private static final Logger logger = LogManager.getLogger(CreateLogin.class);
    private CreateLoginData data;

    public CreateLogin(MessageData rawData) {
        super(rawData);
        data = (CreateLoginData) rawData;
    }

    @Override
    public boolean process(BackendUtil backendUtil) {
        backendUtil.send(this);

        Message result = backendUtil.waitForResultResponse(data.getId());
        ResultData resultData = (ResultData) result.getRawData();

        return resultData.getResult();
    }
}
