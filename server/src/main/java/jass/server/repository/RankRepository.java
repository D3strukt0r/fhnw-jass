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
import jass.server.entity.RankEntity;

import java.sql.SQLException;
import java.util.List;

/**
 * A model with all known ranks.
 *
 * @author Victor Hargrave
 * @version %I%, %G%
 * @since 1.0.0
 */
public final class RankRepository extends Repository<Dao<RankEntity, Integer>, RankEntity> {
    /**
     * The singleton.
     */
    private static RankRepository singleton = null;

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
    public static RankRepository getSingleton(final Dao<RankEntity, Integer> dao) {
        if (singleton == null) {
            singleton = new RankRepository(dao);
        }
        return singleton;
    }

    /**
     * @param dao The DAO to edit inside the database.
     *
     * @author Victor Hargrave
     * @since 1.0.0
     */
    public RankRepository(final Dao<RankEntity, Integer> dao) {
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
                getDao().create((new RankEntity().setId(1).setKey("6").setPointsTrumpf(0).setPointsObeAbe(0).setPointsOndeufe(11)));
            }
            if (!getDao().idExists(2)) {
                getDao().create((new RankEntity().setId(2).setKey("7").setPointsTrumpf(0).setPointsObeAbe(0).setPointsOndeufe(0)));
            }
            if (!getDao().idExists(3)) {
                getDao().create((new RankEntity().setId(3).setKey("8").setPointsTrumpf(0).setPointsObeAbe(8).setPointsOndeufe(8)));
            }
            if (!getDao().idExists(4)) {
                getDao().create((new RankEntity().setId(4).setKey("9").setPointsTrumpf(0).setPointsObeAbe(0).setPointsOndeufe(0)));
            }
            if (!getDao().idExists(5)) {
                getDao().create((new RankEntity().setId(5).setKey("10").setPointsTrumpf(10).setPointsObeAbe(10).setPointsOndeufe(10)));
            }
            if (!getDao().idExists(6)) {
                getDao().create((new RankEntity().setId(6).setKey("jack").setPointsTrumpf(2).setPointsObeAbe(2).setPointsOndeufe(2)));
            }
            if (!getDao().idExists(7)) {
                getDao().create((new RankEntity().setId(7).setKey("queen").setPointsTrumpf(3).setPointsObeAbe(3).setPointsOndeufe(3)));
            }
            if (!getDao().idExists(8)) {
                getDao().create((new RankEntity().setId(8).setKey("king").setPointsTrumpf(4).setPointsObeAbe(4).setPointsOndeufe(4)));
            }
            if (!getDao().idExists(9)) {
                getDao().create((new RankEntity().setId(9).setKey("ace").setPointsTrumpf(11).setPointsObeAbe(11).setPointsOndeufe(0)));
            }
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * @param name The name of the rank.
     *
     * @return Returns the RankEntity of the rank, or null if not found or
     * something went wrong.
     *
     * @author Victor Hargrave
     * @since 1.0.0
     */
    public RankEntity getByName(final String name) {
        try {
            // Find all users with the given username (only one)
            List<RankEntity> results = getDao().queryBuilder().where().eq("key", name).query();
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
