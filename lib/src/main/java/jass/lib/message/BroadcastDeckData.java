

package jass.lib.message;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * The data model for the BroadcastDeckData message.
 *
 * @author Victor Hargrave
 */


public final class BroadcastDeckData extends MessageData {
    private final int deckId;
    /*private final JSONArray cards;*/
    private final int cardOneId;
    private final String cardOneSuit;
    private final String cardOneRank;
    private final int cardTwoId;
    private final String cardTwoSuit;
    private final String cardTwoRank;
    private final int cardThreeId;
    private final String cardThreeRank;
    private final String cardThreeSuit;
    private final int cardFourId;
    private final String cardFourRank;
    private final String cardFourSuit;
    private final int cardFiveId;
    private final String cardFiveRank;
    private final String cardFiveSuit;
    private final int cardSixId;
    private final String cardSixRank;
    private final String cardSixSuit;
    private final int cardSevenId;
    private final String cardSevenRank;
    private final String cardSevenSuit;
    private final int cardEightId;
    private final String cardEightRank;
    private final String cardEightSuit;
    private final int cardNineId;
    private final String cardNineRank;
    private final String cardNineSuit;


    public BroadcastDeckData(int deckId, CardData card1, CardData card2, CardData card3, CardData card4,
                             CardData card5, CardData card6, CardData card7, CardData card8, CardData card9, List<CardData> cards) {
        super("BroadcastDeck");
        this.deckId = deckId;
        /*this.cards = new JSONArray(cards);
        for (int i = 0; i < this.cards.length(); i++) {
            JSONObject jsonobject = this.cards.getJSONObject(i);
            String suit = jsonobject.getString("suit");
            String rank = jsonobject.getString("rank");
        }*/
        this.cardOneId = card1.getId();
        this.cardOneRank = card1.getRank();
        this.cardOneSuit = card1.getSuit();

        this.cardTwoId = card2.getId();
        this.cardTwoRank = card2.getRank();
        this.cardTwoSuit = card2.getSuit();

        this.cardThreeId = card3.getId();
        this.cardThreeRank = card3.getRank();
        this.cardThreeSuit = card3.getSuit();

        this.cardFourId = card4.getId();
        this.cardFourRank = card4.getRank();
        this.cardFourSuit = card4.getSuit();

        this.cardFiveId = card5.getId();
        this.cardFiveRank = card5.getRank();
        this.cardFiveSuit = card5.getSuit();

        this.cardSixId = card6.getId();
        this.cardSixRank = card6.getRank();
        this.cardSixSuit = card6.getSuit();

        this.cardSevenId = card7.getId();
        this.cardSevenRank = card7.getRank();
        this.cardSevenSuit = card7.getSuit();

        this.cardEightId = card8.getId();
        this.cardEightRank = card8.getRank();
        this.cardEightSuit = card8.getSuit();

        this.cardNineId = card9.getId();
        this.cardNineRank = card9.getRank();
        this.cardNineSuit = card9.getSuit();

    }

    public BroadcastDeckData(final JSONObject data) {
        super(data);
        deckId = data.getInt("deckId");
        /*cards = data.getJSONArray("cards");*/

        cardOneId = data.getInt("cardOneId");
        cardOneRank = data.getString("cardOneRank");
        cardOneSuit = data.getString("cardOneSuit");

        cardTwoId = data.getInt("cardTwoId");
        cardTwoRank = data.getString("cardTwoRank");
        cardTwoSuit = data.getString("cardTwoSuit");

        cardThreeId = data.getInt("cardThreeId");
        cardThreeRank = data.getString("cardThreeRank");
        cardThreeSuit = data.getString("cardThreeSuit");

        cardFourId = data.getInt("cardFourId");
        cardFourRank = data.getString("cardFourRank");
        cardFourSuit = data.getString("cardFourSuit");

        cardFiveId = data.getInt("cardFiveId");
        cardFiveRank = data.getString("cardFiveRank");
        cardFiveSuit = data.getString("cardFiveSuit");

        cardSixId = data.getInt("cardSixId");
        cardSixRank = data.getString("cardSixRank");
        cardSixSuit = data.getString("cardSixSuit");

        cardSevenId = data.getInt("cardSevenId");
        cardSevenRank = data.getString("cardSevenRank");
        cardSevenSuit = data.getString("cardSevenSuit");

        cardEightId = data.getInt("cardEightId");
        cardEightRank = data.getString("cardEightRank");
        cardEightSuit = data.getString("cardEightSuit");

        cardNineId = data.getInt("cardNineId");
        cardNineRank = data.getString("cardNineRank");
        cardNineSuit = data.getString("cardNineSuit");

    }

    public int getDeckId() {
        return deckId;
    }

    public int getCardOneId() {
        return cardOneId;
    }

    public String getCardOneSuit() {
        return cardOneSuit;
    }

    public String getCardOneRank() {
        return cardOneRank;
    }

    public int getCardTwoId() {
        return cardTwoId;
    }

    public String getCardTwoSuit() {
        return cardTwoSuit;
    }

    public String getCardTwoRank() {
        return cardTwoRank;
    }

    public int getCardThreeId() {
        return cardThreeId;
    }

    public String getCardThreeRank() {
        return cardThreeRank;
    }

    public String getCardThreeSuit() {
        return cardThreeSuit;
    }

    public int getCardFourId() {
        return cardFourId;
    }

    public String getCardFourRank() {
        return cardFourRank;
    }

    public String getCardFourSuit() {
        return cardFourSuit;
    }

    public int getCardFiveId() {
        return cardFiveId;
    }

    public String getCardFiveRank() {
        return cardFiveRank;
    }

    public String getCardFiveSuit() {
        return cardFiveSuit;
    }

    public int getCardSixId() {
        return cardSixId;
    }

    public String getCardSixRank() {
        return cardSixRank;
    }

    public String getCardSixSuit() {
        return cardSixSuit;
    }

    public int getCardSevenId() {
        return cardSevenId;
    }

    public String getCardSevenRank() {
        return cardSevenRank;
    }

    public String getCardSevenSuit() {
        return cardSevenSuit;
    }

    public int getCardEightId() {
        return cardEightId;
    }

    public String getCardEightRank() {
        return cardEightRank;
    }

    public String getCardEightSuit() {
        return cardEightSuit;
    }

    public int getCardNineId() {
        return cardNineId;
    }

    public String getCardNineRank() {
        return cardNineRank;
    }

    public String getCardNineSuit() {
        return cardNineSuit;
    }

    /*public JSONArray getCards() {
        return cards;
    }*/
}


/*package jass.lib.message;

import org.json.JSONObject;

*//**
 * The data model for the Game Found message.
 *
 * @author Thomas Weber
 *//*

public final class BroadcastDeckData extends MessageData {
    *//**
     * Game ID.
     *//*
    private final int gameId;

    *//**
     * Player one ID.
     *//*
    private final int playerOneId;

    *//**
     * Player one username.
     *//*
    private final String playerOne;

    *//**
     * Player one team ID.
     *//*
    private final int playerOneTeamId;

    *//**
     * Player two ID.
     *//*
    private final int playerTwoId;

    *//**
     * Player two username.
     *//*
    private final String playerTwo;

    *//**
     * Player two team ID.
     *//*
    private final int playerTwoTeamId;

    *//**
     * Player three ID.
     *//*
    private final int playerThreeId;

    *//**
     * Player three username.
     *//*
    private final String playerThree;

    *//**
     * Player three team ID.
     *//*
    private final int playerThreeTeamId;

    *//**
     * Player four ID.
     *//*
    private final int playerFourId;

    *//**
     * Player four username.
     *//*
    private final String playerFour;

    *//**
     * Player four team ID.
     *//*
    private final int playerFourTeamId;

    *//**
     * @param gameId            Game ID.
     * @param playerOneId       Player one ID.
     * @param playerOne         Player one username.
     * @param playerOneTeamId   Player one team ID.
     * @param playerTwoId       Player two ID.
     * @param playerTwo         Player two username.
     * @param playerTwoTeamId   Player two team ID.
     * @param playerThreeId     Player three ID.
     * @param playerThree       Player three username.
     * @param playerThreeTeamId Player three team ID.
     * @param playerFourId      Player four ID.
     * @param playerFour        Player four username.
     * @param playerFourTeamId  Player four team ID.
     *//*
    public BroadcastDeckData(final int gameId, final int playerOneId, final String playerOne, final int playerOneTeamId, final int playerTwoId, final String playerTwo, final int playerTwoTeamId, final int playerThreeId, final String playerThree, final int playerThreeTeamId, final int playerFourId, final String playerFour, final int playerFourTeamId) {
        super("BroadcastDeck");

        this.gameId = gameId;
        this.playerOneId = playerOneId;
        this.playerOne = playerOne;
        this.playerOneTeamId = playerOneTeamId;

        this.playerTwoId = playerTwoId;
        this.playerTwo = playerTwo;
        this.playerTwoTeamId = playerTwoTeamId;

        this.playerThreeId = playerThreeId;
        this.playerThree = playerThree;
        this.playerThreeTeamId = playerThreeTeamId;

        this.playerFourId = playerFourId;
        this.playerFour = playerFour;
        this.playerFourTeamId = playerFourTeamId;
    }

    *//**
     * @param data The message containing all the data.
     *//*
    public BroadcastDeckData(final JSONObject data) {
        super(data);
        gameId = data.getInt("gameId");

        playerOneId = data.getInt("playerOneId");
        playerOne = data.getString("playerOne");
        playerOneTeamId = data.getInt("playerOneTeamId");

        playerTwoId = data.getInt("playerTwoId");
        playerTwo = data.getString("playerTwo");
        playerTwoTeamId = data.getInt("playerTwoTeamId");

        playerThreeId = data.getInt("playerThreeId");
        playerThree = data.getString("playerThree");
        playerThreeTeamId = data.getInt("playerThreeTeamId");

        playerFourId = data.getInt("playerFourId");
        playerFour = data.getString("playerFour");
        playerFourTeamId = data.getInt("playerFourTeamId");
    }

    *//**
     * @return Returns the game ID.
     *//*
    public int getGameId() {
        return gameId;
    }

    *//**
     * @return Returns the player one's ID.
     *//*
    public int getPlayerOneId() {
        return playerOneId;
    }

    *//**
     * @return Returns the player one's username.
     *//*
    public String getPlayerOne() {
        return playerOne;
    }

    *//**
     * @return Returns the player one's team ID.
     *//*
    public int getPlayerOneTeamId() {
        return playerOneTeamId;
    }

    *//**
     * @return Returns the player two's ID.
     *//*
    public int getPlayerTwoId() {
        return playerTwoId;
    }

    *//**
     * @return Returns the player two's username.
     *//*
    public String getPlayerTwo() {
        return playerTwo;
    }

    *//**
     * @return Returns the player two's team id.
     *//*
    public int getPlayerTwoTeamId() {
        return playerTwoTeamId;
    }

    *//**
     * @return Returns the player three's ID.
     *//*
    public int getPlayerThreeId() {
        return playerThreeId;
    }

    *//**
     * @return Returns the player three's username.
     *//*
    public String getPlayerThree() {
        return playerThree;
    }

    *//**
     * @return Returns the player three's team ID.
     *//*
    public int getPlayerThreeTeamId() {
        return playerThreeTeamId;
    }

    *//**
     * @return Returns the player four's ID.
     *//*
    public int getPlayerFourId() {
        return playerFourId;
    }

    *//**
     * @return Returns the player four's username.
     *//*
    public String getPlayerFour() {
        return playerFour;
    }

    *//**
     * @return Returns the player four's team ID.
     *//*
    public int getPlayerFourTeamId() {
        return playerFourTeamId;
    }
}*/
