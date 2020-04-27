package jass.lib.database;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

/**
 * @param <D> The DAO for the entity
 * @param <E> The entity
 *
 * @author Victor Hargrave & Manuele Vaccari
 */
public abstract class Repository<D extends Dao<E, Integer>, E extends Entity> {
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

    /**
     * @param id The id of the tuple to find.
     *
     * @return Returns the entity or null if not found.
     */
    public E getById(final int id) {
        try {
            return dao.queryForId(id);
        } catch (SQLException e) {
            return null;
        }
    }

    /**
     * @return Returns all tuples or null if an error happened.
     */
    public List<E> getAll() {
        try {
            return dao.queryForAll();
        } catch (SQLException e) {
            return null;
        }
    }
}
