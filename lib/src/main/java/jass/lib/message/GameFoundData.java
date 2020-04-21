package jass.lib.message;

import org.json.JSONObject;

/**
 * The data model for the Game Found message.
 *
 * @author Thomas Weber
 */

public class GameFoundData extends MessageData {
    private final int gameId;

    private final int playerOneId;
    private final String playerOne;
    private final int playerOneTeamId;

    private final int playerTwoId;
    private final String playerTwo;
    private final int playerTwoTeamId;

    private final int playerThreeId;
    private final String playerThree;
    private final int playerThreeTeamId;

    private final int playerFourId;
    private final String playerFour;
    private final int playerFourTeamId;

    public GameFoundData(int gameId, int playerOneId, String playerOne, int playerOneTeamId, int playerTwoId, String playerTwo, int playerTwoTeamId, int playerThreeId, String playerThree, int playerThreeTeamId, int playerFourId, String playerFour, int playerFourTeamId) {
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

    public GameFoundData(JSONObject data) {
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

    public int getGameId() { return gameId; }

    public int getPlayerOneId() { return playerOneId; }

    public String getPlayerOne() { return playerOne; }

    public int getPlayerOneTeamId() { return playerOneTeamId; }

    public int getPlayerTwoId() { return playerTwoId; }

    public String getPlayerTwo() { return playerTwo; }

    public int getPlayerTwoTeamId() { return playerTwoTeamId; }

    public int getPlayerThreeId() { return playerThreeId; }

    public String getPlayerThree() { return playerThree; }

    public int getPlayerThreeTeamId() { return playerThreeTeamId; }

    public int getPlayerFourId() { return playerFourId; }

    public String getPlayerFour() { return playerFour; }

    public int getPlayerFourTeamId() { return playerFourTeamId; }
}
