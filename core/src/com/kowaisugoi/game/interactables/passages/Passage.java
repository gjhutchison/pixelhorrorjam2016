package com.kowaisugoi.game.interactables.passages;

import com.kowaisugoi.game.graphics.Transition;
import com.kowaisugoi.game.interactables.Interactable;
import com.kowaisugoi.game.rooms.RoomId;
import com.kowaisugoi.game.system.GameUtil.Direction;

public interface Passage extends Interactable {
    public RoomId getDestination();

    public void roomTransition();

    public void transitionComplete();
}
