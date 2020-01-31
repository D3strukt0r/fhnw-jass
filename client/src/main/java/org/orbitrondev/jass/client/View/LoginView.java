package org.orbitrondev.jass.client.View;

import com.jfoenix.controls.JFXButton;
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

public class LoginView extends View<LoginModel> {
    private VBox errorMessage;
    private JFXTextField username;
    private JFXPasswordField password;
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
            ViewHelper.useSpacer(25),
            password,
            ViewHelper.useSpacer(25),
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

    public JFXButton getBtnLogin() {
        return btnLogin;
    }

    public JFXButton getBtnRegister() {
        return btnRegister;
    }
}
