package jass.server.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import jass.lib.database.Entity;
import jass.lib.message.CardData;

/**
 * A model with all known (and cached) Games.
 *
 * @author Victor Hargrave
 * @version %I%, %G%
 * @since 0.0.1
 */
@DatabaseTable(tableName = "card")
public final class CardEntity extends Entity {
    /**
     * The ID.
     */
    @DatabaseField(id = true)
    private int id;

    /**
     * The rank.
     */
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private RankEntity rank;

    /**
     * The suit.
     */
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private SuitEntity suit;

    /**
     * For ORMLite all persisted classes must define a no-arg constructor with
     * at least package visibility.
     */
    public CardEntity() {
    }

    /**
     * @return Returns the ID.
     */
    public int getId() {
        return id;
    }

    /**
     * @param id The ID.
     *
     * @return Returns the object for further processing.
     */
    public CardEntity setId(final int id) {
        this.id = id;
        return this;
    }

    /**
     * @return Returns the rank.
     */
    public RankEntity getRank() {
        return rank;
    }

    /**
     * @param rank The rank.
     *
     * @return Returns the object for further processing.
     */
    public CardEntity setRank(final RankEntity rank) {
        this.rank = rank;
        return this;
    }

    /**
     * @return Returns the suit.
     */
    public SuitEntity getSuit() {
        return suit;
    }

    /**
     * @param suit The suit.
     *
     * @return Returns the object for further processing.
     */
    public CardEntity setSuit(final SuitEntity suit) {
        this.suit = suit;
        return this;
    }

    /**
     * @param cardEntity The card.
     *
     * @return Returns a card DTO.
     */
    public static CardData toCardData(final CardEntity cardEntity) {
        return new CardData(cardEntity.id, cardEntity.suit.getId(), cardEntity.suit.getKey(), cardEntity.rank.getId(), cardEntity.rank.getKey());
    }
}
