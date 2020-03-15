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

package jass.client.view;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import jass.client.mvc.View;
import jass.client.model.ChangePasswordModel;
import jass.client.utils.I18nUtil;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import jass.client.utils.ViewUtil;

/**
 * The change password view.
 *
 * @author Manuele Vaccari
 * @version %I%, %G%
 * @since 0.0.1
 */
public class ChangePasswordView extends View<ChangePasswordModel> {
    private VBox errorMessage;
    private JFXPasswordField oldPassword;
    private JFXPasswordField newPassword;
    private JFXPasswordField repeatNewPassword;
    private JFXButton btnChange;
    private JFXButton btnCancel;

    public ChangePasswordView(Stage stage, ChangePasswordModel model) {
        super(stage, model);
        stage.titleProperty().bind(I18nUtil.createStringBinding("gui.changePassword.title"));
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

        // Create old password input field
        oldPassword = ViewUtil.usePasswordField("gui.changePassword.oldPassword");
        oldPassword.getValidators().addAll(
            ViewUtil.useRequiredValidator("gui.changePassword.oldPassword.empty")
        );

        // Create password input field
        newPassword = ViewUtil.usePasswordField("gui.changePassword.newPassword");
        newPassword.getValidators().addAll(
            ViewUtil.useRequiredValidator("gui.changePassword.newPassword.empty")
        );

        repeatNewPassword = ViewUtil.usePasswordField("gui.changePassword.repeatNewPassword");
        repeatNewPassword.getValidators().addAll(
            ViewUtil.useRequiredValidator("gui.changePassword.repeatNewPassword.empty"),
            ViewUtil.useIsSameValidator(newPassword, "gui.changePassword.repeatNewPassword.notSame")
        );

        // Create body
        HBox btnRow = new HBox();
        btnRow.setSpacing(4); // Otherwise the login and register are right beside each other

        // Create button to register
        btnChange = ViewUtil.usePrimaryButton("gui.changePassword.change");
        btnChange.setDisable(true);

        // Create button to change
        btnCancel = ViewUtil.useSecondaryButton("gui.changePassword.cancel");

        // Add buttons to btnRow
        btnRow.getChildren().addAll(
            btnChange,
            ViewUtil.useHorizontalSpacer(1),
            btnCancel
        );

        // Add body content to body
        body.getChildren().addAll(
            errorMessage,
            ViewUtil.useSpacer(10),
            oldPassword,
            ViewUtil.useSpacer(25),
            newPassword,
            ViewUtil.useSpacer(25),
            repeatNewPassword,
            ViewUtil.useSpacer(25),
            btnRow
        );

        // Add body to root
        root.getChildren().addAll(
            ViewUtil.useDefaultMenuBar(),
            ViewUtil.useNavBar("gui.changePassword.title"),
            body
        );

        Scene scene = new Scene(root);
        // https://stackoverflow.com/questions/29962395/how-to-write-a-keylistener-for-javafx
        scene.setOnKeyPressed(event -> {
            // Click the connect button by clicking ENTER
            if (event.getCode() == KeyCode.ENTER) {
                if (!btnChange.isDisable()) {
                    btnChange.fire();
                }
            }
        });
        scene.getStylesheets().add(getClass().getResource("/css/app.css").toExternalForm());
        return scene;
    }

    public VBox getErrorMessage() {
        return errorMessage;
    }

    public JFXPasswordField getOldPassword() {
        return oldPassword;
    }

    public JFXPasswordField getNewPassword() {
        return newPassword;
    }

    public JFXPasswordField getRepeatNewPassword() {
        return repeatNewPassword;
    }

    public JFXButton getBtnChange() {
        return btnChange;
    }

    public JFXButton getBtnCancel() {
        return btnCancel;
    }
}
