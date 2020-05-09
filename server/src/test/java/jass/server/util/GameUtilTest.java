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
 * @author Thomas Weber
 * @version %I%, %G%
 * @since 0.0.1
 */
public final class GameUtilTest {
    private static ArrayList<RankEntity> ranks = new ArrayList<>();
    private static ArrayList<SuitEntity> suits = new ArrayList<>();
    private static ArrayList<CardEntity> cards = new ArrayList<>();

    /**
     * Test one properties.
     * Test for validateMoveTrump() function.
     */
    private static DeckEntity deckT1 = new DeckEntity();
    private static RoundEntity roundT1 = new RoundEntity();
    private static CardEntity firstCardOfTurnT1 = new CardEntity();
    private static CardEntity playedCardT1 = new CardEntity();

    /**
     * Test two properties.
     * Test for validateMoveTrump() function.
     */
    private static DeckEntity deckT2 = new DeckEntity();
    private static RoundEntity roundT2 = new RoundEntity();
    private static CardEntity firstCardOfTurnT2 = new CardEntity();
    private static CardEntity playedCardT2 = new CardEntity();

    /**
     * Test three properties.
     * Test for validateMoveTrump() function.
     */
    private static DeckEntity deckT3 = new DeckEntity();
    private static RoundEntity roundT3 = new RoundEntity();
    private static CardEntity firstCardOfTurnT3 = new CardEntity();
    private static CardEntity playedCardT3 = new CardEntity();

    /**
     * Test four properties.
     * Test for validateMoveTrump() function.
     */
    private static DeckEntity deckT4 = new DeckEntity();
    private static RoundEntity roundT4 = new RoundEntity();
    private static CardEntity firstCardOfTurnT4 = new CardEntity();
    private static CardEntity playedCardT4 = new CardEntity();

    /**
     * Test one properties.
     * Test for validateMoveObeAbe() function.
     */
    private static DeckEntity obeabeDeckT1 = new DeckEntity();
    private static RoundEntity obeabeRoundT1 = new RoundEntity();
    private static CardEntity obeabeFirstCardOfTurnT1 = new CardEntity();
    private static CardEntity obeabePlayedCardT1 = new CardEntity();

    /**
     * Test two properties.
     * Test for validateMoveObeAbe() function.
     */
    private static DeckEntity obeabeDeckT2 = new DeckEntity();
    private static RoundEntity obeabeRoundT2 = new RoundEntity();
    private static CardEntity obeabeFirstCardOfTurnT2 = new CardEntity();
    private static CardEntity obeabePlayedCardT2 = new CardEntity();

    /**
     * Test three properties.
     * Test for validateMoveObeAbe() function.
     */
    private static DeckEntity obeabeDeckT3 = new DeckEntity();
    private static RoundEntity obeabeRoundT3 = new RoundEntity();
    private static CardEntity obeabeFirstCardOfTurnT3 = new CardEntity();
    private static CardEntity obeabePlayedCardT3 = new CardEntity();

    /**
     * Test four properties.
     * Test for validateMoveObeAbe() function.
     */
    private static DeckEntity obeabeDeckT4 = new DeckEntity();
    private static RoundEntity obeabeRoundT4 = new RoundEntity();
    private static CardEntity obeabeFirstCardOfTurnT4 = new CardEntity();
    private static CardEntity obeabePlayedCardT4 = new CardEntity();

    /**
     * Test one properties.
     * Test for validateMoveOndeUfe() function.
     */
    private static DeckEntity ondeufeDeckT1 = new DeckEntity();
    private static RoundEntity ondeufeRoundT1 = new RoundEntity();
    private static CardEntity ondeufeFirstCardOfTurnT1 = new CardEntity();
    private static CardEntity ondeufePlayedCardT1 = new CardEntity();

    /**
     * Test two properties.
     * Test for validateMoveOndeUfe() function.
     */
    private static DeckEntity ondeufeDeckT2 = new DeckEntity();
    private static RoundEntity ondeufeRoundT2 = new RoundEntity();
    private static CardEntity ondeufeFirstCardOfTurnT2 = new CardEntity();
    private static CardEntity ondeufePlayedCardT2 = new CardEntity();

    /**
     * Test three properties.
     * Test for validateMoveOndeUfe() function.
     */
    private static DeckEntity ondeufeDeckT3 = new DeckEntity();
    private static RoundEntity ondeufeRoundT3 = new RoundEntity();
    private static CardEntity ondeufeFirstCardOfTurnT3 = new CardEntity();
    private static CardEntity ondeufePlayedCardT3 = new CardEntity();

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
     * - Assertion is that this move is true/valid, as you are never forced to
     *   play the jack.
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
     * - The player had two cards with suit spade in his hand but has already
     *   played both before this turn
     * - The player plays an Ace of hearts.
     * - Assertion is that this move is true/valid, as he has already played out
     *   his spades.
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
     * - The player has two cards with suit spade in his hand, one already
     *   played & one still in his hands
     * - The player plays an Ace of hearts.
     * - Assertion is that this move is false/invalid, as he must have played
     *   the remaining spade.
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
     * - The player had two cards with suit spade in his hand but has already
     *   played both before this turn
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

    private void insertTestDataObeAbe() {

        /*
         * - First turn of round is queen of Spades
         * - The player had two cards with suit spade in his hand but has
         *   already played both before this turn
         * - The player plays an Ace of hearts.
         * - Assertion is that this move is true/valid, as he has already played
         *   out his spades.
         */
        obeabeRoundT2.setGameMode(GameMode.OBE_ABE);
        obeabeDeckT2.setRound(obeabeRoundT2);
        obeabeDeckT2.setCardOne(cards.get(20));
        obeabeDeckT2.setCardOneHasBeenPlayed(true);
        obeabeDeckT2.setCardTwo(cards.get(23));
        obeabeDeckT2.setCardTwoHasBeenPlayed(true);
        obeabeDeckT2.setCardThree(cards.get(9));
        obeabeDeckT2.setCardThreeHasBeenPlayed(false);
        obeabeDeckT2.setCardFour(cards.get(30));
        obeabeDeckT2.setCardFourHasBeenPlayed(false);
        obeabeDeckT2.setCardFive(cards.get(31));
        obeabeDeckT2.setCardFiveHasBeenPlayed(false);
        obeabeDeckT2.setCardSix(cards.get(32));
        obeabeDeckT2.setCardSixHasBeenPlayed(false);
        obeabeDeckT2.setCardSeven(cards.get(33));
        obeabeDeckT2.setCardSevenHasBeenPlayed(false);
        obeabeDeckT2.setCardEight(cards.get(34));
        obeabeDeckT2.setCardEightHasBeenPlayed(false);
        obeabeDeckT2.setCardNine(cards.get(35));
        obeabeDeckT2.setCardNineHasBeenPlayed(false);
        obeabeFirstCardOfTurnT2 = cards.get(24);
        obeabePlayedCardT2 = obeabeDeckT2.getCardThree();

        /*
         * - First turn of round is queen of Spades
         * - The player has two cards with suit spade in his hand, one already
         *   played & one still in his hands
         * - The player plays an Ace of hearts.
         * - Assertion is that this move is false/invalid, as he must have
         *   played the remaining spade.
         */
        obeabeRoundT3.setGameMode(GameMode.OBE_ABE);
        obeabeDeckT3.setRound(obeabeRoundT3);
        obeabeDeckT3.setCardOne(cards.get(20));
        obeabeDeckT3.setCardOneHasBeenPlayed(true);
        obeabeDeckT3.setCardTwo(cards.get(23));
        obeabeDeckT3.setCardTwoHasBeenPlayed(false);
        obeabeDeckT3.setCardThree(cards.get(8));
        obeabeDeckT3.setCardThreeHasBeenPlayed(false);
        obeabeDeckT3.setCardFour(cards.get(30));
        obeabeDeckT3.setCardFourHasBeenPlayed(false);
        obeabeDeckT3.setCardFive(cards.get(31));
        obeabeDeckT3.setCardFiveHasBeenPlayed(true);
        obeabeDeckT3.setCardSix(cards.get(32));
        obeabeDeckT3.setCardSixHasBeenPlayed(false);
        obeabeDeckT3.setCardSeven(cards.get(33));
        obeabeDeckT3.setCardSevenHasBeenPlayed(false);
        obeabeDeckT3.setCardEight(cards.get(34));
        obeabeDeckT3.setCardEightHasBeenPlayed(false);
        obeabeDeckT3.setCardNine(cards.get(35));
        obeabeDeckT3.setCardNineHasBeenPlayed(false);
        obeabeFirstCardOfTurnT3 = cards.get(24);
        obeabePlayedCardT3 = obeabeDeckT3.getCardThree();

        /*
         * - First turn of round is queen of Spades
         * - The player had two cards with suit spade in his hand but has
         *   already played both before this turn
         * - The player plays an Ace of clubs.
         * - Assertion is that this move is true/valid, as clubs are trump.
         */
        obeabeRoundT4.setGameMode(GameMode.OBE_ABE);
        obeabeDeckT4.setRound(obeabeRoundT4);
        obeabeDeckT4.setCardOne(cards.get(20));
        obeabeDeckT4.setCardOneHasBeenPlayed(true);
        obeabeDeckT4.setCardTwo(cards.get(23));
        obeabeDeckT4.setCardTwoHasBeenPlayed(true);
        obeabeDeckT4.setCardThree(cards.get(9));
        obeabeDeckT4.setCardThreeHasBeenPlayed(false);
        obeabeDeckT4.setCardFour(cards.get(30));
        obeabeDeckT4.setCardFourHasBeenPlayed(false);
        obeabeDeckT4.setCardFive(cards.get(31));
        obeabeDeckT4.setCardFiveHasBeenPlayed(true);
        obeabeDeckT4.setCardSix(cards.get(32));
        obeabeDeckT4.setCardSixHasBeenPlayed(false);
        obeabeDeckT4.setCardSeven(cards.get(33));
        obeabeDeckT4.setCardSevenHasBeenPlayed(false);
        obeabeDeckT4.setCardEight(cards.get(34));
        obeabeDeckT4.setCardEightHasBeenPlayed(false);
        obeabeDeckT4.setCardNine(cards.get(35));
        obeabeDeckT4.setCardNineHasBeenPlayed(false);
        obeabeFirstCardOfTurnT4 = cards.get(24);
        obeabePlayedCardT4 = obeabeDeckT4.getCardNine();
    }

    /**
     * Test some cases during game mode obe abe.
     */
    @Test
    public void testValidateMoveObeAbe() {
        insertTestDataObeAbe();

        assertTrue(GameUtil.validateMoveObeAbe(obeabePlayedCardT2, obeabeDeckT2, obeabeFirstCardOfTurnT2));
        assertFalse(GameUtil.validateMoveObeAbe(obeabePlayedCardT3, obeabeDeckT3, obeabeFirstCardOfTurnT3));
        assertTrue(GameUtil.validateMoveObeAbe(obeabePlayedCardT4, obeabeDeckT4, obeabeFirstCardOfTurnT4));
    }

    /**
     * Generate Rank Data.
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

    private void insertTestDataOndeUfe() {

        /*
         * - First turn of round is queen of Spades
         * - The player had two cards with suit spade in his hand but has
         *   already played both before this turn
         * - The player plays an Ace of hearts.
         * - Assertion is that this move is true/valid, as he has already played
         *   out his spades.
         */
        ondeufeRoundT1.setGameMode(GameMode.ONDE_UFE);
        ondeufeDeckT1.setRound(ondeufeRoundT1);
        ondeufeDeckT1.setCardOne(cards.get(20));
        ondeufeDeckT1.setCardOneHasBeenPlayed(true);
        ondeufeDeckT1.setCardTwo(cards.get(23));
        ondeufeDeckT1.setCardTwoHasBeenPlayed(true);
        ondeufeDeckT1.setCardThree(cards.get(9));
        ondeufeDeckT1.setCardThreeHasBeenPlayed(false);
        ondeufeDeckT1.setCardFour(cards.get(30));
        ondeufeDeckT1.setCardFive(cards.get(31));
        ondeufeDeckT1.setCardFiveHasBeenPlayed(false);
        ondeufeDeckT1.setCardSix(cards.get(32));
        ondeufeDeckT1.setCardSixHasBeenPlayed(false);
        ondeufeDeckT1.setCardSeven(cards.get(33));
        ondeufeDeckT1.setCardSevenHasBeenPlayed(false);
        ondeufeDeckT1.setCardEight(cards.get(34));
        ondeufeDeckT1.setCardEightHasBeenPlayed(false);
        ondeufeDeckT1.setCardNine(cards.get(35));
        ondeufeDeckT1.setCardNineHasBeenPlayed(false);
        ondeufeFirstCardOfTurnT1 = cards.get(24);
        ondeufePlayedCardT1 = ondeufeDeckT1.getCardThree();

        /*
         * - First turn of round is queen of Spades
         * - The player has two cards with suit spade in his hand, one already
         *   played & one still in his hands
         * - The player plays an Ace of hearts.
         * - Assertion is that this move is false/invalid, as he must have
         *   played the remaining spade.
         */
        ondeufeRoundT2.setGameMode(GameMode.ONDE_UFE);
        ondeufeDeckT2.setRound(ondeufeRoundT2);
        ondeufeDeckT2.setCardOne(cards.get(20));
        ondeufeDeckT2.setCardOneHasBeenPlayed(true);
        ondeufeDeckT2.setCardTwo(cards.get(23));
        ondeufeDeckT2.setCardTwoHasBeenPlayed(false);
        ondeufeDeckT2.setCardThree(cards.get(8));
        ondeufeDeckT2.setCardThreeHasBeenPlayed(false);
        ondeufeDeckT2.setCardFour(cards.get(30));
        ondeufeDeckT2.setCardFourHasBeenPlayed(false);
        ondeufeDeckT2.setCardFive(cards.get(31));
        ondeufeDeckT2.setCardFiveHasBeenPlayed(true);
        ondeufeDeckT2.setCardSix(cards.get(32));
        ondeufeDeckT2.setCardSixHasBeenPlayed(false);
        ondeufeDeckT2.setCardSeven(cards.get(33));
        ondeufeDeckT2.setCardSevenHasBeenPlayed(false);
        ondeufeDeckT2.setCardEight(cards.get(34));
        ondeufeDeckT2.setCardEightHasBeenPlayed(false);
        ondeufeDeckT2.setCardNine(cards.get(35));
        ondeufeDeckT2.setCardNineHasBeenPlayed(false);
        ondeufeFirstCardOfTurnT2 = cards.get(24);
        ondeufePlayedCardT2 = obeabeDeckT2.getCardThree();

        /*
         * - First turn of round is queen of Spades
         * - The player had two cards with suit spade in his hand but has
         *   already played both before this turn
         * - The player plays an Ace of clubs.
         * - Assertion is that this move is true/valid, as clubs are trump.
         */
        ondeufeRoundT3.setGameMode(GameMode.ONDE_UFE);
        ondeufeDeckT3.setRound(ondeufeRoundT3);
        ondeufeDeckT3.setCardOne(cards.get(20));
        ondeufeDeckT3.setCardOneHasBeenPlayed(true);
        ondeufeDeckT3.setCardTwo(cards.get(23));
        ondeufeDeckT3.setCardTwoHasBeenPlayed(true);
        ondeufeDeckT3.setCardThree(cards.get(9));
        ondeufeDeckT3.setCardThreeHasBeenPlayed(false);
        ondeufeDeckT3.setCardFour(cards.get(30));
        ondeufeDeckT3.setCardFourHasBeenPlayed(false);
        ondeufeDeckT3.setCardFive(cards.get(31));
        ondeufeDeckT3.setCardFiveHasBeenPlayed(true);
        ondeufeDeckT3.setCardSix(cards.get(32));
        ondeufeDeckT3.setCardSixHasBeenPlayed(false);
        ondeufeDeckT3.setCardSeven(cards.get(33));
        ondeufeDeckT3.setCardSevenHasBeenPlayed(false);
        ondeufeDeckT3.setCardEight(cards.get(34));
        ondeufeDeckT3.setCardEightHasBeenPlayed(false);
        ondeufeDeckT3.setCardNine(cards.get(35));
        ondeufeDeckT3.setCardNineHasBeenPlayed(false);
        ondeufeFirstCardOfTurnT3 = cards.get(24);
        ondeufePlayedCardT3 = ondeufeDeckT3.getCardNine();
    }

    /**
     * Test some cases during game mode obe abe.
     */
    @Test
    public void testValidateMoveOndeUfe() {
        insertTestDataOndeUfe();

        assertTrue(GameUtil.validateMoveOndeUfe(ondeufePlayedCardT1, ondeufeDeckT1, ondeufeFirstCardOfTurnT1));
        assertFalse(GameUtil.validateMoveOndeUfe(ondeufePlayedCardT2, ondeufeDeckT2, ondeufeFirstCardOfTurnT2));
        assertTrue(GameUtil.validateMoveOndeUfe(ondeufePlayedCardT3, ondeufeDeckT3, ondeufeFirstCardOfTurnT3));
    }

    /**
     * Generate Suit Data.
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
