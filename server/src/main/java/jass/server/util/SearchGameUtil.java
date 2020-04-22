/*
 * fhnw-jass is jass game programmed in java for a school project.
 * Copyright (C) 2020 Manuele Vaccari, Victor Hargrave, Sasa Trajkova, Thomas
 * Weber
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

package jass.server.util;

import jass.lib.message.GameFoundData;
import jass.server.entity.GameEntity;
import jass.server.entity.TeamEntity;
import jass.server.entity.UserEntity;
import jass.server.message.GameFound;
import jass.server.repository.GameRepository;
import jass.server.repository.TeamRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import jass.lib.servicelocator.Service;

/**
 * Creates a game once 4 clients are searching for a game in the lobby.
 *
 * @author Thomas Weber
 * @version %I%, %G%
 * @since 0.0.1
 */
public final class SearchGameUtil implements Service {
    /**
     * The service name.
     */
    public static final String SERVICE_NAME = "SearchGameUtil";

    /**
     * The logger to print to console and save in a .log file.
     */
    private static final Logger logger = LogManager.getLogger(SearchGameUtil.class);

    /**
     * List of all clients which are searching for a game.
     */
    private static final List<ClientUtil> clients = Collections.synchronizedList(new ArrayList<ClientUtil>());

    /**
     * Empty constructor.
     */
    public SearchGameUtil() {
    }

    /**
     * Add new client to search for game - if the client already is in the
     * searching list remove the client.
     *
     * @param client The client to add.
     */
    public void addClientToSearchGame(final ClientUtil client) {
        if (!clients.contains(client)) {
            clients.add(client);
            logger.info("Added new client " + client.getUsername() + " to waiting list. Total of " + clients.size() + " users searching for a game");
            this.createNewGame();
        }

    }

    /**
     * Remove client from searching a game - either connection lost or game has
     * been found.
     *
     * @param client The client to remove.
     */
    public void removeClientFromSearchingGame(final ClientUtil client) {
        synchronized (clients) {
            Boolean removedSuccessfully = false;
            Iterator<ClientUtil> iterator = clients.iterator();
            while (iterator.hasNext()) {
                ClientUtil c = (ClientUtil) iterator.next();
                if (c.getToken().equals(client.getToken())) {
                    iterator.remove();
                    logger.info("Removed client " + client.getUsername() + " from waiting list");
                    break;
                }
            }
        }
    }

    /**
     * Check if new game can be started.
     */
    private void createNewGame() {
        if (clients.size() >= 4) {
            // Get players for game
            ClientUtil playerOne = this.clients.get(0);
            ClientUtil playerTwo = this.clients.get(1);
            ClientUtil playerThree = this.clients.get(2);
            ClientUtil playerFour = this.clients.get(3);

            // Assign and create Teams
            TeamEntity teamOne = new TeamEntity(playerOne.getUser(), playerThree.getUser());
            TeamRepository.getSingleton(null).add(teamOne);
            TeamEntity teamTwo = new TeamEntity(playerTwo.getUser(), playerFour.getUser());
            TeamRepository.getSingleton(null).add(teamTwo);

            // Initialize new Game
            GameEntity newGame = new GameEntity(teamOne, teamTwo, true);
            GameRepository.getSingleton(null).add(newGame);

            logger.info("Successfully created game with ID: " + newGame.getId());
            // TODO - Where do we add new Game? GameUtil?

            // Remove players from waiting list
            this.clients.subList(0, 4).clear();
            logger.info("Players searching for a game:  " + this.clients.size());

            // Broadcast to all Players
            broadcastGameFound(playerOne, newGame, playerOne.getUser(), playerTwo.getUser(), playerThree.getUser(), playerFour.getUser(), teamOne, teamTwo);
            broadcastGameFound(playerTwo, newGame, playerOne.getUser(), playerTwo.getUser(), playerThree.getUser(), playerFour.getUser(), teamOne, teamTwo);
            broadcastGameFound(playerThree, newGame, playerOne.getUser(), playerTwo.getUser(), playerThree.getUser(), playerFour.getUser(), teamOne, teamTwo);
            broadcastGameFound(playerFour, newGame, playerOne.getUser(), playerTwo.getUser(), playerThree.getUser(), playerFour.getUser(), teamOne, teamTwo);

            // If there are still enough players searching for a game create new game
            if (clients.size() >= 4) {
                this.createNewGame();
            }

        }
    }

    /**
     * @param client  The client to send the message to.
     * @param game    The game.
     * @param p1      Player one.
     * @param p2      Player two.
     * @param p3      Player three.
     * @param p4      Player four.
     * @param teamOne Team one.
     * @param teamTwo Team two.
     */
    private void broadcastGameFound(final ClientUtil client, final GameEntity game, final UserEntity p1, final UserEntity p2, final UserEntity p3, final UserEntity p4, final TeamEntity teamOne, final TeamEntity teamTwo) {
        int playerOneTeamId = getTeamIdForPlayer(p1, teamOne, teamTwo);
        int playerTwoTeamId = getTeamIdForPlayer(p2, teamOne, teamTwo);
        int playerThreeTeamId = getTeamIdForPlayer(p3, teamOne, teamTwo);
        int playerFourTeamId = getTeamIdForPlayer(p4, teamOne, teamTwo);

        GameFound gameFoundMsg = new GameFound(new GameFoundData(game.getId(), p1.getId(), p1.getUsername(), playerOneTeamId, p2.getId(), p2.getUsername(), playerTwoTeamId, p3.getId(), p3.getUsername(), playerThreeTeamId, p4.getId(), p4.getUsername(), playerFourTeamId));
        client.send(gameFoundMsg);
    }

    /**
     * @param user    The user.
     * @param teamOne The first team.
     * @param teamTwo The second team.
     *
     * @return Returns the ID of the team the user belongs to.
     */
    private int getTeamIdForPlayer(final UserEntity user, final TeamEntity teamOne, final TeamEntity teamTwo) {
        boolean userIsInTeamOne = teamOne.checkIfPlayerIsInTeam(user);
        if (userIsInTeamOne == true) {
            return teamOne.getId();
        } else {
            return teamTwo.getId();
        }
    }

    @Override
    public String getServiceName() {
        return SERVICE_NAME;
    }
}
