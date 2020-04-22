package jass.server.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import jass.lib.database.Entity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.smartcardio.Card;

/**
 * A model with all known (and cached) teams.
 *
 * @author Victor Hargrave
 * @version %I%, %G%
 * @since 0.0.1
 */

@DatabaseTable(tableName = "round")
public class RoundEntity implements Entity {

    private static final Logger logger = LogManager.getLogger(DeckEntity.class);

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(foreign = true)
    private UserEntity gameModeChooser;

    @DatabaseField(foreign = true)
    private GameEntity game;

    @DatabaseField()
    private int pointsTeamOne;

    @DatabaseField()
    private int pointsTeamTwo;

    /**
     * For ORMLite all persisted classes must define a no-arg constructor with
     * at least package visibility.
     */
    RoundEntity() {
    }

    public int getId() {
        return id;
    }
}
