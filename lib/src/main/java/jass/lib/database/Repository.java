package jass.lib.database;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

public abstract class Repository<D extends Dao<E, ?>, E extends Entity> {

    private final D dao;

    public D getDao() {
        return dao;
    }

    ///////////////////////////////////////

    public Repository(final D dao) {
        this.dao = dao;
    }

    ///////////////////////////////////////

    public boolean add(final E entity) {
        try {
            // Add the object to the db
            dao.create(entity);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean remove(final E entity) {
        try {
            // Delete the object from the db
            dao.delete(entity);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean update(final E entity) {
        try {
            // Update the object in the db
            dao.update(entity);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}
