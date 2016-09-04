package com.kowaisugoi.game.interactables.objects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.kowaisugoi.game.interactables.Interactable;

/**
 * Created by ecrothers on 2016-08-30.
 */
public interface Item extends Interactable {
    public Sprite getInventorySprite();
}
