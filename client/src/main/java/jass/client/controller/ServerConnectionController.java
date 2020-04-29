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

package jass.client.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import jass.client.util.DatabaseUtil;
import jass.client.util.GameUtil;
import jass.client.util.I18nUtil;
import jass.client.util.SocketUtil;
import jass.client.util.ViewUtil;
import jass.client.util.WindowUtil;
import jass.client.view.LoginView;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.StringConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import jass.client.entity.ServerEntity;
import jass.client.repository.ServerRepository;
import jass.client.mvc.Controller;
import jass.client.view.ServerConnectionView;
import jass.lib.servicelocator.ServiceLocator;

import java.io.IOException;
import java.net.ConnectException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * The controller for the server connection view.
 *
 * @author Manuele Vaccari
 * @version %I%, %G%
 * @since 0.0.1
 */
public final class ServerConnectionController extends Controller {
    /**
     * The logger to print to console and save in a .log file.
     */
    private static final Logger logger = LogManager.getLogger(ServerConnectionController.class);

    /**
     * The view.
     */
    private ServerConnectionView view;

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
     * The root element of the view.
     */
    @FXML
    private VBox root;

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

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        DatabaseUtil db = ServiceLocator.get(DatabaseUtil.class);

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
        // https://stackoverflow.com/questions/51199903/how-to-bind-a-value-to-the-result-of-a-calculation
        // Check the css at .custom-container (padding left and right = 40)
        DoubleProperty padding = new SimpleDoubleProperty(40.0);
        NumberBinding wrapping = Bindings.subtract(root.widthProperty(), padding);
        ipHint.wrappingWidthProperty().bind(wrapping);

        port.promptTextProperty().bind(I18nUtil.createStringBinding(port.getPromptText()));
        portHint.textProperty().bind(I18nUtil.createStringBinding(portHint.getText()));
        portHint.wrappingWidthProperty().bind(wrapping);

        connectAutomatically.textProperty().bind(I18nUtil.createStringBinding(connectAutomatically.getText()));
        secure.textProperty().bind(I18nUtil.createStringBinding(secure.getText()));

        // TODO: Cannot use toUpperCase
        //connect.textProperty().bind(I18nUtil.createStringBinding(() -> I18nUtil.get(connect.getText()).toUpperCase()));
        connect.textProperty().bind(I18nUtil.createStringBinding(connect.getText()));

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
        chooseServer.getItems().add(new ServerEntity(null, 0));
        chooseServer.getSelectionModel().selectFirst();
        // Find all saved element
        if (db != null) {
            for (ServerEntity server : db.getServerDao()) {
                chooseServer.getItems().add(server);
            }
        }

        /*
         * Disable/Enable the Connect button depending on if the inputs are valid
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
     * Disables all the input fields in the view.
     *
     * @since 0.0.1
     */
    public void disableInputs() {
        ipOrDomain.setDisable(true);
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
        ipOrDomain.setDisable(false);
        port.setDisable(false);
        secure.setDisable(false);
        connectAutomatically.setDisable(false);
    }

    /**
     * Enables all the form fields in the view, if it's a new entry.
     *
     * @since 0.0.1
     */
    public void enableAllIfNew() {
        ServerEntity server = chooseServer.getSelectionModel().getSelectedItem();
        if (server == null || server.getIp() == null) {
            enableInputs();
        }
        connect.setDisable(false);
    }

    /**
     * As the view contains an error message field, this updates the text and
     * the window appropriately.
     *
     * @param translatorKey The key of the translation.
     *
     * @since 0.0.1
     */
    public void setErrorMessage(final String translatorKey) {
        Platform.runLater(() -> {
            if (errorMessage.getChildren().size() == 0) {
                // Make window larger, so it doesn't become crammed, only if we haven't done so yet
                // TODO: This keeps the window size even after switching to e.g. login
                //view.getStage().setHeight(view.getStage().getHeight() + 30);
                errorMessage.setPrefHeight(30);
            }
            Text text = ViewUtil.useText(translatorKey);
            text.setFill(Color.RED);
            errorMessage.getChildren().clear();
            errorMessage.getChildren().addAll(text, ViewUtil.useSpacer(20));
        });
    }

    /**
     * Shuts down the application.
     */
    @FXML
    private void clickOnExit() {
        Platform.exit();
    }

    /**
     * Updates the view depending if we create a new element or choose an
     * existing one.
     *
     * @since 0.0.1
     */
    @FXML
    private void clickOnChooseServer() {
        ServerEntity server = chooseServer.getSelectionModel().getSelectedItem();
        if (server == null || server.getIp() == null) {
            enableInputs();
            ipOrDomain.setText("");
            port.setText("");
            secure.setSelected(false);
            connectAutomatically.setSelected(false);
        } else {
            disableInputs();
            ipOrDomain.setText(server.getIp());
            port.setText(Integer.toString(server.getPort()));
            secure.setSelected(server.isSecure());
            connectAutomatically.setSelected(server.isConnectAutomatically());
        }
    }

    /**
     * Handles the click on the connect button. Inputs should already be
     * checked. This will try to connect to the server.
     *
     * @since 0.0.1
     */
    @FXML
    private void clickOnConnect() {
        // Disable everything to prevent something while working on the data
        disableAll();

        // Connection would freeze window (and the animations) so do it in a different thread.
        new Thread(() -> {
            ServerEntity server = new ServerEntity(
                ipOrDomain.getText(),
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

                ServerRepository.getSingleton(null).setToConnectAutomatically(server); // Make sure it's the only entry
            } catch (ConnectException e) {
                enableAllIfNew();
                setErrorMessage("gui.serverConnection.connect.connection");
            } catch (IOException e) {
                enableAllIfNew();
                setErrorMessage("gui.serverConnection.connect.failed");
            } catch (CertificateException | NoSuchAlgorithmException | UnrecoverableKeyException | KeyStoreException | KeyManagementException e) {
                enableAllIfNew();
                setErrorMessage("gui.serverConnection.connect.ssl");
            }

            if (socket != null) {
                // If the user selected "Create new connection" add it to the DB
                ServerEntity selectedItem = chooseServer.getSelectionModel().getSelectedItem();
                if (selectedItem != null && selectedItem.getIp() == null) {
                    if (!ServerRepository.getSingleton(null).add(server)) {
                        logger.error("Server connection not saved to database");
                    }
                }
                WindowUtil.switchTo(view, LoginView.class);
            }
        }).start();
    }

    /**
     * @return Returns the connect button
     */
    public JFXButton getConnect() {
        return connect;
    }

    /**
     * @param view The view.
     */
    public void setView(final ServerConnectionView view) {
        this.view = view;
    }
}
