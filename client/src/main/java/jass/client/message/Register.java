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

package jass.client.message;

import jass.client.util.SocketUtil;
import jass.lib.message.RegisterData;
import jass.lib.message.MessageData;
import jass.lib.message.ResultData;

/**
 * Register a user on the the server.
 *
 * @author Manuele Vaccari
 * @version %I%, %G%
 * @since 1.0.0
 */
public final class Register extends Message {
    /**
     * The data of the message.
     */
    private final RegisterData data;

    /**
     * After the message was sent, this is the result.
     */
    private ResultData resultData;

    /**
     * @param rawData The data (still not casted)
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    public Register(final MessageData rawData) {
        super(rawData);
        data = (RegisterData) rawData;
    }

    /**
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    @Override
    public boolean process(final SocketUtil socket) {
        socket.send(this);

        Message result = socket.waitForResultResponse(data.getId());
        resultData = (ResultData) result.getRawData();

        return resultData.getResult();
    }

    /**
     * @return Returns the result.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    public ResultData getResultData() {
        return resultData;
    }
}
