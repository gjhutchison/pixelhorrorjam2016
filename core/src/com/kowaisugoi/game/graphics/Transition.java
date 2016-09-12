package com.kowaisugoi.game.graphics;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public interface Transition {
    public void draw(SpriteBatch batch);

    public void draw(ShapeRenderer renderer);

    public void update(float delta);
}
