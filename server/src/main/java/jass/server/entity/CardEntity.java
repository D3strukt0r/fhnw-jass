package jass.server.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import jass.lib.database.Entity;
import jass.lib.message.CardData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * A model with all known (and cached) Games.
 *
 * @author Victor Hargrave
 * @version %I%, %G%
 * @since 0.0.1
 */

@DatabaseTable(tableName = "card")
public class CardEntity implements Entity {

    private static final Logger logger = LogManager.getLogger(CardEntity.class);

    @DatabaseField(id = true)
    private int id;

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private RankEntity rank;

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private SuitEntity suit;

    /**
     * For ORMLite all persisted classes must define a no-arg constructor with
     * at least package visibility.
     */
    CardEntity() { }


    public CardEntity(int id, RankEntity rank, SuitEntity suit) {
        this.id = id;
        this.rank = rank;
        this.suit = suit;
    }

    public int getId() {
        return id;
    }

    public RankEntity getRank() {
        return rank;
    }

    public void setRank(RankEntity rank) {
        this.rank = rank;
    }

    public SuitEntity getSuit() {
        return suit;
    }

    public void setSuit(SuitEntity suit) {
        this.suit = suit;
    }

    public static CardData toCardData(CardEntity cardEntity) {
        CardData cardData = new CardData(cardEntity.id, cardEntity.suit.getId(),
            cardEntity.suit.getKey(), cardEntity.rank.getId(), cardEntity.rank.getKey());
        return cardData;
    }
}
