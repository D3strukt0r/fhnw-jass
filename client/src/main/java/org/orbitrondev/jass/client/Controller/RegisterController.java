package org.orbitrondev.jass.client.Controller;

import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import org.orbitrondev.jass.client.Entity.LoginEntity;
import org.orbitrondev.jass.client.Message.CreateLogin;
import org.orbitrondev.jass.client.Message.Login;
import org.orbitrondev.jass.client.Model.RegisterModel;
import org.orbitrondev.jass.client.Utils.BackendUtil;
import org.orbitrondev.jass.client.View.ViewHelper;
import org.orbitrondev.jass.client.View.RegisterView;
import org.orbitrondev.jass.lib.MVC.Controller;
import org.orbitrondev.jass.lib.Message.CreateLoginData;
import org.orbitrondev.jass.lib.Message.LoginData;
import org.orbitrondev.jass.lib.ServiceLocator.ServiceLocator;

import java.util.concurrent.atomic.AtomicBoolean;

public class RegisterController extends Controller<RegisterModel, RegisterView> {
    protected RegisterController(RegisterModel model, RegisterView view) {
        super(model, view);

        // register ourselves to listen for button clicks
        view.getBtnRegister().setOnAction(event -> clickOnRegister());
        view.getBtnLogin().setOnAction(event -> ControllerHelper.switchToLoginWindow(view));

        // Disable/Enable the login button depending on if the inputs are valid
        AtomicBoolean usernameValid = new AtomicBoolean(false);
        AtomicBoolean passwordValid = new AtomicBoolean(false);
        AtomicBoolean repeatPasswordValid = new AtomicBoolean(false);
        Runnable updateButtonClickable = () -> {
            if (!usernameValid.get() || !passwordValid.get() || !repeatPasswordValid.get()) {
                view.getBtnRegister().setDisable(true);
            } else {
                view.getBtnRegister().setDisable(false);
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
        view.getRepeatPassword().textProperty().addListener((o, oldVal, newVal) -> {
            if (!oldVal.equals(newVal)) {
                repeatPasswordValid.set(view.getRepeatPassword().validate());
                updateButtonClickable.run();
            }
        });

        // register ourselves to handle window-closing event
        view.getStage().setOnCloseRequest(event -> Platform.exit());
    }

    public void disableInputs() {
        view.getUsername().setDisable(true);
        view.getPassword().setDisable(true);
        view.getRepeatPassword().setDisable(true);
    }

    public void disableAll() {
        disableInputs();
        view.getBtnRegister().setDisable(true);
        view.getBtnLogin().setDisable(true);
    }

    public void enableInputs() {
        view.getUsername().setDisable(false);
        view.getPassword().setDisable(false);
        view.getRepeatPassword().setDisable(false);
    }

    public void enableAll() {
        enableInputs();
        view.getBtnRegister().setDisable(false);
        view.getBtnLogin().setDisable(false);
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
        // Disable everything to prevent something while working on the data
        disableAll();

        LoginEntity login = new LoginEntity(view.getUsername().getText(), view.getPassword().getText());

        // Connection would freeze window (and the animations) so do it in a different thread.
        Runnable loginTask = () -> {
            BackendUtil backend = (BackendUtil) ServiceLocator.get("backend");
            Login loginMsg = new Login(new LoginData(login.getUsername(), login.getPassword()));

            if (loginMsg.process(backend)) {
                login.setToken(loginMsg.getToken());
                ServiceLocator.add(login);
                ControllerHelper.switchToDashboardWindow(view);
            } else {
                enableAll();
                setErrorMessage("gui.login.loginFailed");
            }
        };
        Runnable registerTask = () -> {
            BackendUtil backend = (BackendUtil) ServiceLocator.get("backend");
            CreateLogin createLoginMsg = new CreateLogin(new CreateLoginData(login.getUsername(), login.getPassword()));

            if (createLoginMsg.process(backend)) {
                // If registered, try logging in now.
                new Thread(loginTask).start();
            } else {
                enableAll();
                setErrorMessage("gui.register.registerFailed");
            }
        };
        new Thread(registerTask).start();
    }
}
