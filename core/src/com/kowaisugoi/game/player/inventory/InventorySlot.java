package com.kowaisugoi.game.player.inventory;

import com.badlogic.gdx.math.Rectangle;
import com.kowaisugoi.game.interactables.objects.PickupableItem;

public class InventorySlot {

    private boolean _remove;
    private PickupableItem _item;
    private Rectangle _interactionBox;

    public InventorySlot(PickupableItem item) {
        _item = item;
        _remove = false;
    }

    public boolean interact(float curX, float curY) {
        if (_interactionBox != null) {
            return _interactionBox.contains(curX, curY);
        }
        return false;
    }

    public boolean shouldRemove() {
        return _remove;
    }

    public void removeItem() {
        _remove = true;
    }

    public void setInteractionBox(Rectangle rectangle) {
        _interactionBox = rectangle;
    }

    public PickupableItem getItem() {
        return _item;
    }
}
