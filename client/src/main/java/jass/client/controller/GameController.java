package jass.client.controller;
import jass.client.mvc.Controller;
import jass.client.util.I18nUtil;
import jass.client.util.SocketUtil;
import jass.client.util.ViewUtil;
import jass.client.util.WindowUtil;
import jass.client.view.GameView;
import jass.client.view.LoginView;
import jass.client.view.ServerConnectionView;
import jass.lib.servicelocator.ServiceLocator;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * The controller for the dashboard (game) view.
 *
 * @author Sasa Trajkova
 * @version %I%, %G%
 * @since 0.0.1
 */
public final class GameController extends Controller {

    /**
     * The view.
     */
    private GameView view;

    /**
     * The "File" element.
     */
    @FXML
    private Menu mFile;

    /**
     * The "File -> Change Language" element.
     */
    @FXML
    private Menu mFileChangeLanguage;

    /**
     * The "File -> Disconnect" element.
     */
    @FXML
    private MenuItem mFileDisconnect;

    /**
     * The "File -> Logout" element.
     */
    @FXML
    private MenuItem mFileLogout;

    /**
     * The "File -> Exit" element.
     */
    @FXML
    private MenuItem mFileExit;

    /**
     * The "Help" element.
     */
    @FXML
    private Menu mHelp;

    /**
     * The "Help -> About" element.
     */
    @FXML
    private MenuItem mHelpAbout;

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        // TODO: Do something

        /*
         * Bind all texts
         */
        mFile.textProperty().bind(I18nUtil.createStringBinding(mFile.getText()));
        mFileChangeLanguage.textProperty().bind(I18nUtil.createStringBinding(mFileChangeLanguage.getText()));
        ViewUtil.useLanguageMenuContent(mFileChangeLanguage);
        mFileDisconnect.textProperty().bind(I18nUtil.createStringBinding(mFileDisconnect.getText()));
        mFileLogout.textProperty().bind(I18nUtil.createStringBinding(mFileLogout.getText()));
        mFileExit.textProperty().bind(I18nUtil.createStringBinding(mFileExit.getText()));
        mFileExit.setAccelerator(KeyCombination.keyCombination("Alt+F4"));

        mHelp.textProperty().bind(I18nUtil.createStringBinding(mHelp.getText()));
        mHelpAbout.textProperty().bind(I18nUtil.createStringBinding(mHelpAbout.getText()));
    }

    /**
     * Disconnect from the server and returns to the server connection window.
     */
    @FXML
    private void clickOnDisconnect() {
        SocketUtil socket = (SocketUtil) ServiceLocator.get(SocketUtil.SERVICE_NAME);
        if (socket != null) { // Not necessary but keeps IDE happy
            socket.close();
        }
        ServiceLocator.remove("backend");
        WindowUtil.switchToNewWindow(view, ServerConnectionView.class);
    }

    /**
     * Keeps the server connection but returns to the login window.
     */
    @FXML
    public void clickOnLogout() {
        //TODO handle logout properly
        WindowUtil.switchToNewWindow(view, LoginView.class);
    }

    /**
     * Shuts down the application.
     */
    @FXML
    private void clickOnExit() {
        Platform.exit();
    }

    /**
     * @param view The view.
     */
    public void setView(final GameView view) {
        this.view = view;
    }
}
