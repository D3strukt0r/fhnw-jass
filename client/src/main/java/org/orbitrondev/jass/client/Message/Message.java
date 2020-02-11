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

import org.orbitrondev.jass.client.Utils.BackendUtil;
import org.orbitrondev.jass.lib.Message.MessageData;

/**
 * The base model every message that is being sent to the server, needs to implement.
 *
 * @author Manuele Vaccari
 * @version %I%, %G%
 * @since 0.0.1
 */
public abstract class Message {
    protected final MessageData rawData;

    public Message(MessageData data) {
        this.rawData = data;
    }

    public int getId() {
        return rawData.getId();
    }

    public MessageData getRawData() {
        return rawData;
    }

    /**
     * Perform whatever actions are required for this particular type of message.
     */
    public abstract boolean process(BackendUtil backendUtil);

    /**
     * A message is really just a bunch of strings separated by vertical bars
     */
    @Override
    public String toString() {
        return MessageData.serialize(rawData);
    }
}
