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

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.field.DataPersisterManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import jass.lib.servicelocator.Service;
import jass.server.entity.DeckEntity;
import jass.server.entity.GameEntity;
import jass.server.entity.RoundEntity;
import jass.server.entity.TeamEntity;
import jass.server.entity.UserEntity;
import jass.server.persister.CardPersister;
import jass.server.repository.DeckRepository;
import jass.server.repository.GameRepository;
import jass.server.repository.RoundRepository;
import jass.server.repository.TeamRepository;
import jass.server.repository.UserRepository;

import java.io.Closeable;
import java.io.IOException;
import java.sql.SQLException;

/**
 * A class to create a database connection.
 * <p>
 * Also contains a lot of code from following example code:
 * https://github.com/j256/ormlite-jdbc/blob/master/src/test/java/com/j256/ormlite/examples/manytomany/ManyToManyMain.java
 *
 * @author Manuele Vaccari & Victor Hargrave
 * @version %I%, %G%
 * @since 0.0.1
 */
public final class DatabaseUtil implements Service, Closeable {
    public enum SupportedDatabase {
        /**
         * A list of all the supported database systems.
         */
        SQLITE
    }

    /**
     * Default location to store the data.
     */
    public static final String DEFAULT_LOCATION = "data/jass_server.sqlite3";

    /**
     * The database connection.
     */
    private final ConnectionSource connectionSource;

    /**
     * The DAO for the users.
     */
    private Dao<UserEntity, Integer> userDao;

    /**
     * The DAO for the teams.
     */
    private Dao<TeamEntity, Integer> teamDao;

    /**
     * The DAO for the games.
     */
    private Dao<GameEntity, Integer> gameDao;

    /**
     * The DAO for the decks.
     */
    private Dao<DeckEntity, Integer> deckDao;

    /**
     * The DAO for the rounds.
     */
    private Dao<RoundEntity, Integer> roundDao;

    /**
     * Create a database connection.
     *
     * @param databaseLocation A string containing the location of the file to
     *                         be accessed (and if necessary created).
     *
     * @throws SQLException If an SQL error occurs.
     * @since 0.0.1
     */
    public DatabaseUtil(final SupportedDatabase type, final String databaseLocation) throws SQLException {
        // Create our data-source for the database
        if (type == SupportedDatabase.SQLITE) {
            connectionSource = new JdbcConnectionSource("jdbc:sqlite:" + databaseLocation);
        } else {
            throw new SQLException();
        }

        // Add custom persistors
        DataPersisterManager.registerDataPersisters(CardPersister.getSingleton());

        // Setup our database and DAOs
        setupDatabase();
    }

    /**
     * Setup our database and DAOs, for the created connection.
     *
     * @throws SQLException If an SQL error occurs.
     * @since 0.0.1
     */
    private void setupDatabase() throws SQLException {
        /*
         * Create our DAOs. One for each class and associated table.
         */
        userDao = DaoManager.createDao(connectionSource, UserEntity.class);
        UserRepository.getSingleton(userDao);
        teamDao = DaoManager.createDao(connectionSource, TeamEntity.class);
        TeamRepository.getSingleton(teamDao);
        gameDao = DaoManager.createDao(connectionSource, GameEntity.class);
        GameRepository.getSingleton(gameDao);
        roundDao = DaoManager.createDao(connectionSource, RoundEntity.class);
        RoundRepository.getSingleton(roundDao);
        deckDao = DaoManager.createDao(connectionSource, DeckEntity.class);
        DeckRepository.getSingleton(deckDao);

        /*
         * Drop and Recreate all the tables except for the user if server
         * restarts.
         */
        TableUtils.createTableIfNotExists(connectionSource, UserEntity.class);
        TableUtils.createTableIfNotExists(connectionSource, GameEntity.class);
        TableUtils.createTableIfNotExists(connectionSource, TeamEntity.class);
        TableUtils.createTableIfNotExists(connectionSource, RoundEntity.class);
        TableUtils.createTableIfNotExists(connectionSource, DeckEntity.class);
    }

    /**
     * Close the database connection.
     *
     * @since 0.0.1
     */
    @Override
    public void close() {
        if (connectionSource != null) {
            try {
                connectionSource.close();
            } catch (IOException e) { /* Ignore */ }
        }
    }
}
