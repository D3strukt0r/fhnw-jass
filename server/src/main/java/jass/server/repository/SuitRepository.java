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
import jass.server.entity.SuitEntity;

import java.sql.SQLException;
import java.util.List;

/**
 * A model with all known suits.
 *
 * @author Victor Hargrave
 * @version %I%, %G%
 * @since 1.0.0
 */
public final class SuitRepository extends Repository<Dao<SuitEntity, Integer>, SuitEntity> {
    /**
     * The singleton.
     */
    private static SuitRepository singleton = null;

    /**
     * Creates a new singleton or returns the existing one.
     *
     * @param dao The DAO to edit inside the database.
     *
     * @return Returns the Repository.
     *
     * @author Victor Hargrave
     * @since 1.0.0
     */
    public static SuitRepository getSingleton(final Dao<SuitEntity, Integer> dao) {
        if (singleton == null) {
            singleton = new SuitRepository(dao);
        }
        return singleton;
    }

    /**
     * @param dao The DAO to edit inside the database.
     *
     * @author Victor Hargrave
     * @since 1.0.0
     */
    public SuitRepository(final Dao<SuitEntity, Integer> dao) {
        super(dao);
    }

    /**
     * @return Returns true if successful otherwise false.
     *
     * @author Victor Hargrave
     * @since 1.0.0
     */
    public boolean insertSeedData() {
        try {
            if (!getDao().idExists(1)) {
                getDao().create((new SuitEntity()).setId(1).setKey("hearts"));
            }
            if (!getDao().idExists(2)) {
                getDao().create((new SuitEntity()).setId(2).setKey("diamonds"));
            }
            if (!getDao().idExists(3)) {
                getDao().create((new SuitEntity()).setId(3).setKey("spades"));
            }
            if (!getDao().idExists(4)) {
                getDao().create((new SuitEntity()).setId(4).setKey("clubs"));
            }
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * @param name The name of the suit.
     *
     * @return Returns the SuitEntity of the suit, or null if not found or
     * something went wrong.
     *
     * @author Victor Hargrave
     * @since 1.0.0
     */
    public SuitEntity getByName(final String name) {
        try {
            // Find all users with the given username (only one)
            List<SuitEntity> results = getDao().queryBuilder().where().eq("key", name).query();
            if (results.size() != 0) {
                return results.get(0);
            } else {
                return null;
            }
        } catch (SQLException e) {
            return null;
        }
    }
}
