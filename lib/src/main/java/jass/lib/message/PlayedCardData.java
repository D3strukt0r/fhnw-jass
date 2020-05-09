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
 * @author Victor Hargrave & Manuele Vaccari
 * @version %I%, %G%
 * @since 0.0.1
 */
public final class PlayedCardData extends MessageData {
    /**
     * The game ID.
     */
    private final boolean playedCardValid;

    /**
     * This is used by the server to notify the client to give an appropriate
     * answer back.
     */
    public PlayedCardData(boolean playedCardValid) {
        super("PlayedCard");
        this.playedCardValid = playedCardValid;
    }

    /**
     * @param data The message containing all the data.
     */
    public PlayedCardData(final JSONObject data) {
        super(data);
        this.playedCardValid = data.getBoolean("playedCardValid");
    }

    public boolean getPlayedCardValid() {
        return playedCardValid;
    }
}
