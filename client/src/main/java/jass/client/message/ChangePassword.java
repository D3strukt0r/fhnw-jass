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
import jass.client.repository.LoginRepository;
import jass.client.util.SocketUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import jass.lib.message.ChangePasswordData;
import jass.lib.message.MessageData;
import jass.lib.message.ResultData;
import jass.lib.servicelocator.ServiceLocator;

/**
 * Overwrite the password of the currently logged in user.
 *
 * @author Manuele Vaccari
 * @version %I%, %G%
 * @since 0.0.1
 */
public final class ChangePassword extends Message {
    /**
     * The logger to print to console and save in a .log file.
     */
    private static final Logger logger = LogManager.getLogger(ChangePassword.class);

    /**
     * The data of the message.
     */
    private final ChangePasswordData data;

    /**
     * @param rawData The data (still not casted)
     */
    public ChangePassword(final MessageData rawData) {
        super(rawData);
        data = (ChangePasswordData) rawData;
    }

    @Override
    public boolean process(final SocketUtil socket) {
        socket.send(this);

        Message result = socket.waitForResultResponse(data.getId());
        ResultData resultData = (ResultData) result.getRawData();

        if (resultData.getResult()) {
            // Data is final, so create a new object.
            LoginEntity login = ServiceLocator.get(LoginEntity.class);
            assert login != null;
            LoginEntity newLogin = (new LoginEntity())
                .setUsername(login.getUsername())
                .setPassword(data.getNewPassword())
                .setToken(login.getToken());

            // Replace the ServiceLocator with the new login.
            ServiceLocator.remove(LoginEntity.class);
            ServiceLocator.add(newLogin);

            // Also remove the old login from the database and replace with the new one.
            if (!LoginRepository.getSingleton(null).remove(login) || LoginRepository.getSingleton(null).add(newLogin)) {
                logger.error("Couldn't save login data to local database.");
            }
        }
        return resultData.getResult();
    }
}
