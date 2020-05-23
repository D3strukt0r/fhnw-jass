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
 * A model with all known (and cached) teams.
 *
 * @author Victor Hargrave & Thomas Weber
 * @version %I%, %G%
 * @since 1.0.0
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
     * Boolean value if the first card has been played.
     */
    @DatabaseField(defaultValue = "false", canBeNull = false)
    private boolean cardOneHasBeenPlayed;

    /**
     * Card two.
     */
    @DatabaseField(foreign = true)
    private CardEntity cardTwo;

    /**
     * Boolean value if the second card has been played.
     */
    @DatabaseField(defaultValue = "false", canBeNull = false)
    private boolean cardTwoHasBeenPlayed;

    /**
     * Card three.
     */
    @DatabaseField(foreign = true)
    private CardEntity cardThree;

    /**
     * Boolean value if the third card has been played.
     */
    @DatabaseField(defaultValue = "false", canBeNull = false)
    private boolean cardThreeHasBeenPlayed;

    /**
     * Card four.
     */
    @DatabaseField(foreign = true)
    private CardEntity cardFour;

    /**
     * Boolean value if the fourth card has been played.
     */
    @DatabaseField(defaultValue = "false", canBeNull = false)
    private boolean cardFourHasBeenPlayed;

    /**
     * Card five.
     */
    @DatabaseField(foreign = true)
    private CardEntity cardFive;

    /**
     * Boolean value if the fifth card has been played.
     */
    @DatabaseField(defaultValue = "false", canBeNull = false)
    private boolean cardFiveHasBeenPlayed;

    /**
     * Card six.
     */
    @DatabaseField(foreign = true)
    private CardEntity cardSix;

    /**
     * Boolean value if the sixth card has been played.
     */
    @DatabaseField(defaultValue = "false", canBeNull = false)
    private boolean cardSixHasBeenPlayed;

    /**
     * Card seven.
     */
    @DatabaseField(foreign = true)
    private CardEntity cardSeven;

    /**
     * Boolean value if the seventh card has been played.
     */
    @DatabaseField(defaultValue = "false", canBeNull = false)
    private boolean cardSevenHasBeenPlayed;

    /**
     * Card eight.
     */
    @DatabaseField(foreign = true)
    private CardEntity cardEight;

    /**
     * Boolean value if the eight card has been played.
     */
    @DatabaseField(defaultValue = "false", canBeNull = false)
    private boolean cardEightHasBeenPlayed;

    /**
     * Card nine.
     */
    @DatabaseField(foreign = true)
    private CardEntity cardNine;

    /**
     * Boolean value if the ninth card has been played.
     */
    @DatabaseField(defaultValue = "false", canBeNull = false)
    private boolean cardNineHasBeenPlayed;

    /**
     * For ORMLite all persisted classes must define a no-arg constructor with
     * at least package visibility.
     *
     * @author Victor Hargrave
     * @since 1.0.0
     */
    public DeckEntity() {
    }

    /**
     * @return Returns the ID.
     *
     * @author Victor Hargrave
     * @since 1.0.0
     */
    public int getId() {
        return id;
    }

    /**
     * @return Returns the player
     *
     * @author Victor Hargrave
     * @since 1.0.0
     */
    public UserEntity getPlayer() {
        return player;
    }

    /**
     * @param player The player.
     *
     * @return Returns the object for further processing.
     *
     * @author Victor Hargrave
     * @since 1.0.0
     */
    public DeckEntity setPlayer(final UserEntity player) {
        this.player = player;
        return this;
    }

    /**
     * @return Returns the round.
     *
     * @author Victor Hargrave
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
     * @author Victor Hargrave
     * @since 1.0.0
     */
    public DeckEntity setRound(final RoundEntity round) {
        this.round = round;
        return this;
    }

    /**
     * @return Returns card one.
     *
     * @author Victor Hargrave
     * @since 1.0.0
     */
    public CardEntity getCardOne() {
        return cardOne;
    }

    /**
     * @param cardOne Card one.
     *
     * @return Returns the object for further processing.
     *
     * @author Victor Hargrave
     * @since 1.0.0
     */
    public DeckEntity setCardOne(final CardEntity cardOne) {
        this.cardOne = cardOne;
        return this;
    }

    /**
     * @return Returns boolean value if the first card has been played.
     *
     * @author Thomas Weber
     * @since 1.0.0
     */
    public boolean getCardOneHasBeenPlayed() {
        return cardOneHasBeenPlayed;
    }

    /**
     * @param cardOneHasBeenPlayed The first card has been played boolean.
     *
     * @author Thomas Weber
     * @since 1.0.0
     */
    public void setCardOneHasBeenPlayed(final boolean cardOneHasBeenPlayed) {
        this.cardOneHasBeenPlayed = cardOneHasBeenPlayed;
    }

    /**
     * @return Returns card two.
     *
     * @author Victor Hargrave
     * @since 1.0.0
     */
    public CardEntity getCardTwo() {
        return cardTwo;
    }

    /**
     * @param cardTwo Card two.
     *
     * @return Returns the object for further processing.
     *
     * @author Victor Hargrave
     * @since 1.0.0
     */
    public DeckEntity setCardTwo(final CardEntity cardTwo) {
        this.cardTwo = cardTwo;
        return this;
    }

    /**
     * @return Returns boolean value if the second card has been played.
     *
     * @author Thomas Weber
     * @since 1.0.0
     */
    public boolean getCardTwoHasBeenPlayed() {
        return cardTwoHasBeenPlayed;
    }

    /**
     * @param cardTwoHasBeenPlayed The second card has been played boolean.
     *
     * @author Thomas Weber
     * @since 1.0.0
     */
    public void setCardTwoHasBeenPlayed(final boolean cardTwoHasBeenPlayed) {
        this.cardTwoHasBeenPlayed = cardTwoHasBeenPlayed;
    }

    /**
     * @return Returns card three.
     *
     * @author Victor Hargrave
     * @since 1.0.0
     */
    public CardEntity getCardThree() {
        return cardThree;
    }

    /**
     * @param cardThree Card three.
     *
     * @return Returns the object for further processing.
     *
     * @author Victor Hargrave
     * @since 1.0.0
     */
    public DeckEntity setCardThree(final CardEntity cardThree) {
        this.cardThree = cardThree;
        return this;
    }

    /**
     * @return Returns boolean value if the third card has been played.
     *
     * @author Thomas Weber
     * @since 1.0.0
     */
    public boolean getCardThreeHasBeenPlayed() {
        return cardThreeHasBeenPlayed;
    }

    /**
     * @param cardThreeHasBeenPlayed The third card has been played boolean.
     *
     * @author Thomas Weber
     * @since 1.0.0
     */
    public void setCardThreeHasBeenPlayed(final boolean cardThreeHasBeenPlayed) {
        this.cardThreeHasBeenPlayed = cardThreeHasBeenPlayed;
    }


    /**
     * @return Returns card four.
     *
     * @author Victor Hargrave
     * @since 1.0.0
     */
    public CardEntity getCardFour() {
        return cardFour;
    }

    /**
     * @param cardFour Card four.
     *
     * @return Returns the object for further processing.
     *
     * @author Victor Hargrave
     * @since 1.0.0
     */
    public DeckEntity setCardFour(final CardEntity cardFour) {
        this.cardFour = cardFour;
        return this;
    }

    /**
     * @return Returns boolean value if the fourth card has been played.
     *
     * @author Thomas Weber
     * @since 1.0.0
     */
    public boolean getCardFourHasBeenPlayed() {
        return cardFourHasBeenPlayed;
    }

    /**
     * @param cardFourHasBeenPlayed The fourth card has been played boolean.
     *
     * @author Thomas Weber
     * @since 1.0.0
     */
    public void setCardFourHasBeenPlayed(final boolean cardFourHasBeenPlayed) {
        this.cardFourHasBeenPlayed = cardFourHasBeenPlayed;
    }

    /**
     * @return Returns card five.
     *
     * @author Victor Hargrave
     * @since 1.0.0
     */
    public CardEntity getCardFive() {
        return cardFive;
    }

    /**
     * @param cardFive Card five.
     *
     * @return Returns the object for further processing.
     *
     * @author Victor Hargrave
     * @since 1.0.0
     */
    public DeckEntity setCardFive(final CardEntity cardFive) {
        this.cardFive = cardFive;
        return this;
    }

    /**
     * @return Returns boolean value if the fifth card has been played.
     *
     * @author Thomas Weber
     * @since 1.0.0
     */
    public boolean getCardFiveHasBeenPlayed() {
        return cardFiveHasBeenPlayed;
    }

    /**
     * @param cardFiveHasBeenPlayed The fourth card has been played boolean.
     *
     * @author Thomas Weber
     * @since 1.0.0
     */
    public void setCardFiveHasBeenPlayed(final boolean cardFiveHasBeenPlayed) {
        this.cardFiveHasBeenPlayed = cardFiveHasBeenPlayed;
    }


    /**
     * @return Returns card six.
     *
     * @author Victor Hargrave
     * @since 1.0.0
     */
    public CardEntity getCardSix() {
        return cardSix;
    }

    /**
     * @param cardSix Card six.
     *
     * @return Returns the object for further processing.
     *
     * @author Victor Hargrave
     * @since 1.0.0
     */
    public DeckEntity setCardSix(final CardEntity cardSix) {
        this.cardSix = cardSix;
        return this;
    }

    /**
     * @return Returns boolean value if the sixth card has been played.
     *
     * @author Thomas Weber
     * @since 1.0.0
     */
    public boolean getCardSixHasBeenPlayed() {
        return cardSixHasBeenPlayed;
    }

    /**
     * @param cardSixHasBeenPlayed The sixth card has been played boolean.
     *
     * @author Thomas Weber
     * @since 1.0.0
     */
    public void setCardSixHasBeenPlayed(final boolean cardSixHasBeenPlayed) {
        this.cardSixHasBeenPlayed = cardSixHasBeenPlayed;
    }


    /**
     * @return Returns card seven.
     *
     * @author Victor Hargrave
     * @since 1.0.0
     */
    public CardEntity getCardSeven() {
        return cardSeven;
    }

    /**
     * @param cardSeven Card seven.
     *
     * @return Returns the object for further processing.
     *
     * @author Victor Hargrave
     * @since 1.0.0
     */
    public DeckEntity setCardSeven(final CardEntity cardSeven) {
        this.cardSeven = cardSeven;
        return this;
    }

    /**
     * @return Returns boolean value if the seventh card has been played.
     *
     * @author Thomas Weber
     * @since 1.0.0
     */
    public boolean getCardSevenHasBeenPlayed() {
        return cardSevenHasBeenPlayed;
    }

    /**
     * @param cardSevenHasBeenPlayed The sixth card has been played boolean.
     *
     * @author Thomas Weber
     * @since 1.0.0
     */
    public void setCardSevenHasBeenPlayed(final boolean cardSevenHasBeenPlayed) {
        this.cardSevenHasBeenPlayed = cardSevenHasBeenPlayed;
    }


    /**
     * @return Returns card eight.
     *
     * @author Victor Hargrave
     * @since 1.0.0
     */
    public CardEntity getCardEight() {
        return cardEight;
    }

    /**
     * @param cardEight Card eight.
     *
     * @return Returns the object for further processing.
     *
     * @author Victor Hargrave
     * @since 1.0.0
     */
    public DeckEntity setCardEight(final CardEntity cardEight) {
        this.cardEight = cardEight;
        return this;
    }

    /**
     * @return Returns boolean value if the eight card has been played.
     *
     * @author Thomas Weber
     * @since 1.0.0
     */
    public boolean getCardEightHasBeenPlayed() {
        return cardEightHasBeenPlayed;
    }

    /**
     * @param cardEightHasBeenPlayed The sixth card has been played boolean.
     *
     * @author Thomas Weber
     * @since 1.0.0
     */
    public void setCardEightHasBeenPlayed(final boolean cardEightHasBeenPlayed) {
        this.cardEightHasBeenPlayed = cardEightHasBeenPlayed;
    }


    /**
     * @return Returns card nine.
     *
     * @author Victor Hargrave
     * @since 1.0.0
     */
    public CardEntity getCardNine() {
        return cardNine;
    }

    /**
     * @param cardNine Card nine.
     *
     * @return Returns the object for further processing.
     *
     * @author Victor Hargrave
     * @since 1.0.0
     */
    public DeckEntity setCardNine(final CardEntity cardNine) {
        this.cardNine = cardNine;
        return this;
    }

    /**
     * @return Returns boolean value if the ninth card has been played.
     *
     * @author Thomas Weber
     * @since 1.0.0
     */
    public boolean getCardNineHasBeenPlayed() {
        return cardNineHasBeenPlayed;
    }

    /**
     * @param cardNineHasBeenPlayed The sixth card has been played boolean.
     *
     * @author Thomas Weber
     * @since 1.0.0
     */
    public void setCardNineHasBeenPlayed(final boolean cardNineHasBeenPlayed) {
        this.cardNineHasBeenPlayed = cardNineHasBeenPlayed;
    }

    /**
     * @return Returns the cards as an array.
     *
     * @author Victor Hargrave
     * @since 1.0.0
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

    /**
     * @return Returns booleans if cards were played as an array.
     *
     * @author Thomas Weber
     * @since 1.0.0
     */
    public ArrayList<Boolean> getCardsHaveBeenPlayed() {
        ArrayList<Boolean> cardsHaveBeenPlayed = new ArrayList<>();
        cardsHaveBeenPlayed.add(cardOneHasBeenPlayed);
        cardsHaveBeenPlayed.add(cardTwoHasBeenPlayed);
        cardsHaveBeenPlayed.add(cardThreeHasBeenPlayed);
        cardsHaveBeenPlayed.add(cardFourHasBeenPlayed);
        cardsHaveBeenPlayed.add(cardFiveHasBeenPlayed);
        cardsHaveBeenPlayed.add(cardSixHasBeenPlayed);
        cardsHaveBeenPlayed.add(cardSevenHasBeenPlayed);
        cardsHaveBeenPlayed.add(cardEightHasBeenPlayed);
        cardsHaveBeenPlayed.add(cardNineHasBeenPlayed);
        return cardsHaveBeenPlayed;
    }

    /**
     * @param cardId Set the given card as played.
     *
     * @author Victor Hargrave
     * @since 1.0.0
     */
    public void setPlayedCard(final int cardId) {
        if (cardId == cardOne.getId()) {
            cardOneHasBeenPlayed = true;
        } else if (cardId == cardTwo.getId()) {
            cardTwoHasBeenPlayed = true;
        } else if (cardId == cardThree.getId()) {
            cardThreeHasBeenPlayed = true;
        } else if (cardId == cardFour.getId()) {
            cardFourHasBeenPlayed = true;
        } else if (cardId == cardFive.getId()) {
            cardFiveHasBeenPlayed = true;
        } else if (cardId == cardSix.getId()) {
            cardSixHasBeenPlayed = true;
        } else if (cardId == cardSeven.getId()) {
            cardSevenHasBeenPlayed = true;
        } else if (cardId == cardEight.getId()) {
            cardEightHasBeenPlayed = true;
        } else if (cardId == cardNine.getId()) {
            cardNineHasBeenPlayed = true;
        }
    }
}
