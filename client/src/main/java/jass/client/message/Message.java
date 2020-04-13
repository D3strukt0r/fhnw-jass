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

import jass.client.util.SocketUtil;
import jass.lib.message.MessageData;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * The base model every message that is being sent to the server, needs to implement.
 *
 * @author Manuele Vaccari
 * @version %I%, %G%
 * @since 0.0.1
 */
public abstract class Message {
    protected final MessageData rawData;

    public Message(final MessageData data) {
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
    public abstract boolean process(SocketUtil socket);

    /**
     * Create a message object of the correct class, using reflection
     *
     * @author Bradley Richards
     */
    public static Message fromDataObject(final MessageData messageData) {
        String messageClassName = Message.class.getPackage().getName() + "." + messageData.getMessageType();
        try {
            Class<?> messageClass = Class.forName(messageClassName);
            Constructor<?> constructor = messageClass.getConstructor(MessageData.class);
            return (Message) constructor.newInstance(messageData);
        } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException | ClassNotFoundException e) {
            return null;
        }
    }

    /**
     * A message is really just a bunch of strings separated by vertical bars
     */
    @Override
    public String toString() {
        return MessageData.serialize(rawData);
    }
}
