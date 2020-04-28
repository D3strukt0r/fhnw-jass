package jass.client.util;

import jass.client.controller.GameController;
import jass.client.entity.LoginEntity;
import jass.client.eventlistener.BroadcastDeckEventListener;
import jass.client.eventlistener.ChooseGameModeEventListener;
import jass.client.message.ChosenGameMode;
import jass.lib.Card;
import jass.lib.GameMode;
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
import java.util.Arrays;
import java.util.List;

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
            Arrays.stream(GameMode.values()).forEach(gameMode -> choices.add(gameMode.toString()));

            // Preparing the dialog...
            ChoiceDialog<String> dialog = new ChoiceDialog<>("", choices);
            dialog.setTitle("Choose Game Mode");
            dialog.setHeaderText(null);
            dialog.setContentText("Choose the game mode to play:");
            Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(getClass().getResource("/images/icon.png").toString()));

            // Show the dialog until the player chooses something
            String result = "";
            while (result == null || result.length() == 0) {
                result = dialog.showAndWait().orElse(null);
            }

            GameMode gameMode = GameMode.fromString(result);
            LoginEntity login = (LoginEntity) ServiceLocator.get(LoginEntity.class);

            // If trumpf, choose which card to be trumpf
            ChosenGameMode chosenGameMode;
            if (gameMode == GameMode.TRUMPF) {
                // Save all the possible card types...
                List<String> choices2 = new ArrayList<>();
                Arrays.stream(Card.Suit.values()).forEach(suit -> choices2.add(suit.toString()));

                // Preparing the dialog...
                ChoiceDialog<String> dialog2 = new ChoiceDialog<>("", choices2);
                dialog2.setTitle("Choose suit");
                dialog2.setHeaderText(null);
                dialog2.setContentText("Choose suit for trumpf:");
                Stage stage2 = (Stage) dialog2.getDialogPane().getScene().getWindow();
                stage2.getIcons().add(new Image(getClass().getResource("/images/icon.png").toString()));

                // Show the dialog until the player chooses something
                String result2 = "";
                while (result2 == null || result2.length() == 0) {
                    result2 = dialog2.showAndWait().orElse(null);
                }

                Card.Suit suit = Card.Suit.fromString(result2);
                chosenGameMode = new ChosenGameMode(new ChosenGameModeData(data.getId(), login.getToken(), gameMode, suit));
            } else {
                chosenGameMode = new ChosenGameMode(new ChosenGameModeData(data.getId(), login.getToken(), gameMode));
            }

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
