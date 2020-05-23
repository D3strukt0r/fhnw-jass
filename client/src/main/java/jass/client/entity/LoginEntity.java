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

package jass.client.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import jass.lib.database.Entity;
import jass.lib.servicelocator.Service;

/**
 * A model containing all locally saved login information.
 *
 * @author Manuele Vaccari
 * @version %I%, %G%
 * @since 1.0.0
 */
@DatabaseTable(tableName = "login")
public final class LoginEntity extends Entity implements Service {
    /**
     * The ID.
     */
    @DatabaseField(generatedId = true)
    private int id;

    /**
     * The field name for username.
     */
    public static final String USERNAME_FIELD_NAME = "username";

    /**
     * The username.
     */
    @DatabaseField(columnName = USERNAME_FIELD_NAME, uniqueCombo = true, canBeNull = false)
    private String username;

    /**
     * The field name for password.
     */
    public static final String PASSWORD_FIELD_NAME = "password";

    /**
     * The password.
     */
    @DatabaseField(columnName = PASSWORD_FIELD_NAME, canBeNull = false)
    private String password;

    /**
     * The field name for remember me.
     */
    public static final String REMEMBER_ME_FIELD_NAME = "remember_me";

    /**
     * Whether to connect automatically at startup or not.
     */
    @DatabaseField(columnName = REMEMBER_ME_FIELD_NAME, defaultValue = "false")
    private boolean rememberMe = false;

    /**
     * The field name for server.
     */
    public static final String SERVER_FIELD_NAME = "server";

    /**
     * The server that this login is used for.
     */
    @DatabaseField(foreign = true, columnName = SERVER_FIELD_NAME, uniqueCombo = true, canBeNull = false)
    private ServerEntity server;

    /**
     * The token.
     */
    private String token;

    /**
     * For ORMLite all persisted classes must define a no-arg constructor with
     * at least package visibility.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    public LoginEntity() {
    }

    /**
     * @return Returns the ID.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    public int getId() {
        return id;
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
     * @param username The username.
     *
     * @return Returns the object for further processing.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    public LoginEntity setUsername(final String username) {
        this.username = username;
        return this;
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

    /**
     * @param password The password.
     *
     * @return Returns the object for further processing.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    public LoginEntity setPassword(final String password) {
        this.password = password;
        return this;
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
     * @param token The token.
     *
     * @return Returns the object for further processing.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    public LoginEntity setToken(final String token) {
        this.token = token;
        return this;
    }

    /**
     * @return Returns whether to connect automatically.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    public boolean isRememberMe() {
        return rememberMe;
    }

    /**
     * @param rememberMe Whether to connect automatically.
     *
     * @return Returns the object for further processing.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    public LoginEntity setRememberMe(final boolean rememberMe) {
        this.rememberMe = rememberMe;
        return this;
    }

    /**
     * @return Returns the server this login belongs to.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    public ServerEntity getServer() {
        return server;
    }

    /**
     * @param server The server this login belongs to.
     *
     * @return Returns the object for further processing.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    public LoginEntity setServer(final ServerEntity server) {
        this.server = server;
        return this;
    }
}
