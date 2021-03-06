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

package jass.server.repository;

import com.j256.ormlite.dao.Dao;
import jass.lib.database.Repository;
import jass.server.entity.UserEntity;

import java.sql.SQLException;
import java.util.List;

/**
 * A model with all users.
 *
 * @author Manuele Vaccari
 * @version %I%, %G%
 * @since 1.0.0
 */
public final class UserRepository extends Repository<Dao<UserEntity, Integer>, UserEntity> {
    /**
     * The singleton.
     */
    private static UserRepository singleton = null;

    /**
     * Creates a new singleton or returns the existing one.
     *
     * @param dao The DAO to edit inside the database.
     *
     * @return Returns the Repository.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    public static UserRepository getSingleton(final Dao<UserEntity, Integer> dao) {
        if (singleton == null) {
            singleton = new UserRepository(dao);
        }
        return singleton;
    }

    /**
     * @param dao The DAO to edit inside the database.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    public UserRepository(final Dao<UserEntity, Integer> dao) {
        super(dao);
    }

    /**
     * @param username The username of the user.
     *
     * @return Returns the UserEntity of the user, or null if not found or
     * something went wrong.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    public UserEntity getByUsername(final String username) {
        try {
            // Find all users with the given username (only one)
            List<UserEntity> results = getDao().queryBuilder().where().eq("username", username).query();
            if (results.size() != 0) {
                return results.get(0);
            } else {
                return null;
            }
        } catch (SQLException e) {
            return null;
        }
    }

    /**
     * @param username The username of the user.
     *
     * @return Returns true if the user exists, otherwise false.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    public boolean usernameExists(final String username) {
        try {
            // Check if there is somebody in the db already using the given username.
            List<UserEntity> results = getDao().queryBuilder().where().eq("username", username).query();
            return results.size() != 0;
        } catch (SQLException e) {
            return false;
        }
    }
}
