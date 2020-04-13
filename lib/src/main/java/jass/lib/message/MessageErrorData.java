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

package jass.lib.message;

import org.json.JSONObject;

/**
 * The data model for the message error message.
 *
 * @author Manuele Vaccari
 * @version %I%, %G%
 * @since 0.0.1
 */
public class MessageErrorData extends MessageData {
    /**
     * The error type that occurred.
     */
    private final ErrorType errorMessage;

    public enum ErrorType {
        /**
         * The command is not known.
         */
        INVALID_COMMAND
    }

    /**
     * @param errorMessage The error type that occurred.
     */
    public MessageErrorData(final ErrorType errorMessage) {
        super("MessageError");
        this.errorMessage = errorMessage;
    }

    /**
     * @param data The message containing all the data.
     */
    public MessageErrorData(final JSONObject data) {
        super(data);
        errorMessage = data.getEnum(ErrorType.class, "errorMessage");
    }

    /**
     * @return Returns the error type that occurred.
     */
    public ErrorType getErrorMessage() {
        return errorMessage;
    }
}
