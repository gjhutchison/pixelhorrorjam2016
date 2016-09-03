package com.kowaisugoi.game.interactables;

import com.kowaisugoi.game.graphics.SlideTransition;
import com.kowaisugoi.game.player.Player;
import com.kowaisugoi.game.rooms.RoomId;

import java.util.LinkedList;
import java.util.List;

public class PassageListenerManager implements InteractionListenerManager {
    private List<PassageListener> _passageListener;
    private SlideTransition _slideTransition;
    private boolean _disablePolling;

    private boolean _transferingRoom;
    private RoomId _transferTarget;

    public PassageListenerManager() {
        _passageListener = new LinkedList<PassageListener>();
        _disablePolling = false;
    }

    public void registerSlideTransition(SlideTransition slideTransition) {
        _slideTransition = slideTransition;
    }

    //TODO: Disable interaction with anything while this is going on

    private void startRoomTransition(RoomId target) {
        _slideTransition.startAnimation(SlideTransition.Direction.UP);
        _transferingRoom = true;
        _transferTarget = target;
    }

    public boolean isTransferingRoom() {
        return _transferingRoom;
    }

    @Override
    public void pollNotifications() {
        for (PassageListener listener : _passageListener) {
            if (listener.poll()) {
                startRoomTransition(listener.getTransferLocation());
                listener.resetListener();
                _disablePolling = true;
                return;
            }
        }
    }

    @Override
    public void registerListener(InteractionListener listener) {
        _passageListener.add((PassageListener) listener);
    }
}
