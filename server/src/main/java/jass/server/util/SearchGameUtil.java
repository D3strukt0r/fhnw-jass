package jass.server.util;

import jass.server.entity.GameEntity;
import jass.server.entity.TeamEntity;
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
        logger.info("Added new client " + client.getUsername() +  " to waiting list");
        this.checkForNewGame();
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
    private void checkForNewGame() {
        System.out.println(clients.size());
        if(clients.size() >= 4) {
            TeamEntity teamOne = new TeamEntity(this.clients.get(0).getUser(), clients.get(2).getUser());
            TeamEntity teamTwo = new TeamEntity(this.clients.get(1).getUser(), clients.get(3).getUser());
            GameEntity newGame = new GameEntity(teamOne, teamTwo);
            // TODO - Where do we add new Game? GameUtil?

            this.clients.subList(0, 3).clear();
            System.out.println(this.clients.size());

        }
    }



    @Override
    public String getServiceName() {
        return "SearchGameUtil";
    }
}
