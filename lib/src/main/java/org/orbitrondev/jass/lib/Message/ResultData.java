package org.orbitrondev.jass.lib.Message;

import org.json.JSONObject;

public class ResultData extends MessageData {
    private final boolean result;
    private final JSONObject resultData;

    /**
     * This constructor is used by most messages
     */
    public ResultData(boolean result) {
        super("Result");
        this.result = result;
        resultData = new JSONObject();
    }

    public ResultData(boolean result, JSONObject resultData) {
        super("Result");
        this.result = result;
        this.resultData = resultData;
    }

    public ResultData(JSONObject data) {
        super(data);
        result = data.getBoolean("result");
        resultData = data.getJSONObject("resultData");
    }

    public boolean getResult() {
        return result;
    }

    public JSONObject getResultData() {
        return resultData;
    }
}
