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

package jass.client.repository;

import com.j256.ormlite.dao.Dao;
import jass.client.entity.ServerEntity;
import jass.lib.database.Repository;

/**
 * Helper functions concerning the ServerEntity class.
 *
 * @author Manuele Vaccari
 * @version %I%, %G%
 * @since 0.0.1
 */
public final class ServerRepository extends Repository<Dao<ServerEntity, Integer>, ServerEntity> {
    /**
     * The singleton.
     */
    private static ServerRepository singleton = null;

    /**
     * Creates a new singleton or returns the existing one.
     *
     * @param dao The DAO to edit inside the database.
     *
     * @return Returns the Repository.
     */
    public static ServerRepository getSingleton(final Dao<ServerEntity, Integer> dao) {
        if (singleton == null) {
            singleton = new ServerRepository(dao);
        }
        return singleton;
    }

    /**
     * @param dao The DAO to edit inside the database.
     */
    public ServerRepository(final Dao<ServerEntity, Integer> dao) {
        super(dao);
    }

    /**
     * Sets the given server to connect automatically and disables all other
     * connections.
     *
     * @param server A ServerEntity object
     *
     * @return "true" if everything went alright, "false" if something failed.
     */
    public boolean setToConnectAutomatically(final ServerEntity server) {
        boolean result;
        for (ServerEntity s : getDao()) {
            // Disable the other entry (should be only one)
            if (s.isConnectAutomatically()) {
                s.setConnectAutomatically(false);
                result = update(s);

                if (!result) {
                    return false;
                }
            }
            // Set the new entry to connect automatically (if it is already in the db)
            if (s.getId() == server.getId()) {
                server.setConnectAutomatically(true);
                result = update(server);

                if (!result) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * @return Returns the server which is set as connect automatically.
     */
    public ServerEntity findConnectAutomatically() {
        for (ServerEntity s : getDao()) {
            if (s.isConnectAutomatically()) {
                return s;
            }
        }
        return null;
    }
}
