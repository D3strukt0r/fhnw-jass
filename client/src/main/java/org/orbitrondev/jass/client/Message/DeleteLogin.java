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
import org.orbitrondev.jass.client.Utils.BackendUtil;
import org.orbitrondev.jass.client.Utils.DatabaseUtil;
import org.orbitrondev.jass.lib.Message.LoginData;
import org.orbitrondev.jass.lib.Message.MessageData;
import org.orbitrondev.jass.lib.Message.ResultData;
import org.orbitrondev.jass.lib.ServiceLocator.ServiceLocator;

import java.sql.SQLException;

/**
 * Delete the currently logged in user from the server. After successful deletion, token becomes invalid.
 *
 * @author Manuele Vaccari
 * @version %I%, %G%
 * @since 0.0.1
 */
public class DeleteLogin extends Message {
    private static final Logger logger = LogManager.getLogger(DeleteLogin.class);
    private LoginData data;

    public DeleteLogin(MessageData rawData) {
        super(rawData);
        data = (LoginData) rawData;
    }

    @Override
    public boolean process(BackendUtil backendUtil) {
        backendUtil.send(this);

        Message result = backendUtil.waitForResultResponse(data.getId());
        ResultData resultData = (ResultData) result.getRawData();

        if (resultData.getResult()) {
            LoginEntity login = (LoginEntity) ServiceLocator.get("login");

            DatabaseUtil db = (DatabaseUtil) ServiceLocator.get("db");
            try {
                db.getLoginDao().delete(login);
            } catch (SQLException e) {
                logger.error("Couldn't save login data to local database.");
            }
        }
        return resultData.getResult();
    }
}
