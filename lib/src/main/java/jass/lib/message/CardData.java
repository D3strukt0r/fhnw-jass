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
 * @version %I%, %G%
 * @since 1.0.0
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

    /**
     * Whether the card was already played.
     */
    private boolean isPlayed;

    /**
     * @param cardId The card ID.
     * @param suitId The suit ID.
     * @param suit   The suit name.
     * @param rankId The rank ID.
     * @param rank   The rank name.
     *
     * @author Victor Hargrave
     * @since 1.0.0
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
     *
     * @author Victor Hargrave
     * @since 1.0.0
     */
    public String getSuit() {
        return suit;
    }

    /**
     * @return Returns the rank.
     *
     * @author Victor Hargrave
     * @since 1.0.0
     */
    public String getRank() {
        return rank;
    }

    /**
     * @return Returns the card ID.
     *
     * @author Victor Hargrave
     * @since 1.0.0
     */
    public int getCardId() {
        return cardId;
    }

    /**
     * @return Returns the suit ID.
     *
     * @author Victor Hargrave
     * @since 1.0.0
     */
    public int getSuitId() {
        return suitId;
    }

    /**
     * @return Returns the rank ID.
     *
     * @author Victor Hargrave
     * @since 1.0.0
     */
    public int getRankId() {
        return rankId;
    }

    /**
     * @return Returns whether the card has been played.
     *
     * @author Victor Hargrave
     * @since 1.0.0
     */
    public boolean isPlayed() {
        return isPlayed;
    }

    /**
     * @param played Whether the card has been played.
     *
     * @author Victor Hargrave
     * @since 1.0.0
     */
    public CardData setPlayed(final boolean played) {
        isPlayed = played;
        return this;
    }
}
