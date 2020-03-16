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
public class UserEntity {
    private static final Logger logger = LogManager.getLogger(UserEntity.class);

    /**
     * Fields (Columns)
     */
    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(canBeNull = false)
    private String username;

    @DatabaseField(canBeNull = false)
    private String password;

    @DatabaseField(defaultValue = "false", canBeNull = false)
    private boolean online = false;

    @DatabaseField
    private Date lastLogin;

    @DatabaseField(defaultValue = "false", canBeNull = false)
    private boolean friend = false;

    @DatabaseField(defaultValue = "false", canBeNull = false)
    private boolean blocked = false;

    /**
     * Constructors
     */
    UserEntity() {
        // For ORMLite
        // all persisted classes must define a no-arg constructor
        // with at least package visibility
    }

    public UserEntity(String username) {
        this.username = username;
    }

    public UserEntity(String username, String password) {
        this.username = username;
        setPassword(password);
    }

    /**
     * Methods
     */
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
        try {
            this.password = HashUtil.generateStorngPasswordHash(password);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            logger.fatal("Secure password hashing not possible - stopping server");
            System.exit(0);
        }
    }

    public boolean checkPassword(String password) {
        boolean matched = false;

        try {
            matched = HashUtil.validatePassword(password, this.password);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            logger.fatal("Secure password hashing not possible - stopping server");
            System.exit(0);
        }

        if (matched) setLastLogin(Date.from(Instant.now()));
        return matched;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline() {
        this.online = true;
    }

    public void setOffline() {
        this.online = false;
    }

    public void toggleOnline() {
        this.online = !this.online;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public String getLastLoginFormatted() {
        SimpleDateFormat dateTimeFormatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        return dateTimeFormatter.format(lastLogin);
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public boolean isFriend() {
        return friend;
    }

    public void setFriend(boolean friend) {
        this.friend = friend;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    @Override
    public String toString() {
        return username;
    }
}
