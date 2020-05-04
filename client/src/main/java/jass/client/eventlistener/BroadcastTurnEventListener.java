package jass.client.eventlistener;

import jass.lib.message.BroadcastTurnData;

/**
 * @author Manuele Vaccari
 * @version %I%, %G%
 * @since 0.0.1
 */
public interface BroadcastTurnEventListener {
    /**
     * Executes when a user played a card.
     *
     * @param data The data (basically the ID).
     */
    void onBroadcastTurn(BroadcastTurnData data);
}
