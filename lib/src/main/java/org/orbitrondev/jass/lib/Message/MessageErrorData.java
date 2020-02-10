package org.orbitrondev.jass.lib.Message;

import org.json.JSONObject;

public class MessageErrorData extends MessageData {
    private final ErrorType errorMessage;

    public enum ErrorType {
        INVALID_COMMAND
    }

    public MessageErrorData(ErrorType errorMessage) {
        super("MessageError");
        this.errorMessage = errorMessage;
    }

    public MessageErrorData(JSONObject data) {
        super(data);
        errorMessage = data.getEnum(ErrorType.class, "errorMessage");
    }

    public ErrorType getErrorMessage() {
        return errorMessage;
    }
}
