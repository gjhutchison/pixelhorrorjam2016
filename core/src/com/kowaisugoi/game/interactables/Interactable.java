package com.kowaisugoi.game.interactables;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public interface Interactable {
    public Rectangle getInteractionBox();

    public void draw(SpriteBatch batch);

    public void draw(ShapeRenderer renderer);

    public boolean click(float curX, float curY);
}
