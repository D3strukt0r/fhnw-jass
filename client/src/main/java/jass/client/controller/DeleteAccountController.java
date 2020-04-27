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
import jass.client.entity.LoginEntity;
import jass.client.mvc.Controller;
import jass.client.message.DeleteLogin;
import jass.client.message.Logout;
import jass.client.util.I18nUtil;
import jass.client.util.SocketUtil;
import jass.client.view.DeleteAccountView;
import jass.client.view.GameView;
import jass.client.view.LoginView;
import jass.client.view.ServerConnectionView;
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
import jass.client.util.WindowUtil;
import jass.client.util.ViewUtil;
import jass.lib.message.DeleteLoginData;
import jass.lib.message.LogoutData;
import jass.lib.servicelocator.ServiceLocator;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * The controller for the delete account view.
 *
 * @author Manuele Vaccari
 * @version %I%, %G%
 * @since 0.0.1
 */
public final class DeleteAccountController extends Controller {
    /**
     * The view.
     */
    private DeleteAccountView view;

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
     * The delete button.
     */
    @FXML
    private JFXButton delete;

    /**
     * The cancel button.
     */
    @FXML
    private JFXButton cancel;

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

        delete.textProperty().bind(I18nUtil.createStringBinding(delete.getText()));
        cancel.textProperty().bind(I18nUtil.createStringBinding(cancel.getText()));
    }

    /**
     * Disables all the form fields in the view.
     *
     * @since 0.0.1
     */
    public void disableAll() {
        delete.setDisable(true);
        cancel.setDisable(true);
    }

    /**
     * Enables all the form fields in the view.
     *
     * @since 0.0.1
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
     * @since 0.0.1
     */
    public void setErrorMessage(final String translatorKey) {
        Platform.runLater(() -> {
            if (errorMessage.getChildren().size() == 0) {
                // Make window larger, so it doesn't become crammed, only if we haven't done so yet
                view.getStage().setHeight(view.getStage().getHeight() + 30);
            }
            Text text = ViewUtil.useText(translatorKey);
            text.setFill(Color.RED);
            errorMessage.getChildren().clear();
            errorMessage.getChildren().addAll(text, ViewUtil.useSpacer(20));
        });
    }

    /**
     * Disconnect from the server and returns to the server connection window.
     */
    @FXML
    private void clickOnDisconnect() {
        SocketUtil socket = (SocketUtil) ServiceLocator.get(SocketUtil.class);
        if (socket != null) { // Not necessary but keeps IDE happy
            socket.close();
        }
        ServiceLocator.remove(SocketUtil.class);
        WindowUtil.switchTo(view, ServerConnectionView.class);
    }

    /**
     * Shuts down the application.
     */
    @FXML
    private void clickOnExit() {
        Platform.exit();
    }

    /**
     * Handles the click on the delete button. This will send it to the server,
     * and logout if successful.
     *
     * @since 0.0.1
     */
    @FXML
    public void clickOnDelete() {
        // Disable everything to prevent something while working on the data
        disableAll();

        // Connection would freeze window (and the animations) so do it in a different thread.
        new Thread(() -> {
            SocketUtil backend = (SocketUtil) ServiceLocator.get(SocketUtil.class);
            LoginEntity login = (LoginEntity) ServiceLocator.get(LoginEntity.class);
            DeleteLogin deleteLoginMsg = new DeleteLogin(new DeleteLoginData(login.getToken()));

            // Try to delete the account
            if (deleteLoginMsg.process(backend)) {
                Logout logoutMsg = new Logout(new LogoutData());

                // If deleted, try logging out now.
                if (logoutMsg.process(backend)) {
                    ServiceLocator.remove(LoginEntity.class);
                    WindowUtil.switchTo(view, LoginView.class);
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
     */
    @FXML
    public void clickOnCancel() {
        WindowUtil.switchTo(view, GameView.class);
    }

    /**
     * @param view The view.
     */
    public void setView(final DeleteAccountView view) {
        this.view = view;
    }

    /**
     * @return Returns the delete button
     */
    public JFXButton getDelete() {
        return delete;
    }
}
