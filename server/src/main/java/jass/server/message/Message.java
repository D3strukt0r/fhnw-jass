/*
 * fhnw-jass is jass game programmed in java for a school project.
 * Copyright (C) 2020 Manuele Vaccari & Victor Hargrave & Thomas Weber & Sasa Trajkova
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
import jass.server.util.ClientUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Abstract model which every message has to implement.
 *
 * @author Manuele Vaccari
 * @version %I%, %G%
 * @since 0.0.1
 */
public abstract class Message {
    /**
     * The data of the message (still not casted).
     */
    private final MessageData rawData;

    /**
     * @param rawData The data (still not casted)
     */
    public Message(final MessageData rawData) {
        this.rawData = rawData;
    }

    /**
     * @return Returns the data.
     */
    public MessageData getRawData() {
        return rawData;
    }

    /**
     * Perform whatever actions are required for this particular type of
     * message.
     *
     * @param client The client object, to perform actions with the client.
     */
    public abstract void process(ClientUtil client);

    /**
     * Create a server message object of the correct class, using reflection
     * <p>
     * This would be more understandable - but a *lot* longer - if we used a
     * series of "if" statements:
     * <p>
     * if (parts[0].equals("Login") msg = new Login(parts); else if
     * (parts[0].equals("Logout") msg = new Logout(parts); else if ... else ...
     *
     * @param messageData The data.
     *
     * @return Returns a message object using the data.
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
     * @return Returns the serialized JSON string.
     */
    @Override
    public String toString() {
        return MessageData.serialize(rawData);
    }
}
