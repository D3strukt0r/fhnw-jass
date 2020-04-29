package jass.server.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import jass.lib.Card;
import jass.lib.GameMode;
import jass.lib.database.Entity;

/**
 * A model with all known (and cached) teams.
 *
 * @author Victor Hargrave, Manuele Vaccari
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
     * The chosen game mode.
     */
    @DatabaseField(defaultValue = "null")
    private GameMode gameMode = null;

    /**
     * In case of game mode trumpf, the trumpf card.
     */
    @DatabaseField(defaultValue = "null")
    private Card.Suit trumpfSuit = null;

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

    /**
     * @return Returns the game mode chooser.
     */
    public UserEntity getGameModeChooser() {
        return gameModeChooser;
    }

    /**
     * @param gameMode The game mode.
     */
    public void setGameMode(final GameMode gameMode) {
        this.gameMode = gameMode;
    }

    /**
     * @param trumpfSuit The trumpf suit.
     */
    public void setTrumpfSuit(final Card.Suit trumpfSuit) {
        this.trumpfSuit = trumpfSuit;
    }
}
