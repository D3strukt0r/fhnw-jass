package jass.server.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import jass.lib.database.Entity;

/**
 * A model with all known (and cached) Games.
 *
 * @author Thomas Weber
 * @version %I%, %G%
 * @since 0.0.1
 */
@DatabaseTable(tableName = "game")
public final class GameEntity implements Entity {
    /**
     * The ID.
     */
    @DatabaseField(generatedId = true)
    private int id;

    /**
     * Team one.
     */
    @DatabaseField(foreign = true)
    private TeamEntity teamOne;

    /**
     * Team two.
     */
    @DatabaseField(foreign = true)
    private TeamEntity teamTwo;

    /**
     * Whether the team is still active.
     */
    @DatabaseField(defaultValue = "true", canBeNull = false)
    private boolean isActive = true;

    /**
     * For ORMLite all persisted classes must define a no-arg constructor with
     * at least package visibility.
     */
    GameEntity() {
    }

    /**
     * @param teamOne  Team one.
     * @param teamTwo  Team two.
     * @param isActive Whether the team is still active.
     */
    public GameEntity(final TeamEntity teamOne, final TeamEntity teamTwo, final boolean isActive) {
        this.teamOne = teamOne;
        this.teamTwo = teamTwo;
        this.isActive = isActive;
    }

    /**
     * @return Returns the ID.
     */
    public int getId() {
        return id;
    }

    /**
     * @return Returns team one.
     */
    public TeamEntity getTeamOne() {
        return teamOne;
    }

    /**
     * @return Returns team two.
     */
    public TeamEntity getTeamTwo() {
        return teamTwo;
    }
}
