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

package jass.client.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import jass.lib.database.Entity;
import jass.lib.servicelocator.Service;

/**
 * A model containing all server connections.
 *
 * @author Manuele Vaccari
 * @version %I%, %G%
 * @since 1.0.0
 */
@DatabaseTable(tableName = "server")
public final class ServerEntity extends Entity implements Service {
    /**
     * The ID.
     */
    @DatabaseField(generatedId = true)
    private int id;

    /**
     * The IP address of the server.
     */
    @DatabaseField
    private String ip;

    /**
     * The port of the server.
     */
    @DatabaseField
    private int port;

    /**
     * Whether to use SSL or not.
     */
    @DatabaseField(defaultValue = "false")
    private boolean secure = false;

    /**
     * The field name for connectAutomatically.
     */
    public static final String CONNECT_AUTOMATICALLY_FIELD_NAME = "connect_automatically";

    /**
     * Whether to connect automatically at startup or not.
     */
    @DatabaseField(columnName = CONNECT_AUTOMATICALLY_FIELD_NAME, defaultValue = "false")
    private boolean connectAutomatically = false;

    /**
     * For ORMLite all persisted classes must define a no-arg constructor with
     * at least package visibility.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    public ServerEntity() {
    }

    /**
     * @return Returns the ID.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    public int getId() {
        return id;
    }

    /**
     * @param id The ID
     *
     * @return Returns the object for further processing.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    public ServerEntity setId(final int id) {
        this.id = id;
        return this;
    }

    /**
     * @return Returns the IP address.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    public String getIp() {
        return ip;
    }

    /**
     * @param ip The IP address.
     *
     * @return Returns the object for further processing.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    public ServerEntity setIp(final String ip) {
        this.ip = ip;
        return this;
    }

    /**
     * @return Returns the port.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    public int getPort() {
        return port;
    }

    /**
     * @param port The port.
     *
     * @return Returns the object for further processing.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    public ServerEntity setPort(final int port) {
        this.port = port;
        return this;
    }

    /**
     * @return Returns whether to use SSL or not.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    public boolean isSecure() {
        return secure;
    }

    /**
     * @param secure Whether to use SSL or not.
     *
     * @return Returns the object for further processing.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    public ServerEntity setSecure(final boolean secure) {
        this.secure = secure;
        return this;
    }

    /**
     * @return Returns whether to connect automatically.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    public boolean isConnectAutomatically() {
        return connectAutomatically;
    }

    /**
     * @param connectAutomatically Whether to connect automatically.
     *
     * @return Returns the object for further processing.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    public ServerEntity setConnectAutomatically(final boolean connectAutomatically) {
        this.connectAutomatically = connectAutomatically;
        return this;
    }
}
