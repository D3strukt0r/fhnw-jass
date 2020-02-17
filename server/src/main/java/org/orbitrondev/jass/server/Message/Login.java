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
import org.json.JSONObject;
import org.orbitrondev.jass.lib.Message.LoginData;
import org.orbitrondev.jass.lib.Message.MessageData;
import org.orbitrondev.jass.lib.Message.ResultData;
import org.orbitrondev.jass.server.Client;
import org.orbitrondev.jass.server.Entity.User;
import org.orbitrondev.jass.server.Entity.UserRepository;

/**
 * Login to an existing account. If successful, return an authentication token to the client.
 *
 * @author Manuele Vaccari
 * @version %I%, %G%
 * @since 0.0.1
 */
public class Login extends Message {
    private static final Logger logger = LogManager.getLogger(Login.class);
    private LoginData data;

    public Login(MessageData rawData) {
        super(rawData);
        data = (LoginData) rawData;
    }

    @Override
    public void process(Client client) {
        // Find existing login matching the username
        User user;
        if (UserRepository.usernameExists(data.getUsername())) {
            logger.info("User " + data.getUsername() + " exists");
            user = UserRepository.getByUsername(data.getUsername());
        } else {
            logger.info("User " + data.getUsername() + " does not exist");
            client.send(new Result(new ResultData(false)));
            return;
        }

        // Check if the client used the correct password
        if (user != null && user.checkPassword(data.getPassword())) {
            logger.info("Client used the correct password");
            client.setUser(user);
            String token = User.createToken();
            client.setToken(token);

            JSONObject resultData = new JSONObject();
            resultData.put("token", token);
            client.send(new Result(new ResultData(true, resultData)));
        } else {
            logger.info("Client used the wrong password");
            client.send(new Result(new ResultData(false)));
        }
    }
}
