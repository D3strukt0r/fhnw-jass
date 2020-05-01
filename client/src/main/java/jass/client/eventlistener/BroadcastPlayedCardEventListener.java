package jass.client.eventlistener;

import jass.lib.message.BroadcastPlayedCardData;

/**
 * @author Manuele Vaccari
 * @version %I%, %G%
 * @since 0.0.1
 */
public interface BroadcastPlayedCardEventListener {
    /**
     * Executes when a user played a card.
     *
     * @param data The data (basically the ID).
     */
    void onBroadcastPlayedCard(BroadcastPlayedCardData data);
}
