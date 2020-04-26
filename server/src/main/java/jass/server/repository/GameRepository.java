package jass.server.repository;

import com.j256.ormlite.dao.Dao;
import jass.lib.database.Repository;
import jass.server.entity.GameEntity;
import jass.server.entity.RoundEntity;

import java.sql.SQLException;

/**
 * A model with all known (and cached) teams.
 *
 * @author Thomas Weber
 * @version %I%, %G%
 * @since 0.0.1
 */


public final class GameRepository extends Repository<Dao<GameEntity, Integer>, GameEntity> {

    private static GameRepository singleton = null;

    public static GameRepository getSingleton(final Dao<GameEntity, Integer> dao) {
        if (singleton == null) {
            singleton = new GameRepository(dao);
        }
        return singleton;
    }

    /**
     * @param dao The DAO to edit inside the database.
     */
    public GameRepository(final Dao<GameEntity, Integer> dao) {
        super(dao);
    }
}
