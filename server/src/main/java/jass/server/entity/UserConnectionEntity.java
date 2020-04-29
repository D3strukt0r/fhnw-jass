package jass.server.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import jass.lib.database.Entity;

/**
 * @author Manuele Vaccari
 * @version %I%, %G%
 * @since 0.0.1
 */
@DatabaseTable(tableName = "user_connections")
public class UserConnectionEntity extends Entity {
    /**
     * The ID.
     */
    @DatabaseField(generatedId = true)
    private int id;

    /**
     * User one.
     */
    @DatabaseField(foreign = true, canBeNull = false)
    private UserEntity userOne;

    /**
     * User two.
     */
    @DatabaseField(foreign = true, canBeNull = false)
    private UserEntity userTwo;

    /**
     * Is user two a friend of user one.
     */
    @DatabaseField(defaultValue = "false", canBeNull = false)
    private boolean isFriend = false;

    /**
     * Has user one blocked user two.
     */
    @DatabaseField(defaultValue = "false", canBeNull = false)
    private boolean isBlocked = false;

    /**
     * For ORMLite all persisted classes must define a no-arg constructor with
     * at least package visibility.
     */
    public UserConnectionEntity() {
    }

    /**
     * @return Returns the ID.
     */
    public int getId() {
        return id;
    }

    /**
     * @return Returns user one.
     */
    public UserEntity getUserOne() {
        return userOne;
    }

    /**
     * @param userOne User one.
     *
     * @return Returns the object for further processing.
     */
    public UserConnectionEntity setUserOne(final UserEntity userOne) {
        this.userOne = userOne;
        return this;
    }

    /**
     * @return Returns user two.
     */
    public UserEntity getUserTwo() {
        return userTwo;
    }

    /**
     * @param userTwo User two.
     *
     * @return Returns the object for further processing.
     */
    public UserConnectionEntity setUserTwo(final UserEntity userTwo) {
        this.userTwo = userTwo;
        return this;
    }

    /**
     * @return Returns whether user two is a friend of user one.
     */
    public boolean isFriend() {
        return isFriend;
    }

    /**
     * @param friend Whether user two is a friend of user one.
     *
     * @return Returns the object for further processing.
     */
    public UserConnectionEntity setFriend(final boolean friend) {
        isFriend = friend;
        return this;
    }

    /**
     * @return Returns whether user one has blocked user two.
     */
    public boolean isBlocked() {
        return isBlocked;
    }

    /**
     * @param blocked Whether user one has blocked user two.
     *
     * @return Returns the object for further processing.
     */
    public UserConnectionEntity setBlocked(final boolean blocked) {
        isBlocked = blocked;
        return this;
    }
}
