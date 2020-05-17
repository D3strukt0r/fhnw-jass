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
 * The data model for the change password message.
 *
 * @author Manuele Vaccari
 * @version %I%, %G%
 * @since 1.0.0
 */
public final class ChangePasswordData extends MessageData {
    /**
     * The token for the current session.
     */
    private final String token;

    /**
     * The new password.
     */
    private final String newPassword;

    /**
     * @param token       The token for the current session.
     * @param newPassword The new password.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    public ChangePasswordData(final String token, final String newPassword) {
        super("ChangePassword");
        this.token = token;
        this.newPassword = newPassword;
    }

    /**
     * @param data The message containing all the data.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    public ChangePasswordData(final JSONObject data) {
        super(data);
        token = data.getString("token");
        newPassword = data.getString("newPassword");
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
     * @return Returns the new password.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    public String getNewPassword() {
        return newPassword;
    }
}
