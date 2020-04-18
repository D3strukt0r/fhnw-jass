package jass.lib.database;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

public abstract class Repository<D extends Dao<E, ?>, E extends Entity> {
    /**
     * The DAO.
     */
    private final D dao;

    /**
     * @param dao The DAO for editing the database.
     */
    public Repository(final D dao) {
        this.dao = dao;
    }

    /**
     * @return Returns the DAO.
     */
    public D getDao() {
        return dao;
    }

    /**
     * @param entity The entity to be added.
     *
     * @return Returns true if successful and false if failed.
     */
    public boolean add(final E entity) {
        try {
            // Add the object to the db
            dao.create(entity);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * @param entity The entity to be removed.
     *
     * @return Returns true if successful and false if failed.
     */
    public boolean remove(final E entity) {
        try {
            // Delete the object from the db
            dao.delete(entity);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * @param entity The entity to be updated.
     *
     * @return Returns true if successful and false if failed.
     */
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
