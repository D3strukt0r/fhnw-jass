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

package org.orbitrondev.jass.server.Entity;

import org.orbitrondev.jass.lib.ServiceLocator.ServiceLocator;
import org.orbitrondev.jass.server.Utils.DatabaseUtil;

import java.sql.SQLException;
import java.util.List;

public class UserRepository {
    public static boolean create(UserEntity user) {
        DatabaseUtil db = (DatabaseUtil) ServiceLocator.get("db");
        if (db == null) {
            return false;
        }
        try {
            db.getUserDao().create(user);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public static boolean remove(UserEntity user) {
        DatabaseUtil db = (DatabaseUtil) ServiceLocator.get("db");
        if (db == null) {
            return false;
        }

        try {
            db.getUserDao().delete(user);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public static UserEntity getByUsername(String username) {
        DatabaseUtil db = (DatabaseUtil) ServiceLocator.get("db");
        if (db == null) {
            return null;
        }

        try {
            List<UserEntity> results = db.getUserDao().queryBuilder().where().eq("username", username).query();
            if(results.size() != 0) {
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
        if (db == null) {
            return false;
        }

        try {
            List<UserEntity> results = db.getUserDao().queryBuilder().where().eq("username", username).query();
            return results.size() != 0;
        } catch (SQLException e) {
            return false;
        }
    }
}
