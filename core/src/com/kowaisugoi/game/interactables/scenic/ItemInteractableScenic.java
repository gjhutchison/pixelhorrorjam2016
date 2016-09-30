package com.kowaisugoi.game.interactables.scenic;

import com.badlogic.gdx.math.Rectangle;
import com.kowaisugoi.game.interactables.objects.ItemId;
import com.kowaisugoi.game.interactables.objects.PickupableItem;
import com.kowaisugoi.game.screens.PlayGame;

//TODO: make this into it's own unique class but for now it will just be an extension of GeneralDescribable
public class ItemInteractableScenic extends GeneralDescribable {

    private ItemId _interactableItem;
    private PickupableItem _returnItem;

    private String _interactThought;

    public ItemInteractableScenic(String description,
                                  String interactThought,
                                  Rectangle interactionBox,
                                  ItemId interactableItem,
                                  PickupableItem newItem) {
        super(description, interactionBox);
        _interactableItem = interactableItem;
        _returnItem = newItem;
        _interactThought = interactThought;
    }

    @Override
    public boolean click(float curX, float curY) {
        return super.click(curX, curY);
    }

    @Override
    public boolean isItemInteractable() {
        return true;
    }

    @Override
    public boolean itemIteract(ItemId id) {

        if (id == _interactableItem) {
            PlayGame.getPlayer().getInventory().addItem(_returnItem);
            PlayGame.getPlayer().getInventory().removeItem(id);
            PlayGame.getPlayer().think(_interactThought);
            return true;
        }

        return false;
    }
}
