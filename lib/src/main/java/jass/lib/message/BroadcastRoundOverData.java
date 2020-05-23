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
 * @version %I%, %G%
 * @since 1.0.0
 */
public final class BroadcastRoundOverData extends MessageData {
    /**
     * The round.
     */
    private final int roundId;

    /**
     * The points of team one.
     */
    private final int team1Points;

    /**
     * The points of team two.
     */
    private final int team2Points;

    /**
     * The username of player one of team one.
     */
    private final String team1Player1;

    /**
     * The username of player two of team one.
     */
    private final String team1Player2;

    /**
     * The username of player one of team two.
     */
    private final String team2Player1;

    /**
     * The username of player two of team two.
     */
    private final String team2Player2;

    /**
     * @param roundId      The round.
     * @param team1Points  The points of team one.
     * @param team2Points  The points of team two.
     * @param team1Player1 The username of player one of team one.
     * @param team1Player2 The username of player two of team one.
     * @param team2Player1 The username of player one of team two.
     * @param team2Player2 The username of player two of team two.
     *
     * @author Victor Hargrave
     * @since 1.0.0
     */
    public BroadcastRoundOverData(final int roundId, final int team1Points, final int team2Points, final String team1Player1, final String team1Player2, final String team2Player1, final String team2Player2) {
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
     *
     * @author Victor Hargrave
     * @since 1.0.0
     */
    public BroadcastRoundOverData(final JSONObject data) {
        super(data);
        this.roundId = data.getInt("roundId");
        this.team1Points = data.getInt("team1Points");
        this.team2Points = data.getInt("team2Points");
        this.team1Player1 = data.getString("team1Player1");
        this.team1Player2 = data.getString("team1Player2");
        this.team2Player1 = data.getString("team2Player1");
        this.team2Player2 = data.getString("team2Player2");
    }

    /**
     * @return Returns the round id
     *
     * @author Victor Hargrave
     * @since 1.0.0
     */
    public int getRoundId() {
        return roundId;
    }

    /**
     * @return Returns the points of team one.
     *
     * @author Victor Hargrave
     * @since 1.0.0
     */
    public int getTeam1Points() {
        return team1Points;
    }

    /**
     * @return Returns the points of team two.
     *
     * @author Victor Hargrave
     * @since 1.0.0
     */
    public int getTeam2Points() {
        return team2Points;
    }

    /**
     * @return Returns the username of player one of team one.
     *
     * @author Victor Hargrave
     * @since 1.0.0
     */
    public String getTeam1Player1() {
        return team1Player1;
    }

    /**
     * @return Returns the username of player two of team one.
     *
     * @author Victor Hargrave
     * @since 1.0.0
     */
    public String getTeam1Player2() {
        return team1Player2;
    }

    /**
     * @return Returns the username of player one of team two.
     *
     * @author Victor Hargrave
     * @since 1.0.0
     */
    public String getTeam2Player1() {
        return team2Player1;
    }

    /**
     * @return Returns the username of player two of team two.
     *
     * @author Victor Hargrave
     * @since 1.0.0
     */
    public String getTeam2Player2() {
        return team2Player2;
    }
}
