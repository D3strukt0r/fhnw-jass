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
 * The data model for the login message.
 *
 * @author Manuele Vaccari
 * @version %I%, %G%
 * @since 1.0.0
 */
public final class LoginData extends MessageData {
    public enum Result {
        /**
         * The user does not exist.
         */
        USER_DOES_NOT_EXIST,

        /**
         * Client used a different password than stored.
         */
        WRONG_PASSWORD,

        /**
         * User is already logged in, cannot log in more than once.
         */
        USER_ALREADY_LOGGED_IN
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
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    public LoginData(final String username, final String password) {
        super("Login");
        this.username = username;
        this.password = password;
    }

    /**
     * @param data The message containing all the data.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    public LoginData(final JSONObject data) {
        super(data);
        username = data.getString("username");
        password = data.getString("password");
    }

    /**
     * @return Returns the username.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    public String getUsername() {
        return username;
    }

    /**
     * @return Returns the password.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    public String getPassword() {
        return password;
    }
}
