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
@DatabaseTable(tableName = "rank")
public final class RankEntity implements Entity {
    /**
     * The ID.
     */
    @DatabaseField(id = true)
    private int id;

    /**
     * The rank.
     */
    @DatabaseField()
    private String key;

    /**
     * How many points this rank gives if it's a trumpf.
     */
    @DatabaseField()
    private int pointsTrumpf;

    /**
     * How many points this rank gives if the game mode is Obe Abe.
     */
    @DatabaseField()
    private int pointsObeAbe;

    /**
     * How many points this rank gives if the game mode is Onde Ufe.
     */
    @DatabaseField()
    private int pointsOndeufe;

    /**
     * For ORMLite all persisted classes must define a no-arg constructor with
     * at least package visibility.
     */
    RankEntity() {
    }

    /**
     * @param id            The ID.
     * @param key           The rank.
     * @param pointsTrumpf  Points (Trumpf)
     * @param pointsObeAbe  Points (Obe Abe)
     * @param pointsOndeufe Points (Onde Ufe)
     */
    public RankEntity(final int id, final String key, final int pointsTrumpf, final int pointsObeAbe, final int pointsOndeufe) {
        this.id = id;
        this.key = key;
        this.pointsObeAbe = pointsObeAbe;
        this.pointsOndeufe = pointsOndeufe;
        this.pointsTrumpf = pointsTrumpf;
    }

    /**
     * @return Returns the ID.
     */
    public int getId() {
        return id;
    }

    /**
     * @return Returns the rank.
     */
    public String getKey() {
        return key;
    }

}
