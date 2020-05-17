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
 * @author Victor Hargrave
 * @version %I%, %G%
 * @since 1.0.0
 */
public final class PlayCardData extends MessageData {
    /**
     * The ID of the turn.
     */
    private final int turnId;

    /**
     * The ID of the card.
     */
    private final int cardId;

    /**
     * @param turnId The ID of the turn.
     * @param cardId The ID of the card.
     *
     * @author Victor Hargrave
     * @since 1.0.0
     */
    public PlayCardData(final int turnId, final int cardId) {
        super("PlayCard");
        this.turnId = turnId;
        this.cardId = cardId;
    }

    /**
     * @param data The message containing all the data.
     *
     * @author Victor Hargrave
     * @since 1.0.0
     */
    public PlayCardData(final JSONObject data) {
        super(data);
        turnId = data.getInt("turnId");
        cardId = data.getInt("cardId");
    }

    /**
     * @return Returns the turn ID.
     *
     * @author Victor Hargrave
     * @since 1.0.0
     */
    public int getTurnId() {
        return turnId;
    }

    /**
     * @return Returns the card ID.
     *
     * @author Victor Hargrave
     * @since 1.0.0
     */
    public int getCardId() {
        return cardId;
    }
}
