package org.orbitrondev.jass.client.Entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import org.orbitrondev.jass.lib.ServiceLocator.Service;

/**
 * A subclass of all login information.
 *
 * @author Manuele Vaccari
 * @version %I%, %G%
 * @since 0.0.1
 */
@DatabaseTable(tableName = "login")
public class LoginEntity implements Service {

    /**
     * FIELDS ////////////////////////////////
     */

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField
    private String username;

    @DatabaseField
    private String password;

    @DatabaseField
    private String token;

    /**
     * CONSTRUCTORS //////////////////////////
     */

    LoginEntity() {
        // For ORMLite
        // all persisted classes must define a no-arg constructor
        // with at least package visibility
    }

    public LoginEntity(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public LoginEntity(String username, String password, String token) {
        this.username = username;
        this.password = password;
        this.token = token;
    }

    /**
     * METHODS ///////////////////////////////
     */

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String getName() {
        return "login";
    }
}
