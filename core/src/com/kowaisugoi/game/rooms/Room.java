package com.kowaisugoi.game.rooms;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface Room {
    public void draw(SpriteBatch batch);

    public boolean click(float curX, float curY);
}
