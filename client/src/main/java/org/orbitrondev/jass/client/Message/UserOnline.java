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

package org.orbitrondev.jass.client.Message;

import org.orbitrondev.jass.client.Utils.SocketUtil;
import org.orbitrondev.jass.lib.Message.MessageData;
import org.orbitrondev.jass.lib.Message.ResultData;
import org.orbitrondev.jass.lib.Message.UserOnlineData;

/**
 * Checks whether the user is currently logged in.
 *
 * @author Manuele Vaccari
 * @version %I%, %G%
 * @since 0.0.1
 */
public class UserOnline extends Message {
    private UserOnlineData data;

    public UserOnline(MessageData rawData) {
        super(rawData);
        data = (UserOnlineData) rawData;
    }

    @Override
    public boolean process(SocketUtil socket) {
        socket.send(this);

        Message result = socket.waitForResultResponse(data.getId());
        ResultData resultData = (ResultData) result.getRawData();

        return resultData.getResult();
    }
}
