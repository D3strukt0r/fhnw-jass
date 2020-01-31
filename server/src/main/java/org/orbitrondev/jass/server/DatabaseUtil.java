package org.orbitrondev.jass.server;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import org.orbitrondev.jass.lib.ServiceLocator.Service;
import org.orbitrondev.jass.server.Entity.*;

import java.io.Closeable;
import java.io.IOException;
import java.sql.SQLException;

/**
 * A class to create a database connection.
 *
 * @author Manuele Vaccari
 * @version %I%, %G%
 * @since 0.0.1
 */
public class DatabaseUtil implements Service, Closeable {
    private ConnectionSource connectionSource;

    private Dao<User, String> userDao;

    /**
     * Create a database connection
     *
     * @param databaseLocation A string containing the location of the file to be accessed (and if necessary created)
     *
     * @since 0.0.1
     */
    public DatabaseUtil(String databaseLocation) throws SQLException {
        // this uses h2 but you can change it to match your database
        String databaseUrl = "jdbc:sqlite:" + databaseLocation;

        // create our data-source for the database
        connectionSource = new JdbcConnectionSource(databaseUrl);

        // setup our database and DAOs
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
        userDao = DaoManager.createDao(connectionSource, User.class);

        /*
         * Create the tables, if they don't exist yet.
         */
        TableUtils.createTableIfNotExists(connectionSource, User.class);
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
    public Dao<User, String> getUserDao() {
        return userDao;
    }

    @Override
    public String getName() {
        return "db";
    }
}
