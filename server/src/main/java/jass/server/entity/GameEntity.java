package jass.server.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion;
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

    @DatabaseField(columnName = "id", foreign = true)
    private TeamEntity teamOne;

    @DatabaseField(columnName = "id", foreign = true)
    private TeamEntity teamTwo;

    /**
     * For ORMLite all persisted classes must define a no-arg constructor with
     * at least package visibility.
     */
    GameEntity() { }

    GameEntity(final TeamEntity teamOne, final TeamEntity teamTwo) {
        this.teamOne = teamOne;
        this.teamTwo = teamTwo;
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
