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

import com.jfoenix.validation.IntegerValidator;
import com.jfoenix.validation.RegexValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import jass.client.validator.IsSameValidator;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.Locale;

/**
 * A helper class for the views, to get elements easily.
 *
 * @author Manuele Vaccari
 * @version %I%, %G%
 * @since 1.0.0
 */
public final class ViewUtil {
    /**
     * Utility classes, which are collections of static members, are not meant
     * to be instantiated.
     */
    private ViewUtil() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * @param space The space to create.
     *
     * @return Returns a region with a space.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    public static Region useSpacer(final int space) {
        Region spacer = new Region();
        spacer.setPrefHeight(space);
        VBox.setVgrow(spacer, Priority.ALWAYS);
        return spacer;
    }

    /**
     * @param translatorKey The key of the translation.
     *
     * @return Returns a text element which is automatically translated.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    public static Text useText(final String translatorKey) {
        Text textField = new Text();
        textField.textProperty().bind(I18nUtil.createStringBinding(translatorKey));
        return textField;
    }

    /**
     * @param translatorKey The key of the translation.
     *
     * @return Returns the "required" validator and which is automatically
     * translated.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    public static RequiredFieldValidator useRequiredValidator(final String translatorKey) {
        RequiredFieldValidator requiredValidator = new RequiredFieldValidator();
        requiredValidator.messageProperty().bind(I18nUtil.createStringBinding(translatorKey));
        return requiredValidator;
    }

    /**
     * @param translatorKey The key of the translation.
     *
     * @return Returns the "is integer" validator and which is automatically
     * translated.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    public static IntegerValidator useIsIntegerValidator(final String translatorKey) {
        IntegerValidator isIntValidator = new IntegerValidator();
        isIntValidator.messageProperty().bind(I18nUtil.createStringBinding(translatorKey));
        return isIntValidator;
    }

    /**
     * @param validateTo    The element to compare to.
     * @param translatorKey The key of the translation.
     *
     * @return Returns the "is same" validator and which is automatically
     * translated.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    public static IsSameValidator useIsSameValidator(final TextInputControl validateTo, final String translatorKey) {
        IsSameValidator isSameValidator = new IsSameValidator(validateTo);
        isSameValidator.messageProperty().bind(I18nUtil.createStringBinding(translatorKey));
        return isSameValidator;
    }

    /**
     * @param translatorKey The key of the translation.
     *
     * @return Returns the "is valid ip" validator and which is automatically
     * translated.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    public static RegexValidator useIsValidIpValidator(final String translatorKey) {
        RegexValidator isValidIpValidator = new RegexValidator();
        isValidIpValidator.messageProperty().bind(I18nUtil.createStringBinding(translatorKey));
        isValidIpValidator.setRegexPattern("^((0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)\\.){3}(0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)$");
        return isValidIpValidator;
    }

    /**
     * @param translatorKey The key of the translation.
     *
     * @return Returns the "is valid port" validator and which is automatically
     * translated.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    public static RegexValidator useIsValidPortValidator(final String translatorKey) {
        RegexValidator isValidPortValidator = new RegexValidator();
        isValidPortValidator.messageProperty().bind(I18nUtil.createStringBinding(translatorKey));
        isValidPortValidator.setRegexPattern("^([0-9]{1,4}|[1-5][0-9]{4}|6[0-4][0-9]{3}|65[0-4][0-9]{2}|655[0-2][0-9]|6553[0-5])$");
        return isValidPortValidator;
    }

    /**
     * @param changeLanguageMenu The change language menu element.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    public static void useLanguageMenuContent(final Menu changeLanguageMenu) {
        for (Locale locale : I18nUtil.getSupportedLocales()) {
            StringProperty langInLocale = new SimpleStringProperty();
            String lang;
            switch (locale.getLanguage()) {
                default:
                case "en":
                    langInLocale.bind(I18nUtil.createStringBinding("gui.menu.file.changeLanguage.english"));
                    lang = "English";
                    break;
                case "de":
                    langInLocale.bind(I18nUtil.createStringBinding("gui.menu.file.changeLanguage.german"));
                    lang = "Deutsch";
                    break;
                case "fr":
                    langInLocale.bind(I18nUtil.createStringBinding("gui.menu.file.changeLanguage.french"));
                    // TODO: Can't use special characters
                    //lang = "Français";
                    lang = "Francais";
                    break;
                case "it":
                    langInLocale.bind(I18nUtil.createStringBinding("gui.menu.file.changeLanguage.italian"));
                    lang = "Italiano";
                    break;
            }

            MenuItem language = new MenuItem();
            language.textProperty().bind(Bindings.concat(langInLocale, " (", lang, ")"));
            language.setOnAction(event -> I18nUtil.setLocale(locale));
            changeLanguageMenu.getItems().add(language);
        }
    }
}
