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
 * @since 0.0.1
 */
public final class BroadcastTurnData extends MessageData {

    private final int turnId;

    private final String startingPlayer;

    private final String winningPlayer;

    private final JSONArray playedCards;

    private ArrayList<CardData> playedCardsClient = null;

    public BroadcastTurnData(final int turnId,
                             final String startingPlayer,
                             final String winningPlayer,
                             final List<CardData> playedCards) {
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

    public int getTurnId() {
        return turnId;
    }

    public String getStartingPlayer() {
        return startingPlayer;
    }

    public String getWinningPlayer() {
        return winningPlayer;
    }

    public JSONArray getPlayedCards() {
        return playedCards;
    }

    public ArrayList<CardData> getPlayedCardsClient() {
        return playedCardsClient;
    }
}
