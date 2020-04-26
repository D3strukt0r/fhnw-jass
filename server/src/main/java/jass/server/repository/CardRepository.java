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

package jass.server.repository;

import com.j256.ormlite.dao.Dao;
import jass.lib.database.Repository;
import jass.server.entity.CardEntity;

import java.sql.SQLException;
import java.util.List;

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
     */
    public static CardRepository getSingleton(final Dao<CardEntity, Integer> dao) {
        if (singleton == null) {
            singleton = new CardRepository(dao);
        }
        return singleton;
    }

    /**
     * @param dao The DAO to edit inside the database.
     */
    public CardRepository(final Dao<CardEntity, Integer> dao) {
        super(dao);
    }

    public CardEntity getById(int id) {
        try {
            CardEntity card = getDao().queryForId(id);
            return card;
        } catch (SQLException e) {
            return null;
        }
    }

    public List<CardEntity> getAll() {
        try {
            List<CardEntity> cards = getDao().queryForAll();
            SuitRepository suitRepo = SuitRepository.getSingleton(null);
            RankRepository rankRepo = RankRepository.getSingleton(null);
            cards.forEach(card -> {
                card.setSuit(suitRepo.getById(card.getSuit().getId()));
                card.setRank(rankRepo.getById(card.getSuit().getId()));
            });
            return cards;
        } catch (SQLException e) {
            return null;
        }
    }
}
