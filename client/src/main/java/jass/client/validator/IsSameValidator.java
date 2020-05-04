/*
 * fhnw-jass is jass game programmed in java for a school project.
 * Copyright (C) 2020 Manuele Vaccari & Victor Hargrave & Thomas Weber & Sasa Trajkova
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

package jass.client.validator;

import com.jfoenix.validation.base.ValidatorBase;
import javafx.scene.control.TextInputControl;

/**
 * Custom validator for the JFoenix library, which checks if the content of two
 * input fields are the same.
 *
 * @author Manuele Vaccari
 * @version %I%, %G%
 * @since 0.0.1
 */
public final class IsSameValidator extends ValidatorBase {
    /**
     * The object which is to be validated to.
     */
    private final TextInputControl validateTo;

    /**
     * @param validateTo The object which is to be validated to.
     */
    public IsSameValidator(final TextInputControl validateTo) {
        this.setMessage("Values are not the same");
        this.validateTo = validateTo;
    }

    /**
     * @param validateTo The object which is to be validated to.
     * @param message    A custom message to display.
     */
    public IsSameValidator(final TextInputControl validateTo, final String message) {
        super(message);
        this.validateTo = validateTo;
    }

    /**
     * Validate the fields by comparing two fields.
     */
    protected void eval() {
        if (this.srcControl.get() instanceof TextInputControl) {
            TextInputControl textField = (TextInputControl) this.srcControl.get();
            String text = textField.getText();
            String validateToText = validateTo.getText();

            try {
                this.hasErrors.set(false);
                if (!text.isEmpty() && !validateToText.isEmpty()) {
                    if (!text.equals(validateToText)) {
                        this.hasErrors.set(true);
                    }
                }
            } catch (Exception var4) {
                this.hasErrors.set(true);
            }
        }
    }
}
