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

package jass.client.utils;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.IntegerValidator;
import com.jfoenix.validation.RegexValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import jass.client.validator.IsSameValidator;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.*;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import jiconfont.IconCode;
import jiconfont.icons.google_material_design_icons.GoogleMaterialDesignIcons;
import jiconfont.javafx.IconNode;

import java.util.Locale;

/**
 * A helper class for the views, to get elements easily.
 *
 * @author Manuele Vaccari
 * @version %I%, %G%
 * @since 0.0.1
 */
public class ViewUtil {
    /**
     * Shortcuts for UI elements
     */
    public static Region useSpacer(int space) {
        Region spacer = new Region();
        spacer.setPrefHeight(space);
        VBox.setVgrow(spacer, Priority.ALWAYS);
        return spacer;
    }

    public static Region useHorizontalSpacer(int space) {
        Region spacer = new Region();
        spacer.setPrefWidth(space);
        HBox.setHgrow(spacer, Priority.ALWAYS);
        return spacer;
    }

    public static Text useText(String translatorKey) {
        Text textField = new Text();
        textField.textProperty().bind(I18nUtil.createStringBinding(translatorKey));
        return textField;
    }

    public static Text useText(String translatorKey, Stage paneToBindWidth) {
        Text textField = useText(translatorKey);
        // https://stackoverflow.com/questions/51199903/how-to-bind-a-value-to-the-result-of-a-calculation
        DoubleProperty padding = new SimpleDoubleProperty(40.0); // Check the css at .custom-container (padding left and right = 40)
        NumberBinding wrapping = Bindings.subtract(paneToBindWidth.widthProperty(), padding);
        textField.wrappingWidthProperty().bind(wrapping);
        return textField;
    }

    public static Label useLabel(String translatorKey) {
        Label label = new Label();
        label.textProperty().bind(I18nUtil.createStringBinding(translatorKey));
        return label;
    }

    public static Label useLabel(String translatorKey, IconNode icon) {
        Label label = useLabel(translatorKey);
        label.setGraphic(icon);
        return label;
    }

    public static JFXTextField useTextField(String translatorKey) {
        JFXTextField textField = new JFXTextField();
        textField.setLabelFloat(true);
        textField.promptTextProperty().bind(I18nUtil.createStringBinding(translatorKey));
        return textField;
    }

    public static JFXPasswordField usePasswordField(String translatorKey) {
        JFXPasswordField passwordField = new JFXPasswordField();
        passwordField.setLabelFloat(true);
        passwordField.promptTextProperty().bind(I18nUtil.createStringBinding(translatorKey));
        return passwordField;
    }

    public static JFXCheckBox useCheckBox(String translatorKey) {
        JFXCheckBox checkBox = new JFXCheckBox();
        checkBox.textProperty().bind(I18nUtil.createStringBinding(translatorKey));
        return checkBox;
    }

    public static JFXButton usePrimaryButton(String translatorKey) {
        JFXButton primaryButton = new JFXButton();
        primaryButton.textProperty().bind(I18nUtil.createStringBinding(() -> I18nUtil.get(translatorKey).toUpperCase()));
        primaryButton.setButtonType(JFXButton.ButtonType.RAISED);
        primaryButton.setPrefWidth(Double.MAX_VALUE);
        primaryButton.setMaxWidth(Double.MAX_VALUE);
        primaryButton.getStyleClass().add("primary");
        return primaryButton;
    }

    public static JFXButton useSecondaryButton(String translatorKey) {
        JFXButton secondaryButton = new JFXButton();
        secondaryButton.textProperty().bind(I18nUtil.createStringBinding(() -> I18nUtil.get(translatorKey).toUpperCase()));
        secondaryButton.setButtonType(JFXButton.ButtonType.RAISED);
        secondaryButton.setPrefWidth(Double.MAX_VALUE);
        secondaryButton.setMaxWidth(Double.MAX_VALUE);
        secondaryButton.getStyleClass().add("secondary");
        return secondaryButton;
    }

    public static HBox useNavBar(String translatorKey) {
        HBox navBar = new HBox();
        Text title = new Text();
        title.textProperty().bind(I18nUtil.createStringBinding(() -> I18nUtil.get(translatorKey).toUpperCase()));
        title.setFill(Color.WHITE);
        navBar.getStyleClass().add("navbar");
        navBar.getChildren().add(title);
        return navBar;
    }

    public static HBox useStaticNavBar(String text) {
        HBox navBar = new HBox();
        Text title = new Text(text.toUpperCase());
        title.setFill(Color.WHITE);
        navBar.getStyleClass().add("navbar");
        navBar.getChildren().add(title);
        return navBar;
    }

    public static HBox useVariableNavBar(ObservableValue<String> text) {
        HBox navBar = new HBox();
        Text title = new Text();
        title.textProperty().bind(text);
        title.setFill(Color.WHITE);
        navBar.getStyleClass().add("navbar");
        navBar.getChildren().add(title);
        return navBar;
    }

    /**
     * Shortcuts for validator with UI input elements
     */
    public static RequiredFieldValidator useRequiredValidator(String translatorKey) {
        RequiredFieldValidator requiredValidator = new RequiredFieldValidator();
        requiredValidator.messageProperty().bind(I18nUtil.createStringBinding(translatorKey));
        return requiredValidator;
    }

    public static IntegerValidator useIsIntegerValidator(String translatorKey) {
        IntegerValidator isIntValidator = new IntegerValidator();
        isIntValidator.messageProperty().bind(I18nUtil.createStringBinding(translatorKey));
        return isIntValidator;
    }

    public static IsSameValidator useIsSameValidator(TextInputControl validateTo, String translatorKey) {
        IsSameValidator isSameValidator = new IsSameValidator(validateTo);
        isSameValidator.messageProperty().bind(I18nUtil.createStringBinding(translatorKey));
        return isSameValidator;
    }

    public static RegexValidator useIsValidIpValidator(String translatorKey) {
        RegexValidator isValidIpValidator = new RegexValidator();
        isValidIpValidator.messageProperty().bind(I18nUtil.createStringBinding(translatorKey));
        isValidIpValidator.setRegexPattern("^((0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)\\.){3}(0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)$");
        return isValidIpValidator;
    }

    public static RegexValidator useIsValidPortValidator(String translatorKey) {
        RegexValidator isValidPortValidator = new RegexValidator();
        isValidPortValidator.messageProperty().bind(I18nUtil.createStringBinding(translatorKey));
        isValidPortValidator.setRegexPattern("^([0-9]{1,4}|[1-5][0-9]{4}|6[0-4][0-9]{3}|65[0-4][0-9]{2}|655[0-2][0-9]|6553[0-5])$");
        return isValidPortValidator;
    }

    /**
     * Shortcuts for UI menus
     */
    public static MenuBar useDefaultMenuBar() {
        MenuBar menuBar = new MenuBar();

        // Create menus
        Menu fileMenu = useMenu("gui.menu.file");
        Menu helpMenu = useMenu("gui.menu.help");

        // Add menuItems to the Menus
        fileMenu.getItems().addAll(
            useLanguageMenu(),
            new SeparatorMenuItem(),
            useExitMenuItem()
        );

        // Add Menus to the MenuBar
        menuBar.getMenus().addAll(
            fileMenu,
            helpMenu
        );
        return menuBar;
    }

    public static Menu useMenu(String translatorKey) {
        Menu menu = new Menu();
        menu.textProperty().bind(I18nUtil.createStringBinding(translatorKey));
        return menu;
    }

    public static Menu useLanguageMenu() {
        Menu changeLanguageMenu = useMenu("gui.menu.file.changeLanguage");
        useLanguageMenuContent(changeLanguageMenu);
        return changeLanguageMenu;
    }

    public static void useLanguageMenuContent(Menu changeLanguageMenu) {
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
                    lang = "Français";
                    break;
                case "it":
                    langInLocale.bind(I18nUtil.createStringBinding("gui.menu.file.changeLanguage.italian"));
                    lang = "Italiano";
                    break;
            }

            MenuItem language = new MenuItem(langInLocale.get() + " (" + lang + ")");
            language.setOnAction(event -> I18nUtil.setLocale(locale));
            changeLanguageMenu.getItems().add(language);
        }
    }

    public static MenuItem useMenuItem(String translatorKey) {
        MenuItem item = new MenuItem();
        item.textProperty().bind(I18nUtil.createStringBinding(translatorKey));
        return item;
    }

    public static MenuItem useExitMenuItem() {
        MenuItem exitItem = useMenuItem("gui.menu.file.exit");
        exitItem.setAccelerator(KeyCombination.keyCombination("Alt+F4"));
        exitItem.setOnAction(event -> System.exit(0));
        return exitItem;
    }

    /**
     * Shortcuts for the icons
     */
    public static IconNode useIcon(IconCode iconCode, Color color) {
        IconNode icon = new IconNode(iconCode);
        icon.setFill(color);
        return icon;
    }

    public static IconNode useIconAdd(Color color) {
        return useIcon(GoogleMaterialDesignIcons.ADD, color);
    }

    public static IconNode useIconContact(Color color) {
        return useIcon(GoogleMaterialDesignIcons.CONTACTS, color);
    }

    public static IconNode useIconChat(Color color) {
        return useIcon(GoogleMaterialDesignIcons.FORUM, color);
    }

    public static IconNode useIconSend(Color color) {
        return useIcon(GoogleMaterialDesignIcons.SEND, color);
    }

    public static IconNode useIconExit(Color color) {
        return useIcon(GoogleMaterialDesignIcons.EXIT_TO_APP, color);
    }

    public static IconNode useIconDelete(Color color) {
        return useIcon(GoogleMaterialDesignIcons.DELETE, color);
    }

    public static IconNode useIconEdit(Color color) {
        return useIcon(GoogleMaterialDesignIcons.MODE_EDIT, color);
    }
}
