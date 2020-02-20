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

package org.orbitrondev.jass.client.Controller;

import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.StringConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.orbitrondev.jass.client.Entity.ServerEntity;
import org.orbitrondev.jass.client.Entity.ServerRepository;
import org.orbitrondev.jass.client.Model.ServerConnectionModel;
import org.orbitrondev.jass.client.Utils.SocketUtil;
import org.orbitrondev.jass.client.Utils.DatabaseUtil;
import org.orbitrondev.jass.client.Utils.I18nUtil;
import org.orbitrondev.jass.client.View.ViewHelper;
import org.orbitrondev.jass.client.View.ServerConnectionView;
import org.orbitrondev.jass.client.MVC.Controller;
import org.orbitrondev.jass.lib.ServiceLocator.ServiceLocator;

import java.io.IOException;
import java.net.ConnectException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * The controller for the server connection view.
 *
 * @author Manuele Vaccari
 * @version %I%, %G%
 * @since 0.0.1
 */
public class ServerConnectionController extends Controller<ServerConnectionModel, ServerConnectionView> {
    private static final Logger logger = LogManager.getLogger(ServerConnectionController.class);

    /**
     * Initializes all event listeners for the view.
     *
     * @since 0.0.1
     */
    public ServerConnectionController(ServerConnectionModel model, ServerConnectionView view) {
        super(model, view);
        DatabaseUtil db = (DatabaseUtil) ServiceLocator.get("db");

        // Register ourselves to listen for changes in the dropdown
        view.getChooseServer().setOnAction(event -> updateChosenServer());

        // Register ourselves to listen for button clicks
        view.getBtnConnect().setOnAction(event -> clickOnConnect());

        // Add options to server list drop down
        view.getChooseServer().setConverter(new StringConverter<ServerEntity>() {
            @Override
            public String toString(ServerEntity server) {
                return
                    server == null || server.getIp() == null
                        ? I18nUtil.get("gui.serverConnection.create")
                        : (
                        server.isSecure()
                            ? I18nUtil.get("gui.serverConnection.entry.ssl", server.getIp(), Integer.toString(server.getPort()))
                            : I18nUtil.get("gui.serverConnection.entry", server.getIp(), Integer.toString(server.getPort()))
                    );
            }

            @Override
            public ServerEntity fromString(String string) {
                return null;
            }
        });
        view.getChooseServer().getItems().add(new ServerEntity(null, 0));
        view.getChooseServer().getSelectionModel().selectFirst();
        for (ServerEntity server : db.getServerDao()) {
            view.getChooseServer().getItems().add(server);
        }

        // Disable/Enable the Connect button depending on if the inputs are valid
        AtomicBoolean serverIpValid = new AtomicBoolean(false);
        AtomicBoolean portValid = new AtomicBoolean(false);
        Runnable updateButtonClickable = () -> {
            if (!serverIpValid.get() || !portValid.get()) {
                view.getBtnConnect().setDisable(true);
            } else {
                view.getBtnConnect().setDisable(false);
            }
        };
        view.getServerIp().textProperty().addListener((o, oldVal, newVal) -> {
            if (!oldVal.equals(newVal)) {
                serverIpValid.set(view.getServerIp().validate());
                updateButtonClickable.run();
            }
        });
        view.getPort().textProperty().addListener((o, oldVal, newVal) -> {
            if (!oldVal.equals(newVal)) {
                portValid.set(view.getPort().validate());
                updateButtonClickable.run();
            }
        });

        // Register ourselves to handle window-closing event
        view.getStage().setOnCloseRequest(event -> Platform.exit());
    }

    public void updateChosenServer() {
        ServerEntity server = view.getChooseServer().getSelectionModel().getSelectedItem();

        if (server == null || server.getIp() == null) {
            view.getServerIp().setText("");
            view.getPort().setText("");
            view.getSecure().setSelected(false);
            view.getConnectAutomatically().setSelected(false);
            enableInputs();
        } else {
            disableInputs();
            view.getServerIp().setText(server.getIp());
            view.getPort().setText(Integer.toString(server.getPort()));
            view.getSecure().setSelected(server.isSecure());
            view.getConnectAutomatically().setSelected(server.isConnectAutomatically());
        }
    }

    /**
     * Disables all the input fields in the view.
     *
     * @since 0.0.1
     */
    public void disableInputs() {
        view.getServerIp().setDisable(true);
        view.getPort().setDisable(true);
        view.getSecure().setDisable(true);
        view.getConnectAutomatically().setDisable(true);
    }

    /**
     * Disables all the form fields in the view.
     *
     * @since 0.0.1
     */
    public void disableAll() {
        disableInputs();
        view.getBtnConnect().setDisable(true);
    }

    /**
     * Enables all the input fields in the view.
     *
     * @since 0.0.1
     */
    public void enableInputs() {
        view.getServerIp().setDisable(false);
        view.getPort().setDisable(false);
        view.getSecure().setDisable(false);
        view.getConnectAutomatically().setDisable(false);
    }

    /**
     * Enables all the form fields in the view.
     *
     * @since 0.0.1
     */
    public void enableAll() {
        enableInputs();
        view.getBtnConnect().setDisable(false);
    }

    /**
     * As the view contains an error message field, this updates the text and the window appropriately.
     *
     * @since 0.0.1
     */
    public void setErrorMessage(String translatorKey) {
        Platform.runLater(() -> {
            if (view.getErrorMessage().getChildren().size() == 0) {
                // Make window larger, so it doesn't become crammed, only if we haven't done so yet
                view.getStage().setHeight(view.getStage().getHeight() + 30);
            }
            Text text = ViewHelper.useText(translatorKey);
            text.setFill(Color.RED);
            view.getErrorMessage().getChildren().clear();
            view.getErrorMessage().getChildren().addAll(text, ViewHelper.useSpacer(20));
        });
    }

    /**
     * Handles the click on the connect button. Inputs should already be checked. This will try to connect to the
     * server.
     *
     * @since 0.0.1
     */
    public void clickOnConnect() {
        // Disable everything to prevent something while working on the data
        disableAll();

        // Connection would freeze window (and the animations) so do it in a different thread.
        new Thread(() -> {
            ServerEntity server = new ServerEntity(
                view.getServerIp().getText(),
                Integer.parseInt(view.getPort().getText()),
                view.getSecure().isSelected(),
                view.getConnectAutomatically().isSelected()
            );
            ServiceLocator.add(server);

            SocketUtil backend = null;
            try {
                // Try to connect to the server
                backend = new SocketUtil(server.getIp(), server.getPort(), server.isSecure());
                ServiceLocator.add(backend);
                ServerRepository.setToConnectAutomatically(server); // Make sure it's the only entry
            } catch (ConnectException e) {
                enableAll();
                setErrorMessage("gui.serverConnection.connect.connection");
            } catch (IOException e) {
                enableAll();
                setErrorMessage("gui.serverConnection.connectionFailed");
            } catch (CertificateException | NoSuchAlgorithmException | UnrecoverableKeyException | KeyStoreException | KeyManagementException e) {
                enableAll();
                setErrorMessage("gui.serverConnection.connect.ssl");
            }

            if (backend != null) {
                // If the user selected "Create new connection" add it to the DB
                ServerEntity selectedItem = view.getChooseServer().getSelectionModel().getSelectedItem();
                if (selectedItem != null && selectedItem.getIp() == null) {
                    try {
                        DatabaseUtil db = (DatabaseUtil) ServiceLocator.get("db");
                        db.getServerDao().create(server);
                    } catch (SQLException e) {
                        logger.error("Server connection not saved to database");
                    }
                }
                ControllerHelper.switchToLoginWindow(view);
            }
        }).start();
    }
}
