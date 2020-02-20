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
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.orbitrondev.jass.client.Entity.ServerEntity;
import org.orbitrondev.jass.client.Model.ServerConnectionModel;
import org.orbitrondev.jass.client.Utils.I18nUtil;
import org.orbitrondev.jass.client.MVC.View;

/**
 * The server connection view.
 *
 * @author Manuele Vaccari
 * @version %I%, %G%
 * @since 0.0.1
 */
public class ServerConnectionView extends View<ServerConnectionModel> {
    private VBox errorMessage;
    private JFXTextField serverIp;
    private JFXTextField port;
    private JFXCheckBox secure;
    private JFXCheckBox connectAutomatically;
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

        secure = ViewHelper.useCheckBox("gui.serverConnection.secure");
        connectAutomatically = ViewHelper.useCheckBox("gui.serverConnection.connectAutomatically");

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
            secure,
            ViewHelper.useSpacer(10),
            connectAutomatically,
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

    public JFXCheckBox getSecure() {
        return secure;
    }

    public JFXCheckBox getConnectAutomatically() {
        return connectAutomatically;
    }

    public JFXButton getBtnConnect() {
        return btnConnect;
    }
}
