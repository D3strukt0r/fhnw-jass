package org.orbitrondev.jass.client.Message;

import org.orbitrondev.jass.client.Utils.BackendUtil;
import org.orbitrondev.jass.lib.Message.MessageData;

public class Result extends Message {

    public Result(MessageData rawData) {
        super(rawData);
    }
	
	/**
	 * This message type does no processing at all (only the server)
	 */
	@Override
	public boolean process(BackendUtil backendUtil) {
	    return true;
	}
}
