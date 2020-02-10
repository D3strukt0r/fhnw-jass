package org.orbitrondev.jass.lib.Message;

import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public abstract class MessageData implements Serializable {
    public static MessageData unserialize(String jsonString) {
        JSONObject data = new JSONObject(jsonString);

        try {
            String messageClassName = MessageData.class.getPackage().getName() + "." + data.getString("messageType");
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
