package jass.lib;

public final class Card {
    public enum Suit {
        /**
         * All the suits available for the card.
         */
        Clubs, Diamonds, Hearts, Spades;

        @Override
        public String toString() {
            switch (this) {
                case Clubs:
                    return "clubs";
                case Diamonds:
                    return "diamonds";
                case Hearts:
                    return "hearts";
                case Spades:
                    return "spades";
                default:
                    throw new IllegalStateException("Unexpected value: " + this);
            }
        }

        /**
         * @param string The game mode as string.
         *
         * @return Returns the corresponding game mode object.
         */
        public static Suit fromString(final String string) {
            switch (string) {
                case "clubs":
                    return Clubs;
                case "diamonds":
                    return Diamonds;
                case "hearts":
                    return Hearts;
                case "spades":
                    return Spades;
                default:
                    throw new IllegalStateException("Unexpected value: " + string);
            }
        }
    }

    public enum Rank {
        /**
         * All the ranks available for the card.
         */
        Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten, Jack, Queen, King, Ace;

        @Override
        public String toString() {
            String str = "ace";  // Assume we have an ace, then cover all other cases
            // Get ordinal value, which ranges from 0 to 12
            int ordinal = this.ordinal();
            if (ordinal <= 8) {
                str = Integer.toString(ordinal + 2);
            } else if (ordinal == 9) { // Jack
                str = "jack";
            } else if (ordinal == 10) { // Queen
                str = "queen";
            } else if (ordinal == 11) { // King
                str = "king";
            }
            return str;
        }

        /**
         * @param previous Card to compare against.
         *
         * @return Returns true if current object comes after, false otherwise.
         */
        public boolean isNext(final Card previous) {
            int previousOrdinal = previous.rank.ordinal();
            int nextOrdinal = this.ordinal();

            return (previousOrdinal + 1) == nextOrdinal;
        }
    }

    /**
     * The suit.
     */
    private final Suit suit;

    /**
     * The rank.
     */
    private final Rank rank;

    /**
     * @param suit The suit.
     * @param rank The rank.
     */
    public Card(final Suit suit, final Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }

    /**
     * @return Returns the suit.
     */
    public Suit getSuit() {
        return suit;
    }

    /**
     * @return Returns the rank.
     */
    public Rank getRank() {
        return rank;
    }

    @Override
    public String toString() {
        return rank.toString() + suit.toString();
    }
}
