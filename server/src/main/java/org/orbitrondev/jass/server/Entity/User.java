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

package org.orbitrondev.jass.server.Entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;
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
public class User {
    private static final Logger logger = LogManager.getLogger(User.class);
    private static final int iterations = 127;
    private final byte[] salt = new byte[64];

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
    User() {
        // For ORMLite
        // all persisted classes must define a no-arg constructor
        // with at least package visibility
    }
    public User(String username) {
        this.username = username;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean checkPassword(String password) {
        String newHash = hash(password);
        boolean success = password.equals(newHash);
        if (success) setLastLogin(Date.from(Instant.now()));;
        return success;
    }

    public void changePassword(String newPassword) {
        SecureRandom rand = new SecureRandom();
        rand.nextBytes(salt); // Change the salt with the password!
        setPassword(hash(newPassword));
    }

    // TODO: Put this somewhere else
    public static String createToken() {
        SecureRandom rand = new SecureRandom();
        byte[] token = new byte[16];
        rand.nextBytes(token);
        return bytesToHex(token);
    }

    /**
     * There are many sources of info on how to securely hash passwords. I'm not a
     * crypto expert, so I follow the recommendations of the experts. Here are two
     * examples:
     *
     * https://crackstation.net/hashing-security.htm
     *
     * https://howtodoinjava.com/security/how-to-generate-secure-password-hash-md5-sha-pbkdf2-bcrypt-examples/
     */
    private String hash(String password) {
        try {
            char[] chars = password.toCharArray();
            PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 64 * 8);
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1"); // TODO: Should this be here?
            byte[] hash = skf.generateSecret(spec).getEncoded();
            return bytesToHex(hash);
        } catch (Exception e) {
            logger.fatal("Secure password hashing not possible - stopping server");
            System.exit(0);
            return null; // Will never execute, but keeps Java happy
        }
    }

    // From:
    // https://stackoverflow.com/questions/9655181/how-to-convert-a-byte-array-to-a-hex-string-in-java
    private final static char[] hexArray = "0123456789ABCDEF".toCharArray();

    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
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
