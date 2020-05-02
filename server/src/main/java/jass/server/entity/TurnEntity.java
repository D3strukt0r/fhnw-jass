package jass.server.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import jass.lib.Card;
import jass.lib.GameMode;
import jass.lib.database.Entity;

/**
 * A model with all known (and cached) turns.
 *
 * @author Thomas Weber
 * @version %I%, %G%
 * @since 0.0.1
 */

@DatabaseTable(tableName = "turn")
public final class TurnEntity extends Entity {
    /**
     * The ID.
     */
    @DatabaseField(generatedId = true)
    private int id;

    /**
     * The round that this turn belongs to.
     */
    @DatabaseField(foreign = true)
    private RoundEntity round;

    /**
     * The winning user of this turn
     */
    @DatabaseField(foreign = true)
    private UserEntity winningUser;

    /**
     * The first played card of the turn
     */
    @DatabaseField(foreign = true)
    private CardEntity cardOne;

    /**
     * The second played card of the turn
     */
    @DatabaseField(foreign = true)
    private CardEntity cardTwo;

    /**
     * The third played card of the turn
     */
    @DatabaseField(foreign = true)
    private CardEntity cardThree;

    /**
     * The fourth played card of the turn
     */
    @DatabaseField(foreign = true)
    private CardEntity cardFour;

    /**
     * For ORMLite all persisted classes must define a no-arg constructor with
     * at least package visibility.
     */
    public TurnEntity() {
    }

    /**
     * @return Returns the ID.
     */
    public int getId() {
        return id;
    }

    /**
     * @return Returns the round.
     */
    public RoundEntity getRound() {
        return round;
    }

    /**
     * @param round The round.
     *
     * @return Returns the object for further processing.
     */
    public TurnEntity setRound(final RoundEntity round) {
        this.round = round;
        return this;
    }

    /**
     * @return Returns the winning user.
     */
    public UserEntity getWinningUser() {
        return winningUser;
    }

    /**
     * @param winningUser The winning user.
     */
    public void setWinningUser(UserEntity winningUser) {
        this.winningUser = winningUser;
    }

    /**
     * @return Returns the first card.
     */
    public CardEntity getCardOne() {
        return cardOne;
    }

    /**
     * @param cardOne The first card.
     */
    public void setCardOne(CardEntity cardOne) {
        this.cardOne = cardOne;
    }

    /**
     * @return Returns the second card.
     */
    public CardEntity getCardTwo() {
        return cardTwo;
    }

    /**
     * @param cardTwo The second card.
     */
    public void setCardTwo(CardEntity cardTwo) {
        this.cardTwo = cardTwo;
    }

    /**
     * @return Returns the third card.
     */
    public CardEntity getCardThree() {
        return cardThree;
    }

    /**
     * @param cardThree The third card.
     */
    public void setCardThree(CardEntity cardThree) {
        this.cardThree = cardThree;
    }

    /**
     * @return Returns the fourth card.
     */
    public CardEntity getCardFour() {
        return cardFour;
    }

    /**
     * @param cardFour The fourth card.
     */
    public void setCardFour(CardEntity cardFour) {
        this.cardFour = cardFour;
    }

}
