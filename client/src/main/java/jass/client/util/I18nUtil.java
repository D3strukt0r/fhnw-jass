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

import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;

/**
 * I18N utility class..
 * <p>
 * Copyright (c) 2016 sothawo
 * <p>
 * https://www.sothawo.com/2016/09/how-to-implement-a-javafx-ui-where-the-language-can-be-changed-dynamically/
 *
 * @author P.J. Meisch (pj.meisch@sothawo.com).
 * @since 0.0.1
 */
public final class I18nUtil {
    /**
     * Utility classes, which are collections of static members, are not meant
     * to be instantiated.
     */
    private I18nUtil() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * The current selected Locale.
     */
    private static final ObjectProperty<Locale> locale;

    static {
        locale = new SimpleObjectProperty<>(getDefaultLocale());
        locale.addListener((observable, oldValue, newValue) -> Locale.setDefault(newValue));
    }

    /**
     * Get the supported Locales.
     *
     * @return List of Locale objects.
     *
     * @since 0.0.1
     */
    public static List<Locale> getSupportedLocales() {
        return new ArrayList<>(Arrays.asList(
            Locale.GERMAN,
            Locale.ENGLISH,
            Locale.FRENCH,
            Locale.ITALIAN
        ));
    }

    /**
     * Get the default locale. This is the systems default if contained in the
     * supported locales, english otherwise.
     *
     * @return A Locale constant, containing the current language.
     *
     * @since 0.0.1
     */
    public static Locale getDefaultLocale() {
        Locale sysDefault = Locale.getDefault();
        return getSupportedLocales().contains(sysDefault) ? sysDefault : Locale.ENGLISH;
    }

    /**
     * @return Returns the current locale.
     */
    public static Locale getLocale() {
        return locale.get();
    }

    /**
     * @param locale The locale.
     */
    public static void setLocale(final Locale locale) {
        localeProperty().set(locale);
        Locale.setDefault(locale);
    }

    /**
     * @return Returns the current locale property.
     */
    public static ObjectProperty<Locale> localeProperty() {
        return locale;
    }

    /**
     * Gets the string with the given key from the resource bundle for the
     * current locale and uses it as first argument to MessageFormat.format,
     * passing in the optional args and returning the result.
     *
     * @param key  Message key
     * @param args Optional arguments for the message
     *
     * @return localized formatted string
     *
     * @since 0.0.1
     */
    public static String get(final String key, final Object... args) {
        ResourceBundle bundle = ResourceBundle.getBundle("i18n/messages", getLocale());
        return MessageFormat.format(bundle.getString(key), args);
    }

    /**
     * Creates a String Binding to a localized String that is computed by
     * calling the given func.
     *
     * @param func Function called on every change
     *
     * @return StringBinding
     *
     * @since 0.0.1
     */
    public static StringBinding createStringBinding(final Callable<String> func) {
        return Bindings.createStringBinding(func, locale);
    }

    /**
     * Creates a String binding to a localized String for the given message
     * bundle key.
     *
     * @param key  Key
     * @param args Custom variables for the string
     *
     * @return String binding
     *
     * @since 0.0.1
     */
    public static StringBinding createStringBinding(final String key, final Object... args) {
        return createStringBinding(() -> get(key, args));
    }
}
