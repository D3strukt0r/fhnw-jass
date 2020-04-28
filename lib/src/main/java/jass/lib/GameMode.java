package jass.lib;

public enum GameMode {
    /**
     * Trumpf game mode.
     */
    TRUMPF,

    /**
     * Obe abe game mode.
     */
    OBE_ABE,

    /**
     * Onde ufe game mode.
     */
    ONDE_UFE;

    @Override
    public String toString() {
        switch (this) {
            case TRUMPF:
                return "Trumpf";
            case OBE_ABE:
                return "Obe Abe";
            case ONDE_UFE:
                return "Onde Ufe";
            default:
                throw new IllegalStateException("Unexpected value: " + this);
        }
    }

    /**
     * @param string The game mode as string.
     *
     * @return Returns the corresponding game mode object.
     */
    public static GameMode fromString(final String string) {
        switch (string) {
            case "Trumpf":
                return TRUMPF;
            case "Obe Abe":
                return OBE_ABE;
            case "Onde Ufe":
                return ONDE_UFE;
            default:
                throw new IllegalStateException("Unexpected value: " + string);
        }
    }
}
