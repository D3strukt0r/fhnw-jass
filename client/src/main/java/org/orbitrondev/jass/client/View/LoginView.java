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

package org.orbitrondev.jass.client.View;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.orbitrondev.jass.client.Model.LoginModel;
import org.orbitrondev.jass.client.Utils.I18nUtil;
import org.orbitrondev.jass.lib.MVC.View;

/**
 * The login view.
 *
 * @author Manuele Vaccari
 * @version %I%, %G%
 * @since 0.0.1
 */
public class LoginView extends View<LoginModel> {
    private VBox errorMessage;
    private JFXTextField username;
    private JFXPasswordField password;
    private JFXCheckBox connectAutomatically;
    private JFXButton btnLogin;
    private JFXButton btnRegister;

    public LoginView(Stage stage, LoginModel model) {
        super(stage, model);
        stage.titleProperty().bind(I18nUtil.createStringBinding("gui.login.title"));
        stage.setWidth(300);
        stage.setResizable(false);
    }

    @Override
    protected Scene create_GUI() {
        // Create root
        VBox root = new VBox();
        root.getStyleClass().add("background-white");

        // Create body
        VBox body = new VBox();
        body.getStyleClass().add("custom-container");

        // Create error message container
        errorMessage = new VBox();

        // Create username input field
        username = ViewHelper.useTextField("gui.login.username");
        username.getValidators().addAll(
            ViewHelper.useRequiredValidator("gui.login.username.empty")
        );

        // Create password input field
        password = ViewHelper.usePasswordField("gui.login.password");
        password.getValidators().addAll(
            ViewHelper.useRequiredValidator("gui.login.password.empty")
        );

        // Create checkboxes
        connectAutomatically = ViewHelper.useCheckBox("gui.serverConnection.connectAutomatically");

        // Create body
        HBox btnRow = new HBox();
        btnRow.setSpacing(4); // Otherwise the login and register are right beside each other

        // Create button to login
        btnLogin = ViewHelper.usePrimaryButton("gui.login.login");
        btnLogin.setDisable(true);

        // Create button to register
        btnRegister = ViewHelper.useSecondaryButton("gui.login.register");

        // Add buttons to btnRow
        btnRow.getChildren().addAll(
            btnLogin,
            ViewHelper.useHorizontalSpacer(1),
            btnRegister
        );

        // Add body content to body
        body.getChildren().addAll(
            errorMessage,
            ViewHelper.useSpacer(10),
            username,
            ViewHelper.useSpacer(20),
            password,
            ViewHelper.useSpacer(15),
            connectAutomatically,
            ViewHelper.useSpacer(15),
            btnRow
        );

        // Add body to root
        root.getChildren().addAll(
            ViewHelper.useDefaultMenuBar(),
            ViewHelper.useNavBar("gui.login.title"),
            body
        );

        Scene scene = new Scene(root);
        // https://stackoverflow.com/questions/29962395/how-to-write-a-keylistener-for-javafx
        scene.setOnKeyPressed(event -> {
            // Click the connect button by clicking ENTER
            if (event.getCode() == KeyCode.ENTER) {
                if (!btnLogin.isDisable()) {
                    btnLogin.fire();
                }
            }
        });
        scene.getStylesheets().add(getClass().getResource("/css/app.css").toExternalForm());
        return scene;
    }

    public VBox getErrorMessage() {
        return errorMessage;
    }

    public JFXTextField getUsername() {
        return username;
    }

    public JFXPasswordField getPassword() {
        return password;
    }

    public JFXCheckBox getConnectAutomatically() {
        return connectAutomatically;
    }

    public JFXButton getBtnLogin() {
        return btnLogin;
    }

    public JFXButton getBtnRegister() {
        return btnRegister;
    }
}
