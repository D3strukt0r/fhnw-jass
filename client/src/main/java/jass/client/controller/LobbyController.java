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
import jass.client.eventlistener.GameFoundEventListener;
import jass.client.message.CancelSearchGame;
import jass.client.message.Logout;
import jass.client.message.SearchGame;
import jass.client.mvc.Controller;
import jass.client.util.GameUtil;
import jass.client.util.I18nUtil;
import jass.client.util.SocketUtil;
import jass.client.util.ViewUtil;
import jass.client.util.WindowUtil;
import jass.client.view.AboutView;
import jass.client.view.GameView;
import jass.lib.message.CancelSearchGameData;
import jass.lib.message.GameFoundData;
import jass.lib.message.LogoutData;
import jass.lib.message.SearchGameData;
import jass.lib.servicelocator.ServiceLocator;
import javafx.scene.control.Alert;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import jass.client.view.LobbyView;
import jass.client.view.LoginView;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * The controller for the lobby view.
 *
 * @author Manuele Vaccari & Thomas Weber & Sasa Trajkova & Victor Hargrave
 * @version %I%, %G%
 * @since 1.0.0
 */
public final class LobbyController extends Controller implements GameFoundEventListener {
    /**
     * The logger to print to console and save in a .log file.
     */
    private static final Logger logger = LogManager.getLogger(LobbyController.class);

    // GameService gameService

    /**
     * The view.
     */
    private LobbyView view;

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
     * The "File -> Logout" element.
     */
    @FXML
    private MenuItem mFileLogout;

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
     * The "File -> Change password" element.
     */
    @FXML
    private MenuItem mEditChangePassword;

    /**
     * The "File -> Delete account" element.
     */
    @FXML
    private MenuItem mEditDeleteAccount;

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
     * The Find Match button.
     */
    @FXML
    private JFXButton findMatch;

    /**
     * The Cancel Match button.
     */
    @FXML
    private JFXButton cancelMatch;

    /**
     * The searching text.
     */
    @FXML
    private Text searching;

    /**
     * A quick access variable for the current game.
     */
    private GameUtil gameUtil;

    /**
     * @author Manuele Vaccari & Thomas Weber & Sasa Trajkova & Victor Hargrave
     * @since 1.0.0
     */
    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        SocketUtil socket = ServiceLocator.get(SocketUtil.class);
        if (socket != null) { // Not necessary but keeps IDE happy
            socket.setGameFoundEventListener(this);
        }

        gameUtil = ServiceLocator.get(GameUtil.class);
        if (gameUtil == null) {
            gameUtil = new GameUtil();
            ServiceLocator.add(gameUtil);
        }

        /*
         * Bind all texts
         */
        mFile.textProperty().bind(I18nUtil.createStringBinding(mFile.getText()));
        mFileChangeLanguage.textProperty().bind(I18nUtil.createStringBinding(mFileChangeLanguage.getText()));
        ViewUtil.useLanguageMenuContent(mFileChangeLanguage);
        mFileDisconnect.textProperty().bind(I18nUtil.createStringBinding(mFileDisconnect.getText()));
        mFileLogout.textProperty().bind(I18nUtil.createStringBinding(mFileLogout.getText()));
        mFileExit.textProperty().bind(I18nUtil.createStringBinding(mFileExit.getText()));
        mFileExit.setAccelerator(KeyCombination.keyCombination("Alt+F4"));

        mEdit.textProperty().bind(I18nUtil.createStringBinding(mEdit.getText()));
        mEditChangePassword.textProperty().bind(I18nUtil.createStringBinding(mEditChangePassword.getText()));
        mEditDeleteAccount.textProperty().bind(I18nUtil.createStringBinding(mEditDeleteAccount.getText()));

        mHelp.textProperty().bind(I18nUtil.createStringBinding(mHelp.getText()));
        mHelpAbout.textProperty().bind(I18nUtil.createStringBinding(mHelpAbout.getText()));

        findMatch.textProperty().bind(I18nUtil.createStringBinding(findMatch.getText()));
        cancelMatch.textProperty().bind(I18nUtil.createStringBinding(cancelMatch.getText()));
        searching.textProperty().bind(I18nUtil.createStringBinding(searching.getText()));
    }

    /**
     * After clicking on Find match, change the button text to "Cancel" and show
     * text "searching" and the logic for searching for a game (client &
     * server).
     *
     * @author Thomas Weber & Victor Hargrave & Manuele Vaccari
     * @since 1.0.0
     */
    @FXML
    public void clickOnFindMatch() {
        // Get token and initialize SearchGame Message
        LoginEntity login = ServiceLocator.get(LoginEntity.class);
        assert login != null;
        String token = login.getToken();
        String userName = login.getUsername();
        SearchGame searchGameMsg = new SearchGame(new SearchGameData(token, userName));
        SocketUtil backend = ServiceLocator.get(SocketUtil.class);
        assert backend != null;

        searching.setVisible(true);
        cancelMatch.setVisible(true);
        findMatch.setVisible(false);
        // Send SearchGame Message to Server
        if (!searchGameMsg.process(backend)) {
            logger.error("Error starting search for game");
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Error searching for a game. Please try again!");
                alert.showAndWait();
            });
        }

    }

    /**
     * After clicking on Cancel match, Find match button appears and text
     * "searching" is hidden.
     *
     * @author Thomas Weber & Victor Hargrave & Manuele Vaccari
     * @since 1.0.0
     */
    @FXML
    public void clickOnCancelMatch() {
        // Get token and initialize SearchGame Message
        LoginEntity login = ServiceLocator.get(LoginEntity.class);
        assert login != null;
        String token = login.getToken();
        String userName = login.getUsername();
        CancelSearchGame cancelSearchGameMsg = new CancelSearchGame(new CancelSearchGameData(token, userName));
        SocketUtil backend = ServiceLocator.get(SocketUtil.class);
        assert backend != null;

        searching.setVisible(false);
        findMatch.setVisible(true);
        cancelMatch.setVisible(false);
        // Send SearchGame Message to Server
        if (!cancelSearchGameMsg.process(backend)) {
            logger.error("Error cancelling search for game");
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Error cancelling search for a game. Please try again!");
                alert.showAndWait();
            });
        }
    }

    /**
     * Work to do after a game was found.
     *
     * @param msgData The game found data.
     *
     * @author Thomas Weber
     * @since 1.0.0
     */
    public void onGameFound(final GameFoundData msgData) {
        logger.info("Successfully found game!");

        Platform.runLater(() -> {
            searching.setVisible(false);
            findMatch.setVisible(true);
            cancelMatch.setVisible(false);
        });

        GameUtil gameUtil = ServiceLocator.get(GameUtil.class);
        assert gameUtil != null;
        gameUtil.setGame(msgData);

        WindowUtil.switchTo(view, GameView.class);
    }

    /**
     * Disconnect from the server and returns to the server connection window.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    @FXML
    private void clickOnDisconnect() {
        ServiceLocator.remove(LoginEntity.class);
        SocketUtil socket = ServiceLocator.get(SocketUtil.class);
        if (socket != null) { // Not necessary but keeps IDE happy
            socket.close();
        }
        ServiceLocator.remove(SocketUtil.class);
        WindowUtil.switchTo(view, LoginView.class);
    }

    /**
     * Keeps the server connection but returns to the login window.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    @FXML
    public void clickOnLogout() {
        SocketUtil socket = ServiceLocator.get(SocketUtil.class);
        assert socket != null;

        Logout logoutMsg = new Logout(new LogoutData());
        socket.send(logoutMsg);
        ServiceLocator.remove(LoginEntity.class);
        WindowUtil.switchTo(view, LoginView.class);
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
     * Shuts down the application.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    @FXML
    private void clickOnChangePassword() {
        close();
        WindowUtil.switchTo(getView(), ChangePasswordView.class);
    }

    /**
     * Shuts down the application.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    @FXML
    private void clickOnDeleteAccount() {
        close();
        WindowUtil.switchTo(getView(), DeleteAccountView.class);
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
     * @param view The view.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    public void setView(final LobbyView view) {
        this.view = view;
    }
}
