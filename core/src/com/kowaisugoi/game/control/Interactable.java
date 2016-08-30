package com.kowaisugoi.game.control;

import com.badlogic.gdx.math.Rectangle;

public interface Interactable {
    public Rectangle getInteractionBox();

    public boolean isClicked(float curX, float curY);

}
