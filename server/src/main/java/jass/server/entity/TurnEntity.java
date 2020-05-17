/*
 * fhnw-jass is jass game programmed in java for a school project.
 * Copyright (C) 2020 Manuele Vaccari & Victor Hargrave & Thomas Weber & Sasa
 * Trajkova
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package jass.server.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import jass.lib.database.Entity;

import java.util.ArrayList;

/**
 * A model with all known (and cached) turns.
 *
 * @author Thomas Weber & Victor Hargrave & Manuele Vaccari
 * @version %I%, %G%
 * @since 1.0.0
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
    @DatabaseField(foreign = true, canBeNull = false)
    private RoundEntity round;

    /**
     * The winning user of this turn.
     */
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private UserEntity winningUser;

    /**
     * The starting player of this turn.
     */
    @DatabaseField(foreign = true, canBeNull = false, foreignAutoRefresh = true)
    private UserEntity startingPlayer;

    /**
     * The first played card of the turn.
     */
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private CardEntity cardOne;

    /**
     * The user who played card one.
     */
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private UserEntity playerCardOne;

    /**
     * The second played card of the turn.
     */
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private CardEntity cardTwo;

    /**
     * The user who played card two.
     */
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private UserEntity playerCardTwo;

    /**
     * The third played card of the turn.
     */
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private CardEntity cardThree;

    /**
     * The user who played card three.
     */
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private UserEntity playerCardThree;

    /**
     * The fourth played card of the turn.
     */
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private CardEntity cardFour;

    /**
     * The user who played card four.
     */
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private UserEntity playerCardFour;

    /**
     * For ORMLite all persisted classes must define a no-arg constructor with
     * at least package visibility.
     *
     * @author Thomas Weber
     * @since 1.0.0
     */
    public TurnEntity() {
    }

    /**
     * @return Returns the ID.
     *
     * @author Thomas Weber
     * @since 1.0.0
     */
    public int getId() {
        return id;
    }

    /**
     * @return Returns the round.
     *
     * @author Thomas Weber
     * @since 1.0.0
     */
    public RoundEntity getRound() {
        return round;
    }

    /**
     * @param round The round.
     *
     * @return Returns the object for further processing.
     *
     * @author Thomas Weber
     * @since 1.0.0
     */
    public TurnEntity setRound(final RoundEntity round) {
        this.round = round;
        return this;
    }

    /**
     * @return Returns the winning user.
     *
     * @author Thomas Weber
     * @since 1.0.0
     */
    public UserEntity getWinningUser() {
        return winningUser;
    }

    /**
     * @param winningUser The winning user.
     *
     * @return Returns the object for further processing.
     *
     * @author Thomas Weber
     * @since 1.0.0
     */
    public TurnEntity setWinningUser(final UserEntity winningUser) {
        this.winningUser = winningUser;
        return this;
    }

    /**
     * @return Returns the starting player.
     *
     * @author Victor Hargrave
     * @since 1.0.0
     */
    public UserEntity getStartingPlayer() {
        return startingPlayer;
    }

    /**
     * @param startingPlayer The starting player.
     *
     * @return Returns the object for further processing.
     *
     * @author Victor Hargrave
     * @since 1.0.0
     */
    public TurnEntity setStartingPlayer(final UserEntity startingPlayer) {
        this.startingPlayer = startingPlayer;
        return this;
    }

    /**
     * @return Returns the first card.
     *
     * @author Thomas Weber
     * @since 1.0.0
     */
    public CardEntity getCardOne() {
        return cardOne;
    }

    /**
     * @param cardOne The first card.
     *
     * @return Returns the object for further processing.
     *
     * @author Thomas Weber
     * @since 1.0.0
     */
    public TurnEntity setCardOne(final CardEntity cardOne) {
        this.cardOne = cardOne;
        return this;
    }

    /**
     * @return Returns the user who played card one.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    public UserEntity getPlayerCardOne() {
        return playerCardOne;
    }

    /**
     * @return Returns the second card.
     *
     * @author Thomas Weber
     * @since 1.0.0
     */
    public CardEntity getCardTwo() {
        return cardTwo;
    }

    /**
     * @param cardTwo The second card.
     *
     * @return Returns the object for further processing.
     *
     * @author Thomas Weber
     * @since 1.0.0
     */
    public TurnEntity setCardTwo(final CardEntity cardTwo) {
        this.cardTwo = cardTwo;
        return this;
    }

    /**
     * @return Returns the player who played card two.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    public UserEntity getPlayerCardTwo() {
        return playerCardTwo;
    }

    /**
     * @return Returns the third card.
     *
     * @author Thomas Weber
     * @since 1.0.0
     */
    public CardEntity getCardThree() {
        return cardThree;
    }

    /**
     * @param cardThree The third card.
     *
     * @return Returns the object for further processing.
     *
     * @author Thomas Weber
     * @since 1.0.0
     */
    public TurnEntity setCardThree(final CardEntity cardThree) {
        this.cardThree = cardThree;
        return this;
    }

    /**
     * @return Returns the player who played card three.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    public UserEntity getPlayerCardThree() {
        return playerCardThree;
    }

    /**
     * @return Returns the fourth card.
     *
     * @author Thomas Weber
     * @since 1.0.0
     */
    public CardEntity getCardFour() {
        return cardFour;
    }

    /**
     * @param cardFour The fourth card.
     *
     * @return Returns the object for further processing.
     *
     * @author Thomas Weber
     * @since 1.0.0
     */
    public TurnEntity setCardFour(final CardEntity cardFour) {
        this.cardFour = cardFour;
        return this;
    }

    /**
     * @return Returns the player who played card four.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    public UserEntity getPlayerCardFour() {
        return playerCardFour;
    }

    /**
     * @return Return all cards of the current turn in an array.
     *
     * @author Victor Hargrave
     * @since 1.0.0
     */
    public ArrayList<CardEntity> getCards() {
        ArrayList<CardEntity> cards = new ArrayList<>();
        if (cardOne != null) {
            cards.add(cardOne);
        }
        if (cardTwo != null) {
            cards.add(cardTwo);
        }
        if (cardThree != null) {
            cards.add(cardThree);
        }
        if (cardFour != null) {
            cards.add(cardFour);
        }
        return cards;
    }

    /**
     * @param card Add next card to the turn.
     * @param user The user who played the card.
     *
     * @author Victor Hargrave
     * @since 1.0.0
     */
    public void addCard(final CardEntity card, final UserEntity user) {
        if (cardOne == null) {
            cardOne = card;
            playerCardOne = user;
        } else if (cardTwo == null) {
            cardTwo = card;
            playerCardTwo = user;
        } else if (cardThree == null) {
            cardThree = card;
            playerCardThree = user;
        } else if (cardFour == null) {
            cardFour = card;
            playerCardFour = user;
        }
    }
}
