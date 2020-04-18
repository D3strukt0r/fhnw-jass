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
import java.time.Instant;
import java.util.Date;

/**
 * A model with all known (and cached) users.
 *
 * @author Manuele Vaccari
 * @version %I%, %G%
 * @since 0.0.1
 */
@DatabaseTable(tableName = "users")
public class UserEntity implements Entity {
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
    @DatabaseField(canBeNull = false)
    private String username;

    /**
     * The hashed password.
     */
    @DatabaseField(canBeNull = false)
    private String password;

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

    // TODO: This field should be a Many-To-Many between User and User.
    @DatabaseField(defaultValue = "false", canBeNull = false)
    private boolean friend = false;

    // TODO: This field should be a Many-To-Many between User and User.
    @DatabaseField(defaultValue = "false", canBeNull = false)
    private boolean blocked = false;

    /**
     * For ORMLite all persisted classes must define a no-arg constructor with
     * at least package visibility.
     */
    UserEntity() {
    }

    /**
     * @param username The username.
     */
    public UserEntity(final String username) {
        this.username = username;
    }

    /**
     * @param username The username.
     * @param password The password.
     */
    public UserEntity(final String username, final String password) {
        this.username = username;
        setPassword(password);
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
     */
    public void setUsername(final String username) {
        this.username = username;
    }

    /**
     * @return Returns the password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password The password.
     */
    public void setPassword(final String password) {
        try {
            this.password = HashUtil.generateStrongPasswordHash(password);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            logger.fatal("Secure password hashing not possible - stopping server");
            System.exit(0);
        }
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

        if (matched) {
            setLastLogin(Date.from(Instant.now()));
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
     */
    public void setOnline() {
        this.online = true;
    }

    /**
     * Set the user to being offline.
     */
    public void setOffline() {
        this.online = false;
    }

    /**
     * Switch between online states.
     */
    public void toggleOnline() {
        this.online = !this.online;
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
     */
    public void setLastLogin(final Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    // TODO: This shouldn't be here.
    public boolean isFriend() {
        return friend;
    }

    // TODO: This shouldn't be here.
    public void setFriend(final boolean friend) {
        this.friend = friend;
    }

    // TODO: This shouldn't be here.
    public boolean isBlocked() {
        return blocked;
    }

    // TODO: This shouldn't be here.
    public void setBlocked(final boolean blocked) {
        this.blocked = blocked;
    }

    // TODO: Is this required?
    @Override
    public String toString() {
        return username;
    }
}
