package org.orbitrondev.jass.client.Controller;

import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.orbitrondev.jass.client.Entity.LoginEntity;
import org.orbitrondev.jass.client.Model.DeleteAccountModel;
import org.orbitrondev.jass.client.Model.LoginModel;
import org.orbitrondev.jass.client.Utils.BackendUtil;
import org.orbitrondev.jass.client.View.DeleteAccountView;
import org.orbitrondev.jass.client.View.ViewHelper;
import org.orbitrondev.jass.client.View.LoginView;
import org.orbitrondev.jass.lib.MVC.Controller;
import org.orbitrondev.jass.lib.ServiceLocator.ServiceLocator;

import java.io.IOException;

public class DeleteAccountController extends Controller<DeleteAccountModel, DeleteAccountView> {
    protected DeleteAccountController(DeleteAccountModel model, DeleteAccountView view) {
        super(model, view);

        // register ourselves to listen for button clicks
        view.getBtnDelete().setOnAction(event -> clickOnDelete());

        // register ourselves to listen for button clicks
        view.getBtnCancel().setOnAction(event -> clickOnCancel());

        // register ourselves to handle window-closing event
        view.getStage().setOnCloseRequest(event -> Platform.exit());
    }

    public void disableAll() {
        view.getBtnDelete().setDisable(true);
        view.getBtnCancel().setDisable(true);
    }

    public void enableAll() {
        view.getBtnDelete().setDisable(false);
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

    private void openLoginWindow() {
        Platform.runLater(() -> {
            // Open login window and close delete account window
            Stage appStage = new Stage();
            LoginModel model = new LoginModel();
            LoginView newView = new LoginView(appStage, model);
            new LoginController(model, newView);

            view.stop();
            view = null;
            newView.start();
        });
    }

    public void clickOnDelete() {
        // Disable everything to prevent something while working on the data
        disableAll();

        // Connection would freeze window (and the animations) so do it in a different thread.
        Runnable logoutTask = () -> {
            boolean userLoggedOut = false;
            try {
                // Try to logout
                BackendUtil backend = (BackendUtil) ServiceLocator.get("backend");
                userLoggedOut = backend.sendLogout();
            } catch (IOException e) {
                // This exception contains ConnectException, which basically means, it couldn't connect to the server.
                enableAll();
                setErrorMessage("gui.deleteAccount.logoutFailed");
            }

            if (userLoggedOut) {
                ServiceLocator.remove("login");
                openLoginWindow();
            } else {
                enableAll();
                setErrorMessage("gui.deleteAccount.logoutFailed");
            }
        };
        Runnable deleteTask = () -> {
            boolean userDeleted = false;
            try {
                // Try to delete the account
                BackendUtil backend = (BackendUtil) ServiceLocator.get("backend");
                LoginEntity login = (LoginEntity) ServiceLocator.get("login");
                userDeleted = backend.sendDeleteLogin(login.getToken());
            } catch (IOException e) {
                // This exception contains ConnectException, which basically means, it couldn't connect to the server.
                enableAll();
                setErrorMessage("gui.deleteAccount.deleteFailed");
            }

            if (userDeleted) {
                // If deleted, try logging out now.
                new Thread(logoutTask).start();
            } else {
                enableAll();
                setErrorMessage("gui.deleteAccount.deleteFailed");
            }
        };
        new Thread(deleteTask).start();
    }

    public void clickOnCancel() {
        ControllerHelper.switchToDashboardWindow(view);
    }
}
