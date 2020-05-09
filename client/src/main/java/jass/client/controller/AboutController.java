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

package jass.client.controller;

import com.jfoenix.controls.JFXButton;
import jass.client.mvc.Controller;
import jass.client.util.I18nUtil;
import jass.client.view.AboutView;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * The controller for the about view.
 *
 * @author Manuele Vaccari
 * @version %I%, %G%
 * @since 0.0.1
 */
public final class AboutController extends Controller {
    /**
     * The view.
     */
    private AboutView view;

    /**
     * The root element of the view.
     */
    @FXML
    private VBox root;

    /**
     * The navbar.
     */
    @FXML
    private Text navbar;

    /**
     * The message.
     */
    @FXML
    private Text message;

    /**
     * The delete button.
     */
    @FXML
    private JFXButton ok;

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        /*
         * Bind all texts
         */
        navbar.textProperty().bind(I18nUtil.createStringBinding(navbar.getText()));

        message.textProperty().bind(I18nUtil.createStringBinding(message.getText()));
        DoubleProperty padding = new SimpleDoubleProperty(40.0);
        NumberBinding wrapping = Bindings.subtract(root.widthProperty(), padding);
        message.wrappingWidthProperty().bind(wrapping);

        ok.textProperty().bind(I18nUtil.createStringBinding(ok.getText()));
    }

    /**
     * Closes the window.
     */
    @FXML
    private void clickOnOk() {
        view.stop();
    }

    /**
     * @param view The view.
     */
    public void setView(final AboutView view) {
        this.view = view;
    }
}
