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

package jass.client.util;

import jass.client.eventlistener.BroadcastAPlayerQuitEventListener;
import jass.client.eventlistener.BroadcastDeckEventListener;
import jass.client.eventlistener.BroadcastGameModeEventListener;
import jass.client.eventlistener.BroadcastPointsEventListener;
import jass.client.eventlistener.BroadcastRoundOverEventListener;
import jass.client.eventlistener.BroadcastTurnEventListener;
import jass.client.eventlistener.ChooseGameModeEventListener;
import jass.client.eventlistener.DisconnectEventListener;
import jass.client.eventlistener.GameFoundEventListener;
import jass.client.eventlistener.PlayedCardEventListener;
import jass.lib.message.BroadcastAPlayerQuitData;
import jass.lib.message.BroadcastDeckData;
import jass.lib.message.BroadcastGameModeData;
import jass.lib.message.BroadcastPointsData;
import jass.lib.message.BroadcastRoundOverData;
import jass.lib.message.BroadcastTurnData;
import jass.lib.message.ChooseGameModeData;
import jass.lib.message.GameFoundData;
import jass.lib.message.MessageData;
import jass.lib.message.PlayedCardData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

/**
 * @author Manuele Vaccari & Victor Hargrave & Thomas Weber
 * @version %I%, %G%
 * @since 1.0.0
 */
public final class EventUtil {
    /**
     * The logger to print to console and save in a .log file.
     */
    private static final Logger logger = LogManager.getLogger(EventUtil.class);

    /**
     * A list of all objects listening to a disconnect event.
     */
    private static final ArrayList<DisconnectEventListener> disconnectListeners = new ArrayList<>();

    /**
     * Object listening to game found event.
     */
    private static final ArrayList<GameFoundEventListener> gameFoundListeners = new ArrayList<>();

    /**
     * Object listening to broadcast deck event.
     */
    private static final ArrayList<BroadcastDeckEventListener> broadcastDeckListeners = new ArrayList<>();

    /**
     * A list of all objects listening to a choose game mode event.
     */
    private static final ArrayList<ChooseGameModeEventListener> chooseGameModeListeners = new ArrayList<>();

    /**
     * A list of objects listening to broadcast game mode event.
     */
    private static final ArrayList<BroadcastGameModeEventListener> broadcastGameModeListeners = new ArrayList<>();

    /**
     * A list of all objects listening to a play card event.
     */
    private static final ArrayList<PlayedCardEventListener> playedCardListeners = new ArrayList<>();

    /**
     * A list of objects listening to broadcast played card event.
     */
    private static final ArrayList<BroadcastTurnEventListener> broadcastTurnListeners = new ArrayList<>();

    /**
     * A list of objects listening to broadcast points event.
     */
    private static final ArrayList<BroadcastPointsEventListener> broadcastPointsListeners = new ArrayList<>();

    /**
     * A list of objects listening to round over events.
     */
    private static final ArrayList<BroadcastRoundOverEventListener> broadcastRoundOverListeners = new ArrayList<>();

    /**
     * A list of objects listening to a player quit events.
     */
    private static final ArrayList<BroadcastAPlayerQuitEventListener> broadcastAPlayerQuitListeners = new ArrayList<>();

    /**
     * @param msgData The message to send to the listeners.
     *
     * @return Returns true if an event was caught, otherwise false (something
     * else must handle the message)
     *
     * @author Thomas Weber, Manuele Vaccari, Victor Hargrave
     * @since 1.0.0
     */
    public static boolean handleEventListenerOnMessage(final MessageData msgData) {
        switch (msgData.getMessageType()) {
            case "GameFound":
                for (GameFoundEventListener listener : new ArrayList<>(gameFoundListeners)) {
                    logger.info("Invoking onGameFound event on " + listener.getClass().getName());
                    listener.onGameFound((GameFoundData) msgData);
                }
                return true;
            case "BroadcastDeck":
                for (BroadcastDeckEventListener listener : new ArrayList<>(broadcastDeckListeners)) {
                    logger.info("Invoking onBroadcastDeck event on " + listener.getClass().getName());
                    listener.onBroadcastDeck((BroadcastDeckData) msgData);
                }
                return true;
            case "ChooseGameMode":
                for (ChooseGameModeEventListener listener : new ArrayList<>(chooseGameModeListeners)) {
                    logger.info("Invoking onChooseGameMode event on " + listener.getClass().getName());
                    listener.onChooseGameMode((ChooseGameModeData) msgData);
                }
                return true;
            case "BroadcastGameMode":
                for (BroadcastGameModeEventListener listener : new ArrayList<>(broadcastGameModeListeners)) {
                    logger.info("Invoking onBroadcastGameMode event on " + listener.getClass().getName());
                    listener.onBroadcastGameMode((BroadcastGameModeData) msgData);
                }
                return true;
            case "PlayedCard":
                for (PlayedCardEventListener listener : new ArrayList<>(playedCardListeners)) {
                    logger.info("Invoking onPlayedCard event on " + listener.getClass().getName());
                    listener.onPlayedCard((PlayedCardData) msgData);
                }
                return true;
            case "BroadcastTurn":
                for (BroadcastTurnEventListener listener : new ArrayList<>(broadcastTurnListeners)) {
                    logger.info("Invoking onBroadcastTurn event on " + listener.getClass().getName());
                    listener.onBroadcastTurn((BroadcastTurnData) msgData);
                }
                return true;
            case "BroadcastPoints":
                for (BroadcastPointsEventListener listener : new ArrayList<>(broadcastPointsListeners)) {
                    logger.info("Invoking onBroadcastPoints event on " + listener.getClass().getName());
                    listener.onBroadcastPoints((BroadcastPointsData) msgData);
                }
                return true;
            case "BroadcastRoundOver":
                for (BroadcastRoundOverEventListener listener : new ArrayList<>(broadcastRoundOverListeners)) {
                    logger.info("Invoking onRoundOver event on " + listener.getClass().getName());
                    listener.onRoundOver((BroadcastRoundOverData) msgData);
                }
                return true;
            case "BroadcastAPlayerQuit":
                for (BroadcastAPlayerQuitEventListener listener : new ArrayList<>(broadcastAPlayerQuitListeners)) {
                    logger.info("Invoking onAPlayerQuit event on " + listener.getClass().getName());
                    listener.onAPlayerQuit((BroadcastAPlayerQuitData) msgData);
                }
                return true;
            default:
                return false;
        }
    }

    /**
     * Utility classes, which are collections of static members, are not meant
     * to be instantiated.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    private EventUtil() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * @param listener A DisconnectEventListener object
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    public static void addDisconnectListener(final DisconnectEventListener listener) {
        disconnectListeners.add(listener);
    }

    /**
     * @return Returns an array of the listeners.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    public static ArrayList<DisconnectEventListener> getDisconnectListeners() {
        return disconnectListeners;
    }

    /**
     * @param listener A DisconnectEventListener object
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    public static void removeDisconnectListener(final DisconnectEventListener listener) {
        disconnectListeners.remove(listener);
    }

    /**
     * @param listener The listener to listen to game found.
     *
     * @author Thomas Weber
     * @since 1.0.0
     */
    public static void addGameFoundEventListener(final GameFoundEventListener listener) {
        gameFoundListeners.add(listener);
    }

    /**
     * @return Returns an array of the listeners.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    public static ArrayList<GameFoundEventListener> getGameFoundListeners() {
        return gameFoundListeners;
    }

    /**
     * @param listener A GameFoundEventListener object
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    public static void removeGameFoundEventListener(final GameFoundEventListener listener) {
        gameFoundListeners.remove(listener);
    }

    /**
     * @param listener The listener to listen to broadcast deck.
     *
     * @author Victor Hargrave
     * @since 1.0.0
     */
    public static void addBroadcastDeckEventListener(final BroadcastDeckEventListener listener) {
        broadcastDeckListeners.add(listener);
    }

    /**
     * @return Returns an array of the listeners.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    public static ArrayList<BroadcastDeckEventListener> getBroadcastDeckListeners() {
        return broadcastDeckListeners;
    }

    /**
     * @param listener A BroadcastDeckEventListener object.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    public static void removeBroadcastDeckEventListener(final BroadcastDeckEventListener listener) {
        broadcastDeckListeners.remove(listener);
    }

    /**
     * @param listener A ChooseGameModeEventListener object.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    public static void addChooseGameModeEventListener(final ChooseGameModeEventListener listener) {
        chooseGameModeListeners.add(listener);
    }

    /**
     * @return Returns an array of the listeners.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    public static ArrayList<ChooseGameModeEventListener> getChooseGameModeListeners() {
        return chooseGameModeListeners;
    }

    /**
     * @param listener A ChooseGameModeEventListener object.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    public static void removeChooseGameModeEventListener(final ChooseGameModeEventListener listener) {
        chooseGameModeListeners.remove(listener);
    }

    /**
     * @param listener A ChooseGameModeEventListener object.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    public static void addBroadcastGameModeEventListener(final BroadcastGameModeEventListener listener) {
        broadcastGameModeListeners.add(listener);
    }

    /**
     * @return Returns an array of the listeners.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    public static ArrayList<BroadcastGameModeEventListener> getBroadcastGameModeListeners() {
        return broadcastGameModeListeners;
    }

    /**
     * @param listener A ChooseGameModeEventListener object.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    public static void removeBroadcastGameModeEventListener(final BroadcastGameModeEventListener listener) {
        broadcastGameModeListeners.remove(listener);
    }

    /**
     * @param listener A PlayedCardEventListener object.
     *
     * @author Victor Hargrave
     * @since 1.0.0
     */
    public static void addPlayedCardEventListener(final PlayedCardEventListener listener) {
        playedCardListeners.add(listener);
    }

    /**
     * @return Returns an array of the listeners.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    public static ArrayList<PlayedCardEventListener> getPlayedCardListeners() {
        return playedCardListeners;
    }

    /**
     * @param listener A PlayedCardEventListener object.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    public static void removePlayedCardEventListener(final PlayedCardEventListener listener) {
        playedCardListeners.remove(listener);
    }

    /**
     * @param listener A BroadcastTurnEventListener object.
     *
     * @author Victor Hargrave
     * @since 1.0.0
     */
    public static void addBroadcastedTurnEventListener(final BroadcastTurnEventListener listener) {
        broadcastTurnListeners.add(listener);
    }

    /**
     * @return Returns an array of the listeners.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    public static ArrayList<BroadcastTurnEventListener> getBroadcastTurnListeners() {
        return broadcastTurnListeners;
    }

    /**
     * @param listener A BroadcastTurnEventListener object.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    public static void removeBroadcastedTurnEventListener(final BroadcastTurnEventListener listener) {
        broadcastTurnListeners.remove(listener);
    }

    /**
     * @param listener A BroadcastPointsEventListener object.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    public static void addBroadcastPointsEventListener(final BroadcastPointsEventListener listener) {
        broadcastPointsListeners.add(listener);
    }

    /**
     * @return Returns an array of the listeners.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    public static ArrayList<BroadcastPointsEventListener> getBroadcastPointsListeners() {
        return broadcastPointsListeners;
    }

    /**
     * @param listener A BroadcastPointsEventListener object.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    public static void removeBroadcastPointsEventListener(final BroadcastPointsEventListener listener) {
        broadcastPointsListeners.remove(listener);
    }

    /**
     * @param listener A BroadcastRoundOverEventListener object.
     *
     * @author Victor Hargrave
     * @since 1.0.0
     */
    public static void addRoundOverEventListener(final BroadcastRoundOverEventListener listener) {
        broadcastRoundOverListeners.add(listener);
    }

    /**
     * @return Returns an array of the listeners.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    public static ArrayList<BroadcastRoundOverEventListener> getBroadcastRoundOverListeners() {
        return broadcastRoundOverListeners;
    }

    /**
     * @param listener A BroadcastRoundOverEventListener object.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    public static void removeRoundOverEventListener(final BroadcastRoundOverEventListener listener) {
        broadcastRoundOverListeners.remove(listener);
    }

    /**
     * @param listener A BroadcastAPlayerQuitEventListener object.
     *
     * @author Victor Hargrave
     * @since 1.0.0
     */
    public static void addAPlayerQuitEventListener(final BroadcastAPlayerQuitEventListener listener) {
        broadcastAPlayerQuitListeners.add(listener);
    }

    /**
     * @return Returns an array of the listeners.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    public static ArrayList<BroadcastAPlayerQuitEventListener> getBroadcastAPlayerQuitListeners() {
        return broadcastAPlayerQuitListeners;
    }

    /**
     * @param listener A BroadcastAPlayerQuitEventListener object.
     *
     * @author Victor Hargrave
     * @since 1.0.0
     */
    public static void removeAPlayerQuitEventListener(final BroadcastAPlayerQuitEventListener listener) {
        broadcastAPlayerQuitListeners.remove(listener);
    }

}
