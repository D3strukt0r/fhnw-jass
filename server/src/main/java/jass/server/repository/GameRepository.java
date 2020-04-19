package jass.server.repository;

import com.j256.ormlite.dao.Dao;
import jass.lib.database.Repository;
import jass.server.entity.GameEntity;

/**
 * A model with all known (and cached) teams.
 *
 * @author Thomas Weber
 * @version %I%, %G%
 * @since 0.0.1
 */

/**
 * Class not needed, as Games not saved to DB
 */
public final class GameRepository extends Repository<Dao<GameEntity, String>, GameEntity> {

    private static GameRepository singleton = null;

    public static GameRepository getSingleton(final Dao<GameEntity, String> dao) {
        if (singleton == null) {
            singleton = new GameRepository(dao);
        }
        return singleton;
    }

    /**
     * @param dao The DAO to edit inside the database.
     */
    public GameRepository(final Dao<GameEntity, String> dao) {
        super(dao);
    }

}
