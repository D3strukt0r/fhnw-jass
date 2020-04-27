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
public final class ChosenGameModeData extends MessageData {
    /**
     * The token for the current session.
     */
    private final ChooseGameModeData.GameMode gameMode;

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
     */
    public ChosenGameModeData(final int id, final String token, final ChooseGameModeData.GameMode gameMode) {
        super(id, "ChosenGameMode");
        this.token = token;
        this.gameMode = gameMode;
    }

    /**
     * @param data The message containing all the data.
     */
    public ChosenGameModeData(final JSONObject data) {
        super(data);
        token = data.getString("token");
        gameMode = data.getEnum(ChooseGameModeData.GameMode.class, "gameMode");
    }

    /**
     * @return Returns the token.
     */
    public String getToken() {
        return token;
    }

    /**
     * @return Returns the game mode.
     */
    public ChooseGameModeData.GameMode getGameMode() {
        return gameMode;
    }
}
