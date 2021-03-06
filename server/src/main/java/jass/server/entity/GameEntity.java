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

/**
 * A model with all known (and cached) Games.
 *
 * @author Thomas Weber
 * @version %I%, %G%
 * @since 1.0.0
 */
@DatabaseTable(tableName = "game")
public final class GameEntity extends Entity {
    /**
     * The ID.
     */
    @DatabaseField(generatedId = true)
    private int id;

    /**
     * Team one.
     */
    @DatabaseField(foreign = true)
    private TeamEntity teamOne;

    /**
     * Team two.
     */
    @DatabaseField(foreign = true)
    private TeamEntity teamTwo;

    /**
     * Whether the game is still active.
     */
    @DatabaseField(defaultValue = "true", canBeNull = false)
    private boolean isActive = true;

    /**
     * For ORMLite all persisted classes must define a no-arg constructor with
     * at least package visibility.
     *
     * @author Thomas Weber
     * @since 1.0.0
     */
    public GameEntity() {
    }

    /**
     * @return Returns the ID.
     *
     * @author Thomas Weber
     * @since 1.0.0
     */
    public int getId() {
        return id;
    }

    /**
     * @return Returns team one.
     *
     * @author Thomas Weber
     * @since 1.0.0
     */
    public TeamEntity getTeamOne() {
        return teamOne;
    }

    /**
     * @param teamOne Team one.
     *
     * @return Returns the object for further processing.
     *
     * @author Thomas Weber
     * @since 1.0.0
     */
    public GameEntity setTeamOne(final TeamEntity teamOne) {
        this.teamOne = teamOne;
        return this;
    }

    /**
     * @return Returns team two.
     *
     * @author Thomas Weber
     * @since 1.0.0
     */
    public TeamEntity getTeamTwo() {
        return teamTwo;
    }

    /**
     * @param teamTwo Team two.
     *
     * @return Returns the object for further processing.
     *
     * @author Thomas Weber
     * @since 1.0.0
     */
    public GameEntity setTeamTwo(final TeamEntity teamTwo) {
        this.teamTwo = teamTwo;
        return this;
    }

    /**
     * @return Returns whether the game is currently running.
     *
     * @author Thomas Weber
     * @since 1.0.0
     */
    public boolean isActive() {
        return isActive;
    }

    /**
     * @param active Whether the game is currently running.
     *
     * @return Returns the object for further processing.
     *
     * @author Thomas Weber
     * @since 1.0.0
     */
    public GameEntity setActive(final boolean active) {
        isActive = active;
        return this;
    }
}
