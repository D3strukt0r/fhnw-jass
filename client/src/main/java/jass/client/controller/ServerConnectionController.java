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

package jass.client.controller;

import com.j256.ormlite.stmt.QueryBuilder;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import jass.client.entity.ServerEntity;
import jass.client.mvc.Controller;
import jass.client.repository.ServerRepository;
import jass.client.util.DatabaseUtil;
import jass.client.util.I18nUtil;
import jass.client.util.SocketUtil;
import jass.client.util.ViewUtil;
import jass.client.util.WindowUtil;
import jass.client.view.AboutView;
import jass.client.view.LoginView;
import jass.lib.servicelocator.ServiceLocator;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.ConnectException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * The controller for the server connection view.
 *
 * @author Manuele Vaccari
 * @version %I%, %G%
 * @since 1.0.0
 */
public final class ServerConnectionController extends Controller {
    /**
     * The logger to print to console and save in a .log file.
     */
    private static final Logger logger = LogManager.getLogger(ServerConnectionController.class);

    /**
     * The root element of the view.
     */
    @FXML
    private VBox root;

    /**
     * The "File" element.
     */
    @FXML
    private Menu mFile;

    /**
     * The "File -> Change Language" element.
     */
    @FXML
    private Menu mFileChangeLanguage;

    /**
     * The "File -> Exit" element.
     */
    @FXML
    private MenuItem mFileExit;

    /**
     * The "Edit" element.
     */
    @FXML
    private Menu mEdit;

    /**
     * The "Edit -> Delete" element.
     */
    @FXML
    private MenuItem mEditDelete;

    /**
     * The "Help" element.
     */
    @FXML
    private Menu mHelp;

    /**
     * The "Help -> About" element.
     */
    @FXML
    private MenuItem mHelpAbout;

    /**
     * The navbar.
     */
    @FXML
    private Text navbar;

    /**
     * The error message.
     */
    @FXML
    private VBox errorMessage;

    /**
     * The saved server lists.
     */
    @FXML
    private JFXComboBox<ServerEntity> chooseServer;

    /**
     * The text field for ip or domain.
     */
    @FXML
    private JFXTextField ipOrDomain;

    /**
     * The IP hint text.
     */
    @FXML
    private Text ipHint;

    /**
     * The port text field.
     */
    @FXML
    private JFXTextField port;

    /**
     * The port hint text.
     */
    @FXML
    private Text portHint;

    /**
     * The SSL checkbox.
     */
    @FXML
    private JFXCheckBox secure;

    /**
     * The "remember me" checkbox.
     */
    @FXML
    private JFXCheckBox connectAutomatically;

    /**
     * The connect button.
     */
    @FXML
    private JFXButton connect;

    /**
     * Whether to create a new entry or not.
     */
    private boolean isNewEntry;

    /**
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        /*
         * Bind all texts
         */
        mFile.textProperty().bind(I18nUtil.createStringBinding(mFile.getText()));
        mFileChangeLanguage.textProperty().bind(I18nUtil.createStringBinding(mFileChangeLanguage.getText()));
        ViewUtil.useLanguageMenuContent(mFileChangeLanguage);
        mFileExit.textProperty().bind(I18nUtil.createStringBinding(mFileExit.getText()));
        mFileExit.setAccelerator(KeyCombination.keyCombination("Alt+F4"));

        mEdit.textProperty().bind(I18nUtil.createStringBinding(mEdit.getText()));
        mEditDelete.textProperty().bind(I18nUtil.createStringBinding(mEditDelete.getText()));

        mHelp.textProperty().bind(I18nUtil.createStringBinding(mHelp.getText()));
        mHelpAbout.textProperty().bind(I18nUtil.createStringBinding(mHelpAbout.getText()));

        // TODO: Cannot use toUpperCase
        //navbar.textProperty().bind(I18nUtil.createStringBinding(() -> I18nUtil.get(navbar.getText()).toUpperCase()));
        navbar.textProperty().bind(I18nUtil.createStringBinding(navbar.getText()));

        ipOrDomain.promptTextProperty().bind(I18nUtil.createStringBinding(ipOrDomain.getPromptText()));
        ipHint.textProperty().bind(I18nUtil.createStringBinding(ipHint.getText()));
        // Check the css at .custom-container (padding left and right = 40)
        ipHint.wrappingWidthProperty().bind(Bindings.subtract(root.widthProperty(), new SimpleDoubleProperty(40.0)));

        port.promptTextProperty().bind(I18nUtil.createStringBinding(port.getPromptText()));
        portHint.textProperty().bind(I18nUtil.createStringBinding(portHint.getText()));
        // Check the css at .custom-container (padding left and right = 40)
        portHint.wrappingWidthProperty().bind(Bindings.subtract(root.widthProperty(), new SimpleDoubleProperty(40.0)));

        connectAutomatically.textProperty().bind(I18nUtil.createStringBinding(connectAutomatically.getText()));
        secure.textProperty().bind(I18nUtil.createStringBinding(secure.getText()));

        // TODO: Cannot use toUpperCase
        //connect.textProperty().bind(I18nUtil.createStringBinding(() -> I18nUtil.get(connect.getText()).toUpperCase()));
        connect.textProperty().bind(I18nUtil.createStringBinding(connect.getText()));
        connect.prefWidthProperty().bind(Bindings.subtract(root.widthProperty(), new SimpleDoubleProperty(40.0)));

        /*
         * Converts the ServerEntity to a String
         */
        chooseServer.setConverter(new StringConverter<ServerEntity>() {
            @Override
            public String toString(final ServerEntity server) {
                if (server == null || server.getIp() == null) {
                    return I18nUtil.get("gui.serverConnection.create");
                } else {
                    if (server.isSecure()) {
                        if (server.isConnectAutomatically()) {
                            return I18nUtil.get("gui.serverConnection.entry.ssl.default", server.getIp(), Integer.toString(server.getPort()));
                        } else {
                            return I18nUtil.get("gui.serverConnection.entry.ssl", server.getIp(), Integer.toString(server.getPort()));
                        }
                    } else {
                        if (server.isConnectAutomatically()) {
                            return I18nUtil.get("gui.serverConnection.entry.default", server.getIp(), Integer.toString(server.getPort()));
                        } else {
                            return I18nUtil.get("gui.serverConnection.entry", server.getIp(), Integer.toString(server.getPort()));
                        }
                    }
                }
            }

            @Override
            public ServerEntity fromString(final String string) {
                return null;
            }
        });
        // Add the "Create new..." element
        chooseServer.getItems().add((new ServerEntity()).setIp(null).setPort(0));
        chooseServer.getSelectionModel().selectFirst();
        isNewEntry = true;
        // Find all saved element
        DatabaseUtil db = ServiceLocator.get(DatabaseUtil.class);
        if (db != null) {
            for (ServerEntity server : ServerRepository.getSingleton(null).getDao()) {
                chooseServer.getItems().add(server);
            }
        }

        /*
         * Disable/Enable the Connect button depending on if the inputs are
         * valid.
         */
        AtomicBoolean serverIpValid = new AtomicBoolean(false);
        AtomicBoolean portValid = new AtomicBoolean(false);
        Runnable updateButtonClickable = () -> connect.setDisable(!serverIpValid.get() || !portValid.get());
        ipOrDomain.textProperty().addListener((o, oldVal, newVal) -> {
            if (!oldVal.equals(newVal)) {
                serverIpValid.set(ipOrDomain.validate());
                updateButtonClickable.run();
            }
        });
        port.textProperty().addListener((o, oldVal, newVal) -> {
            if (!oldVal.equals(newVal)) {
                portValid.set(port.validate());
                updateButtonClickable.run();
            }
        });

        /*
         * Validate input fields
         */
        ipOrDomain.getValidators().addAll(
            ViewUtil.useRequiredValidator("gui.serverConnection.ip.empty")
        );
        port.getValidators().addAll(
            ViewUtil.useRequiredValidator("gui.serverConnection.port.empty"),
            ViewUtil.useIsIntegerValidator("gui.serverConnection.port.nan"),
            ViewUtil.useIsValidPortValidator("gui.serverConnection.port.outOfRange")
        );
    }

    /**
     * As the view contains an error message field, this updates the text and
     * the window appropriately.
     *
     * @param translatorKey The key of the translation.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    public void setErrorMessage(final String translatorKey) {
        Platform.runLater(() -> {
            if (errorMessage.getChildren().size() == 0) {
                // Make window larger, so it doesn't become crammed, only if we
                // haven't done so yet
                Stage stage = getView().getStage();
                stage.setMinHeight(stage.getMinHeight() + 40);
                errorMessage.setPrefHeight(40);
            }
            Text text = ViewUtil.useText(translatorKey);
            text.setFill(Color.RED);
            errorMessage.getChildren().clear();
            errorMessage.getChildren().addAll(text, ViewUtil.useSpacer(20));
        });
    }

    /**
     * Shuts down the application.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    @FXML
    private void clickOnExit() {
        Platform.exit();
    }

    /**
     * Opens the about window.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    @FXML
    public void clickOnAbout() {
        WindowUtil.openInNewWindow(AboutView.class);
    }

    /**
     * Updates the view depending if we create a new element or choose an
     * existing one.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    @FXML
    private void clickOnChooseServer() {
        ServerEntity server = chooseServer.getSelectionModel().getSelectedItem();
        if (server == null || server.getIp() == null) {
            isNewEntry = true;
            ipOrDomain.setText("");
            ipOrDomain.setDisable(false);
            port.setText("");
            port.setDisable(false);
            secure.setSelected(false);
            connectAutomatically.setSelected(false);
        } else {
            isNewEntry = false;
            ipOrDomain.setText(server.getIp());
            ipOrDomain.setDisable(true);
            port.setText(Integer.toString(server.getPort()));
            port.setDisable(true);
            secure.setSelected(server.isSecure());
            connectAutomatically.setSelected(server.isConnectAutomatically());
        }
    }

    /**
     * Handles the click on the connect button. Inputs should already be
     * checked. This will try to connect to the server.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    @FXML
    private void clickOnConnect() {
        // Disable everything to prevent something while working on the data
        ipOrDomain.setDisable(true);
        port.setDisable(true);
        secure.setDisable(true);
        connectAutomatically.setDisable(true);
        connect.setDisable(true);

        // Connection would freeze window (and the animations) so do it in a
        // different thread.
        new Thread(() -> {
            boolean newServer = true;
            ServerEntity server = (new ServerEntity())
                .setIp(ipOrDomain.getText())
                .setPort(Integer.parseInt(port.getText()))
                .setSecure(secure.isSelected())
                .setConnectAutomatically(connectAutomatically.isSelected());

            // Try to find existing server
            DatabaseUtil db = ServiceLocator.get(DatabaseUtil.class);
            if (db != null) {
                try {
                    QueryBuilder<ServerEntity, Integer> findSameServerStmt = ServerRepository.getSingleton(null).getDao().queryBuilder();
                    findSameServerStmt.where()
                        .like(ServerEntity.IP_FIELD_NAME, server.getIp())
                        .and().like(ServerEntity.PORT_FIELD_NAME, server.getPort());
                    List<ServerEntity> findSameServerResult = ServerRepository.getSingleton(null).getDao().query(findSameServerStmt.prepare());

                    if (findSameServerResult.size() != 0) {
                        // Otherwise check if we need to overwrite
                        newServer = false;
                        server = findSameServerResult.get(0);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            SocketUtil socket = null;
            try {
                // Try to connect to the server
                socket = new SocketUtil(server.getIp(), server.getPort(), server.isSecure());
            } catch (ConnectException e) {
                setErrorMessage("gui.serverConnection.connect.connection");
            } catch (IOException e) {
                setErrorMessage("gui.serverConnection.connect.failed");
            } catch (CertificateException | NoSuchAlgorithmException | UnrecoverableKeyException | KeyStoreException | KeyManagementException e) {
                setErrorMessage("gui.serverConnection.connect.ssl");
            }

            if (socket != null) {
                ServiceLocator.add(server);
                ServiceLocator.add(socket);

                // Save the server in the db
                if (db != null) {
                    if (newServer) {
                        if (!ServerRepository.getSingleton(null).add(server)) {
                            logger.error("Couldn't save login data to local database.");
                        }
                        if (server.isConnectAutomatically()) {
                            if (!ServerRepository.getSingleton(null).setToConnectAutomatically(server)) {
                                logger.error("Couldn't set connect automatically.");
                            }
                        }
                    } else {
                        // Update secure
                        if (secure.isSelected() != server.isSecure()) {
                            server.setSecure(secure.isSelected());
                            if (!ServerRepository.getSingleton(null).update(server)) {
                                logger.error("Couldn't update database.");
                            }
                        }

                        // Update connect automatically
                        if (connectAutomatically.isSelected() && !server.isConnectAutomatically()) {
                            if (!ServerRepository.getSingleton(null).setToConnectAutomatically(server)) {
                                logger.error("Couldn't set connect automatically.");
                            }
                        } else if (!connectAutomatically.isSelected() && server.isConnectAutomatically()) {
                            server.setConnectAutomatically(false);
                            if (!ServerRepository.getSingleton(null).update(server)) {
                                logger.error("Couldn't update database.");
                            }
                        }
                    }
                }
                WindowUtil.switchTo(getView(), LoginView.class);
            }

            // Enable all inputs again
            if (isNewEntry) {
                ipOrDomain.setDisable(false);
                port.setDisable(false);
            }
            secure.setDisable(false);
            connectAutomatically.setDisable(false);
            connect.setDisable(false);
        }).start();
    }
}
