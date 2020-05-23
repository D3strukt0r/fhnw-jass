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

package jass.client.util;

/**
 * @author ...
 * @version %I%, %G%
 * @since 1.0.0
 */
public final class StringUtil {
    /**
     * Utility classes, which are collections of static members, are not meant
     * to be instantiated.
     */
    private StringUtil() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * @param str Any string.
     *
     * @return Returns true if the object is null or empty, otherwise false.
     *
     * @author ...
     * @since 1.0.0
     */
    public static boolean isNullOrEmpty(final String str) {
        return str == null || str.isEmpty();
    }
}
