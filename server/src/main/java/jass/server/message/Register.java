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

package jass.server.message;

import jass.lib.message.MessageData;
import jass.lib.message.RegisterData;
import jass.lib.message.ResultData;
import jass.server.entity.UserEntity;
import jass.server.repository.UserRepository;
import jass.server.util.ClientUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

/**
 * Create a completely new login. After creating an account, the user must still
 * login!
 *
 * @author Manuele Vaccari
 * @version %I%, %G%
 * @since 1.0.0
 */
public final class Register extends Message {
    /**
     * The logger to print to console and save in a .log file.
     */
    private static final Logger logger = LogManager.getLogger(Register.class);

    /**
     * The data of the message.
     */
    private final RegisterData data;

    /**
     * The minimum required length of the username.
     */
    private static final int USERNAME_MIN_LENGTH = 3;

    /**
     * The minimum required length of the password.
     */
    private static final int PASSWORD_MIN_LENGTH = 3;

    /**
     * @param rawData The data (still not casted).
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
    public void process(final ClientUtil client) {
        // Check if data is available
        if (data.getUsername() == null || data.getPassword() == null) {
            client.send(new Result(new ResultData(data.getId(), false)));
            return;
        }

        // Check for a valid username
        if (data.getUsername().length() < USERNAME_MIN_LENGTH) {
            client.send(new Result(new ResultData(data.getId(), false, (new JSONObject()).put("reason", RegisterData.Result.USERNAME_TOO_SHORT))));
            return;
        }

        // Check for a valid password (lax password requirements)
        if (data.getPassword().length() < PASSWORD_MIN_LENGTH) {
            client.send(new Result(new ResultData(data.getId(), false, (new JSONObject()).put("reason", RegisterData.Result.PASSWORD_TOO_SHORT))));
            return;
        }

        // Check whether the username is not already taken
        if (UserRepository.getSingleton(null).usernameExists(data.getUsername())) {
            client.send(new Result(new ResultData(data.getId(), false, (new JSONObject()).put("reason", RegisterData.Result.USERNAME_ALREADY_EXISTS))));
            return;
        }

        UserEntity newUser = (new UserEntity())
            .setUsername(data.getUsername())
            .setPassword(data.getPassword());

        // Add the new user to the database, and only return true if it was
        // saved successfully
        if (UserRepository.getSingleton(null).add(newUser)) {
            logger.info("User " + newUser.getUsername() + " created");
            client.send(new Result(new ResultData(data.getId(), true)));
        } else {
            client.send(new Result(new ResultData(data.getId(), false, (new JSONObject()).put("reason", RegisterData.Result.SERVER_ERROR))));
        }
    }
}
