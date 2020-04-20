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
import jass.client.eventlistener.GameFoundEventListener;
import jass.client.message.CancelSearchGame;
import jass.client.message.SearchGame;
import jass.client.mvc.Controller;
import jass.client.util.SocketUtil;
import jass.lib.message.CancelSearchGameData;
import jass.lib.message.SearchGameData;
import jass.lib.servicelocator.ServiceLocator;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import jass.client.util.I18nUtil;
import jass.client.util.ViewUtil;
import jass.client.util.WindowUtil;
import jass.client.view.LobbyView;
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
 * @author Manuele Vaccari & Thomas Weber & Sasa Trajkova
 * @version %I%, %G%
 * @since 0.0.1
 */
public class LobbyController extends Controller implements GameFoundEventListener {
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


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO: Do something
        SocketUtil socket = (SocketUtil) ServiceLocator.get("backend");
        if (socket != null) { // Not necessary but keeps IDE happy
            socket.setGameFoundEventListener(this);
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

        mHelp.textProperty().bind(I18nUtil.createStringBinding(mHelp.getText()));
        mHelpAbout.textProperty().bind(I18nUtil.createStringBinding(mHelpAbout.getText()));

        findMatch.textProperty().bind(I18nUtil.createStringBinding(findMatch.getText()));
        cancelMatch.textProperty().bind(I18nUtil.createStringBinding(cancelMatch.getText()));
        searching.textProperty().bind(I18nUtil.createStringBinding(searching.getText()));
    }

    public void startSearchForGame(ActionEvent actionEvent) {
        // Get token and initialize SearchGame Message
        LoginEntity login = (LoginEntity) ServiceLocator.get("login");
        String token = login.getToken();
        String userName = login.getUsername();
        SearchGame searchGameMsg = new SearchGame(new SearchGameData(token, userName));
        SocketUtil backend = (SocketUtil) ServiceLocator.get("backend");

        // Send SearchGame Message to Server
        if (searchGameMsg.process(backend)) {
            // TODO - Change Button from "Search Game" to "Cancel Search"

        } else {
            //enableAll();
            // TODO - Show Error Message
            logger.error("Error starting search for game");
        }
    }

    /**
     * After clicking on Find match, change the button text to "Cancel" and show text "searching"
     */
    @FXML
    public void clickOnFindMatch() {
        // Get token and initialize SearchGame Message
        LoginEntity login = (LoginEntity) ServiceLocator.get("login");
        String token = login.getToken();
        String userName = login.getUsername();
        SearchGame searchGameMsg = new SearchGame(new SearchGameData(token, userName));
        SocketUtil backend = (SocketUtil) ServiceLocator.get("backend");

        // Send SearchGame Message to Server
        if (searchGameMsg.process(backend)) {
            searching.setVisible(true);
            cancelMatch.setVisible(true);
            findMatch.setVisible(false);
        } else {
            logger.error("Error starting search for game");
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Error searching for a game. Please try again!");
                alert.showAndWait();
            });
        }

    }

    /**
     * After clicking on Cancel match, Find match button appears and text "searching" is hidden
     */
    @FXML
    public void clickOnCancelMatch() {
        // Get token and initialize SearchGame Message
        LoginEntity login = (LoginEntity) ServiceLocator.get("login");
        String token = login.getToken();
        String userName = login.getUsername();
        CancelSearchGame cancelSearchGameMsg = new CancelSearchGame(new CancelSearchGameData(token, userName));
        SocketUtil backend = (SocketUtil) ServiceLocator.get("backend");

        // Send SearchGame Message to Server
        if (cancelSearchGameMsg.process(backend)) {
            searching.setVisible(false);
            findMatch.setVisible(true);
            cancelMatch.setVisible(false);
        } else {
            logger.error("Error cancelling search for game");
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Error cancelling search for a game. Please try again!");
                alert.showAndWait();
            });
        }
    }

    public void onGameFound() {
        logger.info("Successfully found game!");


        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Game Found!");
            alert.showAndWait();
        });

        goToGameView();
    }

    public void goToGameView() {
        // TODO - Move to game view
    }

    /**
     * Disconnect from the server and returns to the server connection window.
     */
    @FXML
    private void clickOnDisconnect() {
        SocketUtil socket = (SocketUtil) ServiceLocator.get("backend");
        if (socket != null) { // Not necessary but keeps IDE happy
            socket.close();
        }
        ServiceLocator.remove("backend");
        WindowUtil.switchToServerConnectionWindow();
        Platform.runLater(() -> this.view.getScene().getWindow().hide());
    }

    /**
     * Keeps the server connection but returns to the login window.
     */
    @FXML
    public void clickOnLogout() {
        SocketUtil socket = (SocketUtil) ServiceLocator.get("backend");
        if (socket != null) { // Not necessary but keeps IDE happy
            socket.close();
        }
        ServiceLocator.remove("backend");
        WindowUtil.switchToLoginWindow();
        Platform.runLater(() -> this.view.getScene().getWindow().hide());
    }

    /**
     * Shuts down the application.
     */
    @FXML
    private void clickOnExit() {
        Platform.exit();
    }

    /**
     * @param view The view.
     */
    public void setView(final LobbyView view) {
        this.view = view;
    }

}
