package jass.server.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import jass.lib.database.Entity;

/**
 * A model with all known (and cached) teams.
 *
 * @author Thomas Weber
 * @version %I%, %G%
 * @since 0.0.1
 */
@DatabaseTable(tableName = "team")
public final class TeamEntity implements Entity {
    /**
     * The ID.
     */
    @DatabaseField(generatedId = true)
    private int id;

    /**
     * Player one inside the team.
     */
    @DatabaseField(foreign = true)
    private UserEntity playerOne;

    /**
     * Player two inside the team.
     */
    @DatabaseField(foreign = true)
    private UserEntity playerTwo;

    /**
     * For ORMLite all persisted classes must define a no-arg constructor with
     * at least package visibility.
     */
    TeamEntity() {
    }

    /**
     * @param playerOne Player one.
     * @param playerTwo Player two.
     */
    public TeamEntity(final UserEntity playerOne, final UserEntity playerTwo) {
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
    }

    /**
     * @return Returns the ID.
     */
    public int getId() {
        return id;
    }

    /**
     * @return Returns player one.
     */
    public UserEntity getPlayerOne() {
        return playerOne;
    }

    /**
     * @param playerOne Player one.
     */
    public void setPlayerOne(final UserEntity playerOne) {
        this.playerOne = playerOne;
    }

    /**
     * @return Returns player two.
     */
    public UserEntity getPlayerTwo() {
        return playerTwo;
    }

    /**
     * @param playerTwo Player two.
     */
    public void setPlayerTwp(final UserEntity playerTwo) {
        this.playerTwo = playerTwo;
    }

    /**
     * @param user The user to check.
     *
     * @return Returns true if the user is inside the team, otherwise false.
     */
    public boolean checkIfPlayerIsInTeam(final UserEntity user) {
        boolean returnValue = false;
        if (playerOne.getId() == user.getId() || playerTwo.getId() == user.getId()) {
            returnValue = true;
        }
        return returnValue;
    }
}
