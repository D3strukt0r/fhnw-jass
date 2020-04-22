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

@DatabaseTable(tableName = "rank")
public class RankEntity implements Entity {

    private static final Logger logger = LogManager.getLogger(RankEntity.class);

    @DatabaseField(id = true)
    private int id;

    @DatabaseField()
    private String key;

    @DatabaseField()
    private int pointsTrumpf;

    @DatabaseField()
    private int pointsObeAbe;

    @DatabaseField()
    private int pointsOndeufe;

    /**
     * For ORMLite all persisted classes must define a no-arg constructor with
     * at least package visibility.
     */
    RankEntity() { }

    public RankEntity(int id, String key, int pointsTrumpf, int pointsObeAbe, int pointsOndeufe) {
        this.id = id;
        this.key = key;
        this.pointsObeAbe = pointsObeAbe;
        this.pointsOndeufe = pointsOndeufe;
        this.pointsTrumpf = pointsTrumpf;
    }

    public int getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

}
