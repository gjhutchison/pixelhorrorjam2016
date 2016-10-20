package com.kowaisugoi.game.interactables.passages;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.kowaisugoi.game.control.flags.FlagId;
import com.kowaisugoi.game.interactables.Interactable;
import com.kowaisugoi.game.rooms.RoomId;

public interface Passage extends Interactable {
    public RoomId getDestination();

    public void roomTransition();

    public void transitionComplete();

    public void setTransitionImage(Sprite sprite);

    public void setTravelFlag(FlagId flag);

    public void setTravelSpeed(float speed);
}
