package jass.server.Test;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.ArrayList;

import jass.lib.Card;
import jass.lib.GameMode;
import jass.server.entity.*;
import jass.server.util.GameUtil;
import org.junit.Before;
import org.junit.Test;

/**
 * Test Class for the validation of the different game modes.
 *
 * The card id's with the corresponding suit and rank values are copied at the bottom of this document as an assistance.
 *
 * @author Thomas Weber
 * @version %I%, %G%
 * @since 0.0.1
 */

public class GameUtilTest {

    private static ArrayList<RankEntity> ranks = new ArrayList<>();
    private static ArrayList<SuitEntity> suits = new ArrayList<>();
    private static ArrayList<CardEntity> cards = new ArrayList<>();

    /**
     * Test one properties
     * Test for validateMoveTrump() function
     */
    private static DeckEntity deckT1 = new DeckEntity();
    private static RoundEntity roundT1 = new RoundEntity();
    private static CardEntity firstCardOfTurnT1 = new CardEntity();
    private static CardEntity playedCardT1 = new CardEntity();

    /**
     * Test two properties
     * Test for validateMoveTrump() function
     */
    private static DeckEntity deckT2 = new DeckEntity();
    private static RoundEntity roundT2 = new RoundEntity();
    private static CardEntity firstCardOfTurnT2 = new CardEntity();
    private static CardEntity playedCardT2 = new CardEntity();

    /**
     * Test three properties
     * Test for validateMoveTrump() function
     */
    private static DeckEntity deckT3 = new DeckEntity();
    private static RoundEntity roundT3 = new RoundEntity();
    private static CardEntity firstCardOfTurnT3 = new CardEntity();
    private static CardEntity playedCardT3 = new CardEntity();

    /**
     * Test four properties
     * Test for validateMoveTrump() function
     */
    private static DeckEntity deckT4 = new DeckEntity();
    private static RoundEntity roundT4 = new RoundEntity();
    private static CardEntity firstCardOfTurnT4 = new CardEntity();
    private static CardEntity playedCardT4 = new CardEntity();

    @Before
    public void createTestData() {
        ranks = insertRankSeedData();
        suits = insertSuitSeedData();
        cards = insertCardSeedData();

        insertTestOneData();
        insertTestTwoData();
        insertTestThreeData();
        insertTestFourData();
    }

    /**
     * Test one data:
     * - Game Mode Trump
     * - Trump Suite Hearts
     * - First turn of round is Heart 6
     * - The player only has one card with suit heart which is the jack.
     * - The player plays an Ace of spades.
     * - Assertion is that this move is true/valid, as you are never forced to play the jack.
     */
    private void insertTestOneData() {
        roundT1.setGameMode(GameMode.TRUMPF);
        roundT1.setTrumpfSuit(Card.Suit.Hearts);
        deckT1.setRound(roundT1);

        deckT1.setCardOne(cards.get(10));
        deckT1.setCardOneHasBeenPlayed(false);
        deckT1.setCardTwo(cards.get(11));
        deckT1.setCardTwoHasBeenPlayed(false);
        deckT1.setCardThree(cards.get(25));
        deckT1.setCardThreeHasBeenPlayed(false);
        deckT1.setCardFour(cards.get(26));
        deckT1.setCardFourHasBeenPlayed(true);
        deckT1.setCardFive(cards.get(27));
        deckT1.setCardFiveHasBeenPlayed(true);
        deckT1.setCardSix(cards.get(28));
        deckT1.setCardSixHasBeenPlayed(false);
        deckT1.setCardSeven(cards.get(26));
        deckT1.setCardSevenHasBeenPlayed(false);
        deckT1.setCardEight(cards.get(30));
        deckT1.setCardEightHasBeenPlayed(true);
        deckT1.setCardNine(cards.get(5));
        deckT1.setCardNineHasBeenPlayed(false);

        firstCardOfTurnT1 = cards.get(0);
        playedCardT1 = deckT1.getCardSeven();
    }

    /**
     * Test two data:
     * - Game Mode Trump
     * - Trump Suite Clubs
     * - First turn of round is queen of Spades
     * - The player had two cards with suit spade in his hand but has already played both before this turn
     * - The player plays an Ace of hearts.
     * - Assertion is that this move is true/valid, as he has already played out his spades.
     */
    private void insertTestTwoData() {
        roundT2.setGameMode(GameMode.TRUMPF);
        roundT2.setTrumpfSuit(Card.Suit.Clubs);
        deckT2.setRound(roundT2);

        deckT2.setCardOne(cards.get(20));
        deckT2.setCardOneHasBeenPlayed(true);
        deckT2.setCardTwo(cards.get(23));
        deckT2.setCardTwoHasBeenPlayed(true);
        deckT2.setCardThree(cards.get(9));
        deckT2.setCardThreeHasBeenPlayed(false);
        deckT2.setCardFour(cards.get(30));
        deckT2.setCardFourHasBeenPlayed(false);
        deckT2.setCardFive(cards.get(31));
        deckT2.setCardFiveHasBeenPlayed(false);
        deckT2.setCardSix(cards.get(32));
        deckT2.setCardSixHasBeenPlayed(false);
        deckT2.setCardSeven(cards.get(33));
        deckT2.setCardSevenHasBeenPlayed(false);
        deckT2.setCardEight(cards.get(34));
        deckT2.setCardEightHasBeenPlayed(false);
        deckT2.setCardNine(cards.get(35));
        deckT2.setCardNineHasBeenPlayed(false);

        firstCardOfTurnT2 = cards.get(24);
        playedCardT2 = deckT2.getCardThree();
    }

    /**
     * Test three data:
     * - Game Mode Trump
     * - Trump Suite Clubs
     * - First turn of round is queen of Spades
     * - The player has two cards with suit spade in his hand, one already played & one still in his hands
     * - The player plays an Ace of hearts.
     * - Assertion is that this move is false/invalid, as he must have played the remaining spade.
     */
    private void insertTestThreeData() {
        roundT3.setGameMode(GameMode.TRUMPF);
        roundT3.setTrumpfSuit(Card.Suit.Clubs);
        deckT3.setRound(roundT3);

        deckT3.setCardOne(cards.get(20));
        deckT3.setCardOneHasBeenPlayed(true);
        deckT3.setCardTwo(cards.get(23));
        deckT3.setCardTwoHasBeenPlayed(false);
        deckT3.setCardThree(cards.get(8));
        deckT3.setCardThreeHasBeenPlayed(false);
        deckT3.setCardFour(cards.get(30));
        deckT3.setCardFourHasBeenPlayed(false);
        deckT3.setCardFive(cards.get(31));
        deckT3.setCardFiveHasBeenPlayed(true);
        deckT3.setCardSix(cards.get(32));
        deckT3.setCardSixHasBeenPlayed(false);
        deckT3.setCardSeven(cards.get(33));
        deckT3.setCardSevenHasBeenPlayed(false);
        deckT3.setCardEight(cards.get(34));
        deckT3.setCardEightHasBeenPlayed(false);
        deckT3.setCardNine(cards.get(35));
        deckT3.setCardNineHasBeenPlayed(false);

        firstCardOfTurnT3 = cards.get(24);
        playedCardT3 = deckT3.getCardThree();
    }

    /**
     * Test four data:
     * - Game Mode Trump
     * - Trump Suite Clubs
     * - First turn of round is queen of Spades
     * - The player had two cards with suit spade in his hand but has already played both before this turn
     * - The player plays an Ace of clubs.
     * - Assertion is that this move is true/valid, as clubs are trump.
     */
    private void insertTestFourData() {
        roundT4.setGameMode(GameMode.TRUMPF);
        roundT4.setTrumpfSuit(Card.Suit.Clubs);
        deckT4.setRound(roundT4);

        deckT4.setCardOne(cards.get(20));
        deckT4.setCardOneHasBeenPlayed(true);
        deckT4.setCardTwo(cards.get(23));
        deckT4.setCardTwoHasBeenPlayed(true);
        deckT4.setCardThree(cards.get(9));
        deckT4.setCardThreeHasBeenPlayed(false);
        deckT4.setCardFour(cards.get(30));
        deckT4.setCardFourHasBeenPlayed(false);
        deckT4.setCardFive(cards.get(31));
        deckT4.setCardFiveHasBeenPlayed(true);
        deckT4.setCardSix(cards.get(32));
        deckT4.setCardSixHasBeenPlayed(false);
        deckT4.setCardSeven(cards.get(33));
        deckT4.setCardSevenHasBeenPlayed(false);
        deckT4.setCardEight(cards.get(34));
        deckT4.setCardEightHasBeenPlayed(false);
        deckT4.setCardNine(cards.get(35));
        deckT4.setCardNineHasBeenPlayed(false);

        firstCardOfTurnT4 = cards.get(24);
        playedCardT4 = deckT4.getCardNine();
    }

    @Test
    public void testValidateMoveTrump() {
        assertTrue(GameUtil.validateMoveTrump(playedCardT1, deckT1, firstCardOfTurnT1, String.valueOf(roundT1.getTrumpfSuit())));
        assertTrue(GameUtil.validateMoveTrump(playedCardT2, deckT2, firstCardOfTurnT2, String.valueOf(roundT2.getTrumpfSuit())));
        assertFalse(GameUtil.validateMoveTrump(playedCardT3, deckT3, firstCardOfTurnT3, String.valueOf(roundT3.getTrumpfSuit())));
        assertTrue(GameUtil.validateMoveTrump(playedCardT4, deckT4, firstCardOfTurnT4, String.valueOf(roundT4.getTrumpfSuit())));
    }

    /**
     * Generate Rank Data
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
     * Generate Suit Data
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
     * Generate Card Data
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
                // Comment out following 4 lines to remove print of cards into console.
                int id = j + addend;
                System.out.printf("Card Id: " + id + "\t\t");
                System.out.printf(" Rank: " + ranks.get(j).getKey() + "\t\t");
                System.out.println(" Suit: " + suits.get(i).getKey());

            }
            addend += 9;
        }
        return createdCards;
    }

}


/**
 *Card Id: 0		 Rank: 6		 Suit: hearts
 * Card Id: 1		 Rank: 6		 Suit: hearts
 * Card Id: 2		 Rank: 7		 Suit: hearts
 * Card Id: 3		 Rank: 8		 Suit: hearts
 * Card Id: 4		 Rank: 9		 Suit: hearts
 * Card Id: 5		 Rank: 10		 Suit: hearts
 * Card Id: 6		 Rank: jack		 Suit: hearts
 * Card Id: 7		 Rank: queen	 Suit: hearts
 * Card Id: 8		 Rank: king		 Suit: hearts
 * Card Id: 9		 Rank: ace		 Suit: hearts
 * Card Id: 10		 Rank: 6		 Suit: diamonds
 * Card Id: 11		 Rank: 6		 Suit: diamonds
 * Card Id: 12		 Rank: 7		 Suit: diamonds
 * Card Id: 13		 Rank: 8		 Suit: diamonds
 * Card Id: 14		 Rank: 9		 Suit: diamonds
 * Card Id: 15		 Rank: 10		 Suit: diamonds
 * Card Id: 16		 Rank: jack		 Suit: diamonds
 * Card Id: 17		 Rank: queen	 Suit: diamonds
 * Card Id: 18		 Rank: king		 Suit: diamonds
 * Card Id: 19		 Rank: ace		 Suit: diamonds
 * Card Id: 20		 Rank: 6		 Suit: spades
 * Card Id: 21		 Rank: 6		 Suit: spades
 * Card Id: 22		 Rank: 7		 Suit: spades
 * Card Id: 23		 Rank: 8		 Suit: spades
 * Card Id: 24		 Rank: 9		 Suit: spades
 * Card Id: 25		 Rank: 10		 Suit: spades
 * Card Id: 26		 Rank: jack		 Suit: spades
 * Card Id: 27		 Rank: queen	 Suit: spades
 * Card Id: 28		 Rank: king		 Suit: spades
 * Card Id: 29		 Rank: ace		 Suit: spades
 * Card Id: 30		 Rank: 6		 Suit: clubs
 * Card Id: 31		 Rank: 6		 Suit: clubs
 * Card Id: 32		 Rank: 7		 Suit: clubs
 * Card Id: 33		 Rank: 8		 Suit: clubs
 * Card Id: 34		 Rank: 9		 Suit: clubs
 * Card Id: 35		 Rank: 10		 Suit: clubs
 * Card Id: 36		 Rank: jack		 Suit: clubs
 * Card Id: 37		 Rank: queen	 Suit: clubs
 * Card Id: 38		 Rank: king		 Suit: clubs
 * Card Id: 39		 Rank: ace		 Suit: clubs
 * */
