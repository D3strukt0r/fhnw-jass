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
 * @author Victor Hargrave & Manuele Vaccari
 * @version %I%, %G%
 * @since 1.0.0
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
     *
     * @author Victor Hargrave
     * @since 1.0.0
     */
    public SuitEntity() {
    }

    /**
     * @return Returns the ID.
     *
     * @author Victor Hargrave
     * @since 1.0.0
     */
    public int getId() {
        return id;
    }

    /**
     * @param id The ID.
     *
     * @return Returns the object for further processing.
     *
     * @author Victor Hargrave
     * @since 1.0.0
     */
    public SuitEntity setId(final int id) {
        this.id = id;
        return this;
    }

    /**
     * @return Returns the suit.
     *
     * @author Victor Hargrave
     * @since 1.0.0
     */
    public String getKey() {
        return key;
    }

    /**
     * @param key The Key.
     *
     * @return Returns the object for further processing.
     *
     * @author Victor Hargrave
     * @since 1.0.0
     */
    public SuitEntity setKey(final String key) {
        this.key = key;
        return this;
    }

    /**
     * @param o The suit object
     *
     * @return Returns whether it's the same object or not.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SuitEntity that = (SuitEntity) o;

        return id == that.id;
    }

    /**
     * @return Returns the hashcode.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    @Override
    public int hashCode() {
        return id;
    }
}
