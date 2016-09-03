package com.kowaisugoi.game.interactables;

import com.kowaisugoi.game.rooms.RoomId;
import com.kowaisugoi.game.screens.PlayGame;

/**
 * Handles events where there has been a click on a standard passage
 */
public class PassageListener implements InteractionListener {

    private boolean _poll;
    private RoomId _transferLocation;

    public PassageListener(RoomId transferLocation) {
        _poll = false;
        _transferLocation = transferLocation;
        PlayGame.PASSAGE_LISTENER_MANAGER.registerListener(this);
    }

    public boolean poll() {
        return _poll;
    }

    public RoomId getTransferLocation() {
        return _transferLocation;
    }

    @Override
    public void notifyListener() {
        _poll = true;
    }

    @Override
    public void resetListener() {
        _poll = false;
    }
}
