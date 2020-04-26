/*
 * fhnw-jass is jass game programmed in java for a school project.
 * Copyright (C) 2020 Manuele Vaccari, Victor Hargrave, Sasa Trajkova, Thomas
 * Weber
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

import jass.lib.message.BroadcastDeckData;
import jass.lib.message.CardData;
import jass.lib.message.GameFoundData;
import jass.server.entity.*;
import jass.server.message.BroadcastDeck;
import jass.server.message.GameFound;
import jass.server.repository.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import jass.lib.servicelocator.Service;

/**
 * Creates a game once 4 clients are searching for a game in the lobby.
 *
 * @author Thomas Weber
 * @version %I%, %G%
 * @since 0.0.1
 */
public final class CardUtil implements Service {
    /**
     * The service name.
     */
    public static final String SERVICE_NAME = "CardUtil";

    /**
     * The logger to print to console and save in a .log file.
     */
    private static final Logger logger = LogManager.getLogger(SearchGameUtil.class);

    /**
     * Empty constructor.
     */
    public CardUtil() {
    }

    /**
     * Add new client to search for game - if the client already is in the
     * searching list remove the client.
     *
     * @param client The client to add.
     */
    public void addClientToSearchGame(final ClientUtil client) {
        /*if (!clients.contains(client)) {
            clients.add(client);
            logger.info("Added new client " + client.getUsername() + " to waiting list. Total of " + clients.size() + " users searching for a game");
            this.createNewGame();
        }*/
    }

    @Override
    public String getServiceName() {
        return SERVICE_NAME;
    }

    public List<DeckEntity> addDecksForPlayers(RoundEntity newRound, UserEntity playerOne, UserEntity playerTwo, UserEntity playerThree, UserEntity playerFour) {
        List<CardEntity> cards = CardRepository.getSingleton(null).getAll();
        Collections.shuffle(cards);

        List<DeckEntity> decks = new ArrayList<DeckEntity>();
        decks.add(addNewDeck(newRound, playerOne, cards, 0));
        decks.add(addNewDeck(newRound, playerTwo, cards, 9));
        decks.add(addNewDeck(newRound, playerThree, cards, 18));
        decks.add(addNewDeck(newRound, playerFour, cards, 27));
        return decks;
    }

    private DeckEntity addNewDeck(RoundEntity newRound, UserEntity player, List<CardEntity> cards, int i) {
        DeckEntity deck = new DeckEntity(player, newRound, cards.get(i),
            cards.get(i + 1), cards.get(i + 2), cards.get(i + 3), cards.get(i + 4), cards.get(i + 5),
            cards.get(i + 6), cards.get(i + 7), cards.get(i + 8));
        DeckRepository.getSingleton(null).add(deck);
        return deck;
    }

    public void broadcastDeck(ClientUtil client, DeckEntity deckEntity) {
        List<CardData> cards = deckEntity.getCards().stream().map(CardEntity::toCardData).collect(Collectors.toList());
        BroadcastDeck broadcastDeckMsg = new BroadcastDeck(new BroadcastDeckData(deckEntity.getId(),
            cards.get(0), cards.get(1), cards.get(2), cards.get(3), cards.get(4), cards.get(5),
            cards.get(6), cards.get(7), cards.get(8), cards));

        client.send(broadcastDeckMsg);
    }
}
