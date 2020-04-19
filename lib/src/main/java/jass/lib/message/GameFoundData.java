package jass.lib.message;

import org.json.JSONObject;

/**
 * The data model for the Game Found message.
 *
 * @author Thomas Weber
 */

public class GameFoundData extends MessageData {
    private final String token;

    private final int playerOneId;
    private final String playerOne;

    private final int playerTwoId;
    private final String playerTwo;

    private final int playerThreeId;
    private final String playerThree;

    private final int playerFourId;
    private final String playerFour;

    public GameFoundData(String token, int playerOneId, String playerOne, int playerTwoId, String playerTwo, int playerThreeId, String playerThree, int playerFourId, String playerFour) {
        super("GameFound");

        this.token = token;
        this.playerOneId = playerOneId;
        this.playerOne = playerOne;
        this.playerTwoId = playerTwoId;
        this.playerTwo = playerTwo;
        this.playerThreeId = playerThreeId;
        this.playerThree = playerThree;
        this.playerFourId = playerFourId;
        this.playerFour = playerFour;
    }

    public GameFoundData(JSONObject data) {
        super(data);
        token = data.getString("token");
        playerOneId = data.getInt("playerOneId");
        playerOne = data.getString("playerOne");
        playerTwoId = data.getInt("playerTwoId");
        playerTwo = data.getString("playerTwo");
        playerThreeId = data.getInt("playerThreeId");
        playerThree = data.getString("playerThree");
        playerFourId = data.getInt("playerFourId");
        playerFour = data.getString("playerFour");
    }

    public String getToken() { return token; }

    public String getPlayerOne() { return playerOne; }

    public String getPlayerTwo() { return playerTwo; }

    public String getPlayerThree() { return playerThree; }

    public String getPlayerFour() { return playerFour; }
}
