/*
 * fhnw-jass is jass game programmed in java for a school project.
 * Copyright (C) 2020 Manuele Vaccari
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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import jass.client.utils.SocketUtil;
import jass.lib.message.MessageData;
import jass.lib.message.ResultData;

/**
 * Logs the current user out from the server. After successful logout, token becomes invalid.
 *
 * @author Manuele Vaccari
 * @version %I%, %G%
 * @since 0.0.1
 */
public class Logout extends Message {
    private static final Logger logger = LogManager.getLogger(Logout.class);

    public Logout(MessageData rawData) {
        super(rawData);
    }

    @Override
    public boolean process(SocketUtil socket) {
        socket.send(this);

        Message result = socket.waitForResultResponse(rawData.getId());
        ResultData resultData = (ResultData) result.getRawData();

        return resultData.getResult();
    }
}
