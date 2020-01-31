package org.orbitrondev.jass.client.Controller;

import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import org.orbitrondev.jass.client.Entity.LoginEntity;
import org.orbitrondev.jass.client.Model.LoginModel;
import org.orbitrondev.jass.client.Utils.BackendUtil;
import org.orbitrondev.jass.client.View.ViewHelper;
import org.orbitrondev.jass.client.View.LoginView;
import org.orbitrondev.jass.lib.MVC.Controller;
import org.orbitrondev.jass.lib.ServiceLocator.ServiceLocator;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

public class LoginController extends Controller<LoginModel, LoginView> {
    protected LoginController(LoginModel model, LoginView view) {
        super(model, view);

        // register ourselves to listen for button clicks
        view.getBtnLogin().setOnAction(event -> clickOnLogin());
        view.getBtnRegister().setOnAction(event -> clickOnRegister());

        // Disable/Enable the login button depending on if the inputs are valid
        AtomicBoolean usernameValid = new AtomicBoolean(false);
        AtomicBoolean passwordValid = new AtomicBoolean(false);
        Runnable updateButtonClickable = () -> {
            if (!usernameValid.get() || !passwordValid.get()) {
                view.getBtnLogin().setDisable(true);
            } else {
                view.getBtnLogin().setDisable(false);
            }
        };
        view.getUsername().textProperty().addListener((o, oldVal, newVal) -> {
            if (!oldVal.equals(newVal)) {
                usernameValid.set(view.getUsername().validate());
                updateButtonClickable.run();
            }
        });
        view.getPassword().textProperty().addListener((o, oldVal, newVal) -> {
            if (!oldVal.equals(newVal)) {
                passwordValid.set(view.getPassword().validate());
                updateButtonClickable.run();
            }
        });

        // register ourselves to handle window-closing event
        view.getStage().setOnCloseRequest(event -> Platform.exit());
    }

    public void disableInputs() {
        view.getUsername().setDisable(true);
        view.getPassword().setDisable(true);
    }

    public void disableAll() {
        disableInputs();
        view.getBtnLogin().setDisable(true);
        view.getBtnRegister().setDisable(true);
    }

    public void enableInputs() {
        view.getUsername().setDisable(false);
        view.getPassword().setDisable(false);
    }

    public void enableAll() {
        enableInputs();
        view.getBtnLogin().setDisable(false);
        view.getBtnRegister().setDisable(false);
    }

    public void setErrorMessage(String translatorKey) {
        Platform.runLater(() -> {
            if (view.getErrorMessage().getChildren().size() == 0) {
                // Make window larger, so it doesn't become crammed, only if we haven't done so yet
                view.getStage().setHeight(view.getStage().getHeight() + 30);
            }
            Text text = ViewHelper.useText(translatorKey);
            text.setFill(Color.RED);
            view.getErrorMessage().getChildren().clear();
            view.getErrorMessage().getChildren().addAll(text, ViewHelper.useSpacer(20));
        });
    }

    public void clickOnRegister() {
        ControllerHelper.switchToRegisterWindow(view);
    }

    public void clickOnLogin() {
        // Disable everything to prevent something while working on the data
        disableAll();

        LoginEntity login = new LoginEntity(view.getUsername().getText(), view.getPassword().getText());
        ServiceLocator.add(login);

        // Connection would freeze window (and the animations) so do it in a different thread.
        Runnable loginTask = () -> {
            try {
                // Try to login (the BackendController automatically saves it to the DB)
                BackendUtil backend = (BackendUtil) ServiceLocator.get("backend");
                backend.sendLogin(login);
            } catch (IOException e) {
                // This exception contains ConnectException, which basically means, it couldn't connect to the server.
                enableAll();
                setErrorMessage("gui.login.loginFailed");
            }

            if (login.getToken() != null) {
                ControllerHelper.switchToDashboardWindow(view);
            } else {
                enableAll();
                setErrorMessage("gui.login.loginFailed");
            }
        };
        new Thread(loginTask).start();
    }
}
