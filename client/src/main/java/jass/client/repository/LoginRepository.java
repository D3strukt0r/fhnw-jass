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

import com.j256.ormlite.dao.Dao;
import jass.client.entity.LoginEntity;
import jass.lib.database.Repository;

/**
 * Helper functions concerning the LoginEntity class.
 *
 * @author Manuele Vaccari
 * @version %I%, %G%
 * @since 0.0.1
 */
public final class LoginRepository extends Repository<Dao<LoginEntity, String>, LoginEntity> {
    /**
     * The singleton.
     */
    private static LoginRepository singleton = null;

    /**
     * Creates a new singleton or returns the existing one.
     *
     * @param dao The DAO to edit inside the database.
     *
     * @return Returns the Repository.
     */
    public static LoginRepository getSingleton(final Dao<LoginEntity, String> dao) {
        if (singleton == null) {
            singleton = new LoginRepository(dao);
        }
        return singleton;
    }

    /**
     * @param dao The DAO to edit inside the database.
     */
    public LoginRepository(final Dao<LoginEntity, String> dao) {
        super(dao);
    }

    /**
     * Sets the given login to connect automatically and disables all other
     * logins.
     *
     * @param login A LoginEntity object
     *
     * @return "true" if everything went alright, "false" if something failed.
     */
    public boolean setToConnectAutomatically(final LoginEntity login) {
        boolean result;
        for (LoginEntity l : getDao()) {
            // Disable the other entry (should be only one)
            if (l.isConnectAutomatically()) {
                l.setConnectAutomatically(false);
                result = update(l);

                if (!result) {
                    return false;
                }
            }
            // Set the new entry to connect automatically (if it is already in the db)
            if (l.getId() == login.getId()) {
                login.setConnectAutomatically(true);
                result = update(login);

                if (!result) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * @return Returns the login which is set as connect automatically.
     */
    public LoginEntity findConnectAutomatically() {
        for (LoginEntity l : getDao()) {
            if (l.isConnectAutomatically()) {
                return l;
            }
        }
        return null;
    }
}
