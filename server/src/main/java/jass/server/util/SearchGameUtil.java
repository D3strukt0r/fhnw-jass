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

package jass.server.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Closeable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import jass.lib.servicelocator.Service;

/**
 * Creates a game once 4 clients are searching for a game in the lobby.
 *
 * @author Thomas Weber, Manuele Vaccari
 * @version %I%, %G%
 * @since 0.0.1
 */
public final class SearchGameUtil extends Thread implements Service, Closeable {
    /**
     * How many players are required to be inside a game (min and max).
     */
    private static final int PLAYERS_PER_GAME = 4;

    /**
     * The logger to print to console and save in a .log file.
     */
    private static final Logger logger = LogManager.getLogger(SearchGameUtil.class);

    /**
     * List of all clients which are searching for a game.
     */
    private static final List<ClientUtil> searchingClients = Collections.synchronizedList(new ArrayList<>());

    /**
     * A list of all currently running games.
     */
    private static final List<GameUtil> runningGames = Collections.synchronizedList(new ArrayList<>());

    /**
     * Run the thread while true.
     */
    private boolean running = true;

    /**
     * Empty constructor.
     */
    public SearchGameUtil() {
        // TODO Restore running games after server maybe crashed/stopped
    }

    @Override
    public void run() {
        while (running) {
            if (searchingClients.size() >= PLAYERS_PER_GAME) {
                // Search for 4 players to add.
                ArrayList<ClientUtil> playersToPlay = new ArrayList<>();
                while (playersToPlay.size() < PLAYERS_PER_GAME) {
                    Random random = new Random();
                    // Pick a random users, from the ones searching.
                    ClientUtil player = searchingClients.get(random.nextInt(searchingClients.size()));
                    // If it wasn't already picked, add it now.
                    if (!playersToPlay.contains(player)) {
                        playersToPlay.add(player);
                    }
                }

                // Create the game.
                GameUtil newGame = new GameUtil(playersToPlay.get(0), playersToPlay.get(1), playersToPlay.get(2), playersToPlay.get(3));
                runningGames.add(newGame);

                // Remove the players from searching.
                for (ClientUtil player : playersToPlay) {
                    searchingClients.remove(player);
                }

                logger.info("Total of " + searchingClients.size() + " users searching for a game");
            } else {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) { /* Ignore */ }
            }
        }
    }

    @Override
    public void close() {
        running = false;
    }

    /**
     * Add new client to search for game - if the client already is in the
     * searching list remove the client.
     *
     * @param client The client to add.
     */
    public synchronized void addClientToSearchGame(final ClientUtil client) {
        if (!searchingClients.contains(client)) {
            searchingClients.add(client);
            logger.info("Added new client " + client.getUsername() + " to waiting list. Total of " + searchingClients.size() + " users searching for a game");
        }
    }

    /**
     * Remove client from searching a game - either connection lost or game has
     * been found.
     *
     * @param client The client to remove.
     */
    public synchronized void removeClientFromSearchingGame(final ClientUtil client) {
        Iterator<ClientUtil> iterator = searchingClients.iterator();
        while (iterator.hasNext()) {
            ClientUtil c = iterator.next();
            if (c.getToken().equals(client.getToken())) {
                iterator.remove();
                logger.info("Removed client " + client.getUsername() + " from waiting list");
                break;
            }
        }
    }
}
