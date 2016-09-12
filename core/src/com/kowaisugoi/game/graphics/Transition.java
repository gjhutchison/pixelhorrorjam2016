package com.kowaisugoi.game.graphics;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * A transition is a type of animation which plays between rooms
 * At some point during the animation, it fires a callback which triggers a room change
 */
public interface Transition {
    public void draw(SpriteBatch batch);

    public void draw(ShapeRenderer renderer);

    public void update(float delta);

    public void play();
}
