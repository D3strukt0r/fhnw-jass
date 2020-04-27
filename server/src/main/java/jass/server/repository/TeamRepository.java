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
public final class TeamRepository extends Repository<Dao<TeamEntity, Integer>, TeamEntity> {
    /**
     * The singleton.
     */
    private static TeamRepository singleton = null;

    /**
     * Creates a new singleton or returns the existing one.
     *
     * @param dao The DAO to edit inside the database.
     *
     * @return Returns the Repository.
     */
    public static TeamRepository getSingleton(final Dao<TeamEntity, Integer> dao) {
        if (singleton == null) {
            singleton = new TeamRepository(dao);
        }
        return singleton;
    }

    /**
     * @param dao The DAO to edit inside the database.
     */
    public TeamRepository(final Dao<TeamEntity, Integer> dao) {
        super(dao);
    }
}
