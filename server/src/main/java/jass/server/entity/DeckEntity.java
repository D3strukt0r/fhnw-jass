package jass.server.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import jass.lib.database.Entity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.smartcardio.Card;

/**
 * A model with all known (and cached) teams.
 *
 * @author Victor Hargrave
 * @version %I%, %G%
 * @since 0.0.1
 */

@DatabaseTable(tableName = "deck")
public class DeckEntity implements Entity {

    private static final Logger logger = LogManager.getLogger(DeckEntity.class);

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(foreign = true)
    private UserEntity player;

    @DatabaseField(foreign = true)
    private RoundEntity round;

    @DatabaseField(foreign = true)
    private CardEntity card1;

    @DatabaseField(foreign = true)
    private CardEntity card2;

    @DatabaseField(foreign = true)
    private CardEntity card3;

    @DatabaseField(foreign = true)
    private CardEntity card4;

    @DatabaseField(foreign = true)
    private CardEntity card5;

    @DatabaseField(foreign = true)
    private CardEntity card6;

    @DatabaseField(foreign = true)
    private CardEntity card7;

    @DatabaseField(foreign = true)
    private CardEntity card8;

    @DatabaseField(foreign = true)
    private CardEntity card9;

    /**
     * For ORMLite all persisted classes must define a no-arg constructor with
     * at least package visibility.
     */
    DeckEntity() {
    }


    public DeckEntity(UserEntity player,
                      RoundEntity round,
                      CardEntity card1,
                      CardEntity card2,
                      CardEntity card3,
                      CardEntity card4,
                      CardEntity card5,
                      CardEntity card6,
                      CardEntity card7,
                      CardEntity card8,
                      CardEntity card9) {
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

    public int getId() {
        return id;
    }
}
