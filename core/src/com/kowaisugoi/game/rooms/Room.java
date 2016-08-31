package com.kowaisugoi.game.rooms;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public interface Room {
    public void draw(SpriteBatch batch);

    public void draw(ShapeRenderer batch);

    public boolean click(float curX, float curY);

    public void cleanUp();
}
