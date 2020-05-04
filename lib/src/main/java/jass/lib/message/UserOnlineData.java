/*
 * fhnw-jass is jass game programmed in java for a school project.
 * Copyright (C) 2020 Manuele Vaccari & Victor Hargrave & Thomas Weber & Sasa Trajkova
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
 * The data model for the user online message.
 *
 * @author Manuele Vaccari
 * @version %I%, %G%
 * @since 0.0.1
 */
public final class UserOnlineData extends MessageData {
    /**
     * The token of the current session.
     */
    private final String token;

    /**
     * The username of the target.
     */
    private final String username;

    /**
     * @param token    The token of the current session.
     * @param username The username of the target.
     */
    public UserOnlineData(final String token, final String username) {
        super("UserOnline");
        this.token = token;
        this.username = username;
    }

    /**
     * @param data The message containing all the data.
     */
    public UserOnlineData(final JSONObject data) {
        super(data);
        token = data.getString("token");
        username = data.getString("username");
    }

    /**
     * @return Returns the token.
     */
    public String getToken() {
        return token;
    }

    /**
     * @return Returns the username.
     */
    public String getUsername() {
        return username;
    }
}
