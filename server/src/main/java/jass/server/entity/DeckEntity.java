package jass.server.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import jass.lib.Card;
import jass.lib.database.Entity;
import jass.server.persister.CardPersister;

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
    @DatabaseField(persisterClass = CardPersister.class)
    private Card cardOne;

    /**
     * Card two.
     */
    @DatabaseField(persisterClass = CardPersister.class)
    private Card cardTwo;

    /**
     * Card three.
     */
    @DatabaseField(persisterClass = CardPersister.class)
    private Card cardThree;

    /**
     * Card four.
     */
    @DatabaseField(persisterClass = CardPersister.class)
    private Card cardFour;

    /**
     * Card five.
     */
    @DatabaseField(persisterClass = CardPersister.class)
    private Card cardFive;

    /**
     * Card six.
     */
    @DatabaseField(persisterClass = CardPersister.class)
    private Card cardSix;

    /**
     * Card seven.
     */
    @DatabaseField(persisterClass = CardPersister.class)
    private Card cardSeven;

    /**
     * Card eight.
     */
    @DatabaseField(persisterClass = CardPersister.class)
    private Card cardEight;

    /**
     * Card nine.
     */
    @DatabaseField(persisterClass = CardPersister.class)
    private Card cardNine;

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
    public Card getCardOne() {
        return cardOne;
    }

    /**
     * @param cardOne Card one.
     *
     * @return Returns the object for further processing.
     */
    public DeckEntity setCardOne(final Card cardOne) {
        this.cardOne = cardOne;
        return this;
    }

    /**
     * @return Returns card two.
     */
    public Card getCardTwo() {
        return cardTwo;
    }

    /**
     * @param cardTwo Card two.
     *
     * @return Returns the object for further processing.
     */
    public DeckEntity setCardTwo(final Card cardTwo) {
        this.cardTwo = cardTwo;
        return this;
    }

    /**
     * @return Returns card three.
     */
    public Card getCardThree() {
        return cardThree;
    }

    /**
     * @param cardThree Card three.
     *
     * @return Returns the object for further processing.
     */
    public DeckEntity setCardThree(final Card cardThree) {
        this.cardThree = cardThree;
        return this;
    }

    /**
     * @return Returns card four.
     */
    public Card getCardFour() {
        return cardFour;
    }

    /**
     * @param cardFour Card four.
     *
     * @return Returns the object for further processing.
     */
    public DeckEntity setCardFour(final Card cardFour) {
        this.cardFour = cardFour;
        return this;
    }

    /**
     * @return Returns card five.
     */
    public Card getCardFive() {
        return cardFive;
    }

    /**
     * @param cardFive Card five.
     *
     * @return Returns the object for further processing.
     */
    public DeckEntity setCardFive(final Card cardFive) {
        this.cardFive = cardFive;
        return this;
    }

    /**
     * @return Returns card six.
     */
    public Card getCardSix() {
        return cardSix;
    }

    /**
     * @param cardSix Card six.
     *
     * @return Returns the object for further processing.
     */
    public DeckEntity setCardSix(final Card cardSix) {
        this.cardSix = cardSix;
        return this;
    }

    /**
     * @return Returns card seven.
     */
    public Card getCardSeven() {
        return cardSeven;
    }

    /**
     * @param cardSeven Card seven.
     *
     * @return Returns the object for further processing.
     */
    public DeckEntity setCardSeven(final Card cardSeven) {
        this.cardSeven = cardSeven;
        return this;
    }

    /**
     * @return Returns card eight.
     */
    public Card getCardEight() {
        return cardEight;
    }

    /**
     * @param cardEight Card eight.
     *
     * @return Returns the object for further processing.
     */
    public DeckEntity setCardEight(final Card cardEight) {
        this.cardEight = cardEight;
        return this;
    }

    /**
     * @return Returns card nine.
     */
    public Card getCardNine() {
        return cardNine;
    }

    /**
     * @param cardNine Card nine.
     *
     * @return Returns the object for further processing.
     */
    public DeckEntity setCardNine(final Card cardNine) {
        this.cardNine = cardNine;
        return this;
    }

    /**
     * @return Returns the cards as an array.
     */
    public ArrayList<Card> getCards() {
        ArrayList<Card> cards = new ArrayList<>();
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
