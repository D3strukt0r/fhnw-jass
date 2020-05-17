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

package jass.server.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import jass.lib.Card;
import jass.lib.GameMode;
import jass.server.entity.CardEntity;
import jass.server.entity.DeckEntity;
import jass.server.entity.RankEntity;
import jass.server.entity.RoundEntity;
import jass.server.entity.SuitEntity;
import org.junit.Before;
import org.junit.Test;

/**
 * Test Class for the validation of the different game modes.
 * <p>
 * The card id's with the corresponding suit and rank values are copied at the
 * bottom of this document as an assistance.
 *
 * @author Thomas Weber, Manuele Vaccari, Sasa Trajkova
 * @version %I%, %G%
 * @since 0.0.1
 */
public final class GameUtilTest {
    /*
     * Card Id: 0  | Rank: 6     | Suit: hearts
     * Card Id: 1  | Rank: 6     | Suit: hearts
     * Card Id: 2  | Rank: 7     | Suit: hearts
     * Card Id: 3  | Rank: 8     | Suit: hearts
     * Card Id: 4  | Rank: 9     | Suit: hearts
     * Card Id: 5  | Rank: 10    | Suit: hearts
     * Card Id: 6  | Rank: jack  | Suit: hearts
     * Card Id: 7  | Rank: queen | Suit: hearts
     * Card Id: 8  | Rank: king  | Suit: hearts
     * Card Id: 9  | Rank: ace   | Suit: hearts
     * Card Id: 10 | Rank: 6     | Suit: diamonds
     * Card Id: 11 | Rank: 6     | Suit: diamonds
     * Card Id: 12 | Rank: 7     | Suit: diamonds
     * Card Id: 13 | Rank: 8     | Suit: diamonds
     * Card Id: 14 | Rank: 9     | Suit: diamonds
     * Card Id: 15 | Rank: 10    | Suit: diamonds
     * Card Id: 16 | Rank: jack  | Suit: diamonds
     * Card Id: 17 | Rank: queen | Suit: diamonds
     * Card Id: 18 | Rank: king  | Suit: diamonds
     * Card Id: 19 | Rank: ace   | Suit: diamonds
     * Card Id: 20 | Rank: 6     | Suit: spades
     * Card Id: 21 | Rank: 6     | Suit: spades
     * Card Id: 22 | Rank: 7     | Suit: spades
     * Card Id: 23 | Rank: 8     | Suit: spades
     * Card Id: 24 | Rank: 9     | Suit: spades
     * Card Id: 25 | Rank: 10    | Suit: spades
     * Card Id: 26 | Rank: jack  | Suit: spades
     * Card Id: 27 | Rank: queen | Suit: spades
     * Card Id: 28 | Rank: king  | Suit: spades
     * Card Id: 29 | Rank: ace   | Suit: spades
     * Card Id: 30 | Rank: 6     | Suit: clubs
     * Card Id: 31 | Rank: 6     | Suit: clubs
     * Card Id: 32 | Rank: 7     | Suit: clubs
     * Card Id: 33 | Rank: 8     | Suit: clubs
     * Card Id: 34 | Rank: 9     | Suit: clubs
     * Card Id: 35 | Rank: 10    | Suit: clubs
     * Card Id: 36 | Rank: jack  | Suit: clubs
     * Card Id: 37 | Rank: queen | Suit: clubs
     * Card Id: 38 | Rank: king  | Suit: clubs
     * Card Id: 39 | Rank: ace   | Suit: clubs
     */

    /**
     * All ranks.
     */
    private static ArrayList<RankEntity> ranks = new ArrayList<>();

    /**
     * All suits.
     */
    private static ArrayList<SuitEntity> suits = new ArrayList<>();

    /**
     * All cards.
     */
    private static ArrayList<CardEntity> cards = new ArrayList<>();

    /**
     * Generates all cards before testing.
     *
     * @author Thomas Weber
     */
    @Before
    public void createTestData() {
        ranks = insertRankSeedData();
        suits = insertSuitSeedData();
        cards = insertCardSeedData();
    }

    /**
     * Generate Rank Data.
     *
     * @author Thomas Weber
     */
    private ArrayList<RankEntity> insertRankSeedData() {
        ArrayList<RankEntity> createdRanks = new ArrayList<>();
        createdRanks.add(new RankEntity().setId(1).setKey("6").setPointsTrumpf(0).setPointsObeAbe(0).setPointsOndeufe(11));
        createdRanks.add(new RankEntity().setId(2).setKey("7").setPointsTrumpf(0).setPointsObeAbe(0).setPointsOndeufe(0));
        createdRanks.add(new RankEntity().setId(3).setKey("8").setPointsTrumpf(0).setPointsObeAbe(8).setPointsOndeufe(8));
        createdRanks.add(new RankEntity().setId(4).setKey("9").setPointsTrumpf(0).setPointsObeAbe(0).setPointsOndeufe(0));
        createdRanks.add(new RankEntity().setId(5).setKey("10").setPointsTrumpf(10).setPointsObeAbe(10).setPointsOndeufe(10));
        createdRanks.add(new RankEntity().setId(6).setKey("jack").setPointsTrumpf(2).setPointsObeAbe(2).setPointsOndeufe(2));
        createdRanks.add(new RankEntity().setId(7).setKey("queen").setPointsTrumpf(3).setPointsObeAbe(3).setPointsOndeufe(3));
        createdRanks.add(new RankEntity().setId(8).setKey("king").setPointsTrumpf(4).setPointsObeAbe(4).setPointsOndeufe(4));
        createdRanks.add(new RankEntity().setId(9).setKey("ace").setPointsTrumpf(11).setPointsObeAbe(11).setPointsOndeufe(0));
        return createdRanks;
    }

    /**
     * Generate Suit Data.
     *
     * @author Thomas Weber
     */
    private ArrayList<SuitEntity> insertSuitSeedData() {
        ArrayList<SuitEntity> createdSuits = new ArrayList<>();
        createdSuits.add((new SuitEntity()).setId(1).setKey("hearts"));
        createdSuits.add((new SuitEntity()).setId(2).setKey("diamonds"));
        createdSuits.add((new SuitEntity()).setId(3).setKey("spades"));
        createdSuits.add((new SuitEntity()).setId(4).setKey("clubs"));
        return createdSuits;
    }

    /**
     * Generate Card Data.
     *
     * @author Thomas Weber
     */
    private ArrayList<CardEntity> insertCardSeedData() {
        ArrayList<CardEntity> createdCards = new ArrayList<>();
        int addend = 0;
        for (int i = 0; i <= 3; i++) {
            for (int j = 0; j <= 8; j++) {
                createdCards.add((new CardEntity())
                    .setId(j + addend)
                    .setRank(ranks.get(j))
                    .setSuit(suits.get(i))
                );
                // Comment out following 4 lines to remove print of cards into
                // console.
                //int id = j + addend;
                //System.out.printf("Card Id: " + id + "\t\t");
                //System.out.printf(" Rank: " + ranks.get(j).getKey() + "\t\t");
                //System.out.println(" Suit: " + suits.get(i).getKey());

            }
            addend += 9;
        }
        return createdCards;
    }

    /*
     * - Game Mode Trump
     * - Trump Suite Hearts
     * - First turn of round is Heart 6
     * - The player only has one card with suit heart which is the jack.
     * - The player plays an Ace of spades.
     * - Assertion is that this move is true/valid, as you are never forced to
     *   play the jack.
     */

    /**
     * @author Thomas Weber
     */
    @Test
    public void testValidateMoveTrump1() {
        RoundEntity round = (new RoundEntity())
            .setGameMode(GameMode.TRUMPF)
            .setTrumpfSuit(Card.Suit.Hearts);
        DeckEntity deck = (new DeckEntity()).setRound(round);

        deck.setCardOne(cards.get(10));
        deck.setCardOneHasBeenPlayed(false);
        deck.setCardTwo(cards.get(11));
        deck.setCardTwoHasBeenPlayed(false);
        deck.setCardThree(cards.get(25));
        deck.setCardThreeHasBeenPlayed(false);
        deck.setCardFour(cards.get(26));
        deck.setCardFourHasBeenPlayed(true);
        deck.setCardFive(cards.get(27));
        deck.setCardFiveHasBeenPlayed(true);
        deck.setCardSix(cards.get(28));
        deck.setCardSixHasBeenPlayed(false);
        deck.setCardSeven(cards.get(26));
        deck.setCardSevenHasBeenPlayed(false);
        deck.setCardEight(cards.get(30));
        deck.setCardEightHasBeenPlayed(true);
        deck.setCardNine(cards.get(5));
        deck.setCardNineHasBeenPlayed(false);

        CardEntity firstCardOfTurn = cards.get(0);
        CardEntity playedCard = deck.getCardSeven();

        assertTrue(GameUtil.validateMoveTrump(playedCard, deck, firstCardOfTurn, String.valueOf(round.getTrumpfSuit())));
    }

    /*
     * Test two:
     * - Game Mode Trump
     * - Trump Suite Clubs
     * - First turn of round is queen of Spades
     * - The player had two cards with suit spade in his hand but has already
     *   played both before this turn
     * - The player plays an Ace of hearts.
     * - Assertion is that this move is true/valid, as he has already played out
     *   his spades.
     */

    /**
     * @author Thomas Weber
     */
    @Test
    public void testValidateMoveTrump2() {
        RoundEntity round = (new RoundEntity())
            .setGameMode(GameMode.TRUMPF)
            .setTrumpfSuit(Card.Suit.Clubs);
        DeckEntity deck = (new DeckEntity()).setRound(round);

        deck.setCardOne(cards.get(20));
        deck.setCardOneHasBeenPlayed(true);
        deck.setCardTwo(cards.get(23));
        deck.setCardTwoHasBeenPlayed(true);
        deck.setCardThree(cards.get(9));
        deck.setCardThreeHasBeenPlayed(false);
        deck.setCardFour(cards.get(30));
        deck.setCardFourHasBeenPlayed(false);
        deck.setCardFive(cards.get(31));
        deck.setCardFiveHasBeenPlayed(false);
        deck.setCardSix(cards.get(32));
        deck.setCardSixHasBeenPlayed(false);
        deck.setCardSeven(cards.get(33));
        deck.setCardSevenHasBeenPlayed(false);
        deck.setCardEight(cards.get(34));
        deck.setCardEightHasBeenPlayed(false);
        deck.setCardNine(cards.get(35));
        deck.setCardNineHasBeenPlayed(false);

        CardEntity firstCardOfTurn = cards.get(24);
        CardEntity playedCard = deck.getCardThree();

        assertTrue(GameUtil.validateMoveTrump(playedCard, deck, firstCardOfTurn, String.valueOf(round.getTrumpfSuit())));
    }

    /*
     * - Game Mode Trump
     * - Trump Suite Clubs
     * - First turn of round is queen of Spades
     * - The player has two cards with suit spade in his hand, one already
     *   played & one still in his hands
     * - The player plays an Ace of hearts.
     * - Assertion is that this move is false/invalid, as he must have played
     *   the remaining spade.
     */

    /**
     * @author Thomas Weber
     */
    @Test
    public void testValidateMoveTrump3() {
        RoundEntity round = (new RoundEntity())
            .setGameMode(GameMode.TRUMPF)
            .setTrumpfSuit(Card.Suit.Clubs);
        DeckEntity deck = (new DeckEntity()).setRound(round);

        deck.setCardOne(cards.get(20));
        deck.setCardOneHasBeenPlayed(true);
        deck.setCardTwo(cards.get(23));
        deck.setCardTwoHasBeenPlayed(false);
        deck.setCardThree(cards.get(8));
        deck.setCardThreeHasBeenPlayed(false);
        deck.setCardFour(cards.get(30));
        deck.setCardFourHasBeenPlayed(false);
        deck.setCardFive(cards.get(31));
        deck.setCardFiveHasBeenPlayed(true);
        deck.setCardSix(cards.get(32));
        deck.setCardSixHasBeenPlayed(false);
        deck.setCardSeven(cards.get(33));
        deck.setCardSevenHasBeenPlayed(false);
        deck.setCardEight(cards.get(34));
        deck.setCardEightHasBeenPlayed(false);
        deck.setCardNine(cards.get(35));
        deck.setCardNineHasBeenPlayed(false);

        CardEntity firstCardOfTurn = cards.get(24);
        CardEntity playedCard = deck.getCardThree();

        assertFalse(GameUtil.validateMoveTrump(playedCard, deck, firstCardOfTurn, String.valueOf(round.getTrumpfSuit())));
    }

    /*
     * Test four data:
     * - Game Mode Trump
     * - Trump Suite Clubs
     * - First turn of round is queen of Spades
     * - The player had two cards with suit spade in his hand but has already
     *   played both before this turn
     * - The player plays an Ace of clubs.
     * - Assertion is that this move is true/valid, as clubs are trump.
     */

    /**
     * @author Thomas Weber
     */
    @Test
    public void testValidateMoveTrump4() {
        RoundEntity round = (new RoundEntity())
            .setGameMode(GameMode.TRUMPF)
            .setTrumpfSuit(Card.Suit.Clubs);
        DeckEntity deck = (new DeckEntity()).setRound(round);

        deck.setCardOne(cards.get(20));
        deck.setCardOneHasBeenPlayed(true);
        deck.setCardTwo(cards.get(23));
        deck.setCardTwoHasBeenPlayed(true);
        deck.setCardThree(cards.get(9));
        deck.setCardThreeHasBeenPlayed(false);
        deck.setCardFour(cards.get(30));
        deck.setCardFourHasBeenPlayed(false);
        deck.setCardFive(cards.get(31));
        deck.setCardFiveHasBeenPlayed(true);
        deck.setCardSix(cards.get(32));
        deck.setCardSixHasBeenPlayed(false);
        deck.setCardSeven(cards.get(33));
        deck.setCardSevenHasBeenPlayed(false);
        deck.setCardEight(cards.get(34));
        deck.setCardEightHasBeenPlayed(false);
        deck.setCardNine(cards.get(35));
        deck.setCardNineHasBeenPlayed(false);

        CardEntity firstCardOfTurn = cards.get(24);
        CardEntity playedCard = deck.getCardNine();

        assertTrue(GameUtil.validateMoveTrump(playedCard, deck, firstCardOfTurn, String.valueOf(round.getTrumpfSuit())));
    }

    /*
     * - First turn of round is queen of Spades
     * - The player had two cards with suit spade in his hand but has
     *   already played both before this turn
     * - The player plays an Ace of hearts.
     * - Assertion is that this move is true/valid, as he has already played
     *   out his spades.
     */

    /**
     * @author Manuele Vaccari
     */
    @Test
    public void testValidateMoveObeAbe1() {
        RoundEntity round = (new RoundEntity())
            .setGameMode(GameMode.OBE_ABE);
        DeckEntity deck = (new DeckEntity()).setRound(round);

        deck.setCardOne(cards.get(20));
        deck.setCardOneHasBeenPlayed(true);
        deck.setCardTwo(cards.get(23));
        deck.setCardTwoHasBeenPlayed(true);
        deck.setCardThree(cards.get(9));
        deck.setCardThreeHasBeenPlayed(false);
        deck.setCardFour(cards.get(30));
        deck.setCardFourHasBeenPlayed(false);
        deck.setCardFive(cards.get(31));
        deck.setCardFiveHasBeenPlayed(false);
        deck.setCardSix(cards.get(32));
        deck.setCardSixHasBeenPlayed(false);
        deck.setCardSeven(cards.get(33));
        deck.setCardSevenHasBeenPlayed(false);
        deck.setCardEight(cards.get(34));
        deck.setCardEightHasBeenPlayed(false);
        deck.setCardNine(cards.get(35));
        deck.setCardNineHasBeenPlayed(false);

        CardEntity firstCardOfTurn = cards.get(24);
        CardEntity playedCard = deck.getCardThree();

        assertTrue(GameUtil.validateMoveObeAbe(playedCard, deck, firstCardOfTurn));
    }

    /*
     * - First turn of round is queen of Spades
     * - The player has two cards with suit spade in his hand, one already
     *   played & one still in his hands
     * - The player plays an Ace of hearts.
     * - Assertion is that this move is false/invalid, as he must have
     *   played the remaining spade.
     */

    /**
     * @author Manuele Vaccari
     */
    @Test
    public void testValidateMoveObeAbe2() {
        RoundEntity round = (new RoundEntity())
            .setGameMode(GameMode.OBE_ABE);
        DeckEntity deck = (new DeckEntity()).setRound(round);

        deck.setCardOne(cards.get(20));
        deck.setCardOneHasBeenPlayed(true);
        deck.setCardTwo(cards.get(23));
        deck.setCardTwoHasBeenPlayed(false);
        deck.setCardThree(cards.get(8));
        deck.setCardThreeHasBeenPlayed(false);
        deck.setCardFour(cards.get(30));
        deck.setCardFourHasBeenPlayed(false);
        deck.setCardFive(cards.get(31));
        deck.setCardFiveHasBeenPlayed(true);
        deck.setCardSix(cards.get(32));
        deck.setCardSixHasBeenPlayed(false);
        deck.setCardSeven(cards.get(33));
        deck.setCardSevenHasBeenPlayed(false);
        deck.setCardEight(cards.get(34));
        deck.setCardEightHasBeenPlayed(false);
        deck.setCardNine(cards.get(35));
        deck.setCardNineHasBeenPlayed(false);

        CardEntity firstCardOfTurn = cards.get(24);
        CardEntity playedCard = deck.getCardThree();

        assertFalse(GameUtil.validateMoveObeAbe(playedCard, deck, firstCardOfTurn));
    }

    /*
     * - First turn of round is queen of Spades
     * - The player had two cards with suit spade in his hand but has
     *   already played both before this turn
     * - The player plays an Ace of clubs.
     * - Assertion is that this move is true/valid, as clubs are trump.
     */

    /**
     * @author Manuele Vaccari
     */
    @Test
    public void testValidateMoveObeAbe3() {
        RoundEntity round = (new RoundEntity())
            .setGameMode(GameMode.OBE_ABE);
        DeckEntity deck = (new DeckEntity()).setRound(round);

        deck.setCardOne(cards.get(20));
        deck.setCardOneHasBeenPlayed(true);
        deck.setCardTwo(cards.get(23));
        deck.setCardTwoHasBeenPlayed(true);
        deck.setCardThree(cards.get(9));
        deck.setCardThreeHasBeenPlayed(false);
        deck.setCardFour(cards.get(30));
        deck.setCardFourHasBeenPlayed(false);
        deck.setCardFive(cards.get(31));
        deck.setCardFiveHasBeenPlayed(true);
        deck.setCardSix(cards.get(32));
        deck.setCardSixHasBeenPlayed(false);
        deck.setCardSeven(cards.get(33));
        deck.setCardSevenHasBeenPlayed(false);
        deck.setCardEight(cards.get(34));
        deck.setCardEightHasBeenPlayed(false);
        deck.setCardNine(cards.get(35));
        deck.setCardNineHasBeenPlayed(false);

        CardEntity firstCardOfTurn = cards.get(24);
        CardEntity playedCard = deck.getCardNine();

        assertTrue(GameUtil.validateMoveObeAbe(playedCard, deck, firstCardOfTurn));
    }

    /*
     * - First turn of round is queen of Spades
     * - The player had two cards with suit spade in his hand but has
     *   already played both before this turn
     * - The player plays an Ace of hearts.
     * - Assertion is that this move is true/valid, as he has already played
     *   out his spades.
     */

    /**
     * @author Sasa Trajkova
     */
    @Test
    public void testValidateMoveOndeUfe1() {
        RoundEntity round = (new RoundEntity())
            .setGameMode(GameMode.ONDE_UFE);
        DeckEntity deck = (new DeckEntity()).setRound(round);

        deck.setCardOne(cards.get(20));
        deck.setCardOneHasBeenPlayed(true);
        deck.setCardTwo(cards.get(23));
        deck.setCardTwoHasBeenPlayed(true);
        deck.setCardThree(cards.get(9));
        deck.setCardThreeHasBeenPlayed(false);
        deck.setCardFour(cards.get(30));
        deck.setCardFive(cards.get(31));
        deck.setCardFiveHasBeenPlayed(false);
        deck.setCardSix(cards.get(32));
        deck.setCardSixHasBeenPlayed(false);
        deck.setCardSeven(cards.get(33));
        deck.setCardSevenHasBeenPlayed(false);
        deck.setCardEight(cards.get(34));
        deck.setCardEightHasBeenPlayed(false);
        deck.setCardNine(cards.get(35));
        deck.setCardNineHasBeenPlayed(false);

        CardEntity firstCardOfTurn = cards.get(24);
        CardEntity playedCard = deck.getCardThree();

        assertTrue(GameUtil.validateMoveOndeUfe(playedCard, deck, firstCardOfTurn));
    }

    /*
     * - First turn of round is queen of Spades
     * - The player has two cards with suit spade in his hand, one already
     *   played & one still in his hands
     * - The player plays an Ace of hearts.
     * - Assertion is that this move is false/invalid, as he must have
     *   played the remaining spade.
     */

    /**
     * @author Sasa Trajkova
     */
    @Test
    public void testValidateMoveOndeUfe2() {
        RoundEntity round = (new RoundEntity())
            .setGameMode(GameMode.ONDE_UFE);
        DeckEntity deck = (new DeckEntity()).setRound(round);

        deck.setCardOne(cards.get(20));
        deck.setCardOneHasBeenPlayed(true);
        deck.setCardTwo(cards.get(23));
        deck.setCardTwoHasBeenPlayed(false);
        deck.setCardThree(cards.get(8));
        deck.setCardThreeHasBeenPlayed(false);
        deck.setCardFour(cards.get(30));
        deck.setCardFourHasBeenPlayed(false);
        deck.setCardFive(cards.get(31));
        deck.setCardFiveHasBeenPlayed(true);
        deck.setCardSix(cards.get(32));
        deck.setCardSixHasBeenPlayed(false);
        deck.setCardSeven(cards.get(33));
        deck.setCardSevenHasBeenPlayed(false);
        deck.setCardEight(cards.get(34));
        deck.setCardEightHasBeenPlayed(false);
        deck.setCardNine(cards.get(35));
        deck.setCardNineHasBeenPlayed(false);

        CardEntity firstCardOfTurn = cards.get(24);
        CardEntity playedCard = deck.getCardThree();

        assertFalse(GameUtil.validateMoveOndeUfe(playedCard, deck, firstCardOfTurn));
    }

    /*
     * - First turn of round is queen of Spades
     * - The player had two cards with suit spade in his hand but has
     *   already played both before this turn
     * - The player plays an Ace of clubs.
     * - Assertion is that this move is true/valid, as clubs are trump.
     */

    /**
     * @author Sasa Trajkova
     */
    @Test
    public void testValidateMoveOndeUfe3() {
        RoundEntity round = (new RoundEntity())
            .setGameMode(GameMode.ONDE_UFE);
        DeckEntity deck = (new DeckEntity()).setRound(round);

        deck.setCardOne(cards.get(20));
        deck.setCardOneHasBeenPlayed(true);
        deck.setCardTwo(cards.get(23));
        deck.setCardTwoHasBeenPlayed(true);
        deck.setCardThree(cards.get(9));
        deck.setCardThreeHasBeenPlayed(false);
        deck.setCardFour(cards.get(30));
        deck.setCardFourHasBeenPlayed(false);
        deck.setCardFive(cards.get(31));
        deck.setCardFiveHasBeenPlayed(true);
        deck.setCardSix(cards.get(32));
        deck.setCardSixHasBeenPlayed(false);
        deck.setCardSeven(cards.get(33));
        deck.setCardSevenHasBeenPlayed(false);
        deck.setCardEight(cards.get(34));
        deck.setCardEightHasBeenPlayed(false);
        deck.setCardNine(cards.get(35));
        deck.setCardNineHasBeenPlayed(false);

        CardEntity firstCardOfTurn = cards.get(24);
        CardEntity playedCard = deck.getCardNine();

        assertTrue(GameUtil.validateMoveOndeUfe(playedCard, deck, firstCardOfTurn));
    }
}
