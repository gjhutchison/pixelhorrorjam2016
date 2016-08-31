package com.kowaisugoi.game.interactables.passages;

import com.kowaisugoi.game.interactables.Interactable;
import com.kowaisugoi.game.rooms.RoomId;

public interface Passage extends Interactable {
    public RoomId getDestination();
}
