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

package jass.client.util;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import jass.client.entity.LoginEntity;
import jass.client.entity.ServerEntity;
import jass.client.repository.LoginRepository;
import jass.client.repository.ServerRepository;
import jass.lib.servicelocator.Service;

import java.io.Closeable;
import java.io.IOException;
import java.sql.SQLException;

/**
 * A class to create a database connection.
 *
 * @author Manuele Vaccari
 * @version %I%, %G%
 * @since 1.0.0
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
    public static final String DEFAULT_LOCATION = "jass.sqlite3";

    /**
     * The database connection.
     */
    private final ConnectionSource connectionSource;

    /**
     * Create a database connection.
     *
     * @param type             The type of database to use.
     * @param databaseLocation A string containing the location of the file to
     *                         be accessed (and if necessary created).
     *
     * @throws SQLException If an SQL error occurs.
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    public DatabaseUtil(final SupportedDatabase type, final String databaseLocation) throws SQLException {
        // Create our data-source for the database
        if (type == SupportedDatabase.SQLITE) {
            connectionSource = new JdbcConnectionSource("jdbc:sqlite:" + databaseLocation);
        } else {
            throw new SQLException();
        }

        // setup our database and DAOs
        setupDatabase();
    }

    /**
     * Setup our database and DAOs, for the created connection.
     *
     * @throws SQLException If an SQL error occurs.
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    private void setupDatabase() throws SQLException {
        /*
         * Create our DAOs. One for each class and associated table.
         */
        Dao<LoginEntity, Integer> loginDao = DaoManager.createDao(connectionSource, LoginEntity.class);
        LoginRepository.getSingleton(loginDao);
        Dao<ServerEntity, Integer> serverDao = DaoManager.createDao(connectionSource, ServerEntity.class);
        ServerRepository.getSingleton(serverDao);

        /*
         * Create the tables, if they don't exist yet.
         */
        TableUtils.createTableIfNotExists(connectionSource, LoginEntity.class);
        TableUtils.createTableIfNotExists(connectionSource, ServerEntity.class);
        ServerRepository.getSingleton(null).insertSeedData();
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
            } catch (IOException e) {
                // we don't care
            }
        }
    }
}
