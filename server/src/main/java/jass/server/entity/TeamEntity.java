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
import jass.lib.database.Entity;
import jass.server.repository.TeamRepository;
import jass.server.repository.UserRepository;

import java.sql.SQLException;

/**
 * A model with all known (and cached) teams.
 *
 * @author Thomas Weber
 * @version %I%, %G%
 * @since 0.0.1
 */
@DatabaseTable(tableName = "team")
public final class TeamEntity extends Entity {
    /**
     * The ID.
     */
    @DatabaseField(generatedId = true)
    private int id;

    /**
     * Player one inside the team.
     */
    @DatabaseField(foreign = true)
    private UserEntity playerOne;

    /**
     * Whether the variable has been loaded from the database.
     */
    private boolean playerOneLoaded = false;

    /**
     * Player two inside the team.
     */
    @DatabaseField(foreign = true)
    private UserEntity playerTwo;

    /**
     * Whether the variable has been loaded from the database.
     */
    private boolean playerTwoLoaded = false;

    /**
     * For ORMLite all persisted classes must define a no-arg constructor with
     * at least package visibility.
     */
    public TeamEntity() {
    }

    /**
     * @return Returns the ID.
     */
    public int getId() {
        return id;
    }

    /**
     * @return Returns player one.
     */
    public UserEntity getPlayerOne() {
        if (!playerOneLoaded) {
            try {
                UserRepository.getSingleton(null).getDao().refresh(playerOne);
                playerOneLoaded = true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return playerOne;
    }

    /**
     * @param playerOne Player one.
     *
     * @return Returns the object for further processing.
     */
    public TeamEntity setPlayerOne(final UserEntity playerOne) {
        this.playerOne = playerOne;
        return this;
    }

    /**
     * @return Returns player two.
     */
    public UserEntity getPlayerTwo() {
        if (!playerTwoLoaded) {
            try {
                UserRepository.getSingleton(null).getDao().refresh(playerTwo);
                playerTwoLoaded = true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return playerTwo;
    }

    /**
     * @param playerTwo Player two.
     *
     * @return Returns the object for further processing.
     */
    public TeamEntity setPlayerTwo(final UserEntity playerTwo) {
        this.playerTwo = playerTwo;
        return this;
    }

    /**
     * @param user The user to check.
     *
     * @return Returns true if the user is inside the team, otherwise false.
     */
    public boolean checkIfPlayerIsInTeam(final UserEntity user) {
        return playerOne.equals(user) || playerTwo.equals(user);
    }
}
