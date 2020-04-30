package jass.server.persister;

import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.field.types.StringType;
import jass.lib.Card;

/**
 * @author Manuele Vaccari
 * @version %I%, %G%
 * @since 0.0.1
 */
public final class CardPersister extends StringType {
    /**
     * The singleton.
     */
    private static final CardPersister singleton = new CardPersister();

    /**
     * @return Returns the existing singleton.
     */
    public static CardPersister getSingleton() {
        return singleton;
    }

    private CardPersister() {
        super(SqlType.STRING, new Class<?>[]{Card.class});
    }

    @Override
    public Object javaToSqlArg(final FieldType fieldType, final Object javaObject) {
        Card card = (Card) javaObject;
        return card != null ? card.toString() : null;
    }

    @Override
    public Object sqlArgToJava(final FieldType fieldType, final Object sqlArg, final int columnPos) {
        return sqlArg != null ? Card.fromString((String) sqlArg) : null;
    }
}
