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
import jass.lib.message.RegisterData;
import jass.lib.message.MessageData;
import jass.lib.message.ResultData;

/**
 * Create a completely new login. After creating an account, the user must still login!
 *
 * @author Manuele Vaccari
 * @version %I%, %G%
 * @since 0.0.1
 */
public class Register extends Message {
    private static final Logger logger = LogManager.getLogger(Register.class);
    private RegisterData data;

    public Register(MessageData rawData) {
        super(rawData);
        data = (RegisterData) rawData;
    }

    /**
     * We can only create a new account if the name is at least 3 characters, and is not in use either as a user or as a
     * chatroom
     */
    @Override
    public void process(ClientUtil client) {
        boolean result = false;

        // Check for a valid username
        if (data.getUsername() != null && data.getUsername().length() >= 3) {
            // Check for a valid password (lax password requirements)
            if (data.getPassword() != null && data.getPassword().length() >= 3) {
                // Check whether the username is not already taken
                if (!UserRepository.getSingleton(null).usernameExists(data.getUsername())) {
                    UserEntity newUser = new UserEntity(data.getUsername(), data.getPassword());

                    // Add the new user to the database, and only return true if it was saved successfully
                    if (UserRepository.getSingleton(null).add(newUser)) {
                        logger.info("User " + newUser.getUsername() + " created");
                        result = true;
                    }
                }
            }
        }

        client.send(new Result(new ResultData(data.getId(), result)));
    }

}
