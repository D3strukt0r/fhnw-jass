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

package jass.lib.message;

/**
 * The data model for the Card Data.
 *
 * @author Victor Hargrave
 */
public final class CardData {
    /**
     * The card ID.
     */
    private final int cardId;

    /**
     * The suit ID.
     */
    private final int suitId;

    /**
     * The suit name.
     */
    private final String suit;

    /**
     * The rank ID.
     */
    private final int rankId;

    /**
     * The rank name.
     */
    private final String rank;

    public boolean isPlayed;

    /**
     * @param cardId The card ID.
     * @param suitId The suit ID.
     * @param suit   The suit name.
     * @param rankId The rank ID.
     * @param rank   The rank name.
     */
    public CardData(final int cardId, final int suitId, final String suit, final int rankId, final String rank) {
        this.cardId = cardId;
        this.suitId = suitId;
        this.suit = suit;
        this.rankId = rankId;
        this.rank = rank;
    }

    /**
     * @return Returns the suit.
     */
    public String getSuit() {
        return suit;
    }

    /**
     * @return Returns the rank.
     */
    public String getRank() {
        return rank;
    }

    /**
     * @return Returns the card ID.
     */
    public int getCardId() {
        return cardId;
    }

    /**
     * @return Returns the suit ID.
     */
    public int getSuitId() {
        return suitId;
    }

    /**
     * @return Returns the rank ID.
     */
    public int getRankId() {
        return rankId;
    }

    public boolean getIsPlayed() {
        return isPlayed;
    }

    public void setIsPlayed(boolean isPlayed) {
        this.isPlayed = isPlayed;
    }
}
