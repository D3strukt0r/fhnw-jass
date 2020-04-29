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
public final class GameEntity extends Entity {
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
    public GameEntity() {
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
     * @param teamOne Team one.
     *
     * @return Returns the object for further processing.
     */
    public GameEntity setTeamOne(final TeamEntity teamOne) {
        this.teamOne = teamOne;
        return this;
    }

    /**
     * @return Returns team two.
     */
    public TeamEntity getTeamTwo() {
        return teamTwo;
    }

    /**
     * @param teamTwo Team two.
     *
     * @return Returns the object for further processing.
     */
    public GameEntity setTeamTwo(final TeamEntity teamTwo) {
        this.teamTwo = teamTwo;
        return this;
    }

    /**
     * @return Returns whether the game is currently running.
     */
    public boolean isActive() {
        return isActive;
    }

    /**
     * @param active Whether the game is currently running.
     *
     * @return Returns the object for further processing.
     */
    public GameEntity setActive(final boolean active) {
        isActive = active;
        return this;
    }
}
