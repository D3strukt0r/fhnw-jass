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

package org.orbitrondev.jass.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.orbitrondev.jass.lib.Message.MessageData;
import org.orbitrondev.jass.lib.Message.MessageErrorData;
import org.orbitrondev.jass.server.Entity.UserEntity;
import org.orbitrondev.jass.server.Message.Message;
import org.orbitrondev.jass.server.Message.MessageError;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.SocketException;

/**
 * Copyright 2015, FHNW, Prof. Dr. Brad Richards. All rights reserved. This code is licensed under the terms of the BSD
 * 3-clause license (see the file license.txt).
 *
 * @author Brad Richards
 * @author Manuele Vaccari (to work with Json messaging)
 */
public class Client extends Thread {
    private static final Logger logger = LogManager.getLogger(Client.class);

    private Socket socket;
    private volatile boolean clientReachable = true;

    private UserEntity user = null;
    private String token = null;

    /**
     * Create a new client object, communicating over the given socket. Immediately start a thread to receive messages
     * from the client.
     */
    public Client(Socket socket) {
        super();
        this.setName("ClientThread");
        this.socket = socket;

        // Create thread to read incoming messages
        this.start();

        logger.info("New client created: " + getUsername());
    }

    @Override
    public void run() {
        while (clientReachable) {
            Message msg = null;
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String msgText = in.readLine(); // Will wait here for complete line
                logger.info("Receiving message: " + msgText);

                // Convert JSON string into a workable object
                MessageData msgData = MessageData.unserialize(msgText);

                // Create a server message object of the correct class, using reflection
                if (msgData == null) {
                    logger.error("Received invalid message");
                } else {
                    msg = Message.fromDataObject(msgData);
                    if (msg == null) {
                        logger.error("Received invalid message of type " + msgData.getMessageType());
                    } else {
                        logger.info("Received message of type " + msgData.getMessageType());
                    }
                }
            } catch (SocketException e) {
                logger.info("Client " + getUsername() + " disconnected");
                disconnect();
                continue;
            } catch (IOException e) {
                logger.error(e.toString());
            }

            // Note the syntax "Client.this" - writing "this" would reference the Runnable
            // object
            if (msg != null)
                msg.process(Client.this);
            else { // Invalid message or broken socket
                send(new MessageError(new MessageErrorData(MessageErrorData.ErrorType.INVALID_COMMAND)));
            }
        }
    }

    /**
     * Send a message to this client. In case of an exception, log the client out.
     */
    public void send(Message msg) {
        try {
            OutputStreamWriter out = new OutputStreamWriter(socket.getOutputStream());
            logger.info("Sending message: " + msg.toString());
            out.write(msg.toString() + "\n"); // This will send the serialized MessageData object
            out.flush();
        } catch (IOException e) {
            logger.info("Client " + getUsername() + " unreachable; logged out");
            disconnect();
        }
    }

    public void disconnect() {
        clientReachable = false;
        token = null;
        user = null;

        // Free up RAM by deleting disconnected clients.
        Listener.remove(this);
    }

    public UserEntity getUser() {
        return user;
    }

    public String getUsername() {
        String name = "<undefined>";
        if (user != null) name = user.getUsername();
        return name;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
