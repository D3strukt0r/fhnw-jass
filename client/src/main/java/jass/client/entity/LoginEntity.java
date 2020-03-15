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
import jass.lib.servicelocator.Service;

/**
 * A model containing all locally saved login informations.
 *
 * @author Manuele Vaccari
 * @version %I%, %G%
 * @since 0.0.1
 */
@DatabaseTable(tableName = "login")
public class LoginEntity implements Service {
    /**
     * Fields (Columns)
     */
    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField
    private String username;

    @DatabaseField
    private String password;

    @DatabaseField
    private String token;

    @DatabaseField(defaultValue = "false")
    private boolean connectAutomatically = false;

    /**
     * Constructors
     */
    LoginEntity() {
        // For ORMLite
        // all persisted classes must define a no-arg constructor
        // with at least package visibility
    }

    public LoginEntity(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public LoginEntity(String username, String password, boolean connectAutomatically) {
        this.username = username;
        this.password = password;
        this.connectAutomatically = connectAutomatically;
    }

    public LoginEntity(String username, String password, String token) {
        this.username = username;
        this.password = password;
        this.token = token;
    }

    public LoginEntity(String username, String password, String token, boolean connectAutomatically) {
        this.username = username;
        this.password = password;
        this.token = token;
        this.connectAutomatically = connectAutomatically;
    }

    /**
     * Methods
     */
    @Override
    public String getServiceName() {
        return "login";
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isConnectAutomatically() {
        return connectAutomatically;
    }

    public void setConnectAutomatically(boolean connectAutomatically) {
        this.connectAutomatically = connectAutomatically;
    }
}
