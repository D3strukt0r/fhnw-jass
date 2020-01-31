package org.orbitrondev.jass.client.Utils;

import com.sun.net.ssl.internal.ssl.Provider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.orbitrondev.jass.client.Entity.LoginEntity;
import org.orbitrondev.jass.lib.ServiceLocator.Service;
import org.orbitrondev.jass.lib.ServiceLocator.ServiceLocator;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.net.Socket;
import java.security.Security;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

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

    private BufferedReader socketIn;
    private OutputStreamWriter socketOut;

    private volatile ArrayList<String> lastMessage = new ArrayList<>();

    private volatile boolean stopResponseThread = false;

    /**
     * Creates a Socket (insecure) to the backend.
     *
     * @param ipAddress A String containing the ip address to reach the server.
     * @param port      An integer containing the port which the server uses.
     *
     * @since 0.0.1
     */
    public BackendUtil(String ipAddress, int port) throws IOException {
        createStandardSocket(ipAddress, port);

        socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        socketOut = new OutputStreamWriter(socket.getOutputStream());

        createResponseThread();
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

        socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        socketOut = new OutputStreamWriter(socket.getOutputStream());

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
            while (true) {
                String msg;
                String[] msgSplit;

                try {
                    msg = socketIn.readLine();
                    logger.info("Response received: " + msg);

                    if (msg != null && msg.length() > 0) {
                        msgSplit = msg.split("\\|");
                        lastMessage.addAll(Arrays.asList(msgSplit));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    break;
                }
                if (msg == null) break; // In case the server closes the socket
                if (stopResponseThread) break;
            }
        };
        Thread t = new Thread(r);
        t.start();
    }

    public void stopResponseThread() {
        stopResponseThread = true;
    }

    /**
     * Wait until the a "Result" response arrives from the server.
     *
     * @since 0.0.1
     */
    private void waitForResultResponse() {
        while (lastMessage.size() == 0 || !lastMessage.get(0).equals("Result")) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) { /* Ignore */ }
        }
    }

    /**
     * Sends a command to the server.
     *
     * @param command A string containing the whole string which is sent to the server.
     *
     * @throws IOException If an I/O error occurs.
     * @since 0.0.1
     */
    public void sendCommand(String command) throws IOException {
        logger.info("Command sent: " + command);
        socketOut.write(command + "\n");
        socketOut.flush();
    }

    /**
     * Builds the command from the parts and send the command to the server.
     *
     * @param commandParts An array of strings containing all the command parts.
     *
     * @throws IOException If an I/O error occurs.
     * @since 0.0.1
     */
    public void sendCommand(String[] commandParts) throws IOException {
        sendCommand(String.join("|", commandParts));
    }

    /**
     * Register a user on the the server.
     *
     * @param username A string containing the name of the user.
     * @param password A string containing the password of the user.
     *
     * @return "true" when user was created. "false" when name already taken (by a user or chatroom) or is simply
     * invalid.
     *
     * @throws IOException If an I/O error occurs.
     * @since 0.0.1
     */
    public boolean sendCreateLogin(String username, String password) throws IOException {
        sendCommand(new String[]{"CreateLogin", username, password});

        waitForResultResponse();
        boolean result = Boolean.parseBoolean(lastMessage.get(1));
        lastMessage.clear();
        return result;
    }

    /**
     * Login to the server.
     *
     * @param username A string containing the name of the user.
     * @param password A string containing the password of the user.
     *
     * @return A string containing the token when logged in, "null" when username/password to match existing user.
     *
     * @throws IOException If an I/O error occurs.
     * @since 0.0.1
     */
    public String sendLogin(String username, String password) throws IOException, SQLException {
        sendCommand(new String[]{"Login", username, password});

        waitForResultResponse();
        if (lastMessage.get(1).equals("true")) {
            String token = lastMessage.get(2);
            LoginEntity login = new LoginEntity(username, password, token);
            ServiceLocator.remove("login");
            ServiceLocator.add(login);

            DatabaseUtil db = (DatabaseUtil) ServiceLocator.get("db");
            db.getLoginDao().create(login);

            lastMessage.clear();
            return token;
        }
        lastMessage.clear();
        return null;
    }

    /**
     * Login to the server.
     *
     * @param login An LoginModel object containing the username and password.
     *
     * @return An object containing the user when logged in, "null" when username/password to match existing user.
     *
     * @throws IOException If an I/O error occurs.
     * @since 0.0.2
     */
    public LoginEntity sendLogin(LoginEntity login) throws IOException, SQLException {
        sendCommand(new String[]{"Login", login.getUsername(), login.getPassword()});

        waitForResultResponse();
        if (lastMessage.get(1).equals("true")) {
            login.setToken(lastMessage.get(2));
            ServiceLocator.remove("login");
            ServiceLocator.add(login);
            DatabaseUtil db = (DatabaseUtil) ServiceLocator.get("db");
            db.getLoginDao().create(login);

            lastMessage.clear();
            return login;
        }
        lastMessage.clear();
        return null;
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
     * @throws IOException If an I/O error occurs.
     * @since 0.0.1
     */
    public boolean sendChangePassword(String token, String newPassword) throws IOException {
        sendCommand(new String[]{"ChangePassword", token, newPassword});

        waitForResultResponse();
        boolean result = Boolean.parseBoolean(lastMessage.get(1));
        lastMessage.clear();
        return result;
    }

    /**
     * Delete the currently logged in user from the server. After successful deletion, token becomes invalid
     *
     * @param token A string containing a token given by the server.
     *
     * @return "true" by default, "false" when token is invalid.
     *
     * @throws IOException If an I/O error occurs.
     * @since 0.0.1
     */
    public boolean sendDeleteLogin(String token) throws IOException {
        sendCommand(new String[]{"DeleteLogin", token});

        waitForResultResponse();
        boolean result = Boolean.parseBoolean(lastMessage.get(1));
        lastMessage.clear();
        return result;
    }

    /**
     * Logs the current user out from the server. After successful logout, token becomes invalid.
     *
     * @return "true" by default. Impossible to fail.
     *
     * @throws IOException If an I/O error occurs.
     * @since 0.0.1
     */
    public boolean sendLogout() throws IOException {
        sendCommand(new String[]{"Logout"});

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
     * @throws IOException If an I/O error occurs.
     * @since 0.0.1
     */
    public boolean sendUserOnline(String token, String username) throws IOException {
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
