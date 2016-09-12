package com.kowaisugoi.game.interactables.objects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.kowaisugoi.game.interactables.Interactable;

public interface Item extends Interactable {
    public Sprite getInventorySprite();

    public ItemId getItemId();
}
