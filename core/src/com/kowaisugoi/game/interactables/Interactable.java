package com.kowaisugoi.game.interactables;

import com.badlogic.gdx.math.Rectangle;

public interface Interactable {
    public Rectangle getInteractionBox();

    public boolean click(float curX, float curY);
}
