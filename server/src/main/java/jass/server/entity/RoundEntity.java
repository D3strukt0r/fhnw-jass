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
public final class RoundEntity extends Entity {
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
    @DatabaseField(defaultValue = "0", canBeNull = false)
    private int pointsTeamOne = 0;

    /**
     * A cached total of the points for team two.
     */
    @DatabaseField(defaultValue = "0", canBeNull = false)
    private int pointsTeamTwo = 0;

    /**
     * For ORMLite all persisted classes must define a no-arg constructor with
     * at least package visibility.
     */
    public RoundEntity() {
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
     * @param gameModeChooser The game mode chooser.
     *
     * @return Returns the object for further processing.
     */
    public RoundEntity setGameModeChooser(final UserEntity gameModeChooser) {
        this.gameModeChooser = gameModeChooser;
        return this;
    }

    /**
     * @return Returns the game mode.
     */
    public GameMode getGameMode() {
        return gameMode;
    }

    /**
     * @param gameMode The game mode.
     *
     * @return Returns the object for further processing.
     */
    public RoundEntity setGameMode(final GameMode gameMode) {
        this.gameMode = gameMode;
        return this;
    }

    /**
     * @return Returns the suit of the trumpf.
     */
    public Card.Suit getTrumpfSuit() {
        return trumpfSuit;
    }

    /**
     * @param trumpfSuit The trumpf suit.
     *
     * @return Returns the object for further processing.
     */
    public RoundEntity setTrumpfSuit(final Card.Suit trumpfSuit) {
        this.trumpfSuit = trumpfSuit;
        return this;
    }

    /**
     * @return Returns the game.
     */
    public GameEntity getGame() {
        return game;
    }

    /**
     * @param game The game.
     *
     * @return Returns the object for further processing.
     */
    public RoundEntity setGame(final GameEntity game) {
        this.game = game;
        return this;
    }

    /**
     * @return Returns the total points for team one.
     */
    public int getPointsTeamOne() {
        return pointsTeamOne;
    }

    /**
     * @param pointsTeamOne The total points for team one.
     *
     * @return Returns the object for further processing.
     */
    public RoundEntity setPointsTeamOne(int pointsTeamOne) {
        this.pointsTeamOne = pointsTeamOne;
        return this;
    }

    /**
     * @return Returns the total points for team two.
     */
    public int getPointsTeamTwo() {
        return pointsTeamTwo;
    }

    /**
     * @param pointsTeamTwo The total points for team two.
     *
     * @return Returns the object for further processing.
     */
    public RoundEntity setPointsTeamTwo(int pointsTeamTwo) {
        this.pointsTeamTwo = pointsTeamTwo;
        return this;
    }
}
