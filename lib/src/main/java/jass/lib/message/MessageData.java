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

package jass.lib.message;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public abstract class MessageData implements Serializable {
    /**
     * @param jsonString The JSON string to be converted.
     *
     * @return Returns the object from the JSON string.
     */
    public static MessageData unserialize(final String jsonString) {
        JSONObject data;
        try {
            data = new JSONObject(jsonString);
        } catch (JSONException e) {
            return null;
        }

        try {
            /*
             * @author https://stackoverflow.com/questions/3574065/instantiate-a-class-object-with-constructor-that-accepts-a-string-parameter
             */
            String messageClassName = MessageData.class.getPackage().getName() + "." + data.getString("messageType") + "Data";
            Class<?> messageClass = Class.forName(messageClassName);
            Constructor<?> constructor = messageClass.getConstructor(JSONObject.class);
            return (MessageData) constructor.newInstance(data);
        } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException | ClassNotFoundException e) {
            return null;
        }
    }

    /**
     * @param messageData The message to be converted.
     *
     * @return Returns the object serialized as a JSON string.
     */
    public static String serialize(final MessageData messageData) {
        JSONObject data = new JSONObject(messageData);
        return data.toString();
    }

    /* Required data */

    /**
     * The counter to keep track of IDs. This keeps incrementing and starts at 0
     * for each new client.
     */
    private static int idCounter = 0;

    /**
     * The ID.
     */
    private final int id;

    /**
     * The Auth token.
     */
    private String token;

    /**
     * The Username.
     */
    private String username;

    /**
     * The type (object name).
     */
    private final String messageType;

    /**
     * @param messageType The type (object name).
     */
    public MessageData(final String messageType) {
        id = createId();
        this.messageType = messageType;
        this.username = this.username != "" || this.username != null ? "" : this.getUsername();
        this.token = this.token != "" || this.token != null ? "" : this.getToken();
    }

    /**
     * @param id          The ID of the message.
     * @param messageType The type (object name).
     */
    public MessageData(final int id, final String messageType) {
        this.id = id;
        this.messageType = messageType;
        this.username = this.username != "" || this.username != null ? "" : this.getUsername();
        this.token = this.token != "" || this.token != null ? "" : this.getToken();
    }

    /**
     * @param data The message containing all the data.
     */
    public MessageData(final JSONObject data) {
        id = data.getInt("id");
        token = data.getString("token");
        username = data.getString("username");
        messageType = data.getString("messageType");
    }

    /**
     * @return Returns an ID for the message to easily identify responses.
     */
    public static int createId() {
        idCounter = idCounter + 1;
        return idCounter;
    }

    /**
     * @return Returns the ID.
     */
    public int getId() {
        return id;
    }

    /**
     * @return Returns the message type.
     */
    public String getMessageType() {
        return messageType;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
