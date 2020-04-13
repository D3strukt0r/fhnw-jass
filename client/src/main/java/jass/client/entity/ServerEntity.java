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
 * @since 0.0.1
 */
@DatabaseTable(tableName = "server")
public class ServerEntity implements Service, Entity {
    /**
     * Fields (Columns)
     */
    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField
    private String ip;

    @DatabaseField
    private int port;

    @DatabaseField(defaultValue = "false")
    private boolean secure = false;

    @DatabaseField(defaultValue = "false")
    private boolean connectAutomatically = false;

    /**
     * Constructors
     */
    ServerEntity() {
        // For ORMLite
        // all persisted classes must define a no-arg constructor
        // with at least package visibility
    }

    public ServerEntity(final String ip, final int port) {
        this.ip = ip;
        this.port = port;
    }

    public ServerEntity(final String ip, final int port, final boolean secure) {
        this.ip = ip;
        this.port = port;
        this.secure = secure;
    }

    public ServerEntity(final String ip, final int port, final boolean secure, final boolean connectAutomatically) {
        this.ip = ip;
        this.port = port;
        this.secure = secure;
        this.connectAutomatically = connectAutomatically;
    }

    /**
     * Methods
     */
    @Override
    public String getServiceName() {
        return "server";
    }

    public int getId() {
        return id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(final String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(final int port) {
        this.port = port;
    }

    public boolean isSecure() {
        return secure;
    }

    public void setSecure(final boolean secure) {
        this.secure = secure;
    }

    public boolean isConnectAutomatically() {
        return connectAutomatically;
    }

    public void setConnectAutomatically(final boolean connectAutomatically) {
        this.connectAutomatically = connectAutomatically;
    }
}
