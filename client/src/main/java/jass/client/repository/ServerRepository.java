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

package jass.client.repository;

import jass.client.entity.ServerEntity;
import jass.client.util.DatabaseUtil;
import jass.lib.servicelocator.ServiceLocator;

import java.sql.SQLException;

/**
 * Helper functions concerning the ServerEntity class.
 *
 * @author Manuele Vaccari
 * @version %I%, %G%
 * @since 0.0.1
 */
public class ServerRepository {
    /**
     * Sets the given server to connect automatically and disables all other connections.
     *
     * @param server A ServerEntity object
     *
     * @return "true" if everything went alright, "false" if something failed.
     */
    public static boolean setToConnectAutomatically(ServerEntity server) {
        DatabaseUtil db = (DatabaseUtil) ServiceLocator.get("db");
        if (db == null) {
            return false;
        }
        try {
            for (ServerEntity s : db.getServerDao()) {
                // Disable the other entry (should be only one)
                if (s.isConnectAutomatically()) {
                    s.setConnectAutomatically(false);
                    db.getServerDao().update(s);
                }
                // Set the new entry to connect automatically (if it is already in the db)
                if (s.getId() == server.getId()) {
                    server.setConnectAutomatically(true);
                    db.getServerDao().update(server);
                }
            }
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public static ServerEntity findConnectAutomatically() {
        DatabaseUtil db = (DatabaseUtil) ServiceLocator.get("db");
        if (db == null) {
            return null;
        }
        for (ServerEntity s : db.getServerDao()) {
            if (s.isConnectAutomatically()) {
                return s;
            }
        }
        return null;
    }
}
