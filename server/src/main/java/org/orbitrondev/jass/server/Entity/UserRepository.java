package org.orbitrondev.jass.server.Entity;

import org.orbitrondev.jass.lib.ServiceLocator.ServiceLocator;
import org.orbitrondev.jass.server.DatabaseUtil;

import java.sql.SQLException;
import java.util.List;

public class UserRepository {
    public static boolean create(User user) {
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

    public static boolean remove(User user) {
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

    public static User getByUsername(String username) {
        DatabaseUtil db = (DatabaseUtil) ServiceLocator.get("db");
        if (db == null) {
            return null;
        }

        try {
            List<User> results = db.getUserDao().queryBuilder().where().eq("username", username).query();
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
            List<User> results = db.getUserDao().queryBuilder().where().eq("username", username).query();
            return results.size() != 0;
        } catch (SQLException e) {
            return false;
        }
    }
}
