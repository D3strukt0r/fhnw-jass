/*
 * fhnw-jass is jass game programmed in java for a school project.
 * Copyright (C) 2020 Manuele Vaccari, Victor Hargrave, Sasa Trajkova, Thomas
 * Weber
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

package jass.server.util;

import jass.lib.message.ChosenGameModeData;
import jass.lib.message.MessageData;
import jass.lib.message.MessageErrorData;
import jass.lib.servicelocator.ServiceLocator;
import jass.server.entity.UserEntity;
import jass.server.eventlistener.ChosenGameModeEventListener;
import jass.server.message.Message;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import jass.server.message.MessageError;

import javax.net.ssl.SSLException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

/**
 * Copyright 2015, FHNW, Prof. Dr. Brad Richards. All rights reserved. This code
 * is licensed under the terms of the BSD 3-clause license (see the file
 * license.txt).
 *
 * @author Brad Richards
 * @author Manuele Vaccari (to work with Json messaging)
 */
public final class ClientUtil extends Thread {
    /**
     * The logger to print to console and save in a .log file.
     */
    private static final Logger logger = LogManager.getLogger(ClientUtil.class);

    /**
     * The socket of the client.
     */
    private final Socket socket;

    /**
     * Whether the client is currently reachable. Shuts down everything when
     * false.
     */
    private volatile boolean clientReachable = true;

    /**
     * The user of the current connection.
     */
    private UserEntity user = null;

    /**
     * The token of the current connection.
     */
    private String token = null;

    /**
     * A list of all objects listening to a choose game mode event.
     */
    private final ArrayList<ChosenGameModeEventListener> chosenGameModeListener = new ArrayList<>();

    /**
     * Create a new client object, communicating over the given socket.
     * Immediately start a thread to receive messages from the client.
     *
     * @param socket The socket of the client.
     */
    public ClientUtil(final Socket socket) {
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
                logger.info(getUsername() + " - Receiving message: " + msgText);

                if (msgText == null) {
                    logger.info(getUsername() + " - Client disconnected");
                    disconnect();
                    continue;
                }

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

                        handleEventListenerOnMessage(msgData.getMessageType(), msgData);
                    }
                }
            } catch (SocketException | SSLException e) {
                logger.info(getUsername() + " - Client disconnected");
                disconnect();
                continue;
            } catch (IOException e) {
                logger.error(e.toString());
            }

            // Note the syntax "Client.this" - writing "this" would reference the Runnable
            // object
            if (msg != null) {
                msg.process(ClientUtil.this);
            } else { // Invalid message or broken socket
                send(new MessageError(new MessageErrorData(MessageErrorData.ErrorType.INVALID_COMMAND)));
            }
        }
    }

    /**
     * @param msgType Message type to check against available events.
     * @param msgData The message to send to the listeners.
     *
     * @author Thomas Weber, Manuele Vaccari
     */
    public void handleEventListenerOnMessage(final String msgType, final MessageData msgData) {
        if ("ChosenGameMode".equals(msgType)) {
            for (ChosenGameModeEventListener listener : chosenGameModeListener) {
                listener.onChosenGameMode((ChosenGameModeData) msgData);
            }
        }
    }

    /**
     * @param listener A DisconnectEventListener object
     *
     * @author Manuele Vaccari
     * @since 0.0.1
     */
    public void addChosenGameModeEventListener(final ChosenGameModeEventListener listener) {
        this.chosenGameModeListener.add(listener);
    }

    /**
     * Send a message to this client. In case of an exception, log the client
     * out.
     *
     * @param msg The message to be sent.
     */
    public void send(final Message msg) {
        try {
            OutputStreamWriter out = new OutputStreamWriter(socket.getOutputStream());
            logger.info(getUsername() + " - Sending message: " + msg.toString());
            out.write(msg.toString() + "\n"); // This will send the serialized MessageData object
            out.flush();
        } catch (IOException e) {
            logger.info(getUsername() + " - Client unreachable; logged out");
            disconnect();
        }
    }

    /**
     * Shuts down all connections.
     */
    public void disconnect() {
        SearchGameUtil searchGameUtil = (SearchGameUtil) ServiceLocator.get(SearchGameUtil.class);
        searchGameUtil.removeClientFromSearchingGame(this);

        // TODO: Close down the game if client was inside.

        clientReachable = false;
        token = null;
        user = null;

        // Free up RAM by deleting disconnected clients.
        ServerSocketUtil.remove(this);
    }

    /**
     * @return Returns the current user of the connection. Returns null if the
     * user is not logged in.
     */
    public UserEntity getUser() {
        return user;
    }

    /**
     * @return Returns the user's username. Returns null if the user is not
     * logged in.
     */
    public String getUsername() {
        String name = "<undefined>";
        if (user != null) {
            name = user.getUsername();
        }
        return name;
    }

    /**
     * Sets the user of the current connection.
     *
     * @param user The user.
     */
    public void setUser(final UserEntity user) {
        this.user = user;
    }

    /**
     * @return Returns the token of the current session.
     */
    public String getToken() {
        return token;
    }

    /**
     * @param token The token.
     */
    public void setToken(final String token) {
        this.token = token;
    }
}
