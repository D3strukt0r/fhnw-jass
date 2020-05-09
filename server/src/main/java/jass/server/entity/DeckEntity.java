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
     * Boolean value if the first card has been played
     */
    @DatabaseField(defaultValue = "false", canBeNull = false)
    private Boolean cardOneHasBeenPlayed;

    /**
     * Card two.
     */
    @DatabaseField(foreign = true)
    private CardEntity cardTwo;


    /**
     * Boolean value if the second card has been played
     */
    @DatabaseField(defaultValue = "false", canBeNull = false)
    private Boolean cardTwoHasBeenPlayed;

    /**
     * Card three.
     */
    @DatabaseField(foreign = true)
    private CardEntity cardThree;

    /**
     * Boolean value if the third card has been played
     */
    @DatabaseField(defaultValue = "false", canBeNull = false)
    private Boolean cardThreeHasBeenPlayed;

    /**
     * Card four.
     */
    @DatabaseField(foreign = true)
    private CardEntity cardFour;


    /**
     * Boolean value if the fourth card has been played
     */
    @DatabaseField(defaultValue = "false", canBeNull = false)
    private Boolean cardFourHasBeenPlayed;

    /**
     * Card five.
     */
    @DatabaseField(foreign = true)
    private CardEntity cardFive;

    /**
     * Boolean value if the fith card has been played
     */
    @DatabaseField(defaultValue = "false", canBeNull = false)
    private Boolean cardFiveHasBeenPlayed;

    /**
     * Card six.
     */
    @DatabaseField(foreign = true)
    private CardEntity cardSix;

    /**
     * Boolean value if the sixth card has been played
     */
    @DatabaseField(defaultValue = "false", canBeNull = false)
    private Boolean cardSixHasBeenPlayed;


    /**
     * Card seven.
     */
    @DatabaseField(foreign = true)
    private CardEntity cardSeven;

    /**
     * Boolean value if the seventh card has been played
     */
    @DatabaseField(defaultValue = "false", canBeNull = false)
    private Boolean cardSevenHasBeenPlayed;


    /**
     * Card eight.
     */
    @DatabaseField(foreign = true)
    private CardEntity cardEight;

    /**
     * Boolean value if the eigth card has been played
     */
    @DatabaseField(defaultValue = "false", canBeNull = false)
    private Boolean cardEightHasBeenPlayed;


    /**
     * Card nine.
     */
    @DatabaseField(foreign = true)
    private CardEntity cardNine;

    /**
     * Boolean value if the ninth card has been played
     */
    @DatabaseField(defaultValue = "false", canBeNull = false)
    private Boolean cardNineHasBeenPlayed;


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
     * @return Returns boolean value if the first card has been played.
     */
    public boolean getCardOneHasBeenPlayed() { return cardOneHasBeenPlayed; }

    /**
     * @param cardOneHasBeenPlayed The first card has been played boolean.
     */
    public void setCardOneHasBeenPlayed(Boolean cardOneHasBeenPlayed) { this.cardOneHasBeenPlayed = cardOneHasBeenPlayed; }

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
     * @return Returns boolean value if the second card has been played.
     */
    public boolean getCardTwoHasBeenPlayed() { return cardTwoHasBeenPlayed; }

    /**
     * @param cardTwoHasBeenPlayed The second card has been played boolean.
     */
    public void setCardTwoHasBeenPlayed(Boolean cardTwoHasBeenPlayed) { this.cardTwoHasBeenPlayed = cardTwoHasBeenPlayed; }

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
     * @return Returns boolean value if the third card has been played.
     */
    public boolean getCardThreeHasBeenPlayed() { return cardThreeHasBeenPlayed; }

    /**
     * @param cardThreeHasBeenPlayed The third card has been played boolean.
     */
    public void setCardThreeHasBeenPlayed(Boolean cardThreeHasBeenPlayed) { this.cardThreeHasBeenPlayed = cardThreeHasBeenPlayed; }


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
     * @return Returns boolean value if the fourth card has been played.
     */
    public boolean getCardFourHasBeenPlayed() { return cardFourHasBeenPlayed; }

    /**
     * @param cardFourHasBeenPlayed The fourth card has been played boolean.
     */
    public void setCardFourHasBeenPlayed(Boolean cardFourHasBeenPlayed) { this.cardFourHasBeenPlayed = cardFourHasBeenPlayed; }

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
     * @return Returns boolean value if the fifth card has been played.
     */
    public boolean getCardFiveHasBeenPlayed() { return cardFiveHasBeenPlayed; }

    /**
     * @param cardFiveHasBeenPlayed The fourth card has been played boolean.
     */
    public void setCardFiveHasBeenPlayed(Boolean cardFiveHasBeenPlayed) { this.cardFiveHasBeenPlayed = cardFiveHasBeenPlayed; }


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
     * @return Returns boolean value if the sixth card has been played.
     */
    public boolean getCardSixHasBeenPlayed() { return cardSixHasBeenPlayed; }

    /**
     * @param cardSixHasBeenPlayed The sixth card has been played boolean.
     */
    public void setCardSixHasBeenPlayed(Boolean cardSixHasBeenPlayed) { this.cardSixHasBeenPlayed = cardSixHasBeenPlayed; }


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
     * @return Returns boolean value if the seventh card has been played.
     */
    public boolean getCardSevenHasBeenPlayed() { return cardSevenHasBeenPlayed; }

    /**
     * @param cardSevenHasBeenPlayed The sixth card has been played boolean.
     */
    public void setCardSevenHasBeenPlayed(Boolean cardSevenHasBeenPlayed) { this.cardSevenHasBeenPlayed = cardSevenHasBeenPlayed; }


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
     * @return Returns boolean value if the eigth card has been played.
     */
    public boolean getCardEightHasBeenPlayed() { return cardEightHasBeenPlayed; }

    /**
     * @param cardEightHasBeenPlayed The sixth card has been played boolean.
     */
    public void setCardEightHasBeenPlayed(Boolean cardEightHasBeenPlayed) { this.cardEightHasBeenPlayed = cardEightHasBeenPlayed; }


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
     * @return Returns boolean value if the ninth card has been played.
     */
    public boolean getCardNineHasBeenPlayed() { return cardNineHasBeenPlayed; }

    /**
     * @param cardNineHasBeenPlayed The sixth card has been played boolean.
     */
    public void setCardNineHasBeenPlayed(Boolean cardNineHasBeenPlayed) { this.cardNineHasBeenPlayed = cardNineHasBeenPlayed; }

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

    /**
     * @return Returns booleans if cards were played as an array.
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

    public void setPlayedCard(int cardId) {
        if (cardId == cardOne.getId()) {
            cardOneHasBeenPlayed = true;
        }
        else if (cardId == cardTwo.getId()) {
            cardTwoHasBeenPlayed = true;
        }
        else if (cardId == cardThree.getId()) {
            cardThreeHasBeenPlayed = true;
        }
        else if (cardId == cardFour.getId()) {
            cardFourHasBeenPlayed = true;
        }
        else if (cardId == cardFive.getId()) {
            cardFiveHasBeenPlayed = true;
        }
        else if (cardId == cardSix.getId()) {
            cardSixHasBeenPlayed = true;
        }
        else if (cardId == cardSeven.getId()) {
            cardSevenHasBeenPlayed = true;
        }
        else if (cardId == cardEight.getId()) {
            cardEightHasBeenPlayed = true;
        }
        else if (cardId == cardNine.getId()) {
            cardNineHasBeenPlayed = true;
        }
    }


}
