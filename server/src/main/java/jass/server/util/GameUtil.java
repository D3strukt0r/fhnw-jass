/*
 * fhnw-jass is jass game programmed in java for a school project.
 * Copyright (C) 2020 Manuele Vaccari & Victor Hargrave & Thomas Weber & Sasa
 * Trajkova
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

package jass.server.util;

import jass.lib.Card;
import jass.lib.GameMode;
import jass.lib.message.BroadcastAPlayerQuitData;
import jass.lib.message.BroadcastGameModeData;
import jass.lib.message.BroadcastPointsData;
import jass.lib.message.BroadcastRoundOverData;
import jass.lib.message.BroadcastTurnData;
import jass.lib.message.ChooseGameModeData;
import jass.lib.message.ChosenGameModeData;
import jass.lib.message.ContinuePlayingData;
import jass.lib.message.GameFoundData;
import jass.lib.message.PlayCardData;
import jass.lib.message.PlayedCardData;
import jass.lib.message.StopPlayingData;
import jass.lib.servicelocator.ServiceLocator;
import jass.server.entity.CardEntity;
import jass.server.entity.DeckEntity;
import jass.server.entity.GameEntity;
import jass.server.entity.RoundEntity;
import jass.server.entity.SuitEntity;
import jass.server.entity.TeamEntity;
import jass.server.entity.TurnEntity;
import jass.server.entity.UserEntity;
import jass.server.eventlistener.ChosenGameModeEventListener;
import jass.server.eventlistener.ContinuePlayingEventListener;
import jass.server.eventlistener.PlayedCardEventListener;
import jass.server.eventlistener.StopPlayingEventListener;
import jass.server.message.BroadcastAPlayerQuit;
import jass.server.message.BroadcastGameMode;
import jass.server.message.BroadcastPoints;
import jass.server.message.BroadcastRoundOver;
import jass.server.message.BroadcastTurn;
import jass.server.message.ChooseGameMode;
import jass.server.message.GameFound;
import jass.server.message.Message;
import jass.server.message.PlayedCard;
import jass.server.repository.CardRepository;
import jass.server.repository.DeckRepository;
import jass.server.repository.GameRepository;
import jass.server.repository.RankRepository;
import jass.server.repository.RoundRepository;
import jass.server.repository.SuitRepository;
import jass.server.repository.TeamRepository;
import jass.server.repository.TurnRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Thomas Weber & Manuele Vaccari & Victor Hargrave & Sasa Trajkova
 * @version %I%, %G%
 * @since 1.0.0
 */
public final class GameUtil implements ChosenGameModeEventListener, PlayedCardEventListener, StopPlayingEventListener, ContinuePlayingEventListener {
    /**
     * The logger to print to console and save in a .log file.
     */
    private static final Logger logger = LogManager.getLogger(GameUtil.class);

    /**
     * The client for player one.
     */
    private final ClientUtil clientPlayerOne;

    /**
     * The client for player two.
     */
    private final ClientUtil clientPlayerTwo;

    /**
     * The client for player three.
     */
    private final ClientUtil clientPlayerThree;

    /**
     * The client for player four.
     */
    private final ClientUtil clientPlayerFour;

    /**
     * The game.
     */
    private final GameEntity game;

    /**
     * The current round in the game.
     */
    private RoundEntity currentRound;

    /**
     * The current turn in the game.
     */
    private TurnEntity currentTurn;

    /**
     * The current deck of player one in the game.
     */
    private DeckEntity currentDeckPlayerOne;

    /**
     * The current deck of player two in the game.
     */
    private DeckEntity currentDeckPlayerTwo;

    /**
     * The current deck of player three in the game.
     */
    private DeckEntity currentDeckPlayerThree;

    /**
     * The current deck of player four in the game.
     */
    private DeckEntity currentDeckPlayerFour;

    /**
     * Players wanting to continue playing another round
     */
    private int continuePlayClicks = 0;

    /**
     * @param clientPlayerOne   Player one.
     * @param clientPlayerTwo   Player two.
     * @param clientPlayerThree Player three.
     * @param clientPlayerFour  Player four.
     *
     * @author Thomas Weber & Victor Hargrave & Manuele Vaccari
     * @since 1.0.0
     */
    public GameUtil(final ClientUtil clientPlayerOne, final ClientUtil clientPlayerTwo, final ClientUtil clientPlayerThree, final ClientUtil clientPlayerFour) {
        this.clientPlayerOne = clientPlayerOne;
        UserEntity playerOne = clientPlayerOne.getUser();
        this.clientPlayerTwo = clientPlayerTwo;
        UserEntity playerTwo = clientPlayerTwo.getUser();
        this.clientPlayerThree = clientPlayerThree;
        UserEntity playerThree = clientPlayerThree.getUser();
        this.clientPlayerFour = clientPlayerFour;
        UserEntity playerFour = clientPlayerFour.getUser();

        // Add event listeners
        addEventListeners(clientPlayerOne, clientPlayerTwo, clientPlayerThree, clientPlayerFour);

        // Assign and create Teams
        TeamEntity teamOne = (new TeamEntity()).setPlayerOne(playerOne).setPlayerTwo(playerThree);
        TeamRepository.getSingleton(null).add(teamOne);
        TeamEntity teamTwo = (new TeamEntity()).setPlayerOne(playerTwo).setPlayerTwo(playerFour);
        TeamRepository.getSingleton(null).add(teamTwo);

        // Initialize new Game
        game = (new GameEntity()).setTeamOne(teamOne).setTeamTwo(teamTwo);
        GameRepository.getSingleton(null).add(game);
        logger.info("Successfully created game with ID: " + game.getId());

        // Broadcast to all Players that a game was created for them
        GameFound gameFound = new GameFound(new GameFoundData(
            game.getId(),
            playerOne.getId(), playerOne.getUsername(), getTeamIdForPlayer(playerOne, teamOne, teamTwo),
            playerTwo.getId(), playerTwo.getUsername(), getTeamIdForPlayer(playerTwo, teamOne, teamTwo),
            playerThree.getId(), playerThree.getUsername(), getTeamIdForPlayer(playerThree, teamOne, teamTwo),
            playerFour.getId(), playerFour.getUsername(), getTeamIdForPlayer(playerFour, teamOne, teamTwo)
        ));
        broadcast(gameFound);

        // Create a round
        CardUtil cardUtil = ServiceLocator.get(CardUtil.class);
        assert cardUtil != null;
        currentRound = (new RoundEntity()).setGameModeChooser(playerOne).setGame(game);
        RoundRepository.getSingleton(null).add(currentRound);

        // Send a deck to each user
        List<DeckEntity> decks = cardUtil.addDecksForPlayers(currentRound, playerOne, playerTwo, playerThree, playerFour);
        cardUtil.broadcastDeck(clientPlayerOne, decks.get(0));
        currentDeckPlayerOne = decks.get(0);
        cardUtil.broadcastDeck(clientPlayerTwo, decks.get(1));
        currentDeckPlayerTwo = decks.get(1);
        cardUtil.broadcastDeck(clientPlayerThree, decks.get(2));
        currentDeckPlayerThree = decks.get(2);
        cardUtil.broadcastDeck(clientPlayerFour, decks.get(3));
        currentDeckPlayerFour = decks.get(3);

        // Send a message to player one to choose a game mode
        sendChooseGameMode();
    }

    /**
     * @author Thomas Weber
     * @since 1.0.0
     */
    private void startNewRound() {
        UserEntity gameModeChooser = this.getGameModeChooser();
        // Create a round
        CardUtil cardUtil = ServiceLocator.get(CardUtil.class);
        assert cardUtil != null;
        currentRound = (new RoundEntity()).setGameModeChooser(gameModeChooser).setGame(game);
        RoundRepository.getSingleton(null).add(currentRound);

        // Make sure decks are set to null
        currentDeckPlayerOne = null;
        currentDeckPlayerTwo = null;
        currentDeckPlayerThree = null;
        currentDeckPlayerFour = null;

        // Send a deck to each user
        List<DeckEntity> decks = cardUtil.addDecksForPlayers(currentRound, clientPlayerOne.getUser(), clientPlayerTwo.getUser(), clientPlayerThree.getUser(), clientPlayerFour.getUser());
        cardUtil.broadcastDeck(clientPlayerOne, decks.get(0));
        currentDeckPlayerOne = decks.get(0);
        cardUtil.broadcastDeck(clientPlayerTwo, decks.get(1));
        currentDeckPlayerTwo = decks.get(1);
        cardUtil.broadcastDeck(clientPlayerThree, decks.get(2));
        currentDeckPlayerThree = decks.get(2);
        cardUtil.broadcastDeck(clientPlayerFour, decks.get(3));
        currentDeckPlayerFour = decks.get(3);

        // Send a message to player one to choose a game mode
        sendChooseGameMode();
    }

    /**
     * @param clientPlayerOne   The client of player one.
     * @param clientPlayerTwo   The client of player two.
     * @param clientPlayerThree The client of player three.
     * @param clientPlayerFour  The client of player four.
     *
     * @author Victor Hargrave
     * @since 1.0.0
     */
    private void addEventListeners(final ClientUtil clientPlayerOne, final ClientUtil clientPlayerTwo, final ClientUtil clientPlayerThree, final ClientUtil clientPlayerFour) {
        clientPlayerOne.addChosenGameModeEventListener(this);
        clientPlayerTwo.addChosenGameModeEventListener(this);
        clientPlayerThree.addChosenGameModeEventListener(this);
        clientPlayerFour.addChosenGameModeEventListener(this);

        clientPlayerOne.addPlayedCardEventListener(this);
        clientPlayerTwo.addPlayedCardEventListener(this);
        clientPlayerThree.addPlayedCardEventListener(this);
        clientPlayerFour.addPlayedCardEventListener(this);

        clientPlayerOne.addStopPlayingEventListener(this);
        clientPlayerTwo.addStopPlayingEventListener(this);
        clientPlayerThree.addStopPlayingEventListener(this);
        clientPlayerFour.addStopPlayingEventListener(this);

        clientPlayerOne.addContinuePlayingEventListener(this);
        clientPlayerTwo.addContinuePlayingEventListener(this);
        clientPlayerThree.addContinuePlayingEventListener(this);
        clientPlayerFour.addContinuePlayingEventListener(this);
    }

    /**
     * @param clientPlayerOne   The client of player one.
     * @param clientPlayerTwo   The client of player two.
     * @param clientPlayerThree The client of player three.
     * @param clientPlayerFour  The client of player four.
     *
     * @author Victor Hargrave
     * @since 1.0.0
     */
    private void removeEventListeners(final ClientUtil clientPlayerOne, final ClientUtil clientPlayerTwo, final ClientUtil clientPlayerThree, final ClientUtil clientPlayerFour) {
        clientPlayerOne.removeChosenGameModeEventListener(this);
        clientPlayerTwo.removeChosenGameModeEventListener(this);
        clientPlayerThree.removeChosenGameModeEventListener(this);
        clientPlayerFour.removeChosenGameModeEventListener(this);

        clientPlayerOne.removePlayedCardEventListener(this);
        clientPlayerTwo.removePlayedCardEventListener(this);
        clientPlayerThree.removePlayedCardEventListener(this);
        clientPlayerFour.removePlayedCardEventListener(this);

        clientPlayerOne.removeStopPlayingEventListener(this);
        clientPlayerTwo.removeStopPlayingEventListener(this);
        clientPlayerThree.removeStopPlayingEventListener(this);
        clientPlayerFour.removeStopPlayingEventListener(this);

        clientPlayerOne.removeContinuePlayingEventListener(this);
        clientPlayerTwo.removeContinuePlayingEventListener(this);
        clientPlayerThree.removeContinuePlayingEventListener(this);
        clientPlayerFour.removeContinuePlayingEventListener(this);
    }

    /**
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    @Override
    public void onChosenGameMode(final ChosenGameModeData data) {
        // Get the right client to compare to.
        String gameModeChooserUsername = currentRound.getGameModeChooser().getUsername();
        ClientUtil client;
        if (gameModeChooserUsername.equals(clientPlayerOne.getUsername())) {
            client = clientPlayerOne;
        } else if (gameModeChooserUsername.equals(clientPlayerTwo.getUsername())) {
            client = clientPlayerTwo;
        } else if (gameModeChooserUsername.equals(clientPlayerThree.getUsername())) {
            client = clientPlayerThree;
        } else if (gameModeChooserUsername.equals(clientPlayerFour.getUsername())) {
            client = clientPlayerFour;
        } else {
            return;
        }

        // Check with token if player one actually is the one who sent the data.
        if (data.getToken().equals(client.getToken())) {
            currentRound.setGameMode(data.getGameMode());
            BroadcastGameMode broadcastGameMode;
            if (data.getGameMode() == GameMode.TRUMPF) {
                currentRound.setTrumpfSuit(data.getTrumpfSuit());
                broadcastGameMode = new BroadcastGameMode(new BroadcastGameModeData(data.getGameMode(), data.getTrumpfSuit()));
            } else {
                broadcastGameMode = new BroadcastGameMode(new BroadcastGameModeData(data.getGameMode()));
            }
            RoundRepository.getSingleton(null).update(currentRound);
            broadcast(broadcastGameMode);

            // send turn information to clients
            TurnEntity turn = addNewTurn(client.getUser(), currentRound);
            BroadcastTurn broadcastTurn = new BroadcastTurn(new BroadcastTurnData(turn.getId(),
                turn.getStartingPlayer().getUsername(), "",
                turn.getCards().stream().map(CardEntity::toCardData).collect(Collectors.toList())
            ));
            this.currentTurn = turn;
            broadcast(broadcastTurn);
        }
    }

    /**
     * @param message Message to broadcast to all players.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    private void broadcast(final Message message) {
        clientPlayerOne.send(message);
        clientPlayerTwo.send(message);
        clientPlayerThree.send(message);
        clientPlayerFour.send(message);
    }

    /**
     * @param user    The user.
     * @param teamOne The first team.
     * @param teamTwo The second team.
     *
     * @return Returns the ID of the team the user belongs to.
     *
     * @author Thomas Weber
     * @since 1.0.0
     */
    private int getTeamIdForPlayer(final UserEntity user, final TeamEntity teamOne, final TeamEntity teamTwo) {
        boolean userIsInTeamOne = teamOne.checkIfPlayerIsInTeam(user);
        if (userIsInTeamOne) {
            return teamOne.getId();
        } else {
            return teamTwo.getId();
        }
    }

    /**
     * Sends a ChooseGameMode message to the user defined in the RoundEntity.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    private void sendChooseGameMode() {
        ChooseGameMode chooseGameMode = new ChooseGameMode(new ChooseGameModeData());
        String gameModeChooserUsername = currentRound.getGameModeChooser().getUsername();
        if (gameModeChooserUsername.equals(clientPlayerOne.getUsername())) {
            clientPlayerOne.send(chooseGameMode);
        } else if (gameModeChooserUsername.equals(clientPlayerTwo.getUsername())) {
            clientPlayerTwo.send(chooseGameMode);
        } else if (gameModeChooserUsername.equals(clientPlayerThree.getUsername())) {
            clientPlayerThree.send(chooseGameMode);
        } else if (gameModeChooserUsername.equals(clientPlayerFour.getUsername())) {
            clientPlayerFour.send(chooseGameMode);
        } else {
            throw new IllegalStateException("Unexpected value: " + currentRound.getGameModeChooser().getUsername());
        }
    }

    /**
     * @author Thomas Weber & Victor Hargrave & Manuele Vaccari
     * @since 1.0.0
     */
    @Override
    public void onPlayedCard(final PlayCardData data) throws InterruptedException {
        boolean isValid = validateMove(data);
        boolean isRoundOver = false;
        ClientUtil clientUtil = this.getClientUtilByUsername(data.getUsername());
        DeckEntity currentDeck = this.getCurrentDeckByUsername(data.getUsername());

        if (clientUtil == null || currentDeck == null) {
            return;
        }

        if (isValid) {
            TurnRepository turnRepository = TurnRepository.getSingleton(null);
            TurnEntity turn = turnRepository.getById(data.getTurnId());
            CardEntity card = CardRepository.getSingleton(null).getById(data.getCardId());
            if (turn != null) {
                if (card != null) {
                    turn.addCard(card, clientUtil.getUser());
                    turnRepository.update(turn);
                    currentDeck.setPlayedCard(data.getCardId());
                    DeckRepository.getSingleton(null).update(currentDeck);
                }
                if (turn.getCardFour() != null) {
                    UserEntity winningUser = calculateTurnWinner(currentRound, turn);
                    if (winningUser != null) {
                        turn.setWinningUser(winningUser);
                        turnRepository.update(turn);
                    }

                    // Calculate points depending on game mode
                    int points = calculatePointsByGameMode(turn);

                    try {
                        isRoundOver = isRoundOver(turnRepository, winningUser);
                        // The last "Stich" gives 5 extra points to winning team
                        if (isRoundOver) {
                            points = (points + 5);
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    // Update points for the winning team
                    broadcastPointsToWinningTeam(turn, winningUser, points);

                }
                String winningUsername = turn.getWinningUser() != null ? turn.getWinningUser().getUsername() : "";
                BroadcastTurn broadcastTurn = new BroadcastTurn(new BroadcastTurnData(turn.getId(),
                    turn.getStartingPlayer().getUsername(), winningUsername,
                    turn.getCards().stream().map(CardEntity::toCardData).collect(Collectors.toList())
                ));
                this.currentTurn = turn;
                broadcast(broadcastTurn);
                if (isRoundOver) {

                    BroadcastRoundOver broadcastRoundOver = new BroadcastRoundOver(
                        new BroadcastRoundOverData(currentRound.getId(), currentRound.getPointsTeamOne(), currentRound.getPointsTeamTwo(),
                            game.getTeamOne().getPlayerOne().getUsername(), game.getTeamOne().getPlayerTwo().getUsername(),
                            game.getTeamTwo().getPlayerOne().getUsername(), game.getTeamTwo().getPlayerTwo().getUsername()));
                    broadcast(broadcastRoundOver);
                    return;
                }

                // start new turn after 3.5 seconds
                if (turn.getWinningUser() != null) {
                    Thread.sleep(3500);
                    TurnEntity newTurn = addNewTurn(turn.getWinningUser(), currentRound);

                    BroadcastTurn newBroadcastTurn = new BroadcastTurn(new BroadcastTurnData(newTurn.getId(),
                        newTurn.getStartingPlayer().getUsername(), "",
                        newTurn.getCards().stream().map(CardEntity::toCardData).collect(Collectors.toList())
                    ));
                    this.currentTurn = newTurn;
                    broadcast(newBroadcastTurn);
                }
            }
        } else {
            // If playedCard is first card in current turn no validations have to be made, just set the playedCard as first card of turn
            PlayedCard result = new PlayedCard(new PlayedCardData(isValid));
            clientUtil.send(result);
        }
    }

    /**
     * @param turn        The current turn.
     * @param winningUser The user who won.
     * @param points      The total points.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    private void broadcastPointsToWinningTeam(final TurnEntity turn, final UserEntity winningUser, final int points) {
        BroadcastPoints pointsMsg = new BroadcastPoints(new BroadcastPointsData(turn.getId(), points));
        if (game.getTeamOne().checkIfPlayerIsInTeam(winningUser)) {
            currentRound.addPointsTeamOne(points);
            RoundRepository.getSingleton(null).update(currentRound);
            getClientUtilByUsername(game.getTeamOne().getPlayerOne().getUsername()).send(pointsMsg);
            getClientUtilByUsername(game.getTeamOne().getPlayerTwo().getUsername()).send(pointsMsg);
        } else if (game.getTeamTwo().checkIfPlayerIsInTeam(winningUser)) {
            currentRound.addPointsTeamTwo(points);
            RoundRepository.getSingleton(null).update(currentRound);
            getClientUtilByUsername(game.getTeamTwo().getPlayerOne().getUsername()).send(pointsMsg);
            getClientUtilByUsername(game.getTeamTwo().getPlayerTwo().getUsername()).send(pointsMsg);
        } else {
            logger.fatal("User does not belong to any team.");
        }
    }

    /**
     * @param turnRepository The turn repo.
     * @param winningUser    The user who won.
     *
     * @return Returns whether the round is already over.
     *
     * @throws SQLException If an SQL error occurs.
     * @author Victor Hargrave
     * @since 1.0.0
     */
    private boolean isRoundOver(final TurnRepository turnRepository, final UserEntity winningUser) throws SQLException {
        boolean isRoundOver;
        int numberOfTurnsPlayed = turnRepository.getDao().queryForEq("round_id", currentRound.getId()).size();
        isRoundOver = numberOfTurnsPlayed == 9 && winningUser != null;
        return isRoundOver;
    }

    /**
     * @param turn The current turn.
     *
     * @return Returns the total points.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    private int calculatePointsByGameMode(final TurnEntity turn) {
        int points = 0;
        if (currentRound.getGameMode() == GameMode.TRUMPF) {
            points = calculateCardPointsTrumpf(turn.getCards(), currentRound.getTrumpfSuit());
        } else if (currentRound.getGameMode() == GameMode.OBE_ABE) {
            points = calculateCardPointsObeAbe(turn.getCards());
        } else if (currentRound.getGameMode() == GameMode.ONDE_UFE) {
            points = calculateCardPointsOndeUfe(turn.getCards());
        } else {
            logger.fatal("Unknown game mode to calculate points.");
        }
        return points;
    }

    ////////////////////////////////////////////////////////////////////////////
    // Figure out the points for cards.
    ////////////////////////////////////////////////////////////////////////////

    /**
     * @param cards  All cards
     * @param trumpf The trump suit to check
     *
     * @return Returns the points of all cards together.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    private static int calculateCardPointsTrumpf(final ArrayList<CardEntity> cards, final Card.Suit trumpf) {
        // Convert suit enum to database entity
        SuitEntity trumpfSuit = SuitRepository.getSingleton(null).getByName(trumpf.toString().toLowerCase());
        assert trumpfSuit != null;

        int points = 0;
        for (CardEntity card : cards) {
            if (card.getSuit().equals(trumpfSuit)) {
                if (card.getRank().getKey().equals("jack")) {
                    points += 20;
                } else if (card.getRank().getKey().equals("9")) {
                    points += 14;
                } else {
                    points += card.getRank().getPointsTrumpf();
                }
            } else {
                points += card.getRank().getPointsTrumpf();
            }
        }
        return points;
    }

    /**
     * @param cards All cards
     *
     * @return Returns the points of all cards together.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    private static int calculateCardPointsObeAbe(final ArrayList<CardEntity> cards) {
        int points = 0;
        for (CardEntity card : cards) {
            points += card.getRank().getPointsObeAbe();
        }
        return points;
    }

    /**
     * @param cards All cards
     *
     * @return Returns the points of all cards together.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    private static int calculateCardPointsOndeUfe(final ArrayList<CardEntity> cards) {
        int points = 0;
        for (CardEntity card : cards) {
            points += card.getRank().getPointsOndeufe();
        }
        return points;
    }

    ////////////////////////////////////////////////////////////////////////////
    // Figure out the winner of a turn.
    ////////////////////////////////////////////////////////////////////////////

    /**
     * @param currentRound The current round.
     * @param currentTurn  The current turn.
     *
     * @return Returns the user who won the current turn.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    private static UserEntity calculateTurnWinner(final RoundEntity currentRound, final TurnEntity currentTurn) {
        CardEntity winningCard = null;
        if (currentRound.getGameMode() == GameMode.TRUMPF) {
            winningCard = calculateTurnWinnerTrump(currentRound.getTrumpfSuit(), currentTurn);
        } else if (currentRound.getGameMode() == GameMode.OBE_ABE) {
            winningCard = calculateTurnWinnerObeAbe(currentTurn);
        } else if (currentRound.getGameMode() == GameMode.ONDE_UFE) {
            winningCard = calculateTurnWinnerOndeufe(currentTurn);
        }

        if (winningCard == null) {
            return null;
        }

        // Figure out who played the card and return the user.
        if (winningCard.equals(currentTurn.getCardOne())) {
            return currentTurn.getPlayerCardOne();
        } else if (winningCard.equals(currentTurn.getCardTwo())) {
            return currentTurn.getPlayerCardTwo();
        } else if (winningCard.equals(currentTurn.getCardThree())) {
            return currentTurn.getPlayerCardThree();
        } else if (winningCard.equals(currentTurn.getCardFour())) {
            return currentTurn.getPlayerCardFour();
        } else {
            return null;
        }
    }

    /**
     * @param trumpf      The trumpf of the current round.
     * @param currentTurn The current turn.
     *
     * @return Returns the card which wins the current turn.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    private static CardEntity calculateTurnWinnerTrump(final Card.Suit trumpf, final TurnEntity currentTurn) {
        RankRepository rankRepo = RankRepository.getSingleton(null);
        SuitRepository suitRepo = SuitRepository.getSingleton(null);

        // Get suit of first card played
        SuitEntity suitOfFirstCard = currentTurn.getCardOne().getSuit();

        // Convert suit enum to database entity
        SuitEntity trumpfSuit = suitRepo.getByName(trumpf.toString().toLowerCase());
        assert trumpfSuit != null;

        // Get all 4 cards
        ArrayList<CardEntity> cards = currentTurn.getCards();

        // Check by order
        if (CardRepository.isAnyCardOfSuit(cards, trumpfSuit)) {
            if (CardRepository.isAnyCardOfSuitAndRank(cards, trumpfSuit, rankRepo.getByName("jack"))) {
                return CardRepository.getCardOfSuitAndRank(cards, trumpfSuit, rankRepo.getByName("jack"));
            } else if (CardRepository.isAnyCardOfSuitAndRank(cards, trumpfSuit, rankRepo.getByName("9"))) {
                return CardRepository.getCardOfSuitAndRank(cards, trumpfSuit, rankRepo.getByName("9"));
            } else if (CardRepository.isAnyCardOfSuitAndRank(cards, trumpfSuit, rankRepo.getByName("ace"))) {
                return CardRepository.getCardOfSuitAndRank(cards, trumpfSuit, rankRepo.getByName("ace"));
            } else if (CardRepository.isAnyCardOfSuitAndRank(cards, trumpfSuit, rankRepo.getByName("king"))) {
                return CardRepository.getCardOfSuitAndRank(cards, trumpfSuit, rankRepo.getByName("king"));
            } else if (CardRepository.isAnyCardOfSuitAndRank(cards, trumpfSuit, rankRepo.getByName("queen"))) {
                return CardRepository.getCardOfSuitAndRank(cards, trumpfSuit, rankRepo.getByName("queen"));
            } else if (CardRepository.isAnyCardOfSuitAndRank(cards, trumpfSuit, rankRepo.getByName("10"))) {
                return CardRepository.getCardOfSuitAndRank(cards, trumpfSuit, rankRepo.getByName("10"));
            } else if (CardRepository.isAnyCardOfSuitAndRank(cards, trumpfSuit, rankRepo.getByName("8"))) {
                return CardRepository.getCardOfSuitAndRank(cards, trumpfSuit, rankRepo.getByName("8"));
            } else if (CardRepository.isAnyCardOfSuitAndRank(cards, trumpfSuit, rankRepo.getByName("7"))) {
                return CardRepository.getCardOfSuitAndRank(cards, trumpfSuit, rankRepo.getByName("7"));
            } else if (CardRepository.isAnyCardOfSuitAndRank(cards, trumpfSuit, rankRepo.getByName("6"))) {
                return CardRepository.getCardOfSuitAndRank(cards, trumpfSuit, rankRepo.getByName("6"));
            } else {
                return null;
            }
        } else {
            if (CardRepository.isAnyCardOfSuitAndRank(cards, suitOfFirstCard, rankRepo.getByName("ace"))) {
                return CardRepository.getCardOfSuitAndRank(cards, suitOfFirstCard, rankRepo.getByName("ace"));
            } else if (CardRepository.isAnyCardOfSuitAndRank(cards, suitOfFirstCard, rankRepo.getByName("king"))) {
                return CardRepository.getCardOfSuitAndRank(cards, suitOfFirstCard, rankRepo.getByName("king"));
            } else if (CardRepository.isAnyCardOfSuitAndRank(cards, suitOfFirstCard, rankRepo.getByName("queen"))) {
                return CardRepository.getCardOfSuitAndRank(cards, suitOfFirstCard, rankRepo.getByName("queen"));
            } else if (CardRepository.isAnyCardOfSuitAndRank(cards, suitOfFirstCard, rankRepo.getByName("jack"))) {
                return CardRepository.getCardOfSuitAndRank(cards, suitOfFirstCard, rankRepo.getByName("jack"));
            } else if (CardRepository.isAnyCardOfSuitAndRank(cards, suitOfFirstCard, rankRepo.getByName("10"))) {
                return CardRepository.getCardOfSuitAndRank(cards, suitOfFirstCard, rankRepo.getByName("10"));
            } else if (CardRepository.isAnyCardOfSuitAndRank(cards, suitOfFirstCard, rankRepo.getByName("9"))) {
                return CardRepository.getCardOfSuitAndRank(cards, suitOfFirstCard, rankRepo.getByName("9"));
            } else if (CardRepository.isAnyCardOfSuitAndRank(cards, suitOfFirstCard, rankRepo.getByName("8"))) {
                return CardRepository.getCardOfSuitAndRank(cards, suitOfFirstCard, rankRepo.getByName("8"));
            } else if (CardRepository.isAnyCardOfSuitAndRank(cards, suitOfFirstCard, rankRepo.getByName("7"))) {
                return CardRepository.getCardOfSuitAndRank(cards, suitOfFirstCard, rankRepo.getByName("7"));
            } else if (CardRepository.isAnyCardOfSuitAndRank(cards, suitOfFirstCard, rankRepo.getByName("6"))) {
                return CardRepository.getCardOfSuitAndRank(cards, suitOfFirstCard, rankRepo.getByName("6"));
            } else {
                return null;
            }
        }
    }

    /**
     * @param currentTurn The current turn.
     *
     * @return Returns the card which wins the current turn.
     *
     * @author Sasa Trajkova
     * @since 1.0.0
     */
    private static CardEntity calculateTurnWinnerObeAbe(final TurnEntity currentTurn) {
        RankRepository rankRepo = RankRepository.getSingleton(null);

        // Get suit of first card played
        SuitEntity suitOfFirstCard = currentTurn.getCardOne().getSuit();

        // Get all 4 cards
        ArrayList<CardEntity> cards = currentTurn.getCards();

        // Check by order
        if (CardRepository.isAnyCardOfSuitAndRank(cards, suitOfFirstCard, rankRepo.getByName("ace"))) {
            return CardRepository.getCardOfSuitAndRank(cards, suitOfFirstCard, rankRepo.getByName("ace"));
        } else if (CardRepository.isAnyCardOfSuitAndRank(cards, suitOfFirstCard, rankRepo.getByName("king"))) {
            return CardRepository.getCardOfSuitAndRank(cards, suitOfFirstCard, rankRepo.getByName("king"));
        } else if (CardRepository.isAnyCardOfSuitAndRank(cards, suitOfFirstCard, rankRepo.getByName("queen"))) {
            return CardRepository.getCardOfSuitAndRank(cards, suitOfFirstCard, rankRepo.getByName("queen"));
        } else if (CardRepository.isAnyCardOfSuitAndRank(cards, suitOfFirstCard, rankRepo.getByName("jack"))) {
            return CardRepository.getCardOfSuitAndRank(cards, suitOfFirstCard, rankRepo.getByName("jack"));
        } else if (CardRepository.isAnyCardOfSuitAndRank(cards, suitOfFirstCard, rankRepo.getByName("10"))) {
            return CardRepository.getCardOfSuitAndRank(cards, suitOfFirstCard, rankRepo.getByName("10"));
        } else if (CardRepository.isAnyCardOfSuitAndRank(cards, suitOfFirstCard, rankRepo.getByName("9"))) {
            return CardRepository.getCardOfSuitAndRank(cards, suitOfFirstCard, rankRepo.getByName("9"));
        } else if (CardRepository.isAnyCardOfSuitAndRank(cards, suitOfFirstCard, rankRepo.getByName("8"))) {
            return CardRepository.getCardOfSuitAndRank(cards, suitOfFirstCard, rankRepo.getByName("8"));
        } else if (CardRepository.isAnyCardOfSuitAndRank(cards, suitOfFirstCard, rankRepo.getByName("7"))) {
            return CardRepository.getCardOfSuitAndRank(cards, suitOfFirstCard, rankRepo.getByName("7"));
        } else if (CardRepository.isAnyCardOfSuitAndRank(cards, suitOfFirstCard, rankRepo.getByName("6"))) {
            return CardRepository.getCardOfSuitAndRank(cards, suitOfFirstCard, rankRepo.getByName("6"));
        } else {
            return null;
        }
    }

    /**
     * @param currentTurn The current turn.
     *
     * @return Returns the card which wins the current turn.
     *
     * @author Victor Hargrave
     * @since 1.0.0
     */
    private static CardEntity calculateTurnWinnerOndeufe(final TurnEntity currentTurn) {
        RankRepository rankRepo = RankRepository.getSingleton(null);

        // Get suit of first card played
        SuitEntity suitOfFirstCard = currentTurn.getCardOne().getSuit();

        // Get all 4 cards
        ArrayList<CardEntity> cards = currentTurn.getCards();

        // Check by order
        if (CardRepository.isAnyCardOfSuitAndRank(cards, suitOfFirstCard, rankRepo.getByName("6"))) {
            return CardRepository.getCardOfSuitAndRank(cards, suitOfFirstCard, rankRepo.getByName("6"));
        } else if (CardRepository.isAnyCardOfSuitAndRank(cards, suitOfFirstCard, rankRepo.getByName("7"))) {
            return CardRepository.getCardOfSuitAndRank(cards, suitOfFirstCard, rankRepo.getByName("7"));
        } else if (CardRepository.isAnyCardOfSuitAndRank(cards, suitOfFirstCard, rankRepo.getByName("8"))) {
            return CardRepository.getCardOfSuitAndRank(cards, suitOfFirstCard, rankRepo.getByName("8"));
        } else if (CardRepository.isAnyCardOfSuitAndRank(cards, suitOfFirstCard, rankRepo.getByName("9"))) {
            return CardRepository.getCardOfSuitAndRank(cards, suitOfFirstCard, rankRepo.getByName("9"));
        } else if (CardRepository.isAnyCardOfSuitAndRank(cards, suitOfFirstCard, rankRepo.getByName("10"))) {
            return CardRepository.getCardOfSuitAndRank(cards, suitOfFirstCard, rankRepo.getByName("10"));
        } else if (CardRepository.isAnyCardOfSuitAndRank(cards, suitOfFirstCard, rankRepo.getByName("jack"))) {
            return CardRepository.getCardOfSuitAndRank(cards, suitOfFirstCard, rankRepo.getByName("jack"));
        } else if (CardRepository.isAnyCardOfSuitAndRank(cards, suitOfFirstCard, rankRepo.getByName("queen"))) {
            return CardRepository.getCardOfSuitAndRank(cards, suitOfFirstCard, rankRepo.getByName("queen"));
        } else if (CardRepository.isAnyCardOfSuitAndRank(cards, suitOfFirstCard, rankRepo.getByName("king"))) {
            return CardRepository.getCardOfSuitAndRank(cards, suitOfFirstCard, rankRepo.getByName("king"));
        } else if (CardRepository.isAnyCardOfSuitAndRank(cards, suitOfFirstCard, rankRepo.getByName("ace"))) {
            return CardRepository.getCardOfSuitAndRank(cards, suitOfFirstCard, rankRepo.getByName("ace"));
        } else {
            return null;
        }
    }

    ////////////////////////////////////////////////////////////////////////////

    /**
     * @param startingPlayer The player to start.
     * @param round          The round.
     *
     * @return Returns the new turn.
     *
     * @author Victor Hargrave
     * @since 1.0.0
     */
    private static TurnEntity addNewTurn(final UserEntity startingPlayer, final RoundEntity round) {
        TurnEntity turn = (new TurnEntity())
            .setRound(round)
            .setStartingPlayer(startingPlayer);
        TurnRepository.getSingleton(null).add(turn);
        return turn;
    }

    /**
     * @param data The card which was played.
     *
     * @return Returns true if valid, otherwise false.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    private boolean validateMove(final PlayCardData data) {
        boolean isValidMove = false;
        CardEntity playedCard = CardRepository.getSingleton(null).getById(data.getCardId());
        DeckEntity deckOfPlayer = getCurrentDeckByUsername(data.getUsername());

        CardEntity cardOne = currentTurn.getCardOne();
        if (currentRound.getGameMode() == GameMode.TRUMPF) {
            isValidMove = validateMoveTrump(playedCard, deckOfPlayer, cardOne, String.valueOf(currentRound.getTrumpfSuit()));
        } else if (currentRound.getGameMode() == GameMode.OBE_ABE) {
            isValidMove = validateMoveObeAbe(playedCard, deckOfPlayer, cardOne);
        } else if (currentRound.getGameMode() == GameMode.ONDE_UFE) {
            isValidMove = validateMoveOndeUfe(playedCard, deckOfPlayer, cardOne);
        }

        return isValidMove;
    }

    /**
     * Validates a move from one of the clients for the game mode "Trump". Unit
     * test of this method in "GameUtilTest".
     *
     * @param playedCard      The card which has been played
     * @param deck            The deck of the client to have made the move
     * @param firstCardOfTurn The first card of this turn which was played
     * @param trumpSuit       Suit which is trump in this round
     *
     * @return True if the move is valid, false if invalid
     *
     * @author Thomas Weber
     * @since 1.0.0
     */
    public static boolean validateMoveTrump(final CardEntity playedCard, final DeckEntity deck, final CardEntity firstCardOfTurn, final String trumpSuit) {
        // In case the playedCard is first card of current turn the move is
        // always valid
        if (firstCardOfTurn == null || firstCardOfTurn.getId() == playedCard.getId()) {
            return true;
        }

        // If playedCard equals the trump suit, the move is always valid
        if (playedCard.getSuit().getKey().equals(trumpSuit)) {
            return true;
        }

        boolean isValidMove = true;

        // If the suit of the playedCard does not equal the suit of the
        // firstCardOfTurn, it might be an invalid move - depending on if the
        // client had another card in his hands which he must have played.
        if (!firstCardOfTurn.getSuit().getKey().equals(playedCard.getSuit().getKey())) {

            // Get deck of the current player as array and which cards have been
            // played
            ArrayList<CardEntity> cardsInDeck = deck.getCards();
            ArrayList<Boolean> cardsHaveBeenPlayed = deck.getCardsHaveBeenPlayed();

            for (int i = 0; i < cardsInDeck.size(); i++) {
                // Only unplayed cards should be checked
                if (cardsHaveBeenPlayed.get(i) == null || !cardsHaveBeenPlayed.get(i)) {
                    // If a card has the same suite as the firstCardOfTurn, this
                    // card has to have been played and thus this move is
                    // invalid
                    if (cardsInDeck.get(i).getSuit().getKey().equals(firstCardOfTurn.getSuit().getKey())) {

                        // Check exception of trump jack as you are never forced
                        // to play this card.
                        if (cardsInDeck.get(i).getSuit().getKey().equals(trumpSuit)) {
                            if (cardsInDeck.get(i).getRank().getId() != 6) {
                                isValidMove = false;
                                break;
                            }
                        } else {
                            isValidMove = false;
                            break;
                        }

                    }
                }
            }

        }

        return isValidMove;
    }

    /**
     * @param playedCard      The card that the user wants to play.
     * @param deck            The complete deck of the player who wants to
     *                        play.
     * @param firstCardOfTurn The first card played in the current turn.
     *
     * @return Returns true if the move is valid otherwise false.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    public static boolean validateMoveObeAbe(final CardEntity playedCard, final DeckEntity deck, final CardEntity firstCardOfTurn) {
        // In case the playedCard is first card of current turn the move is
        // always valid
        if (firstCardOfTurn == null || firstCardOfTurn.getId() == playedCard.getId()) {
            return true;
        }

        // If the suit of the playedCard does not equal the suit of the
        // firstCardOfTurn, it might be an invalid move - depending on if the
        // client had another card in his hands which he must have played.
        if (firstCardOfTurn.getSuit().getKey().equals(playedCard.getSuit().getKey())) {
            return true;
        }

        // Get deck of the current player as array and which cards have been
        // played
        ArrayList<CardEntity> cardsInDeck = deck.getCards();
        ArrayList<Boolean> cardsHaveBeenPlayed = deck.getCardsHaveBeenPlayed();

        for (int i = 0; i < cardsInDeck.size(); i++) {
            // Only unplayed cards should be checked
            if (cardsHaveBeenPlayed.get(i) == null || !cardsHaveBeenPlayed.get(i)) {
                // If a card has the same suite as the firstCardOfTurn, this
                // card has to have been played and thus this move is invalid
                if (cardsInDeck.get(i).getSuit().getKey().equals(firstCardOfTurn.getSuit().getKey())) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * @param playedCard      The card that the user wants to play.
     * @param deck            The complete deck of the player who wants to
     *                        play.
     * @param firstCardOfTurn The first card played in the current turn.
     *
     * @return Returns true if the move is valid otherwise false.
     *
     * @author Manuele Vaccari, Sasa Trajkova
     * @since 1.0.0
     */
    public static boolean validateMoveOndeUfe(final CardEntity playedCard, final DeckEntity deck, final CardEntity firstCardOfTurn) {
        // In case the playedCard is first card of current turn the move is
        // always valid
        if (firstCardOfTurn == null || firstCardOfTurn.getId() == playedCard.getId()) {
            return true;
        }

        // If the suit of the playedCard does not equal the suit of the
        // firstCardOfTurn, it might be an invalid move - depending on if the
        // client had another card in his hands which he must have played.
        if (firstCardOfTurn.getSuit().getKey().equals(playedCard.getSuit().getKey())) {
            return true;
        }

        // Get deck of the current player as array and which cards have been
        // played
        ArrayList<CardEntity> cardsInDeck = deck.getCards();
        ArrayList<Boolean> cardsHaveBeenPlayed = deck.getCardsHaveBeenPlayed();

        for (int i = 0; i < cardsInDeck.size(); i++) {
            // Only unplayed cards should be checked
            if (cardsHaveBeenPlayed.get(i) == null || !cardsHaveBeenPlayed.get(i)) {
                // If a card has the same suite as the firstCardOfTurn, this
                // card has to have been played and thus this move is invalid
                if (cardsInDeck.get(i).getSuit().getKey().equals(firstCardOfTurn.getSuit().getKey())) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * @param username The username.
     *
     * @return Returns the client util of the user.
     *
     * @author Victor Hargrave
     * @since 1.0.0
     */
    private ClientUtil getClientUtilByUsername(final String username) {
        if (username.equals(clientPlayerOne.getUsername())) {
            return clientPlayerOne;
        } else if (username.equals(clientPlayerTwo.getUsername())) {
            return clientPlayerTwo;
        } else if (username.equals(clientPlayerThree.getUsername())) {
            return clientPlayerThree;
        } else if (username.equals(clientPlayerFour.getUsername())) {
            return clientPlayerFour;
        }
        return null;
    }

    /**
     * @param username The username.
     *
     * @return Returns the deck of the user.
     *
     * @author Victor Hargrave
     * @since 1.0.0
     */
    private DeckEntity getCurrentDeckByUsername(final String username) {
        if (username.equals(clientPlayerOne.getUsername())) {
            return currentDeckPlayerOne;
        } else if (username.equals(clientPlayerTwo.getUsername())) {
            return currentDeckPlayerTwo;
        } else if (username.equals(clientPlayerThree.getUsername())) {
            return currentDeckPlayerThree;
        } else if (username.equals(clientPlayerFour.getUsername())) {
            return currentDeckPlayerFour;
        }
        return null;
    }

    /**
     * @author Victor Hargrave
     * @since 1.0.0
     */
    @Override
    public void onStopPlaying(final StopPlayingData data) {
        ClientUtil client = getClientUtilByUsername(data.getUsername());
        if (client != null) {
            setGameToInactive();
            BroadcastAPlayerQuit broadcastAPlayerQuit = new BroadcastAPlayerQuit(new BroadcastAPlayerQuitData());
            broadcast(broadcastAPlayerQuit);
            removeEventListeners(clientPlayerOne, clientPlayerTwo, clientPlayerThree, clientPlayerFour);
            SearchGameUtil searchGameUtil = ServiceLocator.get(SearchGameUtil.class);
            assert searchGameUtil != null;
            searchGameUtil.remove(clientPlayerOne);
            searchGameUtil.remove(clientPlayerTwo);
            searchGameUtil.remove(clientPlayerThree);
            searchGameUtil.remove(clientPlayerFour);
            // remove reference to this game to initiate garbage collection
            SearchGameUtil.runningGames.remove(this);
        }
    }

    /**
     * @author Victor Hargrave
     * @since 1.0.0
     */
    private void setGameToInactive() {
        game.setActive(false);
        GameRepository.getSingleton(null).update(game);
    }

    /**
     * @author Thomas Weber
     * @since 1.0.0
     */
    @Override
    public void onContinuePlaying(final ContinuePlayingData data) {
        continuePlayClicks++;
        // If all four players decide to play another round, initialize new round
        if (continuePlayClicks == 4) {
            // Set back the counter
            continuePlayClicks = 0;
            // Initialize new round
            this.startNewRound();
        }
    }

    /**
     * @author Thomas Weber
     * @since 1.0.0
     */
    private UserEntity getGameModeChooser() {
        UserEntity lastRoundGameModeChooser = currentRound.getGameModeChooser();
        if (lastRoundGameModeChooser.getUsername().equals(clientPlayerOne.getUsername())) {
            return clientPlayerTwo.getUser();
        } else if (lastRoundGameModeChooser.getUsername().equals(clientPlayerTwo.getUsername())) {
            return clientPlayerThree.getUser();
        } else if (lastRoundGameModeChooser.getUsername().equals(clientPlayerThree.getUsername())) {
            return clientPlayerFour.getUser();
        } else {
            return clientPlayerOne.getUser();
        }

    }

}
