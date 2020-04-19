package jass.server.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import jass.lib.database.Entity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * A model with all known (and cached) teams.
 *
 * @author Thomas Weber
 * @version %I%, %G%
 * @since 0.0.1
 */

@DatabaseTable(tableName = "team")
public class TeamEntity implements Entity {

    private static final Logger logger = LogManager.getLogger(TeamEntity.class);

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(foreign = true)
    private UserEntity playerOne;

    @DatabaseField(foreign = true)
    private UserEntity playerTwo;

    /**
     * For ORMLite all persisted classes must define a no-arg constructor with
     * at least package visibility.
     */
    TeamEntity() { }


    public TeamEntity(final UserEntity playerOne, final UserEntity playerTwo) {
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
    }

    public int getId () {
        return id;
    }

    public UserEntity getPlayerOne() {
        return playerOne;
    }

    public void setPlayerOne(UserEntity playerOne) {
        this.playerOne = playerOne;
    }

    public UserEntity getPlayerTwo(){
        return playerTwo;
    }

    public void setPlayerTwp(UserEntity playerTwo) {
        this.playerTwo = playerTwo;
    }
}
