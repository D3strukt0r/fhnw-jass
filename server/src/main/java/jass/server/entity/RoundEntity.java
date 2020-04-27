package jass.server.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import jass.lib.database.Entity;

/**
 * A model with all known (and cached) teams.
 *
 * @author Victor Hargrave
 * @version %I%, %G%
 * @since 0.0.1
 */
@DatabaseTable(tableName = "round")
public final class RoundEntity implements Entity {
    /**
     * The ID.
     */
    @DatabaseField(generatedId = true)
    private int id;

    /**
     * The user which chooses the game mode.
     */
    @DatabaseField(foreign = true)
    private UserEntity gameModeChooser;

    /**
     * The game that this round belongs to.
     */
    @DatabaseField(foreign = true)
    private GameEntity game;

    /**
     * A cached total of the points for team one.
     */
    @DatabaseField()
    private int pointsTeamOne;

    /**
     * A cached total of the points for team two.
     */
    @DatabaseField()
    private int pointsTeamTwo;

    /**
     * For ORMLite all persisted classes must define a no-arg constructor with
     * at least package visibility.
     */
    RoundEntity() {
    }

    /**
     * @param gameModeChooser The user which chooses the game mode.
     * @param game            The game.
     */
    public RoundEntity(final UserEntity gameModeChooser, final GameEntity game) {
        this.gameModeChooser = gameModeChooser;
        this.game = game;
        this.pointsTeamOne = 0;
        this.pointsTeamTwo = 0;
    }

    /**
     * @return Returns the ID.
     */
    public int getId() {
        return id;
    }
}
