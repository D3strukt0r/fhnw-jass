package org.orbitrondev.jass.server;

import com.sun.net.ssl.internal.ssl.Provider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.Security;
import java.util.ArrayList;

/**
 * Copyright 2015, FHNW, Prof. Dr. Brad Richards. All rights reserved. This code
 * is licensed under the terms of the BSD 3-clause license (see the file
 * license.txt).
 *
 * @author Brad Richards
 */
public class Listener extends Thread {
    private static final Logger logger = LogManager.getLogger(Listener.class);

    private final ServerSocket listener;
    private final int port;

    private static final ArrayList<Client> clients = new ArrayList<>();

    public Listener(int port, boolean secure) throws IOException {
        super();
        this.port = port;
        this.setName("ListenerThread");

        if (secure) {
            // Registering the JSSE provider
            Security.addProvider(new Provider());

            // Specifying the Keystore details. Note that you must manually add or generate
            // keys using the Java keystore tool
            System.setProperty("javax.net.ssl.keyStore", "server.ks");
            System.setProperty("javax.net.ssl.keyStorePassword", "sseucpreert");

            // Initialize the Server Socket
            SSLServerSocketFactory sslServerSocketfactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
            listener = (SSLServerSocket) sslServerSocketfactory.createServerSocket(port, 10, null);
        } else {
            listener = new ServerSocket(port, 10, null);
        }
    }

    @Override
    public void run() {
        logger.info("Starting listener on port " + port);
        while (true) {
            try {
                Socket socket = listener.accept();
                clients.add(new Client(socket));
            } catch (Exception e) {
                logger.info(e.toString());
            }
        }
    }

    public synchronized static boolean exists(String username) {
        for (Client c : clients) {
            if (c.getUser() != null && c.getUser().getUsername().equals(username)) return true;
        }
        return false;
    }

    public synchronized static void remove(Client client) {
        clients.remove(client);
    }
}
