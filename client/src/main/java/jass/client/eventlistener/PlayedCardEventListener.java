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

package jass.client.eventlistener;

import jass.lib.message.PlayedCardData;

/**
 * @author Manuele Vaccari
 * @version %I%, %G%
 * @since 1.0.0
 */
public interface PlayedCardEventListener {
    /**
     * Executes when a user is requested to play a card.
     *
     * @param data The data (basically the ID).
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    void onPlayedCard(PlayedCardData data);
}
