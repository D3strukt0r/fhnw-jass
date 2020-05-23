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

package jass.server.util;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

/**
 * There are many sources of info on how to securely hash passwords. I'm not a
 * crypto expert, so I follow the recommendations of the experts. Here are two
 * examples:
 * <p>
 * https://crackstation.net/hashing-security.htm
 *
 * @author https://howtodoinjava.com/security/how-to-generate-secure-password-hash-md5-sha-pbkdf2-bcrypt-examples/
 * @version %I%, %G%
 * @since 1.0.0
 */
public final class HashUtil {
    /**
     * Utility classes, which are collections of static members, are not meant
     * to be instantiated.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    private HashUtil() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * @param password The password to hash.
     *
     * @return Returns a hashed password.
     *
     * @throws NoSuchAlgorithmException Some security error.
     * @throws InvalidKeySpecException  Some security error.
     * @since 1.0.0
     */
    public static String generateStrongPasswordHash(final String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        int iterations = 1000;
        char[] chars = password.toCharArray();
        byte[] salt = getSalt();

        PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 64 * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = skf.generateSecret(spec).getEncoded();
        return iterations + ":" + toHex(salt) + ":" + toHex(hash);
    }

    /**
     * @return Returns the salt.
     *
     * @throws NoSuchAlgorithmException Some security error.
     * @since 1.0.0
     */
    private static byte[] getSalt() throws NoSuchAlgorithmException {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt;
    }

    /**
     * @param array The array to stringify.
     *
     * @return Returns the hex string.
     *
     * @since 1.0.0
     */
    private static String toHex(final byte[] array) {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if (paddingLength > 0) {
            return String.format("%0" + paddingLength + "d", 0) + hex;
        } else {
            return hex;
        }
    }

    /**
     * @param originalPassword The password to checked.
     * @param storedPassword   The password to check against.
     *
     * @return Returns true if it's the same, otherwise false.
     *
     * @throws NoSuchAlgorithmException Some security error.
     * @throws InvalidKeySpecException  Some security error.
     * @since 1.0.0
     */
    public static boolean validatePassword(final String originalPassword, final String storedPassword) throws NoSuchAlgorithmException, InvalidKeySpecException {
        String[] parts = storedPassword.split(":");
        int iterations = Integer.parseInt(parts[0]);
        byte[] salt = fromHex(parts[1]);
        byte[] hash = fromHex(parts[2]);

        PBEKeySpec spec = new PBEKeySpec(originalPassword.toCharArray(), salt, iterations, hash.length * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] testHash = skf.generateSecret(spec).getEncoded();

        int diff = hash.length ^ testHash.length;
        for (int i = 0; i < hash.length && i < testHash.length; i++) {
            diff |= hash[i] ^ testHash[i];
        }
        return diff == 0;
    }

    /**
     * @param hex The string to make to a hex string.
     *
     * @return Returns a hex string.
     *
     * @since 1.0.0
     */
    private static byte[] fromHex(final String hex) {
        byte[] bytes = new byte[hex.length() / 2];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return bytes;
    }
}
