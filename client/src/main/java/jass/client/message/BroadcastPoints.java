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

package jass.client.message;

import jass.client.util.SocketUtil;
import jass.lib.message.BroadcastPointsData;
import jass.lib.message.MessageData;

/**
 * Receives message for gained points in a turn.
 *
 * @author Manuele Vaccari
 * @version %I%, %G%
 * @since 1.0.0
 */
public final class BroadcastPoints extends Message {
    /**
     * The data of the message.
     */
    private final BroadcastPointsData data;

    /**
     * @param rawData The data (still not casted)
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    public BroadcastPoints(final MessageData rawData) {
        super(rawData);
        data = (BroadcastPointsData) rawData;
    }

    /**
     * No processing required. Is handled in event handlers.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    @Override
    public boolean process(final SocketUtil socket) {
        return false;
    }

    /**
     * @return Returns the data.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    public BroadcastPointsData getData() {
        return data;
    }
}
