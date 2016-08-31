package com.kowaisugoi.game.interactables.objects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by ecrothers on 2016-08-30.
 */
public class PickupItem implements Item {
    @Override
    public Sprite getWorldSprite() {
        return null;
    }

    public Sprite getInventorySprite() {
        return null;
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
