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

package org.orbitrondev.jass.client.Entity;

import org.orbitrondev.jass.client.Utils.DatabaseUtil;
import org.orbitrondev.jass.lib.ServiceLocator.ServiceLocator;

import java.sql.SQLException;

/**
 * Helper functions concerning the LoginEntity class.
 *
 * @author Manuele Vaccari
 * @version %I%, %G%
 * @since 0.0.1
 */
public class LoginRepository {
    /**
     * Sets the given login to connect automatically and disables all other logins.
     *
     * @param login A LoginEntity object
     *
     * @return "true" if everything went alright, "false" if something failed.
     */
    public static boolean setToConnectAutomatically(LoginEntity login) {
        DatabaseUtil db = (DatabaseUtil) ServiceLocator.get("db");
        if (db == null) {
            return false;
        }
        try {
            for (LoginEntity l : db.getLoginDao()) {
                // Disable the other entry (should be only one)
                if (l.isConnectAutomatically()) {
                    l.setConnectAutomatically(false);
                    db.getLoginDao().update(l);
                }
                // Set the new entry to connect automatically (if it is already in the db)
                if (l.getId() == login.getId()) {
                    login.setConnectAutomatically(true);
                    db.getLoginDao().update(login);
                }
            }
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}
