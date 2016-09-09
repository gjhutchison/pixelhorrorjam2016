package com.kowaisugoi.game.rooms;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Disposable;
import com.kowaisugoi.game.interactables.objects.ItemId;

public interface Room extends Disposable {
    public void setVisible(boolean visible);

    public void drawFx(SpriteBatch batch);

    public void draw(SpriteBatch batch);

    public void draw(ShapeRenderer batch);

    public boolean click(float curX, float curY);

    public boolean click(float curX, float curY, ItemId itemId);

    public void beautifyCursor(float curX, float curY);

    public void update(float delta);

    public void enter();
}
