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

package org.orbitrondev.jass.server.Message;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.orbitrondev.jass.lib.Message.CreateLoginData;
import org.orbitrondev.jass.lib.Message.MessageData;
import org.orbitrondev.jass.lib.Message.ResultData;
import org.orbitrondev.jass.server.Client;
import org.orbitrondev.jass.server.Entity.UserEntity;
import org.orbitrondev.jass.server.Entity.UserRepository;

/**
 * Create a completely new login. After creating an account, the user must still login!
 *
 * @author Manuele Vaccari
 * @version %I%, %G%
 * @since 0.0.1
 */
public class CreateLogin extends Message {
    private static final Logger logger = LogManager.getLogger(CreateLogin.class);
    private CreateLoginData data;

    public CreateLogin(MessageData rawData) {
        super(rawData);
        data = (CreateLoginData) rawData;
    }

    /**
     * We can only create a new account if the name is at least 3 characters, and is not in use either as a user or as a
     * chatroom
     */
    @Override
    public void process(Client client) {
        boolean result = false;

        // Check for a valid username
        if (data.getUsername() != null && data.getUsername().length() >= 3) {
            // Check for a valid password (lax password requirements)
            if (data.getPassword() != null && data.getPassword().length() >= 3) {
                // Check whether the username is not already taken
                if (!UserRepository.usernameExists(data.getUsername())) {
                    UserEntity newUser = new UserEntity(data.getUsername(), data.getPassword());

                    // Add the new user to the database, and only return true if it was saved successfully
                    if (UserRepository.create(newUser)) {
                        logger.info("User " + newUser.getUsername() + " created");
                        result = true;
                    } else {
                        logger.warn("User " + newUser.getUsername() + " could not be created");
                    }
                }
            }
        }

        client.send(new Result(new ResultData(data.getId(), result)));
    }

}
