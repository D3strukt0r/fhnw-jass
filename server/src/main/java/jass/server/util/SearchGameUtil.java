package jass.server.util;

import jass.server.entity.GameEntity;
import jass.server.entity.TeamEntity;
import jass.server.entity.UserEntity;
import jass.server.repository.GameRepository;
import jass.server.repository.TeamRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.font.TextAttribute;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import jass.lib.servicelocator.Service;

/**
 * Creates a game once 4 clients are searching for a game in the lobby
 *
 * @author Thomas Weber
 * @version %I%, %G%
 * @since 0.0.1
 */

public class SearchGameUtil implements Service {
    private static final Logger logger = LogManager.getLogger(SearchGameUtil.class);

    private static final List<ClientUtil> clients = Collections.synchronizedList(new ArrayList<ClientUtil>());

    public SearchGameUtil() {}

    // Add new client to search for game
    public void addClientToSearchGame(ClientUtil client) {
        clients.add(client);
        logger.info("Added new client " + client.getUsername() +  " to waiting list. Total of " + clients.size() + " users searching for a game");
        this.createNewGame();
    }

    // Remove client from searching a game - either connection lost or game has been found
    public void removeClientFromSearchingGame(ClientUtil client) {
        synchronized (clients) {
            Boolean removedSuccessfully = false;
            Iterator<ClientUtil> iterator = clients.iterator();
            while (iterator.hasNext()) {
                ClientUtil c = (ClientUtil) iterator.next();
                if (c.getToken().equals(client.getToken())) {
                    iterator.remove();
                    logger.info("Removed client " + client.getUsername() +  " from waiting list");
                    break;
                }
            }
        }
    }

    // Check if new game can be started
    private void createNewGame() {
        if(clients.size() >= 4) {
            // Get players for game
            UserEntity playerOne = this.clients.get(0).getUser();
            UserEntity playerTwo = this.clients.get(1).getUser();
            UserEntity playerThree = this.clients.get(2).getUser();
            UserEntity playerFour = this.clients.get(3).getUser();

            // Assign and create Teams
            TeamEntity teamOne = new TeamEntity(playerOne, playerThree);
            TeamRepository.getSingleton(null).add(teamOne);
            TeamEntity teamTwo = new TeamEntity(playerTwo, playerFour);
            TeamRepository.getSingleton(null).add(teamTwo);

            // Initialize new Game
            GameEntity newGame = new GameEntity(teamOne, teamTwo);
            GameRepository.getSingleton(null).add(newGame);

            logger.info("Successfully created game with ID: " + newGame.getId());
            // TODO - Where do we add new Game? GameUtil?

            // Remove players from waiting list
            this.clients.subList(0, 4).clear();
            logger.info("Players searching for a game:  " + this.clients.size());

            if (clients.size() >= 4) {
                this.createNewGame();
            }

        }
    }



    @Override
    public String getServiceName() {
        return "SearchGameUtil";
    }
}
