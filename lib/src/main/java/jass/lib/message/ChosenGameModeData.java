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

import jass.lib.Card;
import jass.lib.GameMode;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Manuele Vaccari
 * @version %I%, %G%
 * @since 1.0.0
 */
public final class ChosenGameModeData extends MessageData {
    /**
     * The token for the current session.
     */
    private final GameMode gameMode;

    /**
     * The suit to be the trumpf, if game mode trumpf is chosen.
     */
    private final Card.Suit trumpfSuit;

    /**
     * The token of the current session.
     */
    private final String token;

    /**
     * This is used by the client when answering the server what game mode to
     * use.
     *
     * @param id       The ID.
     * @param token    The token for the current session.
     * @param gameMode The game mode.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    public ChosenGameModeData(final int id, final String token, final GameMode gameMode) {
        super(id, "ChosenGameMode");
        this.token = token;
        this.gameMode = gameMode;
        this.trumpfSuit = null;
    }

    /**
     * This is used by the client when answering the server what game mode to
     * use. And the trumpf card.
     *
     * @param id         The ID.
     * @param token      The token for the current session.
     * @param gameMode   The game mode.
     * @param trumpfSuit The trumpf suit.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    public ChosenGameModeData(final int id, final String token, final GameMode gameMode, final Card.Suit trumpfSuit) {
        super(id, "ChosenGameMode");
        this.token = token;
        this.gameMode = gameMode;
        this.trumpfSuit = trumpfSuit;
    }

    /**
     * @param data The message containing all the data.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    public ChosenGameModeData(final JSONObject data) {
        super(data);
        token = data.getString("token");
        gameMode = data.getEnum(GameMode.class, "gameMode");
        Card.Suit trumpfSuitTemp;
        try {
            trumpfSuitTemp = data.getEnum(Card.Suit.class, "trumpfSuit");
        } catch (JSONException exception) {
            trumpfSuitTemp = null;
        }
        trumpfSuit = trumpfSuitTemp;
    }

    /**
     * @return Returns the token.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    public String getToken() {
        return token;
    }

    /**
     * @return Returns the game mode.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    public GameMode getGameMode() {
        return gameMode;
    }

    /**
     * @return Returns the trumpf suit.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    public Card.Suit getTrumpfSuit() {
        return trumpfSuit;
    }
}
