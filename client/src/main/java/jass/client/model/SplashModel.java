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

package jass.client.model;

import javafx.concurrent.Task;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import jass.client.entity.LoginEntity;
import jass.client.repository.LoginRepository;
import jass.client.entity.ServerEntity;
import jass.client.repository.ServerRepository;
import jass.client.Main;
import jass.client.message.Login;
import jass.client.util.SocketUtil;
import jass.client.util.DatabaseUtil;
import jass.client.mvc.Model;
import jass.lib.message.LoginData;
import jass.lib.servicelocator.ServiceLocator;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * The model for the splash view.
 *
 * @author Manuele Vaccari
 * @version %I%, %G%
 * @since 0.0.1
 */
public final class SplashModel extends Model {
    /**
     * The logger to print to console and save in a .log file.
     */
    private static final Logger logger = LogManager.getLogger(SplashModel.class);

    /**
     * Whether the client is already connected to the server or not.
     */
    private boolean connected = false;

    /**
     * Whether the client is already logged in or not.
     */
    private boolean loggedIn = false;

    /**
     * All the tasks to be done at startup.
     */
    private final Task<Void> initializer = new Task<Void>() {
        @Override
        protected Void call() {
            // Create the service locator to hold our resources

            // List of all tasks
            ArrayList<Runnable> tasks = new ArrayList<>();

            // Initialize the db connection in the service locator
            tasks.add(() -> {
                try {
                    DatabaseUtil db = new DatabaseUtil(DatabaseUtil.SupportedDatabase.SQLITE, Main.dbLocation);
                    ServiceLocator.add(db);
                    logger.info("Connection to database created");
                } catch (SQLException e) {
                    logger.fatal("Error creating connection to database");
                }
            });
            // Check whether we can already connect to the server automatically
            tasks.add(() -> {
                ServerEntity server = ServerRepository.getSingleton(null).findConnectAutomatically();
                if (server != null) {
                    logger.info("Server to connect automatically found");
                    try {
                        SocketUtil backend = new SocketUtil(server.getIp(), server.getPort(), server.isSecure());
                        ServiceLocator.add(backend);
                        connected = true;
                        logger.info("Connected to server");
                    } catch (IOException | KeyStoreException | CertificateException | NoSuchAlgorithmException | UnrecoverableKeyException | KeyManagementException e) { /* Ignore and continue */ }
                }
            });
            // Check whether we can already login
            tasks.add(() -> {
                LoginEntity login = LoginRepository.getSingleton(null).findConnectAutomatically();
                if (login != null) {
                    logger.info("Automatic login found");
                    SocketUtil backend = ServiceLocator.get(SocketUtil.class);
                    if (backend != null) {
                        logger.info("Backend for login is available...");
                        Login loginMsg = new Login(new LoginData(login.getUsername(), login.getPassword()));

                        // Send the login request to the server. Update locally
                        // if successful.
                        if (loginMsg.process(backend)) {
                            login.setToken(loginMsg.getToken());
                            loggedIn = true;
                            logger.info("Logged in");
                        }
                    }
                }
            });

            // First, take some time, update progress
            // Start the progress bar with 1 instead of 0
            this.updateProgress(1, tasks.size() + 1);
            for (int i = 0; i < tasks.size(); i++) {
                tasks.get(i).run();

                this.updateProgress(i + 2, tasks.size() + 1);
            }

            return null;
        }
    };

    /**
     * Initialized the loader.
     */
    public void initialize() {
        new Thread(initializer).start();
    }

    /**
     * @return Returns the initializer.
     */
    public Task<Void> getInitializer() {
        return initializer;
    }

    /**
     * @return Returns whether we are already connected or not.
     */
    public boolean isConnected() {
        return connected;
    }

    /**
     * @return Returns whether we are already logged in or not.
     */
    public boolean isLoggedIn() {
        return loggedIn;
    }
}
