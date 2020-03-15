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

package jass.server.entity;

import jass.lib.servicelocator.ServiceLocator;
import jass.server.utils.DatabaseUtil;

import java.sql.SQLException;
import java.util.List;

public class UserRepository {
    public static boolean create(UserEntity user) {
        DatabaseUtil db = (DatabaseUtil) ServiceLocator.get("db");
        // Check that database is available, otherwise fail.
        if (db == null) {
            return false;
        }

        try {
            // Add the user to the db
            db.getUserDao().create(user);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public static boolean remove(UserEntity user) {
        DatabaseUtil db = (DatabaseUtil) ServiceLocator.get("db");
        // Check that database is available, otherwise fail.
        if (db == null) {
            return false;
        }

        try {
            // Remove the user from the db
            db.getUserDao().delete(user);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public static boolean update(UserEntity user) {
        DatabaseUtil db = (DatabaseUtil) ServiceLocator.get("db");
        // Check that database is available, otherwise fail.
        if (db == null) {
            return false;
        }

        try {
            // Update the user object in the db
            db.getUserDao().update(user);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public static UserEntity getByUsername(String username) {
        DatabaseUtil db = (DatabaseUtil) ServiceLocator.get("db");
        // Check that database is available, otherwise fail.
        if (db == null) {
            return null;
        }

        try {
            // Find all users with the given username (only one)
            List<UserEntity> results = db.getUserDao().queryBuilder().where().eq("username", username).query();
            if (results.size() != 0) {
                return results.get(0);
            } else {
                return null;
            }
        } catch (SQLException e) {
            return null;
        }
    }

    public static boolean usernameExists(String username) {
        DatabaseUtil db = (DatabaseUtil) ServiceLocator.get("db");
        // Check that database is available, otherwise fail.
        if (db == null) {
            return false;
        }

        try {
            // Check if there is somebody in the db already using the given username.
            List<UserEntity> results = db.getUserDao().queryBuilder().where().eq("username", username).query();
            return results.size() != 0;
        } catch (SQLException e) {
            return false;
        }
    }
}
