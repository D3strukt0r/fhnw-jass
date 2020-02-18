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

import org.orbitrondev.jass.lib.Message.DeleteLoginData;
import org.orbitrondev.jass.lib.Message.MessageData;
import org.orbitrondev.jass.lib.Message.ResultData;
import org.orbitrondev.jass.server.Client;
import org.orbitrondev.jass.server.Entity.UserRepository;

/**
 * Deletes a user.
 *
 * @author Manuele Vaccari
 * @version %I%, %G%
 * @since 0.0.1
 */
public class DeleteLogin extends Message {
    private DeleteLoginData data;

    public DeleteLogin(MessageData rawData) {
        super(rawData);
        data = (DeleteLoginData) rawData;
    }

    @Override
    public void process(Client client) {
        boolean result = false;
        if (client.getToken().equals(data.getToken())) {
            UserRepository.remove(client.getUser());
            client.setToken(null);
            client.setUser(null);
            result = true;
        }
        client.send(new Result(new ResultData(data.getId(), result)));
    }
}
