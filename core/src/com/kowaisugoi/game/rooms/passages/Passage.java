package com.kowaisugoi.game.rooms.passages;

import com.kowaisugoi.game.interactables.Interactable;
import com.kowaisugoi.game.rooms.RoomId;

public interface Passage extends Interactable {
    public RoomId getDestination();
}
