package com.kowaisugoi.game.player.inventory;

import com.kowaisugoi.game.interactables.objects.PickupableItem;

public class InventorySlot {

    private boolean _remove;
    private PickupableItem _item;

    public InventorySlot(PickupableItem item) {
        _item = item;
        _remove = false;
    }

    public boolean interact() {
        return true;
    }

    public boolean shouldRemove() {
        return _remove;
    }

    public void removeItem() {
        _remove = true;
    }

    public PickupableItem getItem() {
        return _item;
    }
}
