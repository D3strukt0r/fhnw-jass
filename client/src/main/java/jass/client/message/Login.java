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

import jass.client.entity.LoginEntity;
import jass.client.util.SocketUtil;
import jass.lib.message.LoginData;
import jass.lib.message.MessageData;
import jass.lib.message.ResultData;
import jass.lib.servicelocator.ServiceLocator;

/**
 * Login to the server.
 *
 * @author Manuele Vaccari
 * @version %I%, %G%
 * @since 0.0.1
 */
public final class Login extends Message {
    /**
     * The data of the message.
     */
    private final LoginData data;

    /**
     * The token.
     */
    private String token = null;

    /**
     * @param rawData The data (still not casted)
     */
    public Login(final MessageData rawData) {
        super(rawData);
        data = (LoginData) rawData;
    }

    @Override
    public boolean process(final SocketUtil socket) {
        socket.send(this);

        Message result = socket.waitForResultResponse(data.getId());
        ResultData resultData = (ResultData) result.getRawData();

        if (resultData.getResult()) {
            token = resultData.getResultData().getString("token");

            LoginEntity login = new LoginEntity(data.getUsername(), data.getPassword(), token);
            ServiceLocator.remove("login");
            ServiceLocator.add(login);
        }
        return resultData.getResult();
    }

    /**
     * @return Returns the token.
     */
    public String getToken() {
        return token;
    }
}