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

import jass.lib.Card;
import jass.lib.message.BroadcastDeckData;
import jass.server.entity.DeckEntity;
import jass.server.entity.RoundEntity;
import jass.server.entity.UserEntity;
import jass.server.message.BroadcastDeck;
import jass.server.repository.DeckRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jass.lib.servicelocator.Service;

/**
 * @author Victor Hargrave
 * @version %I%, %G%
 * @since 0.0.1
 */
public final class CardUtil implements Service {
    /**
     * The logger to print to console and save in a .log file.
     */
    private static final Logger logger = LogManager.getLogger(CardUtil.class);

    /**
     * Empty constructor.
     */
    public CardUtil() {
    }

    /**
     * @param newRound    The new round.
     * @param playerOne   User one.
     * @param playerTwo   User two.
     * @param playerThree User three.
     * @param playerFour  User four.
     *
     * @return Returns the four decks created for each player.
     */
    public List<DeckEntity> addDecksForPlayers(final RoundEntity newRound, final UserEntity playerOne, final UserEntity playerTwo, final UserEntity playerThree, final UserEntity playerFour) {
        List<Card> cards = Card.generateJassDeck();

        Collections.shuffle(cards);
        logger.info("shuffled cards");

        List<DeckEntity> decks = new ArrayList<>();
        decks.add(addNewDeck(newRound, playerOne, cards, 0));
        decks.add(addNewDeck(newRound, playerTwo, cards, 9));
        decks.add(addNewDeck(newRound, playerThree, cards, 18));
        decks.add(addNewDeck(newRound, playerFour, cards, 27));
        return decks;
    }

    /**
     * @param newRound The new round.
     * @param player   The player.
     * @param cards    The cards.
     * @param i        The ID inside the iterator.
     *
     * @return Returns a deck for a specific player.
     */
    private DeckEntity addNewDeck(final RoundEntity newRound, final UserEntity player, final List<Card> cards, final int i) {
        DeckEntity deck = (new DeckEntity())
            .setPlayer(player)
            .setRound(newRound)
            .setCardOne(cards.get(i))
            .setCardTwo(cards.get(i + 1))
            .setCardThree(cards.get(i + 2))
            .setCardFour(cards.get(i + 3))
            .setCardFive(cards.get(i + 4))
            .setCardSix(cards.get(i + 5))
            .setCardSeven(cards.get(i + 6))
            .setCardEight(cards.get(i + 7))
            .setCardNine(cards.get(i + 8));
        DeckRepository.getSingleton(null).add(deck);
        logger.info("added deck for player");
        return deck;
    }

    /**
     * @param client     The client to send the deck to.
     * @param deckEntity The deck to send to the client.
     */
    public void broadcastDeck(final ClientUtil client, final DeckEntity deckEntity) {
        BroadcastDeck broadcastDeckMsg = new BroadcastDeck(new BroadcastDeckData(deckEntity.getId(), deckEntity.getCards()));
        client.send(broadcastDeckMsg);
    }
}
