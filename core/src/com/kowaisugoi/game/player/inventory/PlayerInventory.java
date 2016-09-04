package com.kowaisugoi.game.player.inventory;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.kowaisugoi.game.interactables.objects.PickupableItem;

import java.util.ArrayList;
import java.util.List;

import static com.kowaisugoi.game.screens.PlayGame.GAME_HEIGHT;
import static com.kowaisugoi.game.screens.PlayGame.GAME_WIDTH;

public class PlayerInventory implements Disposable {

    private static final String INVENTORY_SPRITE_URL = "ui/inventory.png";

    private List<InventorySlot> _inventorySlots;
    private Sprite _inventorySprite;

    private boolean _inventoryOpen;

    public PlayerInventory() {
        _inventorySlots = new ArrayList<InventorySlot>();
        _inventorySprite = setupSprite();

        _inventoryOpen = false;
    }

    public void toggleInventory() {
        _inventoryOpen = !_inventoryOpen;

        if (_inventoryOpen) {
            positionSprites();
        }
    }

    private void positionSprites() {
        for (int i = 0; i < _inventorySlots.size(); i++) {
            Sprite sprite = _inventorySlots.get(i).getItem().getInventorySprite();
            sprite.setSize(32, 32);
            sprite.setPosition(19 + (47 * (i - (3 * (i / 3)))), 47 - (37 * (i / 3)));
        }
    }

    private Sprite setupSprite() {
        Sprite sprite = new Sprite(new Texture(INVENTORY_SPRITE_URL));
        sprite.setSize(GAME_WIDTH, GAME_HEIGHT);
        sprite.setPosition(0, 0);

        return sprite;
    }

    public void drawInventory(SpriteBatch batch) {
        if (!_inventoryOpen) {
            return;
        }
        _inventorySprite.draw(batch);
        drawItems(batch);
    }

    public void drawItems(SpriteBatch batch) {
        for (InventorySlot slot : _inventorySlots) {
            slot.getItem().getInventorySprite().draw(batch);
        }
    }

    public void addItem(PickupableItem item) {
        _inventorySlots.add(new InventorySlot(item));
    }

    public boolean isInventoryOpen() {
        return _inventoryOpen;
    }

    @Override
    public void dispose() {
        _inventorySprite.getTexture().dispose();
    }
}
