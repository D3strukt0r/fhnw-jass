package jass.server.repository;

import com.j256.ormlite.dao.Dao;
import jass.lib.database.Repository;
import jass.server.entity.TeamEntity;

/**
 * A model with all known (and cached) teams.
 *
 * @author Thomas Weber
 * @version %I%, %G%
 * @since 0.0.1
 */

/**
 * Class not needed, as Teams not saved to DB
 */
public final class TeamRepository extends Repository<Dao<TeamEntity, String>, TeamEntity> {

    private static TeamRepository singleton = null;

    public static TeamRepository getSingleton(final Dao<TeamEntity, String> dao) {
        if (singleton == null) {
            singleton = new TeamRepository(dao);
        }
        return singleton;
    }

    /**
     * @param dao The DAO to edit inside the database.
     */
    public TeamRepository(final Dao<TeamEntity, String> dao) {
        super(dao);
    }

}
