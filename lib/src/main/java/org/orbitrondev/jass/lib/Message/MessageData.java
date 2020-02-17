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

package org.orbitrondev.jass.lib.Message;

import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public abstract class MessageData implements Serializable {
    public static MessageData unserialize(String jsonString) {
        JSONObject data = new JSONObject(jsonString);

        try {
            String messageClassName = MessageData.class.getPackage().getName() + "." + data.getString("messageType") + "Data";
            Class<?> messageClass = Class.forName(messageClassName);
            Constructor<?> constructor = messageClass.getConstructor(JSONObject.class);
            return (MessageData) constructor.newInstance(data);
        } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException | ClassNotFoundException e) {
            return null;
        }
    }

    public static String serialize(MessageData messageData) {
        JSONObject data = new JSONObject(messageData);
        return data.toString();
    }

    /* Required data */

    private static int idCounter = 0;
    private final int id;

    private final String messageType;

    public MessageData(String messageType) {
        id = createId();
        this.messageType = messageType;
    }

    public MessageData(JSONObject data) {
        id = data.getInt("id");
        messageType = data.getString("messageType");
    }

    /**
     * Create an id for the message to easily identify responses.
     */
    public static int createId() {
        idCounter = idCounter + 1;
        return idCounter;
    }

    public int getId() {
        return id;
    }

    public String getMessageType() {
        return messageType;
    }
}
