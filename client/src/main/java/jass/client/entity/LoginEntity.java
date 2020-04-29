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
 * @since 0.0.1
 */
@DatabaseTable(tableName = "login")
public final class LoginEntity extends Entity implements Service {
    /**
     * The ID.
     */
    @DatabaseField(generatedId = true)
    private int id;

    /**
     * The username.
     */
    @DatabaseField
    private String username;

    /**
     * The password.
     */
    @DatabaseField
    private String password;

    /**
     * The token.
     */
    @DatabaseField
    private String token;

    /**
     * Whether to connect automatically at startup or not.
     */
    @DatabaseField(defaultValue = "false")
    private boolean connectAutomatically = false;

    /**
     * The server that this login is used for.
     */
    @DatabaseField(foreign = true)
    private ServerEntity server;

    /**
     * For ORMLite all persisted classes must define a no-arg constructor with
     * at least package visibility.
     */
    public LoginEntity() {
    }

    /**
     * @return Returns the ID.
     */
    public int getId() {
        return id;
    }

    /**
     * @return Returns the username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username The username.
     *
     * @return Returns the object for further processing.
     */
    public LoginEntity setUsername(final String username) {
        this.username = username;
        return this;
    }

    /**
     * @return Returns the password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password The password.
     *
     * @return Returns the object for further processing.
     */
    public LoginEntity setPassword(final String password) {
        this.password = password;
        return this;
    }

    /**
     * @return Returns the token.
     */
    public String getToken() {
        return token;
    }

    /**
     * @param token The token.
     *
     * @return Returns the object for further processing.
     */
    public LoginEntity setToken(final String token) {
        this.token = token;
        return this;
    }

    /**
     * @return Returns whether to connect automatically.
     */
    public boolean isConnectAutomatically() {
        return connectAutomatically;
    }

    /**
     * @param connectAutomatically Whether to connect automatically.
     *
     * @return Returns the object for further processing.
     */
    public LoginEntity setConnectAutomatically(final boolean connectAutomatically) {
        this.connectAutomatically = connectAutomatically;
        return this;
    }

    /**
     * @return Returns the server this login belongs to.
     */
    public ServerEntity getServer() {
        return server;
    }

    /**
     * @param server The server this login belongs to.
     *
     * @return Returns the object for further processing.
     */
    public LoginEntity setServer(final ServerEntity server) {
        this.server = server;
        return this;
    }
}
