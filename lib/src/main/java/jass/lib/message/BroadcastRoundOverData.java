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

import org.json.JSONObject;

/**
 * The data model for the BroadcastPointsData message.
 *
 * @author Victor Hargrave
 */
public final class BroadcastRoundOverData extends MessageData {
    private final int roundId;

    private final int team1Points;

    private final int team2Points;

    private final String team1Player1;

    private final String team1Player2;

    private final String team2Player1;

    private final String team2Player2;

    public BroadcastRoundOverData(int roundId, int team1Points, int team2Points,
                               String team1Player1, String team1Player2,
                               String team2Player1, String team2Player2) {
        super("BroadcastRoundOver");
        this.roundId = roundId;
        this.team1Points = team1Points;
        this.team2Points = team2Points;
        this.team1Player1 = team1Player1;
        this.team1Player2 = team1Player2;
        this.team2Player1 = team2Player1;
        this.team2Player2 = team2Player2;
    }

    /**
     * @param data The message containing all the data.
     */
    public BroadcastRoundOverData(final JSONObject data) {
        super(data);
        this.roundId = data.getInt("roundId");;
        this.team1Points = data.getInt("team1Points");
        this.team2Points = data.getInt("team2Points");
        this.team1Player1 = data.getString("team1Player1");
        this.team1Player2 = data.getString("team1Player2");
        this.team2Player1 = data.getString("team2Player1");
        this.team2Player2 = data.getString("team2Player2");
    }

    /**
     * The deck.
     */
    public int getRoundId() {
        return roundId;
    }

    public int getTeam1Points() {
        return team1Points;
    }

    public int getTeam2Points() {
        return team2Points;
    }

    public String getTeam1Player1() {
        return team1Player1;
    }

    public String getTeam1Player2() {
        return team1Player2;
    }

    public String getTeam2Player1() {
        return team2Player1;
    }

    public String getTeam2Player2() {
        return team2Player2;
    }
}
