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
 * @author Manuele Vaccari
 */
public final class BroadcastPointsData extends MessageData {
    /**
     * The deck.
     */
    private final int turnId;

    /**
     * The cards.
     */
    private final int points;

    /**
     * @param turnId The deck ID.
     * @param points  The cards.
     */
    public BroadcastPointsData(final int turnId, final int points) {
        super("BroadcastPoints");
        this.turnId = turnId;
        this.points = points;
    }

    /**
     * @param data The message containing all the data.
     */
    public BroadcastPointsData(final JSONObject data) {
        super(data);
        turnId = data.getInt("turnId");
        points = data.getInt("points");
    }

    /**
     * @return Returns the turn ID.
     */
    public int getTurnId() {
        return turnId;
    }

    /**
     * @return Returns the points.
     */
    public int getPoints() {
        return points;
    }
}
