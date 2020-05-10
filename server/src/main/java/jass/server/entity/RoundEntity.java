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

package jass.server.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import jass.lib.Card;
import jass.lib.GameMode;
import jass.lib.database.Entity;

/**
 * A model with all known (and cached) teams.
 *
 * @author Victor Hargrave, Manuele Vaccari
 * @version %I%, %G%
 * @since 0.0.1
 */
@DatabaseTable(tableName = "round")
public final class RoundEntity extends Entity {
    /**
     * The ID.
     */
    @DatabaseField(generatedId = true)
    private int id;

    /**
     * The user which chooses the game mode.
     */
    @DatabaseField(foreign = true)
    private UserEntity gameModeChooser;

    /**
     * The chosen game mode.
     */
    @DatabaseField(defaultValue = "null")
    private GameMode gameMode = null;

    /**
     * In case of game mode trumpf, the trumpf card.
     */
    @DatabaseField(defaultValue = "null")
    private Card.Suit trumpfSuit = null;

    /**
     * The game that this round belongs to.
     */
    @DatabaseField(foreign = true)
    private GameEntity game;

    /**
     * A cached total of the points for team one.
     */
    @DatabaseField(defaultValue = "0", canBeNull = false)
    private int pointsTeamOne = 0;

    /**
     * A cached total of the points for team two.
     */
    @DatabaseField(defaultValue = "0", canBeNull = false)
    private int pointsTeamTwo = 0;

    /**
     * For ORMLite all persisted classes must define a no-arg constructor with
     * at least package visibility.
     */
    public RoundEntity() {
    }

    /**
     * @return Returns the ID.
     */
    public int getId() {
        return id;
    }

    /**
     * @return Returns the game mode chooser.
     */
    public UserEntity getGameModeChooser() {
        return gameModeChooser;
    }

    /**
     * @param gameModeChooser The game mode chooser.
     *
     * @return Returns the object for further processing.
     */
    public RoundEntity setGameModeChooser(final UserEntity gameModeChooser) {
        this.gameModeChooser = gameModeChooser;
        return this;
    }

    /**
     * @return Returns the game mode.
     */
    public GameMode getGameMode() {
        return gameMode;
    }

    /**
     * @param gameMode The game mode.
     *
     * @return Returns the object for further processing.
     */
    public RoundEntity setGameMode(final GameMode gameMode) {
        this.gameMode = gameMode;
        return this;
    }

    /**
     * @return Returns the suit of the trumpf.
     */
    public Card.Suit getTrumpfSuit() {
        return trumpfSuit;
    }

    /**
     * @param trumpfSuit The trumpf suit.
     *
     * @return Returns the object for further processing.
     */
    public RoundEntity setTrumpfSuit(final Card.Suit trumpfSuit) {
        this.trumpfSuit = trumpfSuit;
        return this;
    }

    /**
     * @return Returns the game.
     */
    public GameEntity getGame() {
        return game;
    }

    /**
     * @param game The game.
     *
     * @return Returns the object for further processing.
     */
    public RoundEntity setGame(final GameEntity game) {
        this.game = game;
        return this;
    }

    /**
     * @return Returns the total points for team one.
     */
    public int getPointsTeamOne() {
        return pointsTeamOne;
    }

    /**
     * @param pointsTeamOne The total points for team one.
     *
     * @return Returns the object for further processing.
     */
    public RoundEntity setPointsTeamOne(final int pointsTeamOne) {
        this.pointsTeamOne = pointsTeamOne;
        return this;
    }

    /**
     * @param points The points to add for team one.
     *
     * @return Returns the object for further processing.
     */
    public RoundEntity addPointsTeamOne(final int points) {
        pointsTeamOne += points;
        return this;
    }

    /**
     * @return Returns the total points for team two.
     */
    public int getPointsTeamTwo() {
        return pointsTeamTwo;
    }

    /**
     * @param pointsTeamTwo The total points for team two.
     *
     * @return Returns the object for further processing.
     */
    public RoundEntity setPointsTeamTwo(final int pointsTeamTwo) {
        this.pointsTeamTwo = pointsTeamTwo;
        return this;
    }

    /**
     * @param points The points to add for team one.
     *
     * @return Returns the object for further processing.
     */
    public RoundEntity addPointsTeamTwo(final int points) {
        pointsTeamTwo += points;
        return this;
    }
}
