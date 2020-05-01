/*
 * fhnw-jass is jass game programmed in java for a school project.
 * Copyright (C) 2020 Manuele Vaccari
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
 * @author Manuele Vaccari
 * @version %I%, %G%
 * @since 0.0.1
 */
public final class PlayedCardData extends MessageData {
    /**
     * The game ID.
     */
    private final int gameId;

    /**
     * The card ID.
     */
    private final int cardId;

    /**
     * This is used by the client when answering the server what card was
     * played.
     *
     * @param gameId The game ID.
     * @param cardId The card ID.
     */
    public PlayedCardData(final int gameId, final int cardId) {
        super("PlayedCard");
        this.gameId = gameId;
        this.cardId = cardId;
    }

    /**
     * @param data The message containing all the data.
     */
    public PlayedCardData(final JSONObject data) {
        super(data);
        gameId = data.getInt("gameId");
        cardId = data.getInt("cardId");
    }

    /**
     * @return Returns game ID.
     */
    public int getGameId() {
        return gameId;
    }

    /**
     * @return Returns card ID.
     */
    public int getCardId() {
        return cardId;
    }
}
