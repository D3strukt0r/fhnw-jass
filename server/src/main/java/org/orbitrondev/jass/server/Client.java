package org.orbitrondev.jass.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.orbitrondev.jass.lib.Message.MessageData;
import org.orbitrondev.jass.server.Entity.User;
import org.orbitrondev.jass.server.Message.Message;
import org.orbitrondev.jass.server.Message.MessageError;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Constructor;
import java.net.Socket;

/**
 * Copyright 2015, FHNW, Prof. Dr. Brad Richards. All rights reserved. This code
 * is licensed under the terms of the BSD 3-clause license (see the file
 * license.txt).
 *
 * @author Brad Richards
 */
public class Client {
    private static final Logger logger = LogManager.getLogger(Client.class);

    private Socket socket;
    private volatile boolean clientReachable = true;
    private User user = null;
    private String token = null;

    /**
     * Create a new client object, communicating over the given socket. Immediately
     * start a thread to receive messages from the client.
     */
    public Client(Socket socket) {
        this.socket = socket;

        // Create thread to read incoming messages
        Runnable r = () -> {
            try {
                while (clientReachable) {
                    Message msg = null;
                    try {
                        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        String msgText = in.readLine(); // Will wait here for complete line
                        logger.info("Receiving message: " + msgText);

                        // Convert JSON string into a workable object
                        MessageData msgData = MessageData.unserialize(msgText);

                        // Create a server message object of the correct class, using reflection
                        //
                        // This would be more understandable - but a *lot* longer - if we used
                        // a series of "if" statements:
                        //
                        // if (parts[0].equals("Login") msg = new Login(parts);
                        // else if (parts[0].equals("Logout") msg = new Logout(parts);
                        // else if ...
                        // else ...
                        String messageClassName = Message.class.getPackage().getName() + "." + msgData.getMessageType();
                        try {
                            Class<?> messageClass = Class.forName(messageClassName);
                            Constructor<?> constructor = messageClass.getConstructor(MessageData.class);
                            msg = (Message) constructor.newInstance(msgData);
                            logger.info("Received message of type " + msgData.getMessageType());
                        } catch (Exception e) {
                            logger.error("Received invalid message of type " + msgData.getMessageType());
                        }
                    } catch (IOException e) {
                        logger.error(e.toString());
                    }

                    // Note the syntax "Client.this" - writing "this" would reference the Runnable
                    // object
                    if (msg != null)
                        msg.process(Client.this);
                    else { // Invalid message or broken socket
                        send(new MessageError());
                    }
                }
            } catch (Exception e) {
                logger.info("Client " + getUsername() + " disconnected");
            } finally {
                // Free up RAM by deleting disconnected clients.
                Listener.remove(this);
            }
        };
        Thread t = new Thread(r);
        t.start();

        logger.info("New client created: " + getUsername());
    }

    /**
     * Send a message to this client. In case of an exception, log the client out.
     */
    public void send(Message msg) {
        try {
            OutputStreamWriter out = new OutputStreamWriter(socket.getOutputStream());
            logger.info("Sending message: " + this.toString());
            out.write(msg.toString() + "\n"); // This will send the serialized MessageData object
            out.flush();
        } catch (IOException e) {
            logger.info("Client " + getUsername() + " unreachable; logged out");
            clientReachable = false;
            this.token = null;
        }
    }

    public User getUser() {
        return user;
    }

    public String getUsername() {
        String name = null;
        if (user != null) name = user.getUsername();
        return name;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
