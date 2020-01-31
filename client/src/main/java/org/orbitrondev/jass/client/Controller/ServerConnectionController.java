package org.orbitrondev.jass.client.Controller;

import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.orbitrondev.jass.client.Entity.ServerEntity;
import org.orbitrondev.jass.client.Model.LoginModel;
import org.orbitrondev.jass.client.Model.ServerConnectionModel;
import org.orbitrondev.jass.client.Utils.BackendUtil;
import org.orbitrondev.jass.client.Utils.DatabaseUtil;
import org.orbitrondev.jass.client.Utils.I18nUtil;
import org.orbitrondev.jass.client.View.ViewHelper;
import org.orbitrondev.jass.client.View.LoginView;
import org.orbitrondev.jass.client.View.ServerConnectionView;
import org.orbitrondev.jass.lib.MVC.Controller;
import org.orbitrondev.jass.lib.ServiceLocator.ServiceLocator;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicBoolean;

public class ServerConnectionController extends Controller<ServerConnectionModel, ServerConnectionView> {
    private static final Logger logger = LogManager.getLogger(ServerConnectionController.class);

    public ServerConnectionController(ServerConnectionModel model, ServerConnectionView view) {
        super(model, view);
        DatabaseUtil db = (DatabaseUtil) ServiceLocator.get("db");

        // register ourselves to listen for changes in the dropdown
        view.getChooseServer().setOnAction(event -> updateChosenServer());

        // register ourselves to listen for button clicks
        view.getBtnConnect().setOnAction(event -> clickOnConnect());

        // Add options to server list drop down
        view.getChooseServer().setConverter(new StringConverter<ServerEntity>() {
            @Override
            public String toString(ServerEntity server) {
                return
                    server == null || server.getIp() == null
                        ? I18nUtil.get("gui.serverConnection.create")
                        : (
                        server.isSecure()
                            ? I18nUtil.get("gui.serverConnection.entry.ssl", server.getIp(), Integer.toString(server.getPort()))
                            : I18nUtil.get("gui.serverConnection.entry", server.getIp(), Integer.toString(server.getPort()))
                    );
            }

            @Override
            public ServerEntity fromString(String string) {
                return null;
            }
        });
        view.getChooseServer().getItems().add(new ServerEntity(null, 0));
        view.getChooseServer().getSelectionModel().selectFirst();
        for (ServerEntity server : db.getServerDao()) {
            view.getChooseServer().getItems().add(server);
        }

        // Disable/Enable the Connect button depending on if the inputs are valid
        AtomicBoolean serverIpValid = new AtomicBoolean(false);
        AtomicBoolean portValid = new AtomicBoolean(false);
        Runnable updateButtonClickable = () -> {
            if (!serverIpValid.get() || !portValid.get()) {
                view.getBtnConnect().setDisable(true);
            } else {
                view.getBtnConnect().setDisable(false);
            }
        };
        view.getServerIp().textProperty().addListener((o, oldVal, newVal) -> {
            if (!oldVal.equals(newVal)) {
                serverIpValid.set(view.getServerIp().validate());
                updateButtonClickable.run();
            }
        });
        view.getPort().textProperty().addListener((o, oldVal, newVal) -> {
            if (!oldVal.equals(newVal)) {
                portValid.set(view.getPort().validate());
                updateButtonClickable.run();
            }
        });

        // register ourselves to handle window-closing event
        view.getStage().setOnCloseRequest(event -> Platform.exit());
    }

    public void updateChosenServer() {
        ServerEntity server = view.getChooseServer().getSelectionModel().getSelectedItem();

        if (server == null || server.getIp() == null) {
            enableInputs();
            view.getServerIp().setText("");
            view.getPort().setText("");
        } else {
            disableInputs();
            view.getServerIp().setText(server.getIp());
            view.getPort().setText(Integer.toString(server.getPort()));
        }
    }

    public void disableInputs() {
        view.getServerIp().setDisable(true);
        view.getPort().setDisable(true);
    }

    public void disableAll() {
        disableInputs();
        view.getBtnConnect().setDisable(true);
    }

    public void enableInputs() {
        view.getServerIp().setDisable(false);
        view.getPort().setDisable(false);
    }

    public void enableAll() {
        enableInputs();
        view.getBtnConnect().setDisable(false);
    }

    public void clickOnConnect() {
        // Disable everything to prevent something while working on the data
        disableAll();

        ServerEntity server = new ServerEntity(view.getServerIp().getText(), Integer.parseInt(view.getPort().getText()));
        ServiceLocator.add(server);

        // Connection would freeze window (and the animations) so do it in a different thread.
        Runnable connect = () -> {
            BackendUtil backend = null;
            try {
                // Try to connect to the server
                backend = new BackendUtil(server.getIp(), server.getPort());
                ServiceLocator.add(backend);
            } catch (IOException e) {
                // This exception contains ConnectException, which basically means, it couldn't connect to the server.
                enableAll();
                Platform.runLater(() -> {

                    if (view.getErrorMessage().getChildren().size() == 0) {
                        // Make window larger, so it doesn't become crammed, only if we haven't done so yet
                        view.getStage().setHeight(view.getStage().getHeight() + 30);
                    }
                    Text text = ViewHelper.useText("gui.serverConnection.connectionFailed");
                    text.setFill(Color.RED);
                    view.getErrorMessage().getChildren().clear();
                    view.getErrorMessage().getChildren().addAll(text, ViewHelper.useSpacer(20));
                });
            }

            if (backend != null) {
                // If the user selected "Create new connection" add it to the DB
                ServerEntity selectedItem = view.getChooseServer().getSelectionModel().getSelectedItem();
                if (selectedItem != null && selectedItem.getIp() == null) {
                    try {
                        DatabaseUtil db = (DatabaseUtil) ServiceLocator.get("db");
                        db.getServerDao().create(server);
                    } catch (SQLException e) {
                        logger.error("Server connection not saved to database");
                    }
                }

                Platform.runLater(() -> {
                    // Open login window and close server connection window
                    Stage appStage = new Stage();
                    LoginModel model = new LoginModel();
                    LoginView newView = new LoginView(appStage, model);
                    new LoginController(model, newView);

                    view.stop();
                    view = null;
                    newView.start();
                });
            }
        };
        new Thread(connect).start();
    }
}
