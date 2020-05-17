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
 * The data model for the BroadcastDeckData message.
 *
 * @author Victor Hargrave
 * @version %I%, %G%
 * @since 1.0.0
 */
public final class BroadcastGameModeData extends MessageData {
    /**
     * The token for the current session.
     */
    private final GameMode gameMode;

    /**
     * The suit to be the trumpf, if game mode trumpf is chosen.
     */
    private final Card.Suit trumpfSuit;

    /**
     * @param gameMode The game mode.
     *
     * @author Victor Hargrave
     * @since 1.0.0
     */
    public BroadcastGameModeData(final GameMode gameMode) {
        super("BroadcastGameMode");
        this.gameMode = gameMode;
        trumpfSuit = null;
    }

    /**
     * @param gameMode The game mode.
     *
     * @author Victor Hargrave
     * @since 1.0.0
     */
    public BroadcastGameModeData(final GameMode gameMode, final Card.Suit trumpfSuit) {
        super("BroadcastGameMode");
        this.gameMode = gameMode;
        this.trumpfSuit = trumpfSuit;
    }

    /**
     * @param data The message containing all the data.
     *
     * @author Victor Hargrave
     * @since 1.0.0
     */
    public BroadcastGameModeData(final JSONObject data) {
        super(data);
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
     * @return Returns the game mode.
     *
     * @author Victor Hargrave
     * @since 1.0.0
     */
    public GameMode getGameMode() {
        return gameMode;
    }

    /**
     * @return Returns the trumpf suit.
     *
     * @author Victor Hargrave
     * @since 1.0.0
     */
    public Card.Suit getTrumpfSuit() {
        return trumpfSuit;
    }
}
