package jass.client.util;

import jass.client.controller.GameController;
import jass.client.entity.LoginEntity;
import jass.client.eventlistener.BroadcastDeckEventListener;
import jass.client.eventlistener.ChooseGameModeEventListener;
import jass.client.message.ChosenGameMode;
import jass.lib.message.BroadcastDeckData;
import jass.lib.message.CardData;
import jass.lib.message.ChooseGameModeData;
import jass.lib.message.ChosenGameModeData;
import jass.lib.message.GameFoundData;
import jass.lib.servicelocator.ServiceLocator;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import jass.lib.servicelocator.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Victor Hargrave
 * @version %I%, %G%
 * @since 0.0.1
 */
public final class GameUtil implements Service, BroadcastDeckEventListener, ChooseGameModeEventListener {
    /**
     * The logger to print to console and save in a .log file.
     */
    private static final Logger logger = LogManager.getLogger(GameController.class);

    /**
     * The game.
     */
    private GameFoundData game;

    /**
     * The deck ID.
     */
    private int deckId;

    /**
     * The deck of the player.
     */
    private ObservableList<CardData> playerDeck;

    /**
     * An object for a running game.
     */
    public GameUtil() {
        SocketUtil socket = (SocketUtil) ServiceLocator.get(SocketUtil.class);
        if (socket != null) { // Not necessary but keeps IDE happy
            socket.setBroadcastDeckEventListener(this);
            socket.addChooseGameModeEventListener(this);
        }
    }

    @Override
    public void onDeckBroadcasted(final BroadcastDeckData data) {
        logger.info("Successfully received cards!");
        this.setDeckId(data.getDeckId());
        this.setPlayerDeck(data.getCardsClient());
    }

    @Override
    public void onChooseGameMode(final ChooseGameModeData data) {
        Platform.runLater(() -> {
            // Save all the possible game modes...
            List<String> choices = new ArrayList<>();
            for (ChooseGameModeData.GameMode gameMode : ChooseGameModeData.GameMode.values()) {
                String str = gameMode.toString();
                choices.add(str.substring(0, 1).toUpperCase() + str.substring(1));
            }

            // Preparing the dialog...
            ChoiceDialog<String> dialog = new ChoiceDialog<>("", choices);
            dialog.setTitle("Choose Game Mode");
            dialog.setHeaderText(null);
            dialog.setContentText("Choose the game mode to play:");
            Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(getClass().getResource("/images/icon.png").toString()));

            // Show the dialog until the player chooses something
            String endResult = null;
            while (endResult == null) {
                Optional<String> result = dialog.showAndWait();
                endResult = result.orElse(null);
            }

            LoginEntity login = (LoginEntity) ServiceLocator.get(LoginEntity.class);
            ChooseGameModeData.GameMode endEndResult = ChooseGameModeData.GameMode.valueOf(endResult);
            ChosenGameMode chosenGameMode = new ChosenGameMode(new ChosenGameModeData(data.getId(), login.getToken(), endEndResult));

            // Return the chosen game mode back to the server.
            SocketUtil socket = (SocketUtil) ServiceLocator.get(SocketUtil.class);
            socket.send(chosenGameMode);
        });
    }

    /**
     * @param msgData The game.
     */
    public void setGame(final GameFoundData msgData) {
        this.game = msgData;
    }

    /**
     * @return Returns the game.
     */
    public GameFoundData getGame() {
        return game;
    }

    /**
     * @return Returns the deck ID.
     */
    public int getDeckId() {
        return deckId;
    }

    /**
     * @param deckId The deck ID.
     */
    public void setDeckId(final int deckId) {
        this.deckId = deckId;
    }

    /**
     * @return Returns the deck of the player.
     */
    public ObservableList<CardData> getPlayerDeck() {
        return playerDeck;
    }

    /**
     * @param playerDeck The deck of the player.
     */
    public void setPlayerDeck(final ArrayList<CardData> playerDeck) {
        this.playerDeck = FXCollections.observableArrayList(playerDeck);
    }
}
