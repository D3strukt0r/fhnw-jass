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

package jass.client.message;

import jass.client.util.SocketUtil;
import jass.lib.message.MessageData;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * The base model every message that is being sent to the server, needs to
 * implement.
 *
 * @author Manuele Vaccari
 * @version %I%, %G%
 * @since 1.0.0
 */
public abstract class Message {
    /**
     * The data of the message (still not casted).
     */
    private final MessageData rawData;

    /**
     * @param rawData The data (still not casted)
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    public Message(final MessageData rawData) {
        this.rawData = rawData;
    }

    /**
     * @return Returns the ID of the message.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    public int getId() {
        return rawData.getId();
    }

    /**
     * @return Returns the data.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    public MessageData getRawData() {
        return rawData;
    }

    /**
     * Perform whatever actions are required for this particular type of
     * message.
     *
     * @param socket The socket object, to perform actions with the server.
     *
     * @return Returns true if message succeeded, otherwise false.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    public abstract boolean process(SocketUtil socket);

    /**
     * Create a message object of the correct class, using reflection.
     *
     * @param messageData The data.
     *
     * @return Returns a message object using the data.
     *
     * @author Bradley Richards
     * @since 1.0.0
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
     * A message is really just a bunch of strings separated by vertical bars.
     *
     * @return Returns the serialized JSON string.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    @Override
    public String toString() {
        return MessageData.serialize(rawData);
    }
}
