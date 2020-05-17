/*
 * fhnw-jass is jass game programmed in java for a school project.
 * Copyright (C) 2020 Manuele Vaccari & Victor Hargrave & Thomas Weber & Sasa
 * Trajkova
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package jass.lib.message;

import org.json.JSONObject;

/**
 * The data model for the result message.
 *
 * @author Manuele Vaccari
 * @version %I%, %G%
 * @since 1.0.0
 */
public final class ResultData extends MessageData {
    /**
     * The result.
     */
    private final boolean result;

    /**
     * Additional data for the result.
     */
    private final JSONObject resultData;

    /**
     * @param id     The ID of the message to respond to.
     * @param result The result of the message to respond to.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    public ResultData(final int id, final boolean result) {
        super(id, "Result");
        this.result = result;
        resultData = new JSONObject();
    }

    /**
     * @param id         The ID of the message to respond to.
     * @param result     The result of the message to respond to.
     * @param resultData Additional data for the returning message.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    public ResultData(final int id, final boolean result, final JSONObject resultData) {
        super(id, "Result");
        this.result = result;
        this.resultData = resultData;
    }

    /**
     * @param data The message containing all the data.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    public ResultData(final JSONObject data) {
        super(data);
        result = data.getBoolean("result");
        resultData = data.getJSONObject("resultData");
    }

    /**
     * @return Returns the result.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    public boolean getResult() {
        return result;
    }

    /**
     * @return Returns the additional data.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    public JSONObject getResultData() {
        return resultData;
    }
}
