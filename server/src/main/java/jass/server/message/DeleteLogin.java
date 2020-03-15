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

import jass.server.entity.UserRepository;
import jass.server.utils.ClientUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import jass.lib.message.DeleteLoginData;
import jass.lib.message.MessageData;
import jass.lib.message.ResultData;

/**
 * Deletes a user.
 *
 * @author Manuele Vaccari
 * @version %I%, %G%
 * @since 0.0.1
 */
public class DeleteLogin extends Message {
    private static final Logger logger = LogManager.getLogger(DeleteLogin.class);
    private DeleteLoginData data;

    public DeleteLogin(MessageData rawData) {
        super(rawData);
        data = (DeleteLoginData) rawData;
    }

    @Override
    public void process(ClientUtil client) {
        boolean result = false;

        // Only continue if the user has the right token.
        if (client.getToken() != null && client.getToken().equals(data.getToken())) {
            if (UserRepository.remove(client.getUser())) {
                logger.info("User " + client.getUser().getUsername() + " was deleted");
                client.setToken(null);
                client.setUser(null);
                result = true;
            }
        }

        client.send(new Result(new ResultData(data.getId(), result)));
    }
}
