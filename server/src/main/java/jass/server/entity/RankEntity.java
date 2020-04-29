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
public final class RankEntity extends Entity {
    /**
     * The ID.
     */
    @DatabaseField(id = true)
    private int id;

    /**
     * The rank.
     */
    @DatabaseField
    private String key;

    /**
     * How many points this rank gives if it's a trumpf.
     */
    @DatabaseField
    private int pointsTrumpf;

    /**
     * How many points this rank gives if the game mode is Obe Abe.
     */
    @DatabaseField
    private int pointsObeAbe;

    /**
     * How many points this rank gives if the game mode is Onde Ufe.
     */
    @DatabaseField
    private int pointsOndeufe;

    /**
     * For ORMLite all persisted classes must define a no-arg constructor with
     * at least package visibility.
     */
    public RankEntity() {
    }

    /**
     * @return Returns the ID.
     */
    public int getId() {
        return id;
    }

    /**
     * @param id The ID.
     *
     * @return Returns the object for further processing.
     */
    public RankEntity setId(int id) {
        this.id = id;
        return this;
    }

    /**
     * @return Returns the rank.
     */
    public String getKey() {
        return key;
    }

    /**
     * @param key The kex.
     *
     * @return Returns the object for further processing.
     */
    public RankEntity setKey(final String key) {
        this.key = key;
        return this;
    }

    /**
     * @return Returns the points for trumpf.
     */
    public int getPointsTrumpf() {
        return pointsTrumpf;
    }

    /**
     * @param pointsTrumpf Points trumpf.
     *
     * @return Returns the object for further processing.
     */
    public RankEntity setPointsTrumpf(final int pointsTrumpf) {
        this.pointsTrumpf = pointsTrumpf;
        return this;
    }

    /**
     * @return Returns the points for obe abe.
     */
    public int getPointsObeAbe() {
        return pointsObeAbe;
    }

    /**
     * @param pointsObeAbe Points obe abe.
     *
     * @return Returns the object for further processing.
     */
    public RankEntity setPointsObeAbe(final int pointsObeAbe) {
        this.pointsObeAbe = pointsObeAbe;
        return this;
    }

    /**
     * @return Returns the points for onde ufe
     */
    public int getPointsOndeufe() {
        return pointsOndeufe;
    }

    /**
     * @param pointsOndeufe Points onde ufe.
     *
     * @return Returns the object for further processing.
     */
    public RankEntity setPointsOndeufe(final int pointsOndeufe) {
        this.pointsOndeufe = pointsOndeufe;
        return this;
    }
}
