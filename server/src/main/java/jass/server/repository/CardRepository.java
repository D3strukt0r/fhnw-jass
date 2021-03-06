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

package jass.server.repository;

import com.j256.ormlite.dao.Dao;
import jass.lib.database.Repository;
import jass.server.entity.CardEntity;
import jass.server.entity.RankEntity;
import jass.server.entity.SuitEntity;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * A model with all known cards.
 *
 * @author Victor Hargrave
 * @version %I%, %G%
 * @since 1.0.0
 */
public final class CardRepository extends Repository<Dao<CardEntity, Integer>, CardEntity> {
    /**
     * The singleton.
     */
    private static CardRepository singleton = null;

    /**
     * Creates a new singleton or returns the existing one.
     *
     * @param dao The DAO to edit inside the database.
     *
     * @return Returns the Repository.
     *
     * @author Victor Hargrave
     * @since 1.0.0
     */
    public static CardRepository getSingleton(final Dao<CardEntity, Integer> dao) {
        if (singleton == null) {
            singleton = new CardRepository(dao);
        }
        return singleton;
    }

    /**
     * @param dao The DAO to edit inside the database.
     *
     * @author Victor Hargrave
     * @since 1.0.0
     */
    public CardRepository(final Dao<CardEntity, Integer> dao) {
        super(dao);
    }

    /**
     * @return Returns true if successful otherwise false.
     *
     * @author Victor Hargrave
     * @since 1.0.0
     */
    public boolean insertSeedData() {
        try {
            int addend = 0;
            for (int i = 1; i <= 4; i++) {
                for (int j = 1; j <= 9; j++) {
                    if (!getDao().idExists(j + addend)) {
                        getDao().create((new CardEntity())
                            .setId(j + addend)
                            .setRank(RankRepository.getSingleton(null).getDao().queryForId(j))
                            .setSuit(SuitRepository.getSingleton(null).getDao().queryForId(i))
                        );
                    }
                }
                addend += 9;
            }
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * @param cards The array of cards to check.
     * @param suit  The suit to find.
     *
     * @return Returns true if there is any card with the same suit, otherwise
     * false.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    public static boolean isAnyCardOfSuit(final ArrayList<CardEntity> cards, final SuitEntity suit) {
        for (CardEntity card : cards) {
            if (card.getSuit().equals(suit)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param cards The array of cards to check.
     * @param suit  The suit to find.
     * @param rank  The rank to find.
     *
     * @return Returns true if there is any card with the same suit and rank,
     * otherwise false.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    public static boolean isAnyCardOfSuitAndRank(final ArrayList<CardEntity> cards, final SuitEntity suit, final RankEntity rank) {
        for (CardEntity card : cards) {
            if (card.getRank().equals(rank) && card.getSuit().equals(suit)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param cards The array of cards to check.
     * @param suit  The suit to find.
     * @param rank  The rank to find.
     *
     * @return Returns the card which has the same suit and rank.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    public static CardEntity getCardOfSuitAndRank(final ArrayList<CardEntity> cards, final SuitEntity suit, final RankEntity rank) {
        for (CardEntity card : cards) {
            if (card.getRank().equals(rank) && card.getSuit().equals(suit)) {
                return card;
            }
        }
        return null;
    }
}
