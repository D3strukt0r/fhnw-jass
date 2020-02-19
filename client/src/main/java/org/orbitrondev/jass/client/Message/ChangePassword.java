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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.orbitrondev.jass.client.Entity.LoginEntity;
import org.orbitrondev.jass.client.Utils.SocketUtil;
import org.orbitrondev.jass.client.Utils.DatabaseUtil;
import org.orbitrondev.jass.lib.Message.ChangePasswordData;
import org.orbitrondev.jass.lib.Message.MessageData;
import org.orbitrondev.jass.lib.Message.ResultData;
import org.orbitrondev.jass.lib.ServiceLocator.ServiceLocator;

import java.sql.SQLException;

/**
 * Overwrite the password of the currently logged in user.
 *
 * @author Manuele Vaccari
 * @version %I%, %G%
 * @since 0.0.1
 */
public class ChangePassword extends Message {
    private static final Logger logger = LogManager.getLogger(ChangePassword.class);
    private ChangePasswordData data;

    public ChangePassword(MessageData rawData) {
        super(rawData);
        data = (ChangePasswordData) rawData;
    }

    @Override
    public boolean process(SocketUtil socket) {
        socket.send(this);

        Message result = socket.waitForResultResponse(data.getId());
        ResultData resultData = (ResultData) result.getRawData();

        if (resultData.getResult()) {
            // Data is final, so create a new object.
            LoginEntity login = (LoginEntity) ServiceLocator.get("login");
            LoginEntity newLogin = new LoginEntity(login.getUsername(), data.getNewPassword(), login.getToken());

            // Replace the ServiceLocator with the new login.
            ServiceLocator.remove("login");
            ServiceLocator.add(newLogin);

            // Also remove the old login from the database and replace with the new one.
            DatabaseUtil db = (DatabaseUtil) ServiceLocator.get("db");
            try {
                db.getLoginDao().delete(login);
                db.getLoginDao().create(newLogin);
            } catch (SQLException e) {
                logger.error("Couldn't save login data to local database.");
            }
        }
        return resultData.getResult();
    }
}
