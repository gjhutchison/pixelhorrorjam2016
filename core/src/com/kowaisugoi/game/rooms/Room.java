package com.kowaisugoi.game.rooms;

import com.badlogic.gdx.graphics.g2d.Sprite;

public interface Room {
    public Sprite getSprite();

    public boolean click(float curX, float curY);
}
