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

package jass.server.util;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import jass.lib.servicelocator.Service;
import jass.server.entity.UserEntity;

import java.io.Closeable;
import java.io.IOException;
import java.sql.SQLException;

/**
 * A class to create a database connection.
 *
 * Also contains a lot of code from following example code:
 * https://github.com/j256/ormlite-jdbc/blob/master/src/test/java/com/j256/ormlite/examples/manytomany/ManyToManyMain.java
 *
 * @author Manuele Vaccari
 * @version %I%, %G%
 * @since 0.0.1
 */
public class DatabaseUtil implements Service, Closeable {
    private ConnectionSource connectionSource;

    private Dao<UserEntity, String> userDao;

    /**
     * Create a database connection
     *
     * @param databaseLocation A string containing the location of the file to be accessed (and if necessary created)
     *
     * @since 0.0.1
     */
    public DatabaseUtil(String databaseLocation) throws SQLException {
        // This uses h2 but you can change it to match your database
        String databaseUrl = "jdbc:sqlite:" + databaseLocation;

        // Create our data-source for the database
        connectionSource = new JdbcConnectionSource(databaseUrl);

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

        /*
         * Create the tables, if they don't exist yet.
         */
        TableUtils.createTableIfNotExists(connectionSource, UserEntity.class);
    }

    /**
     * Close the database connection.
     *
     * @since 0.0.1
     */
    @Override
    public void close() {
        if (connectionSource != null) try {
            connectionSource.close();
        } catch (IOException e) { /* Ignore */ }
    }

    /**
     * @return DAO object for the users
     *
     * @since 0.0.1
     */
    public Dao<UserEntity, String> getUserDao() {
        return userDao;
    }

    // For the ServiceLocator
    @Override
    public String getServiceName() {
        return "db";
    }
}
