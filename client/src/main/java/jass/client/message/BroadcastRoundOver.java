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
import jass.lib.message.BroadcastRoundOverData;
import jass.lib.message.MessageData;

/**
 * @author Victor Hargrave
 * @version %I%, %G%
 * @since 1.0.0
 */
public final class BroadcastRoundOver extends Message {
    /**
     * The data of the message.
     */
    private final BroadcastRoundOverData data;

    /**
     * @param rawData The data (still not casted)
     *
     * @author Victor Hargrave
     * @since 1.0.0
     */
    public BroadcastRoundOver(final MessageData rawData) {
        super(rawData);
        data = (BroadcastRoundOverData) rawData;
    }

    /**
     * @author Victor Hargrave
     * @since 1.0.0
     */
    @Override
    public boolean process(final SocketUtil socket) {
        // No processing required. Is handled in event handlers.
        return true;
    }
}
