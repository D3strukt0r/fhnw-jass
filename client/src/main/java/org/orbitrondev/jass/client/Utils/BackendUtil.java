package org.orbitrondev.jass.client.Utils;

import com.sun.net.ssl.internal.ssl.Provider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.orbitrondev.jass.client.Entity.LoginEntity;
import org.orbitrondev.jass.client.Message.Message;
import org.orbitrondev.jass.lib.Message.MessageData;
import org.orbitrondev.jass.lib.ServiceLocator.Service;
import org.orbitrondev.jass.lib.ServiceLocator.ServiceLocator;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.lang.reflect.Constructor;
import java.net.Socket;
import java.security.Security;
import java.util.ArrayList;

/**
 * Backend utility class. Acts as an interface between the program and the server.
 *
 * @author Manuele Vaccari
 * @version %I%, %G%
 * @since 0.0.1
 */
public class BackendUtil implements Service, Closeable {
    private static final Logger logger = LogManager.getLogger(BackendUtil.class);

    private Socket socket;
    private volatile boolean serverReachable = true;

    private ArrayList<Message> lastMessages = new ArrayList<>();

    /**
     * Creates a Socket (insecure) to the backend.
     *
     * @param ipAddress A String containing the ip address to reach the server.
     * @param port      An integer containing the port which the server uses.
     *
     * @since 0.0.1
     */
    public BackendUtil(String ipAddress, int port) throws IOException {
        this(ipAddress, port, false);
    }

    /**
     * Creates a Socket (insecure or secure) to the backend.
     *
     * @param ipAddress A String containing the ip address to reach the server.
     * @param port      An integer containing the port which the server uses.
     * @param secure    A boolean defining whether to use SSL or not.
     *
     * @since 0.0.1
     */
    public BackendUtil(String ipAddress, int port, boolean secure) throws IOException {
        if (secure) {
            createSecureSocket(ipAddress, port);
        } else {
            createStandardSocket(ipAddress, port);
        }

        createResponseThread();
    }

    /**
     * Creates a thread in the background, which waits for a response from the server.
     *
     * @since 0.0.1
     */
    private void createResponseThread() {
        // Create thread to read incoming messages
        Runnable r = () -> {
            while (serverReachable) {
                Message msg = null;
                try {
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String msgText = in.readLine(); // Will wait here for complete line
                    if (msgText == null) break; // In case the server closes the socket

                    logger.info("Receiving message: " + msgText);

                    // Break message into individual parts, and remove extra spaces
                    MessageData msgData = MessageData.unserialize(msgText);

                    // Create a message object of the correct class, using reflection
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

                lastMessages.add(msg);
            }
        };
        Thread t = new Thread(r);
        t.start();
    }

    /**
     * Wait until the corresponding "Result" response arrives from the server.
     *
     * @since 0.0.1
     */
    public Message waitForResultResponse(int id) {
        while (true) {
            if (lastMessages.size() != 0) {
                for (Message m : lastMessages) {
                    if (m.getId() == id) {
                        lastMessages.remove(m);
                        return m;
                    }
                }
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) { /* Ignore */ }
        }
    }


    /**
     * Send a message to this server. In case of an exception, log the client out.
     */
    public void send(Message msg) {
        try {
            OutputStreamWriter out = new OutputStreamWriter(socket.getOutputStream());
            logger.info("Sending message: " + this.toString());
            out.write(msg.toString() + "\n"); // This will send the serialized MessageData object
            out.flush();
        } catch (IOException e) {
            logger.info("Server unreachable; logged out");
            serverReachable = false;
        }
    }

    /**
     * @return "true" if logged in, otherwise "false"
     *
     * @since 0.0.2
     */
    public boolean isLoggedIn() {
        return ServiceLocator.get("login") != null;
    }

    /**
     * @return A string containing the token if logged in, otherwise "null"
     *
     * @since 0.0.2
     */
    public String getToken() {
        LoginEntity login = (LoginEntity) ServiceLocator.get("login");
        if (login != null) {
            return login.getToken();
        }
        return null;
    }

    /**
     * Overwrite the password of currently logged in user.
     *
     * @param token       A string containing a token given by the server.
     * @param newPassword A string containing the new password to overwrite.
     *
     * @return "true" by default, "false" when the token is invalid.
     *
     * @since 0.0.1
     */
    public boolean sendChangePassword(String token, String newPassword) {
        sendCommand(new String[]{"ChangePassword", token, newPassword});

        waitForResultResponse();
        boolean result = Boolean.parseBoolean(lastMessage.get(1));
        lastMessage.clear();
        return result;
    }

    /**
     * Checks whether the user is currently logged in.
     *
     * @param token    A string containing a token given by the server.
     * @param username A string containing the username of the wanted user.
     *
     * @return "true" if user is currently logged in, "false" if not.
     *
     * @since 0.0.1
     */
    public boolean sendUserOnline(String token, String username) {
        sendCommand(new String[]{"UserOnline", token, username});

        waitForResultResponse();
        boolean result = Boolean.parseBoolean(lastMessage.get(1));
        lastMessage.clear();
        return result;
    }

    /**
     * Creates a normal socket to the server.
     *
     * @param ipAddress A String containing the ip address to reach the server.
     * @param port      An integer containing the port which the server uses.
     *
     * @throws IOException If an I/O error occurs.
     * @since 0.0.1
     */
    public void createStandardSocket(String ipAddress, int port) throws IOException {
        logger.info("Connecting to server at: " + ipAddress + ":" + port);
        socket = new Socket(ipAddress, port);
    }

    /**
     * Creates a secure socket (SSL) to the server.
     *
     * @param ipAddress A String containing the ip address to reach the server.
     * @param port      An integer containing the port which the server uses.
     *
     * @throws IOException If an I/O error occurs.
     * @since 0.0.1
     */
    public void createSecureSocket(String ipAddress, int port) throws IOException {
        logger.info("Connecting to server at: " + ipAddress + ":" + port + " (with SSL)");

        // TODO: SSL is not properly setup
        // Check out: https://gitlab.fhnw.ch/bradley.richards/java-projects/blob/master/src/chatroom/Howto_SSL_Certificates_in_Java.odt

        // Registering the JSSE provider
        Security.addProvider(new Provider());

        // Specifying the Truststore details. This is needed if you have created a
        // truststore, for example, for self-signed certificates
        System.setProperty("javax.net.ssl.trustStore", "truststore.ts");
        System.setProperty("javax.net.ssl.trustStorePassword", "trustme");

        // Creating Client Sockets
        SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        socket = sslsocketfactory.createSocket(ipAddress, port);

        // The next line is entirely optional !!
        // The SSL handshake would happen automatically, the first time we send data.
        // Or we can immediately force the handshaking with this method:
        ((SSLSocket) socket).startHandshake();
    }

    /**
     * Verifies that the string is a valid ip address.
     *
     * @param ipAddress A String containing the ip address.
     *
     * @return "true" if valid, "false" if not.
     *
     * @since 0.0.1
     */
    /*
     * Reference: https://stackoverflow.com/questions/5667371/validate-ipv4-address-in-java
     */
    public static boolean isValidIpAddress(String ipAddress) {
        return ipAddress.matches("^((0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)\\.){3}(0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)$");
    }

    /**
     * Verifies that the string is in the valid range of open ports.
     *
     * @param port An integer containing the port.
     *
     * @return "true" if valid, "false" if not.
     *
     * @since 0.0.1
     */
    public static boolean isValidPortNumber(int port) {
        return port >= 1024 && port <= 65535;
    }

    /**
     * Closes the socket.
     *
     * @throws IOException If an I/O error occurs.
     * @since 0.0.1
     */
    @Override
    public void close() throws IOException {
        if (socket != null) {
            socket.close();
        }
    }

    @Override
    public String getName() {
        return "backend";
    }
}
