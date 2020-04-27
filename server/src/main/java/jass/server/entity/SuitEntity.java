package jass.server.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import jass.lib.database.Entity;

/**
 * A model with all known (and cached) Games.
 *
 * @author Victor Hargrave
 * @version %I%, %G%
 * @since 0.0.1
 */
@DatabaseTable(tableName = "suit")
public final class SuitEntity implements Entity {
    /**
     * The ID.
     */
    @DatabaseField(id = true)
    private int id;

    /**
     * The suit.
     */
    @DatabaseField()
    private String key;

    /**
     * For ORMLite all persisted classes must define a no-arg constructor with
     * at least package visibility.
     */
    SuitEntity() {
    }

    /**
     * @param id  The ID.
     * @param key The suit,
     */
    public SuitEntity(final int id, final String key) {
        this.id = id;
        this.key = key;
    }

    /**
     * @return Returns the ID.
     */
    public int getId() {
        return id;
    }

    /**
     * @return Returns the suit.
     */
    public String getKey() {
        return key;
    }

}
