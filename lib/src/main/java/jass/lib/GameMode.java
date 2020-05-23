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

package jass.lib;

/**
 * @author Manuele Vaccari
 * @version %I%, %G%
 * @since 1.0.0
 */
public enum GameMode {
    /**
     * Trumpf game mode.
     */
    TRUMPF,

    /**
     * Obe abe game mode.
     */
    OBE_ABE,

    /**
     * Onde ufe game mode.
     */
    ONDE_UFE;

    /**
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    @Override
    public String toString() {
        switch (this) {
            case TRUMPF:
                return "Trumpf";
            case OBE_ABE:
                return "Obe Abe";
            case ONDE_UFE:
                return "Onde Ufe";
            default:
                throw new IllegalStateException("Unexpected value: " + this);
        }
    }

    /**
     * @param string The game mode as string.
     *
     * @return Returns the corresponding game mode object.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    public static GameMode fromString(final String string) {
        switch (string) {
            case "Trumpf":
                return TRUMPF;
            case "Obe Abe":
                return OBE_ABE;
            case "Onde Ufe":
                return ONDE_UFE;
            default:
                throw new IllegalStateException("Unexpected value: " + string);
        }
    }
}
