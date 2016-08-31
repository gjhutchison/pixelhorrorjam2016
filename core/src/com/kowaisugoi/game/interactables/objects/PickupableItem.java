package com.kowaisugoi.game.interactables.objects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 * Items which can be picked up, and therefore require an inventory sprite
 * Created by ecrothers on 2016-08-30.
 */
public class PickupableItem implements Item {
    public Sprite _sprite;

    public PickupableItem(Sprite sprite) { }

    // Draw the world sprite
    public void draw(SpriteBatch batch) {
    }

    // Draw the inventory sprite
    public void drawInventorySprite(SpriteBatch batch) {
    }

    @Override
    public Rectangle getInteractionBox() {
        return null;
    }

    @Override
    public boolean click(float curX, float curY) {
        return false;
    }
}
