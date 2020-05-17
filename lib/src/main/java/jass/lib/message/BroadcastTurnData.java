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

package jass.lib.message;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Victor Hargrave
 * @version %I%, %G%
 * @since 1.0.0
 */
public final class BroadcastTurnData extends MessageData {
    /**
     * The turn.
     */
    private final int turnId;

    /**
     * The username of the player who starts.
     */
    private final String startingPlayer;

    /**
     * The username of the player who won.
     */
    private final String winningPlayer;

    /**
     * The played cards in the turn.
     */
    private final JSONArray playedCards;

    /**
     * The played cards (unserialized).
     */
    private ArrayList<CardData> playedCardsClient = null;

    /**
     * @param turnId         The turn.
     * @param startingPlayer The username of the player who starts.
     * @param winningPlayer  The username of the player who won.
     * @param playedCards    The played cards in the turn.
     *
     * @author Victor Hargrave
     * @since 1.0.0
     */
    public BroadcastTurnData(final int turnId, final String startingPlayer, final String winningPlayer, final List<CardData> playedCards) {
        super("BroadcastTurn");
        this.turnId = turnId;
        this.startingPlayer = startingPlayer;
        this.winningPlayer = winningPlayer;
        this.playedCards = new JSONArray(playedCards);
    }

    /**
     * @param data The message containing all the data.
     */
    public BroadcastTurnData(final JSONObject data) {
        super(data);
        turnId = data.getInt("turnId");
        startingPlayer = data.getString("startingPlayer");
        winningPlayer = data.getString("winningPlayer");
        playedCards = data.getJSONArray("playedCards");
        playedCardsClient = new ArrayList<>();
        for (int i = 0; i < this.playedCards.length(); i++) {
            JSONObject jsonobject = this.playedCards.getJSONObject(i);
            int cardId = jsonobject.getInt("cardId");
            int suitId = jsonobject.getInt("suitId");
            String suit = jsonobject.getString("suit");
            int rankId = jsonobject.getInt("rankId");
            String rank = jsonobject.getString("rank");

            playedCardsClient.add(new CardData(cardId, suitId, suit, rankId, rank));
        }
    }

    /**
     * @return Returns the turn.
     *
     * @author Victor Hargrave
     * @since 1.0.0
     */
    public int getTurnId() {
        return turnId;
    }

    /**
     * @return Returns the username of the player who starts.
     *
     * @author Victor Hargrave
     * @since 1.0.0
     */
    public String getStartingPlayer() {
        return startingPlayer;
    }

    /**
     * @return Returns the username of the player who won.
     *
     * @author Victor Hargrave
     * @since 1.0.0
     */
    public String getWinningPlayer() {
        return winningPlayer;
    }

    /**
     * @return Returns the played cards in the turn.
     *
     * @author Victor Hargrave
     * @since 1.0.0
     */
    public JSONArray getPlayedCards() {
        return playedCards;
    }

    /**
     * @return Returns the played cards (unserialized).
     *
     * @author Victor Hargrave
     * @since 1.0.0
     */
    public ArrayList<CardData> getPlayedCardsClient() {
        return playedCardsClient;
    }
}
