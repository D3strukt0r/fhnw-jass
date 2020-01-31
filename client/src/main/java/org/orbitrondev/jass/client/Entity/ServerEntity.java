package org.orbitrondev.jass.client.Entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import org.orbitrondev.jass.lib.ServiceLocator.Service;

/**
 * A model containing all server connections.
 *
 * @author Manuele Vaccari
 * @version %I%, %G%
 * @since 0.0.1
 */
@DatabaseTable(tableName = "server")
public class ServerEntity implements Service {

    /**
     * FIELDS ////////////////////////////////
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
    private boolean defaultServer = false;

    /**
     * CONSTRUCTORS //////////////////////////
     */

    ServerEntity() {
        // For ORMLite
        // all persisted classes must define a no-arg constructor
        // with at least package visibility
    }

    public ServerEntity(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public ServerEntity(String ip, int port, boolean secure) {
        this.ip = ip;
        this.port = port;
        this.secure = secure;
    }

    public ServerEntity(String ip, int port, boolean secure, boolean defaultServer) {
        this.ip = ip;
        this.port = port;
        this.secure = secure;
        this.defaultServer = defaultServer;
    }

    /**
     * METHODS ///////////////////////////////
     */

    public int getId() {
        return id;
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    public boolean isSecure() {
        return secure;
    }

    public boolean isDefaultServer() {
        return defaultServer;
    }

    public void setDefaultServer(boolean defaultServer) {
        this.defaultServer = defaultServer;
    }

    @Override
    public String getName() {
        return "server";
    }
}
