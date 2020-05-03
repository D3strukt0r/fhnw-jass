package jass.server.util;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.ArrayList;

import jass.lib.Card;
import jass.lib.GameMode;
import jass.server.entity.*;
import org.junit.Before;
import org.junit.Test;

public class GameUtilTest {

    private static ArrayList<RankEntity> ranks = new ArrayList<>();
    private static ArrayList<SuitEntity> suits = new ArrayList<>();
    private static ArrayList<CardEntity> cards = new ArrayList<>();

    private static DeckEntity deckOne = new DeckEntity();
    private static RoundEntity round = new RoundEntity();
    private static CardEntity firstCardOfTurn = new CardEntity();
    private static CardEntity playedCard = new CardEntity();

    @Before
    public void createTestData() {
        ranks = insertRankSeedData();
        suits = insertSuitSeedData();
        cards = insertCardSeedData();

        round.setGameMode(GameMode.TRUMPF);
        round.setTrumpfSuit(Card.Suit.Hearts);
        deckOne.setRound(round);

        ArrayList<CardEntity> deckOneCards = new ArrayList<>();
        deckOneCards = createNineCardsForTestDeckOne();
        ArrayList<Boolean> deckOneHasBeenPlayed = new ArrayList<>();
        deckOneHasBeenPlayed = createNineCardsHaveBeenPlayedForTestDeckOne();

        this.deckOne.setCardOne(deckOneCards.get(0));
        this.deckOne.setCardOneHasBeenPlayed(deckOneHasBeenPlayed.get(0));
        this.deckOne.setCardTwo(deckOneCards.get(1));
        this.deckOne.setCardTwoHasBeenPlayed(deckOneHasBeenPlayed.get(1));
        this.deckOne.setCardThree(deckOneCards.get(2));
        this.deckOne.setCardThreeHasBeenPlayed(deckOneHasBeenPlayed.get(2));
        this.deckOne.setCardFour(deckOneCards.get(3));
        this.deckOne.setCardFourHasBeenPlayed(deckOneHasBeenPlayed.get(3));
        this.deckOne.setCardFive(deckOneCards.get(4));
        this.deckOne.setCardFiveHasBeenPlayed(deckOneHasBeenPlayed.get(4));
        this.deckOne.setCardSix(deckOneCards.get(5));
        this.deckOne.setCardSixHasBeenPlayed(deckOneHasBeenPlayed.get(5));
        this.deckOne.setCardSeven(deckOneCards.get(6));
        this.deckOne.setCardSevenHasBeenPlayed(deckOneHasBeenPlayed.get(6));
        this.deckOne.setCardEight(deckOneCards.get(7));
        this.deckOne.setCardEightHasBeenPlayed(deckOneHasBeenPlayed.get(7));
        this.deckOne.setCardNine(deckOneCards.get(8));
        this.deckOne.setCardNineHasBeenPlayed(deckOneHasBeenPlayed.get(8));

        firstCardOfTurn = cards.get(18);
        playedCard = cards.get(0);
    }

    public ArrayList<CardEntity> createNineCardsForTestDeckOne() {
        ArrayList<CardEntity> testDeckOne = new ArrayList<>();
        testDeckOne.add(cards.get(0));
        testDeckOne.add(cards.get(10));
        testDeckOne.add(cards.get(20));
        testDeckOne.add(cards.get(15));
        testDeckOne.add(cards.get(12));
        testDeckOne.add(cards.get(8));
        testDeckOne.add(cards.get(29));
        testDeckOne.add(cards.get(31));
        testDeckOne.add(cards.get(35));

        return testDeckOne;
    }

    public ArrayList<Boolean> createNineCardsHaveBeenPlayedForTestDeckOne() {
        ArrayList<Boolean> testDeckOneCardHasBeenPlayed = new ArrayList<>();
        testDeckOneCardHasBeenPlayed.add(false);
        testDeckOneCardHasBeenPlayed.add(true);
        testDeckOneCardHasBeenPlayed.add(false);
        testDeckOneCardHasBeenPlayed.add(false);
        testDeckOneCardHasBeenPlayed.add(false);
        testDeckOneCardHasBeenPlayed.add(true);
        testDeckOneCardHasBeenPlayed.add(false);
        testDeckOneCardHasBeenPlayed.add(true);
        testDeckOneCardHasBeenPlayed.add(false);

        return testDeckOneCardHasBeenPlayed;
    }

    @Test
    public void test() {
        assertFalse(GameUtil.validateMoveTrumpTest(round, firstCardOfTurn, playedCard, deckOne));
    }


    private ArrayList<RankEntity> insertRankSeedData() {
        ArrayList<RankEntity> createdRanks = new ArrayList<>();
        createdRanks.add(new RankEntity().setId(1).setKey("6").setPointsTrumpf(0).setPointsObeAbe(0).setPointsOndeufe(11));
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

    private ArrayList<SuitEntity> insertSuitSeedData() {
        ArrayList<SuitEntity> createdSuits = new ArrayList<>();
        createdSuits.add((new SuitEntity()).setId(1).setKey("hearts"));
        createdSuits.add((new SuitEntity()).setId(2).setKey("diamonds"));
        createdSuits.add((new SuitEntity()).setId(3).setKey("spades"));
        createdSuits.add((new SuitEntity()).setId(4).setKey("clubs"));
        return createdSuits;
    }

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
 * Card Id: 0		 Rank: 6		 Suit: hearts
 * Card Id: 1		 Rank: 6		 Suit: hearts
 * Card Id: 2		 Rank: 7		 Suit: hearts
 * Card Id: 3		 Rank: 8		 Suit: hearts
 * Card Id: 4		 Rank: 9		 Suit: hearts
 * Card Id: 5		 Rank: 10		 Suit: hearts
 * Card Id: 6		 Rank: jack		 Suit: hearts
 * Card Id: 7		 Rank: queen		 Suit: hearts
 * Card Id: 8		 Rank: king		 Suit: hearts
 * Card Id: 9		 Rank: 6		 Suit: diamonds
 * Card Id: 10		 Rank: 6		 Suit: diamonds
 * Card Id: 11		 Rank: 7		 Suit: diamonds
 * Card Id: 12		 Rank: 8		 Suit: diamonds
 * Card Id: 13		 Rank: 9		 Suit: diamonds
 * Card Id: 14		 Rank: 10		 Suit: diamonds
 * Card Id: 15		 Rank: jack		 Suit: diamonds
 * Card Id: 16		 Rank: queen		 Suit: diamonds
 * Card Id: 17		 Rank: king		 Suit: diamonds
 * Card Id: 18		 Rank: 6		 Suit: spades
 * Card Id: 19		 Rank: 6		 Suit: spades
 * Card Id: 20		 Rank: 7		 Suit: spades
 * Card Id: 21		 Rank: 8		 Suit: spades
 * Card Id: 22		 Rank: 9		 Suit: spades
 * Card Id: 23		 Rank: 10		 Suit: spades
 * Card Id: 24		 Rank: jack		 Suit: spades
 * Card Id: 25		 Rank: queen		 Suit: spades
 * Card Id: 26		 Rank: king		 Suit: spades
 * Card Id: 27		 Rank: 6		 Suit: clubs
 * Card Id: 28		 Rank: 6		 Suit: clubs
 * Card Id: 29		 Rank: 7		 Suit: clubs
 * Card Id: 30		 Rank: 8		 Suit: clubs
 * Card Id: 31		 Rank: 9		 Suit: clubs
 * Card Id: 32		 Rank: 10		 Suit: clubs
 * Card Id: 33		 Rank: jack		 Suit: clubs
 * Card Id: 34		 Rank: queen		 Suit: clubs
 * Card Id: 35		 Rank: king		 Suit: clubs
 *
 * */
