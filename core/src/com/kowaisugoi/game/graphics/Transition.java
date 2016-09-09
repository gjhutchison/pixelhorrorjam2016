package com.kowaisugoi.game.graphics;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Created by ecrothers on 2016-09-08.
 */
public interface Transition {
    public void draw(SpriteBatch batch);

    public void draw(ShapeRenderer renderer);

    public void update(float delta);
}
