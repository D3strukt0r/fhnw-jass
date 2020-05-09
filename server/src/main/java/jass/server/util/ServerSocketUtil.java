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

package jass.server.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.TrustManagerFactory;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.ArrayList;

/**
 * Creates a server socket (with or without SSL).
 *
 * @author Manuele Vaccari
 * @author https://stackoverflow.com/questions/53323855/sslserversocket-and-certificate-setup
 * @version %I%, %G%
 * @since 0.0.1
 */
public final class ServerSocketUtil extends Thread {
    /**
     * The logger to print to console and save in a .log file.
     */
    private static final Logger logger = LogManager.getLogger(ServerSocketUtil.class);

    /**
     * The default port of the server.
     */
    public static final int DEFAULT_PORT = 2000;

    /**
     * The server socket listener.
     */
    private final ServerSocket listener;

    /**
     * The port of the listener.
     */
    private final int port;

    /**
     * A list of all the connected clients.
     */
    private static final ArrayList<ClientUtil> clients = new ArrayList<>();

    /**
     * @param port   The port of the server.
     * @param secure Whether to use SSL or not.
     *
     * @throws IOException               An error with the socket.
     * @throws KeyStoreException         An error with SSL.
     * @throws CertificateException      An error with SSL.
     * @throws NoSuchAlgorithmException  An error with SSL.
     * @throws UnrecoverableKeyException An error with SSL.
     * @throws KeyManagementException    An error with SSL.
     */
    public ServerSocketUtil(final int port, final boolean secure) throws IOException, KeyStoreException, CertificateException, NoSuchAlgorithmException, UnrecoverableKeyException, KeyManagementException {
        super();
        this.port = port;
        this.setName("ServerSocketThread");

        if (secure) {
            /*
             * https://docs.oracle.com/javase/8/docs/technotes/guides/security/jsse/JSSERefGuide.html
             * @author https://stackoverflow.com/questions/53323855/sslserversocket-and-certificate-setup
             *
             * Key generation
             * https://gitlab.fhnw.ch/bradley.richards/java-projects/blob/master/src/chatroom/Howto_SSL_Certificates_in_Java.odt
             * $ keytool -genkeypair -alias server -keystore server.keystore -keyalg RSA
             * $ keytool -export -alias server -keystore server.keystore -rfc -file key.cert
             * $ keytool -import -alias client -file key.cert -keystore client.keystore
             */
            // Create and initialize the SSLContext with key material
            char[] trustStorePassword = "JassGame".toCharArray();
            char[] keyStorePassword = "JassGame".toCharArray();

            // First initialize the key and trust material
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            trustStore.load(getClass().getResourceAsStream("/ssl/server.keystore"), trustStorePassword);

            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(getClass().getResourceAsStream("/ssl/server.keystore"), keyStorePassword);

            // KeyManagers decide which key material to use
            KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            kmf.init(keyStore, keyStorePassword);

            // TrustManagers decide whether to allow connections
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            tmf.init(trustStore);

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), SecureRandom.getInstanceStrong());

            // Initialize the Server Socket
            SSLServerSocketFactory factory = sslContext.getServerSocketFactory();

            listener = factory.createServerSocket(port, 10, null);
        } else {
            listener = new ServerSocket(port, 10, null);
        }
    }

    @Override
    @SuppressWarnings("InfiniteLoopStatement")
    public void run() {
        logger.info("Starting listener on port " + port);
        while (true) {
            try {
                // Listen for new incoming connections.
                Socket socket = listener.accept();
                clients.add(new ClientUtil(socket));
            } catch (IOException e) {
                logger.info(e.toString());
            }
        }
    }

    /**
     * @param username A string with the username of the user.
     *
     * @return Returns the client if it exists, otherwise null.
     */
    public static synchronized ClientUtil get(final String username) {
        for (ClientUtil c : clients) {
            if (c.getUser() != null && c.getUser().getUsername().equals(username)) {
                return c;
            }
        }
        return null;
    }

    /**
     * Check if there is someone connected using the given username.
     *
     * @param username A string with the username of the user.
     *
     * @return Returns true if a client is connected, otherwise false.
     */
    public static synchronized boolean exists(final String username) {
        for (ClientUtil c : clients) {
            if (c.getUser() != null && c.getUser().getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Remove the desired client from the list of connected clients.
     *
     * @param client The client object.
     */
    public static synchronized void remove(final ClientUtil client) {
        clients.remove(client);
    }
}
