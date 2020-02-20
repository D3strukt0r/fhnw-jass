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

package org.orbitrondev.jass.client.FXML;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.StringConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.orbitrondev.jass.client.Entity.ServerEntity;
import org.orbitrondev.jass.client.Entity.ServerRepository;
import org.orbitrondev.jass.client.Utils.SocketUtil;
import org.orbitrondev.jass.client.Utils.DatabaseUtil;
import org.orbitrondev.jass.client.Utils.I18nUtil;
import org.orbitrondev.jass.client.View.ViewHelper;
import org.orbitrondev.jass.lib.ServiceLocator.ServiceLocator;

import java.io.IOException;
import java.net.ConnectException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * The controller for the server connection view.
 *
 * @author Manuele Vaccari
 * @version %I%, %G%
 * @since 0.0.1
 */
public class ServerConnectionController extends FXMLController {
    private static final Logger logger = LogManager.getLogger(ServerConnectionController.class);

    @FXML
    public Menu mFile;
    @FXML
    public Menu mFileChangeLanguage;
    @FXML
    public MenuItem mFileExit;
    @FXML
    public Menu mEdit;
    @FXML
    public MenuItem mEditDelete;
    @FXML
    public Menu mHelp;
    @FXML
    public MenuItem mHelpAbout;

    @FXML
    private VBox root;
    @FXML
    private Text navbar;
    @FXML
    private VBox errorMessage;
    @FXML
    private JFXComboBox<ServerEntity> chooseServer;
    @FXML
    private JFXTextField ip;
    @FXML
    public Text ipHint;
    @FXML
    private JFXTextField port;
    @FXML
    public Text portHint;
    @FXML
    public JFXCheckBox secure;
    @FXML
    public JFXCheckBox connectAutomatically;
    @FXML
    private JFXButton connect;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        DatabaseUtil db = (DatabaseUtil) ServiceLocator.get("db");

        // Bind all texts
        mFile.textProperty().bind(I18nUtil.createStringBinding(mFile.getText()));
        mFileChangeLanguage.textProperty().bind(I18nUtil.createStringBinding(mFileChangeLanguage.getText()));
        mFileExit.textProperty().bind(I18nUtil.createStringBinding(mFileExit.getText()));
        mEdit.textProperty().bind(I18nUtil.createStringBinding(mEdit.getText()));
        mEditDelete.textProperty().bind(I18nUtil.createStringBinding(mEditDelete.getText()));
        mHelp.textProperty().bind(I18nUtil.createStringBinding(mHelp.getText()));
        mHelpAbout.textProperty().bind(I18nUtil.createStringBinding(mHelpAbout.getText()));

        // TODO: Cannot use toUpperCase
        //navbar.textProperty().bind(I18nUtil.createStringBinding(() -> I18nUtil.get(navbar.getText()).toUpperCase()));
        navbar.textProperty().bind(I18nUtil.createStringBinding(navbar.getText()));

        ip.promptTextProperty().bind(I18nUtil.createStringBinding(ip.getPromptText()));
        ipHint.textProperty().bind(I18nUtil.createStringBinding(ipHint.getText()));
        // https://stackoverflow.com/questions/51199903/how-to-bind-a-value-to-the-result-of-a-calculation
        // Check the css at .custom-container (padding left and right = 40)
        DoubleProperty padding = new SimpleDoubleProperty(40.0);
        NumberBinding wrapping = Bindings.subtract(root.widthProperty(), padding);
        ipHint.wrappingWidthProperty().bind(wrapping);

        port.promptTextProperty().bind(I18nUtil.createStringBinding(port.getPromptText()));
        portHint.textProperty().bind(I18nUtil.createStringBinding(portHint.getText()));
        portHint.wrappingWidthProperty().bind(wrapping);

        // TODO: Cannot use toUpperCase
        //connect.textProperty().bind(I18nUtil.createStringBinding(() -> I18nUtil.get(connect.getText()).toUpperCase()));
        connect.textProperty().bind(I18nUtil.createStringBinding(connect.getText()));

        // Updates the view depending if we create a new element or choose an existing one
        chooseServer.setOnAction(event -> {
            ServerEntity server = chooseServer.getSelectionModel().getSelectedItem();
            if (server == null || server.getIp() == null) {
                enableInputs();
                ip.setText("");
                port.setText("");
                secure.setSelected(false);
                connectAutomatically.setSelected(false);
            } else {
                disableInputs();
                ip.setText(server.getIp());
                port.setText(Integer.toString(server.getPort()));
                secure.setSelected(server.isSecure());
                connectAutomatically.setSelected(server.isConnectAutomatically());
            }
        });

        // Converts the ServerEntity to a String
        chooseServer.setConverter(new StringConverter<ServerEntity>() {
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
        // Add the "Create new..." element
        chooseServer.getItems().add(new ServerEntity(null, 0));
        chooseServer.getSelectionModel().selectFirst();
        // Find all saved element
        if (db != null) {
            for (ServerEntity server : db.getServerDao()) {
                chooseServer.getItems().add(server);
            }
        }

        // Disable/Enable the Connect button depending on if the inputs are valid
        AtomicBoolean serverIpValid = new AtomicBoolean(false);
        AtomicBoolean portValid = new AtomicBoolean(false);
        Runnable updateButtonClickable = () -> {
            if (!serverIpValid.get() || !portValid.get()) {
                connect.setDisable(true);
            } else {
                connect.setDisable(false);
            }
        };
        ip.textProperty().addListener((o, oldVal, newVal) -> {
            if (!oldVal.equals(newVal)) {
                serverIpValid.set(ip.validate());
                updateButtonClickable.run();
            }
        });
        ip.getValidators().addAll(
            ViewHelper.useRequiredValidator("gui.serverConnection.ip.empty"),
            ViewHelper.useIsValidIpValidator("gui.serverConnection.ip.notIp")
        );


        port.textProperty().addListener((o, oldVal, newVal) -> {
            if (!oldVal.equals(newVal)) {
                portValid.set(port.validate());
                updateButtonClickable.run();
            }
        });
        port.getValidators().addAll(
            ViewHelper.useRequiredValidator("gui.serverConnection.port.empty"),
            ViewHelper.useIsIntegerValidator("gui.serverConnection.port.nan"),
            ViewHelper.useIsValidPortValidator("gui.serverConnection.port.outOfRange")
        );

        connect.setOnAction(event -> {
            // Disable everything to prevent something while working on the data
            disableAll();

            // Connection would freeze window (and the animations) so do it in a different thread.
            new Thread(() -> {
                ServerEntity server = new ServerEntity(
                    ip.getText(),
                    Integer.parseInt(port.getText()),
                    secure.isSelected(),
                    connectAutomatically.isSelected()
                );
                ServiceLocator.add(server);

                SocketUtil socket = null;
                try {
                    // Try to connect to the server
                    socket = new SocketUtil(server.getIp(), server.getPort(), server.isSecure());
                    ServiceLocator.add(socket);
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

                if (socket != null) {
                    // If the user selected "Create new connection" add it to the DB
                    ServerEntity selectedItem = chooseServer.getSelectionModel().getSelectedItem();
                    if (selectedItem != null && selectedItem.getIp() == null) {
                        try {
                            db.getServerDao().create(server);
                        } catch (SQLException e) {
                            logger.error("Server connection not saved to database");
                        }
                    }
                    // TODO: ControllerHelper.switchToLoginWindow(view);
                }
            }).start();
        });
    }

    /**
     * Disables all the input fields in the view.
     *
     * @since 0.0.1
     */
    public void disableInputs() {
        ip.setDisable(true);
        port.setDisable(true);
        secure.setDisable(true);
        connectAutomatically.setDisable(true);
    }

    /**
     * Disables all the form fields in the view.
     *
     * @since 0.0.1
     */
    public void disableAll() {
        disableInputs();
        connect.setDisable(true);
    }

    /**
     * Enables all the input fields in the view.
     *
     * @since 0.0.1
     */
    public void enableInputs() {
        ip.setDisable(false);
        port.setDisable(false);
        secure.setDisable(false);
        connectAutomatically.setDisable(false);
    }

    /**
     * Enables all the form fields in the view.
     *
     * @since 0.0.1
     */
    public void enableAll() {
        enableInputs();
        connect.setDisable(false);
    }

    /**
     * As the view contains an error message field, this updates the text and the window appropriately.
     *
     * @since 0.0.1
     */
    public void setErrorMessage(String translatorKey) {
        Platform.runLater(() -> {
            if (errorMessage.getChildren().size() == 0) {
                // Make window larger, so it doesn't become crammed, only if we haven't done so yet
                double newHeight = root.getHeight() + 30;
                root.setMaxHeight(newHeight);
                root.setPrefHeight(newHeight);
                root.setMinHeight(newHeight);
            }
            Text text = ViewHelper.useText(translatorKey);
            text.setFill(Color.RED);
            errorMessage.getChildren().clear();
            errorMessage.getChildren().addAll(text, ViewHelper.useSpacer(20));
        });
    }
}
