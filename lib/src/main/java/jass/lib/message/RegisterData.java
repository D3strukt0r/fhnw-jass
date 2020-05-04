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
 * The data model for the create login message.
 *
 * @author Manuele Vaccari
 * @version %I%, %G%
 * @since 0.0.1
 */
public final class RegisterData extends MessageData {
    public enum Result {
        /**
         * The username is too short.
         */
        USERNAME_TOO_SHORT,

        /**
         * The password is too short.
         */
        PASSWORD_TOO_SHORT,

        /**
         * Username is already in use.
         */
        USERNAME_ALREADY_EXISTS,

        /**
         * Server error, like couldn't save to database.
         */
        SERVER_ERROR
    }

    /**
     * The username.
     */
    private final String username;

    /**
     * The password.
     */
    private final String password;

    /**
     * @param username The username.
     * @param password The password.
     */
    public RegisterData(final String username, final String password) {
        super("Register");
        this.username = username;
        this.password = password;
    }

    /**
     * @param data The message containing all the data.
     */
    public RegisterData(final JSONObject data) {
        super(data);
        username = data.getString("username");
        password = data.getString("password");
    }

    /**
     * @return Returns the username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * @return Returns the password.
     */
    public String getPassword() {
        return password;
    }
}
