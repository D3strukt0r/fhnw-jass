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
public final class DeckEntity implements Entity {
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
    private CardEntity card1;

    /**
     * Card two.
     */
    @DatabaseField(foreign = true)
    private CardEntity card2;

    /**
     * Card three.
     */
    @DatabaseField(foreign = true)
    private CardEntity card3;

    /**
     * Card four.
     */
    @DatabaseField(foreign = true)
    private CardEntity card4;

    /**
     * Card five.
     */
    @DatabaseField(foreign = true)
    private CardEntity card5;

    /**
     * Card six.
     */
    @DatabaseField(foreign = true)
    private CardEntity card6;

    /**
     * Card seven.
     */
    @DatabaseField(foreign = true)
    private CardEntity card7;

    /**
     * Card eight.
     */
    @DatabaseField(foreign = true)
    private CardEntity card8;

    /**
     * Card nine.
     */
    @DatabaseField(foreign = true)
    private CardEntity card9;

    /**
     * For ORMLite all persisted classes must define a no-arg constructor with
     * at least package visibility.
     */
    DeckEntity() {
    }

    /**
     * @param player The player.
     * @param round  The round.
     * @param card1  Card one.
     * @param card2  Card two.
     * @param card3  Card three.
     * @param card4  Card four.
     * @param card5  Card five.
     * @param card6  Card six.
     * @param card7  Card seven.
     * @param card8  Card eight.
     * @param card9  Card nine.
     */
    public DeckEntity(final UserEntity player, final RoundEntity round, final CardEntity card1, final CardEntity card2, final CardEntity card3, final CardEntity card4, final CardEntity card5, final CardEntity card6, final CardEntity card7, final CardEntity card8, final CardEntity card9) {
        this.player = player;
        this.round = round;
        this.card1 = card1;
        this.card2 = card2;
        this.card3 = card3;
        this.card4 = card4;
        this.card5 = card5;
        this.card6 = card6;
        this.card7 = card7;
        this.card8 = card8;
        this.card9 = card9;
    }

    /**
     * @return Returns the ID.
     */
    public int getId() {
        return id;
    }

    /**
     * @return Returns the cards as an array.
     */
    public ArrayList<CardEntity> getCards() {
        ArrayList<CardEntity> cards = new ArrayList<>();
        cards.add(card1);
        cards.add(card2);
        cards.add(card3);
        cards.add(card4);
        cards.add(card5);
        cards.add(card6);
        cards.add(card7);
        cards.add(card8);
        cards.add(card9);
        return cards;
    }
}
