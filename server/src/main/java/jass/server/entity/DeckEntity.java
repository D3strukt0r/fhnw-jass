package jass.server.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import jass.lib.database.Entity;

import java.util.ArrayList;

/**
 * A model with all known (and cached) teams.
 *
 * @author Victor Hargrave
 * @version %I%, %G%
 * @since 0.0.1
 */
@DatabaseTable(tableName = "deck")
public final class DeckEntity extends Entity {
    /**
     * The ID.
     */
    @DatabaseField(generatedId = true)
    private int id;

    /**
     * The player the deck belongs to.
     */
    @DatabaseField(foreign = true)
    private UserEntity player;

    /**
     * The round the deck belongs to.
     */
    @DatabaseField(foreign = true)
    private RoundEntity round;

    /**
     * Card one.
     */
    @DatabaseField(foreign = true)
    private CardEntity cardOne;

    /**
     * Card two.
     */
    @DatabaseField(foreign = true)
    private CardEntity cardTwo;

    /**
     * Card three.
     */
    @DatabaseField(foreign = true)
    private CardEntity cardThree;

    /**
     * Card four.
     */
    @DatabaseField(foreign = true)
    private CardEntity cardFour;

    /**
     * Card five.
     */
    @DatabaseField(foreign = true)
    private CardEntity cardFive;

    /**
     * Card six.
     */
    @DatabaseField(foreign = true)
    private CardEntity cardSix;

    /**
     * Card seven.
     */
    @DatabaseField(foreign = true)
    private CardEntity cardSeven;

    /**
     * Card eight.
     */
    @DatabaseField(foreign = true)
    private CardEntity cardEight;

    /**
     * Card nine.
     */
    @DatabaseField(foreign = true)
    private CardEntity cardNine;

    /**
     * For ORMLite all persisted classes must define a no-arg constructor with
     * at least package visibility.
     */
    public DeckEntity() {
    }

    /**
     * @return Returns the ID.
     */
    public int getId() {
        return id;
    }

    /**
     * @return Returns the player
     */
    public UserEntity getPlayer() {
        return player;
    }

    /**
     * @param player The player.
     *
     * @return Returns the object for further processing.
     */
    public DeckEntity setPlayer(final UserEntity player) {
        this.player = player;
        return this;
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
    public DeckEntity setRound(final RoundEntity round) {
        this.round = round;
        return this;
    }

    /**
     * @return Returns card one.
     */
    public CardEntity getCardOne() {
        return cardOne;
    }

    /**
     * @param cardOne Card one.
     *
     * @return Returns the object for further processing.
     */
    public DeckEntity setCardOne(final CardEntity cardOne) {
        this.cardOne = cardOne;
        return this;
    }

    /**
     * @return Returns card two.
     */
    public CardEntity getCardTwo() {
        return cardTwo;
    }

    /**
     * @param cardTwo Card two.
     *
     * @return Returns the object for further processing.
     */
    public DeckEntity setCardTwo(final CardEntity cardTwo) {
        this.cardTwo = cardTwo;
        return this;
    }

    /**
     * @return Returns card three.
     */
    public CardEntity getCardThree() {
        return cardThree;
    }

    /**
     * @param cardThree Card three.
     *
     * @return Returns the object for further processing.
     */
    public DeckEntity setCardThree(final CardEntity cardThree) {
        this.cardThree = cardThree;
        return this;
    }

    /**
     * @return Returns card four.
     */
    public CardEntity getCardFour() {
        return cardFour;
    }

    /**
     * @param cardFour Card four.
     *
     * @return Returns the object for further processing.
     */
    public DeckEntity setCardFour(final CardEntity cardFour) {
        this.cardFour = cardFour;
        return this;
    }

    /**
     * @return Returns card five.
     */
    public CardEntity getCardFive() {
        return cardFive;
    }

    /**
     * @param cardFive Card five.
     *
     * @return Returns the object for further processing.
     */
    public DeckEntity setCardFive(final CardEntity cardFive) {
        this.cardFive = cardFive;
        return this;
    }

    /**
     * @return Returns card six.
     */
    public CardEntity getCardSix() {
        return cardSix;
    }

    /**
     * @param cardSix Card six.
     *
     * @return Returns the object for further processing.
     */
    public DeckEntity setCardSix(final CardEntity cardSix) {
        this.cardSix = cardSix;
        return this;
    }

    /**
     * @return Returns card seven.
     */
    public CardEntity getCardSeven() {
        return cardSeven;
    }

    /**
     * @param cardSeven Card seven.
     *
     * @return Returns the object for further processing.
     */
    public DeckEntity setCardSeven(final CardEntity cardSeven) {
        this.cardSeven = cardSeven;
        return this;
    }

    /**
     * @return Returns card eight.
     */
    public CardEntity getCardEight() {
        return cardEight;
    }

    /**
     * @param cardEight Card eight.
     *
     * @return Returns the object for further processing.
     */
    public DeckEntity setCardEight(final CardEntity cardEight) {
        this.cardEight = cardEight;
        return this;
    }

    /**
     * @return Returns card nine.
     */
    public CardEntity getCardNine() {
        return cardNine;
    }

    /**
     * @param cardNine Card nine.
     *
     * @return Returns the object for further processing.
     */
    public DeckEntity setCardNine(final CardEntity cardNine) {
        this.cardNine = cardNine;
        return this;
    }

    /**
     * @return Returns the cards as an array.
     */
    public ArrayList<CardEntity> getCards() {
        ArrayList<CardEntity> cards = new ArrayList<>();
        cards.add(cardOne);
        cards.add(cardTwo);
        cards.add(cardThree);
        cards.add(cardFour);
        cards.add(cardFive);
        cards.add(cardSix);
        cards.add(cardSeven);
        cards.add(cardEight);
        cards.add(cardNine);
        return cards;
    }
}
