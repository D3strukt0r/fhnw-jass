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
 * @author Victor Hargrave
 * @version %I%, %G%
 * @since 0.0.1
 */
@DatabaseTable(tableName = "suit")
public final class SuitEntity extends Entity {
    /**
     * The ID.
     */
    @DatabaseField(id = true)
    private int id;

    /**
     * The suit.
     */
    @DatabaseField
    private String key;

    /**
     * For ORMLite all persisted classes must define a no-arg constructor with
     * at least package visibility.
     */
    public SuitEntity() {
    }

    /**
     * @return Returns the ID.
     */
    public int getId() {
        return id;
    }

    /**
     * @param id The ID.
     *
     * @return Returns the object for further processing.
     */
    public SuitEntity setId(final int id) {
        this.id = id;
        return this;
    }

    /**
     * @return Returns the suit.
     */
    public String getKey() {
        return key;
    }

    /**
     * @param key The Key.
     *
     * @return Returns the object for further processing.
     */
    public SuitEntity setKey(final String key) {
        this.key = key;
        return this;
    }
}
