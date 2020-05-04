package jass.server.eventlistener;

import jass.lib.message.PlayCardData;
import jass.lib.message.PlayedCardData;
import jass.server.util.ClientUtil;

/**
 * @author Manuele Vaccari & Victor Hargrave
 * @version %I%, %G%
 * @since 0.0.1
 */
public interface PlayedCardEventListener {
    /**
     * Executes when a user played a card.
     *
     * @param data The data (basically the ID).
     */
    void onPlayedCard(PlayCardData data) throws InterruptedException;
}
