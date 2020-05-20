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

import com.jfoenix.controls.JFXButton;
import jass.client.entity.LoginEntity;
import jass.client.eventlistener.DisconnectEventListener;
import jass.client.message.DeleteLogin;
import jass.client.message.Logout;
import jass.client.mvc.Controller;
import jass.client.util.EventUtil;
import jass.client.util.I18nUtil;
import jass.client.util.SocketUtil;
import jass.client.util.ViewUtil;
import jass.client.util.WindowUtil;
import jass.client.view.AboutView;
import jass.client.view.LobbyView;
import jass.client.view.LoginView;
import jass.client.view.ServerConnectionView;
import jass.lib.message.DeleteLoginData;
import jass.lib.message.LogoutData;
import jass.lib.servicelocator.ServiceLocator;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.Closeable;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * The controller for the delete account view.
 *
 * @author Manuele Vaccari
 * @version %I%, %G%
 * @since 1.0.0
 */
public final class DeleteAccountController extends Controller implements Closeable, DisconnectEventListener {
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
     * The "File -> Disconnect" element.
     */
    @FXML
    private MenuItem mFileDisconnect;

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
     * The message.
     */
    @FXML
    private Text message;

    /**
     * The container for the buttons.
     */
    @FXML
    private HBox buttonGroup;

    /**
     * The delete button.
     */
    @FXML
    private JFXButton delete;

    /**
     * The cancel button.
     */
    @FXML
    private JFXButton cancel;

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
        mFileDisconnect.textProperty().bind(I18nUtil.createStringBinding(mFileDisconnect.getText()));
        mFileExit.textProperty().bind(I18nUtil.createStringBinding(mFileExit.getText()));
        mFileExit.setAccelerator(KeyCombination.keyCombination("Alt+F4"));

        mEdit.textProperty().bind(I18nUtil.createStringBinding(mEdit.getText()));
        mEditDelete.textProperty().bind(I18nUtil.createStringBinding(mEditDelete.getText()));

        mHelp.textProperty().bind(I18nUtil.createStringBinding(mHelp.getText()));
        mHelpAbout.textProperty().bind(I18nUtil.createStringBinding(mHelpAbout.getText()));

        navbar.textProperty().bind(I18nUtil.createStringBinding(navbar.getText()));

        message.textProperty().bind(I18nUtil.createStringBinding(message.getText()));
        DoubleProperty padding = new SimpleDoubleProperty(40.0);
        NumberBinding wrapping = Bindings.subtract(root.widthProperty(), padding);
        message.wrappingWidthProperty().bind(wrapping);

        buttonGroup.prefWidthProperty().bind(Bindings.subtract(root.widthProperty(), new SimpleDoubleProperty(40.0)));
        delete.textProperty().bind(I18nUtil.createStringBinding(delete.getText()));
        delete.prefWidthProperty().bind(Bindings.divide(root.widthProperty(), buttonGroup.getChildren().size()));
        cancel.textProperty().bind(I18nUtil.createStringBinding(cancel.getText()));
        cancel.prefWidthProperty().bind(Bindings.divide(root.widthProperty(), buttonGroup.getChildren().size()));
    }

    /**
     * Disables all the form fields in the view.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    public void disableAll() {
        delete.setDisable(true);
        cancel.setDisable(true);
    }

    /**
     * Enables all the form fields in the view.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    public void enableAll() {
        delete.setDisable(false);
        cancel.setDisable(false);
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
     * Disconnect from the server and returns to the server connection window.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    @FXML
    private void clickOnDisconnect() {
        onDisconnectEvent();
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
     * Handles the click on the delete button. This will send it to the server,
     * and logout if successful.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    @FXML
    public void clickOnDelete() {
        // Disable everything to prevent something while working on the data
        disableAll();

        // Connection would freeze window (and the animations) so do it in a
        // different thread.
        new Thread(() -> {
            SocketUtil backend = ServiceLocator.get(SocketUtil.class);
            assert backend != null;
            LoginEntity login = ServiceLocator.get(LoginEntity.class);
            assert login != null;
            DeleteLogin deleteLoginMsg = new DeleteLogin(new DeleteLoginData(login.getToken()));

            // Try to delete the account
            if (deleteLoginMsg.process(backend)) {
                Logout logoutMsg = new Logout(new LogoutData());

                // If deleted, try logging out now.
                if (logoutMsg.process(backend)) {
                    ServiceLocator.remove(LoginEntity.class);
                    close();
                    WindowUtil.switchTo(getView(), LoginView.class);
                } else {
                    enableAll();
                    setErrorMessage("gui.deleteAccount.logoutFailed");
                }
            } else {
                enableAll();
                setErrorMessage("gui.deleteAccount.deleteFailed");
            }
        }).start();
    }

    /**
     * After clicking on cancel, return to the lobby.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    @FXML
    public void clickOnCancel() {
        close();
        WindowUtil.switchTo(getView(), LobbyView.class);
    }

    /**
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    @Override
    public void close() {
        EventUtil.removeDisconnectListener(this);
    }

    /**
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    @Override
    public void onDisconnectEvent() {
        ServiceLocator.remove(LoginEntity.class);
        close();
        WindowUtil.switchTo(getView(), ServerConnectionView.class);
    }
}
