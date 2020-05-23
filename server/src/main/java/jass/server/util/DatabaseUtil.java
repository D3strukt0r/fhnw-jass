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

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import jass.lib.servicelocator.Service;
import jass.server.database.MysqlDatabaseType;
import jass.server.entity.CardEntity;
import jass.server.entity.DeckEntity;
import jass.server.entity.GameEntity;
import jass.server.entity.RankEntity;
import jass.server.entity.RoundEntity;
import jass.server.entity.SuitEntity;
import jass.server.entity.TeamEntity;
import jass.server.entity.TurnEntity;
import jass.server.entity.UserEntity;
import jass.server.repository.CardRepository;
import jass.server.repository.DeckRepository;
import jass.server.repository.GameRepository;
import jass.server.repository.RankRepository;
import jass.server.repository.RoundRepository;
import jass.server.repository.SuitRepository;
import jass.server.repository.TeamRepository;
import jass.server.repository.TurnRepository;
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
 * @since 1.0.0
 */
public final class DatabaseUtil implements Service, Closeable {
    /**
     * Default location to store the data.
     */
    public static final String DEFAULT_LOCATION = "sqlite:jass_server.sqlite3";

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
     * The DAO for the suits.
     */
    private Dao<SuitEntity, Integer> suitDao;

    /**
     * The DAO for the ranks.
     */
    private Dao<RankEntity, Integer> rankDao;

    /**
     * The DAO for the decks.
     */
    private Dao<DeckEntity, Integer> deckDao;

    /**
     * The DAO for the rounds.
     */
    private Dao<RoundEntity, Integer> roundDao;

    /**
     * The DAO for the cards.
     */
    private Dao<CardEntity, Integer> cardDao;

    /**
     * The DAO for the turns.
     */
    private Dao<TurnEntity, Integer> turnDao;

    /**
     * Create a database connection.
     * <p>
     * Example: sqlite:jass_server.sqlite3
     * </p>
     *
     * @param databaseUrl A string containing the location of the file to be
     *                    accessed (and if necessary created).
     *
     * @throws SQLException If an SQL error occurs.
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    public DatabaseUtil(final String databaseUrl) throws SQLException {
        // Create our data-source for the database
        String jdbcDatabaseUrl = "jdbc:" + databaseUrl;
        String databaseType = extractDbType(jdbcDatabaseUrl);
        if (databaseType.equals("sqlite")) {
            connectionSource = new JdbcConnectionSource(jdbcDatabaseUrl);
        } else {
            throw new IllegalArgumentException(databaseType + " is unsupported for the database.");
        }

        // Setup our database and DAOs
        setupDatabase();
    }

    /**
     * Create a database connection.
     * <p>
     * Example: jdbc:mysql://localhost:3306/jass?serverTimezone=UTC
     * </p>
     *
     * @param databaseUrl A string containing the location of the file to be
     *                    accessed (and if necessary created).
     * @param username    The username of the database.
     * @param password    The password of the database.
     *
     * @throws SQLException If an SQL error occurs.
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    public DatabaseUtil(final String databaseUrl, final String username, final String password) throws SQLException {
        // Create our data-source for the database
        String jdbcDatabaseUrl = "jdbc:" + databaseUrl;
        String databaseType = extractDbType(jdbcDatabaseUrl);
        if (databaseType.equals("mysql")) {
            connectionSource = new JdbcConnectionSource(jdbcDatabaseUrl, username, password, new MysqlDatabaseType());
        } else if (databaseType.equals("mariadb")) {
            connectionSource = new JdbcConnectionSource(jdbcDatabaseUrl, username, password);
        } else {
            throw new IllegalArgumentException(databaseType + " is unsupported for the database.");
        }

        // Setup our database and DAOs
        setupDatabase();
    }

    /**
     * @param databaseUrl The full database url.
     *
     * @return Returns the type.
     *
     * @author ORMLite library
     * @since 1.0.0
     */
    public static String extractDbType(final String databaseUrl) {
        if (!databaseUrl.startsWith("jdbc:")) {
            throw new IllegalArgumentException("Database URL was expected to start with jdbc: but was " + databaseUrl);
        }
        String[] urlParts = databaseUrl.split(":");
        if (urlParts.length < 2) {
            throw new IllegalArgumentException(
                "Database URL was expected to be in the form: jdbc:db-type:... but was " + databaseUrl);
        }
        return urlParts[1];
    }

    /**
     * Setup our database and DAOs, for the created connection.
     *
     * @throws SQLException If an SQL error occurs.
     * @author Manuele Vaccari & Thomas Weber & Victor Hargrave
     * @since 1.0.0
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
        suitDao = DaoManager.createDao(connectionSource, SuitEntity.class);
        SuitRepository.getSingleton(suitDao);
        rankDao = DaoManager.createDao(connectionSource, RankEntity.class);
        RankRepository.getSingleton(rankDao);
        cardDao = DaoManager.createDao(connectionSource, CardEntity.class);
        CardRepository.getSingleton(cardDao);
        roundDao = DaoManager.createDao(connectionSource, RoundEntity.class);
        RoundRepository.getSingleton(roundDao);
        deckDao = DaoManager.createDao(connectionSource, DeckEntity.class);
        DeckRepository.getSingleton(deckDao);
        turnDao = DaoManager.createDao(connectionSource, TurnEntity.class);
        TurnRepository.getSingleton(turnDao);

        /*
         * Drop and Recreate all the tables except for the user if server
         * restarts.
         */
        TableUtils.createTableIfNotExists(connectionSource, RankEntity.class);
        RankRepository.getSingleton(null).insertSeedData();
        TableUtils.createTableIfNotExists(connectionSource, SuitEntity.class);
        SuitRepository.getSingleton(null).insertSeedData();
        TableUtils.createTableIfNotExists(connectionSource, CardEntity.class);
        CardRepository.getSingleton(null).insertSeedData();

        TableUtils.createTableIfNotExists(connectionSource, UserEntity.class);
        TableUtils.createTableIfNotExists(connectionSource, GameEntity.class);
        TableUtils.createTableIfNotExists(connectionSource, TeamEntity.class);
        TableUtils.createTableIfNotExists(connectionSource, RoundEntity.class);
        TableUtils.createTableIfNotExists(connectionSource, DeckEntity.class);
        TableUtils.createTableIfNotExists(connectionSource, TurnEntity.class);
    }

    /**
     * Close the database connection.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
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
