package jass.server.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import jass.lib.database.Entity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * A model with all known (and cached) Games.
 *
 * @author Victor Hargrave
 * @version %I%, %G%
 * @since 0.0.1
 */

@DatabaseTable(tableName = "suit")
public class SuitEntity implements Entity {

    private static final Logger logger = LogManager.getLogger(SuitEntity.class);

    @DatabaseField(id = true)
    private int id;

    @DatabaseField()
    private String key;

    /**
     * For ORMLite all persisted classes must define a no-arg constructor with
     * at least package visibility.
     */
    SuitEntity() { }

    public SuitEntity(int id, String key) {
        this.id = id;
        this.key = key;
    }

    public int getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

}
