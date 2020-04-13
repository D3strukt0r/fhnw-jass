package jass.server.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.ArrayList;
import java.util.Collections;
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

    // Add new client to waiting list for games
    public void addClientToSearchGame(ClientUtil client) {
        clients.add(client);
        logger.info("Added new client to waiting list");
        this.checkForNewGame();
    }

    // Check if new game can be started
    private void checkForNewGame() {
        System.out.println(clients.size());
        if(clients.size() >= 4) {
            // Create new game
        }
    }

    @Override
    public String getServiceName() {
        return "SearchGameUtil";
    }
}
