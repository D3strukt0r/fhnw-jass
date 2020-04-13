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

package jass.server.message;

import jass.server.entity.UserEntity;
import jass.server.repository.UserRepository;
import jass.server.util.ClientUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import jass.lib.message.ChangePasswordData;
import jass.lib.message.MessageData;
import jass.lib.message.ResultData;

/**
 * Overwrite the password of the currently logged in user.
 *
 * @author Manuele Vaccari
 * @version %I%, %G%
 * @since 0.0.1
 */
public class ChangePassword extends Message {
    private static final Logger logger = LogManager.getLogger(ChangePassword.class);
    private final ChangePasswordData data;

    public ChangePassword(final MessageData rawData) {
        super(rawData);
        data = (ChangePasswordData) rawData;
    }

    @Override
    public void process(final ClientUtil client) {
        boolean result = false;

        // Only continue if the user has the right token.
        if (client.getToken() != null && client.getToken().equals(data.getToken())) {
            UserEntity user = client.getUser();
            user.setPassword(data.getNewPassword());

            // Update inside the db, and only return true, if that was also successful.
            if (UserRepository.getSingleton(null).update(user)) {
                logger.info("User " + user.getUsername() + " changed his password.");
                result = true;
            }
        }

        client.send(new Result(new ResultData(data.getId(), result)));
    }

}
