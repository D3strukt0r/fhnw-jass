package jass.lib.message;

import org.json.JSONObject;

/**
 * The data model for the Game Found message.
 *
 * @author Thomas Weber
 */

public final class GameFoundData extends MessageData {
    /**
     * Game ID.
     */
    private final int gameId;

    /**
     * Player one ID.
     */
    private final int playerOneId;

    /**
     * Player one username.
     */
    private final String playerOne;

    /**
     * Player one team ID.
     */
    private final int playerOneTeamId;

    /**
     * Player two ID.
     */
    private final int playerTwoId;

    /**
     * Player two username.
     */
    private final String playerTwo;

    /**
     * Player two team ID.
     */
    private final int playerTwoTeamId;

    /**
     * Player three ID.
     */
    private final int playerThreeId;

    /**
     * Player three username.
     */
    private final String playerThree;

    /**
     * Player three team ID.
     */
    private final int playerThreeTeamId;

    /**
     * Player four ID.
     */
    private final int playerFourId;

    /**
     * Player four username.
     */
    private final String playerFour;

    /**
     * Player four team ID.
     */
    private final int playerFourTeamId;

    /**
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
     */
    public GameFoundData(final int gameId, final int playerOneId, final String playerOne, final int playerOneTeamId, final int playerTwoId, final String playerTwo, final int playerTwoTeamId, final int playerThreeId, final String playerThree, final int playerThreeTeamId, final int playerFourId, final String playerFour, final int playerFourTeamId) {
        super("GameFound");

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

    /**
     * @param data The message containing all the data.
     */
    public GameFoundData(final JSONObject data) {
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

    /**
     * @return Returns the game ID.
     */
    public int getGameId() {
        return gameId;
    }

    /**
     * @return Returns the player one's ID.
     */
    public int getPlayerOneId() {
        return playerOneId;
    }

    /**
     * @return Returns the player one's username.
     */
    public String getPlayerOne() {
        return playerOne;
    }

    /**
     * @return Returns the player one's team ID.
     */
    public int getPlayerOneTeamId() {
        return playerOneTeamId;
    }

    /**
     * @return Returns the player two's ID.
     */
    public int getPlayerTwoId() {
        return playerTwoId;
    }

    /**
     * @return Returns the player two's username.
     */
    public String getPlayerTwo() {
        return playerTwo;
    }

    /**
     * @return Returns the player two's team id.
     */
    public int getPlayerTwoTeamId() {
        return playerTwoTeamId;
    }

    /**
     * @return Returns the player three's ID.
     */
    public int getPlayerThreeId() {
        return playerThreeId;
    }

    /**
     * @return Returns the player three's username.
     */
    public String getPlayerThree() {
        return playerThree;
    }

    /**
     * @return Returns the player three's team ID.
     */
    public int getPlayerThreeTeamId() {
        return playerThreeTeamId;
    }

    /**
     * @return Returns the player four's ID.
     */
    public int getPlayerFourId() {
        return playerFourId;
    }

    /**
     * @return Returns the player four's username.
     */
    public String getPlayerFour() {
        return playerFour;
    }

    /**
     * @return Returns the player four's team ID.
     */
    public int getPlayerFourTeamId() {
        return playerFourTeamId;
    }
}
