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

package jass.server.message;

import jass.lib.message.MessageData;
import jass.server.util.ClientUtil;

/**
 * @author Thomas Weber
 * @version %I%, %G%
 * @since 1.0.0
 */
public final class ContinuePlaying extends Message {
    /**
     * @param rawData The data (still not casted).
     *
     * @author Thomas Weber
     * @since 1.0.0
     */
    public ContinuePlaying(final MessageData rawData) {
        super(rawData);
    }

    /**
     * No processing required. Is handled in event handlers.
     *
     * @author Thomas Weber
     * @since 1.0.0
     */
    @Override
    public void process(final ClientUtil client) {
    }
}
