package org.orbitrondev.jass.client.Controller;

import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import org.orbitrondev.jass.client.Entity.LoginEntity;
import org.orbitrondev.jass.client.Message.ChangePassword;
import org.orbitrondev.jass.client.Model.ChangePasswordModel;
import org.orbitrondev.jass.client.Utils.BackendUtil;
import org.orbitrondev.jass.client.View.ChangePasswordView;
import org.orbitrondev.jass.client.View.ViewHelper;
import org.orbitrondev.jass.lib.MVC.Controller;
import org.orbitrondev.jass.lib.Message.ChangePasswordData;
import org.orbitrondev.jass.lib.ServiceLocator.ServiceLocator;

import java.util.concurrent.atomic.AtomicBoolean;

public class ChangePasswordController extends Controller<ChangePasswordModel, ChangePasswordView> {
    protected ChangePasswordController(ChangePasswordModel model, ChangePasswordView view) {
        super(model, view);

        // register ourselves to listen for button clicks
        view.getBtnChange().setOnAction(event -> clickOnChange());
        view.getBtnCancel().setOnAction(event -> ControllerHelper.switchToDashboardWindow(view));

        // Disable/Enable the login button depending on if the inputs are valid
        AtomicBoolean oldPasswordValid = new AtomicBoolean(false);
        AtomicBoolean newPasswordValid = new AtomicBoolean(false);
        AtomicBoolean repeatNewPasswordValid = new AtomicBoolean(false);
        Runnable updateButtonClickable = () -> {
            if (!oldPasswordValid.get() || !newPasswordValid.get() || !repeatNewPasswordValid.get()) {
                view.getBtnChange().setDisable(true);
            } else {
                view.getBtnChange().setDisable(false);
            }
        };
        view.getOldPassword().textProperty().addListener((o, oldVal, newVal) -> {
            if (!oldVal.equals(newVal)) {
                oldPasswordValid.set(view.getOldPassword().validate());
                updateButtonClickable.run();
            }
        });
        view.getNewPassword().textProperty().addListener((o, oldVal, newVal) -> {
            if (!oldVal.equals(newVal)) {
                newPasswordValid.set(view.getNewPassword().validate());
                updateButtonClickable.run();
            }
        });
        view.getRepeatNewPassword().textProperty().addListener((o, oldVal, newVal) -> {
            if (!oldVal.equals(newVal)) {
                repeatNewPasswordValid.set(view.getRepeatNewPassword().validate());
                updateButtonClickable.run();
            }
        });

        // register ourselves to handle window-closing event
        view.getStage().setOnCloseRequest(event -> Platform.exit());
    }

    public void disableInputs() {
        view.getOldPassword().setDisable(true);
        view.getNewPassword().setDisable(true);
        view.getRepeatNewPassword().setDisable(true);
    }

    public void disableAll() {
        disableInputs();
        view.getBtnChange().setDisable(true);
        view.getBtnCancel().setDisable(true);
    }

    public void enableInputs() {
        view.getOldPassword().setDisable(false);
        view.getNewPassword().setDisable(false);
        view.getRepeatNewPassword().setDisable(false);
    }

    public void enableAll() {
        enableInputs();
        view.getBtnChange().setDisable(false);
        view.getBtnCancel().setDisable(false);
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

    public void clickOnChange() {
        // Disable everything to prevent something while working on the data
        disableAll();

        // Connection would freeze window (and the animations) so do it in a different thread.
        Runnable changePasswordTask = () -> {
            LoginEntity login = (LoginEntity) ServiceLocator.get("login");
            LoginEntity newLogin = new LoginEntity(login.getUsername(), view.getNewPassword().getText(), login.getToken());

            BackendUtil backend = (BackendUtil) ServiceLocator.get("backend");
            ChangePassword changePasswordMsg = new ChangePassword(new ChangePasswordData(login.getToken(), newLogin.getPassword()));

            if (changePasswordMsg.process(backend)) {
                ServiceLocator.remove("login");
                ServiceLocator.add(newLogin);
                ControllerHelper.switchToDashboardWindow(view);
            } else {
                enableAll();
                setErrorMessage("gui.changePassword.changeFailed");
            }
        };
        new Thread(changePasswordTask).start();
    }
}
