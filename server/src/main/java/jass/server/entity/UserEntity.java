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

package jass.server.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import jass.lib.database.Entity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import jass.server.util.HashUtil;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A model with all known (and cached) users.
 *
 * @author Manuele Vaccari
 * @version %I%, %G%
 * @since 0.0.1
 */
@DatabaseTable(tableName = "users")
public final class UserEntity extends Entity {
    /**
     * The logger to print to console and save in a .log file.
     */
    private static final Logger logger = LogManager.getLogger(UserEntity.class);

    /**
     * The ID.
     */
    @DatabaseField(generatedId = true)
    private int id;

    /**
     * The Username.
     */
    @DatabaseField(canBeNull = false, unique = true)
    private String username;

    /**
     * The hashed password.
     */
    @DatabaseField(canBeNull = false)
    private String password;

    /**
     * The token of the last session.
     */
    @DatabaseField
    private String token;

    /**
     * Whether the user is currently online (connected) or not.
     */
    @DatabaseField(defaultValue = "false", canBeNull = false)
    private boolean online = false;

    /**
     * The last time the user logged in.
     */
    @DatabaseField
    private Date lastLogin;

    /**
     * For ORMLite all persisted classes must define a no-arg constructor with
     * at least package visibility.
     */
    public UserEntity() {
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
    public UserEntity setUsername(final String username) {
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
    public UserEntity setPassword(final String password) {
        try {
            this.password = HashUtil.generateStrongPasswordHash(password);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            logger.fatal("Secure password hashing not possible - stopping server");
            System.exit(0);
        }
        return this;
    }

    /**
     * @param password The password.
     *
     * @return Returns true if the password matches the one currently being
     * used, otherwise false.
     */
    public boolean checkPassword(final String password) {
        boolean matched = false;

        try {
            matched = HashUtil.validatePassword(password, this.password);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            logger.fatal("Secure password hashing not possible - stopping server");
            System.exit(0);
        }
        return matched;
    }

    /**
     * @return Returns whether the user is currently online or not.
     */
    public boolean isOnline() {
        return online;
    }

    /**
     * Set the user to being online.
     *
     * @return Returns the object for further processing.
     */
    public UserEntity setOnline() {
        this.online = true;
        return this;
    }

    /**
     * Set the user to being offline.
     *
     * @return Returns the object for further processing.
     */
    public UserEntity setOffline() {
        this.online = false;
        return this;
    }

    /**
     * Switch between online states.
     *
     * @return Returns the object for further processing.
     */
    public UserEntity toggleOnline() {
        this.online = !this.online;
        return this;
    }

    /**
     * @return Returns the last login time.
     */
    public Date getLastLogin() {
        return lastLogin;
    }

    /**
     * @return Returns the last login time as string.
     */
    public String getLastLoginFormatted() {
        SimpleDateFormat dateTimeFormatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        return dateTimeFormatter.format(lastLogin);
    }

    /**
     * @param lastLogin The last login time.
     *
     * @return Returns the object for further processing.
     */
    public UserEntity setLastLogin(final Date lastLogin) {
        this.lastLogin = lastLogin;
        return this;
    }

    /**
     * @param token A session token.
     */
    public void setToken(final String token) {
        this.token = token;
    }

    /**
     * @return Returns the last session token.
     */
    public String getToken() {
        return this.token;
    }
}
