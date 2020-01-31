package org.orbitrondev.jass.client.View;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.orbitrondev.jass.client.Entity.ServerEntity;
import org.orbitrondev.jass.client.Model.ServerConnectionModel;
import org.orbitrondev.jass.client.Utils.I18nUtil;
import org.orbitrondev.jass.lib.MVC.View;

public class ServerConnectionView extends View<ServerConnectionModel> {
    private VBox errorMessage;
    private JFXTextField serverIp;
    private JFXTextField port;
    private JFXButton btnConnect;
    private JFXComboBox<ServerEntity> chooseServer;

    public ServerConnectionView(Stage stage, ServerConnectionModel model) {
        super(stage, model);
        stage.titleProperty().bind(I18nUtil.createStringBinding("gui.serverConnection.title"));
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

        // Create dropdown to choose existing server connection
        chooseServer = new JFXComboBox<>();

        // Create server ip input field
        serverIp = ViewHelper.useTextField("gui.serverConnection.ip");
        serverIp.getValidators().addAll(
            ViewHelper.useRequiredValidator("gui.serverConnection.ip.empty"),
            ViewHelper.useIsValidIpValidator("gui.serverConnection.ip.notIp")
        );

        // Create server port input field
        port = ViewHelper.useTextField("gui.serverConnection.port");
        port.getValidators().addAll(
            ViewHelper.useRequiredValidator("gui.serverConnection.port.empty"),
            ViewHelper.useIsIntegerValidator("gui.serverConnection.port.nan"),
            ViewHelper.useIsValidPortValidator("gui.serverConnection.port.outOfRange")
        );

        // Create button to connect
        btnConnect = ViewHelper.usePrimaryButton("gui.serverConnection.connect");
        btnConnect.setDisable(true);

        // Add body content to body
        body.getChildren().addAll(
            errorMessage,
            ViewHelper.useSpacer(10),
            chooseServer,
            ViewHelper.useSpacer(25),
            serverIp,
            ViewHelper.useSpacer(25),
            ViewHelper.useText("gui.serverConnection.ip.hint", stage),
            ViewHelper.useSpacer(15),
            port,
            ViewHelper.useSpacer(25),
            ViewHelper.useText("gui.serverConnection.port.hint", stage),
            ViewHelper.useSpacer(15),
            btnConnect
        );

        // Add body to root
        root.getChildren().addAll(
            ViewHelper.useDefaultMenuBar(),
            ViewHelper.useNavBar("gui.serverConnection.title"),
            body
        );

        Scene scene = new Scene(root);
        // https://stackoverflow.com/questions/29962395/how-to-write-a-keylistener-for-javafx
        scene.setOnKeyPressed(event -> {
            // Click the connect button by clicking ENTER
            if (event.getCode() == KeyCode.ENTER) {
                if (!btnConnect.isDisable()) {
                    btnConnect.fire();
                }
            }
        });
        scene.getStylesheets().add(getClass().getResource("/css/app.css").toExternalForm());
        return scene;
    }

    public VBox getErrorMessage() {
        return errorMessage;
    }

    public JFXComboBox<ServerEntity> getChooseServer() {
        return chooseServer;
    }

    public JFXTextField getServerIp() {
        return serverIp;
    }

    public JFXTextField getPort() {
        return port;
    }

    public JFXButton getBtnConnect() {
        return btnConnect;
    }
}
