package jass.server.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import jass.lib.database.Entity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * A model with all known (and cached) Games.
 *
 * @author Thomas Weber
 * @version %I%, %G%
 * @since 0.0.1
 */

@DatabaseTable(tableName = "game")
public class GameEntity implements Entity {

    private static final Logger logger = LogManager.getLogger(GameEntity.class);

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(foreign = true)
    private TeamEntity teamOne;

    @DatabaseField(foreign = true)
    private TeamEntity teamTwo;

    @DatabaseField(defaultValue = "true", canBeNull = false)
    private boolean isActive = true;

    /**
     * For ORMLite all persisted classes must define a no-arg constructor with
     * at least package visibility.
     */
    GameEntity() { }


    public GameEntity(TeamEntity teamOne, TeamEntity teamTwo, Boolean isActive) {
        this.teamOne = teamOne;
        this.teamTwo = teamTwo;
        this.isActive = isActive;
    }

    public int getId() {
        return id;
    }

    public TeamEntity getTeamOne() {
        return teamOne;
    }

    public TeamEntity getTeamTwo() {
        return teamTwo;
    }

}
