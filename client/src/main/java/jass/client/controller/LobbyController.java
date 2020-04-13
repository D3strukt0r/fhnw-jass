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

import jass.client.entity.LoginEntity;
import jass.client.message.SearchGame;
import jass.client.mvc.Controller;
import jass.client.util.DatabaseUtil;
import jass.client.util.SocketUtil;
import jass.lib.message.SearchGameData;
import jass.lib.servicelocator.ServiceLocator;
import javafx.event.ActionEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * The controller for the dashboard (game) view.
 *
 * @author Thomas Weber
 * @version %I%, %G%
 * @since 0.0.1
 */
public class LobbyController extends Controller {
    private static final Logger logger = LogManager.getLogger(LobbyController.class);
    // GameService gameService


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO: Do something
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
            System.out.println(searchGameMsg.getRawData());
        } else {
            //enableAll();
            logger.error("Error starting search for game");
        }
    }

    public void cancelSearchForGame() {

    }

    public void onGameFound() {

    }

    public void goToGameView() {

    }
}
