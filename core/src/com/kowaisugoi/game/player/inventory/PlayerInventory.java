package com.kowaisugoi.game.player.inventory;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Disposable;
import com.kowaisugoi.game.interactables.objects.ItemId;
import com.kowaisugoi.game.interactables.objects.PickupableItem;
import com.kowaisugoi.game.player.Player;

import java.util.ArrayList;
import java.util.List;

import static com.kowaisugoi.game.screens.PlayGame.GAME_HEIGHT;
import static com.kowaisugoi.game.screens.PlayGame.GAME_WIDTH;

public class PlayerInventory implements Disposable {

    private static final String INVENTORY_SPRITE_URL = "ui/inventory.png";
    private static final String INVENTORY_BUTTON_SPRITE_URL = "ui/inventory_button.png";
    private static final String INVENTORY_BUTTON_CLOSED_URL = "ui/inventory_button_close.png";

    private List<InventorySlot> _inventorySlots;
    private Sprite _inventorySprite;
    private Sprite _buttonOpenSprite;
    private Sprite _buttonCloseSprite;

    private boolean _inventoryOpen;

    private InventorySlot _selectedSlot;

    public PlayerInventory() {
        _inventorySlots = new ArrayList<InventorySlot>();
        _inventorySprite = setupSprite();

        _buttonOpenSprite = setupButtonOpenSprite();
        _buttonCloseSprite = setupButtonCloseSprite();

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
            InventorySlot slot = _inventorySlots.get(i);

            float x = 19 + (47 * (i - (3 * (i / 3))));
            float y = 47 - (37 * (i / 3));

            Sprite sprite = _inventorySlots.get(i).getItem().getInventorySprite();
            sprite.setSize(32, 32);
            sprite.setPosition(x, y);

            slot.setInteractionBox(new Rectangle(x, y, 32, 32));

        }
    }

    private Sprite setupSprite() {
        Sprite sprite = new Sprite(new Texture(INVENTORY_SPRITE_URL));
        sprite.setSize(GAME_WIDTH, GAME_HEIGHT);
        sprite.setPosition(0, 0);

        return sprite;
    }

    private Sprite setupButtonOpenSprite() {
        Sprite sprite = new Sprite(new Texture(INVENTORY_BUTTON_SPRITE_URL));
        sprite.setSize(8, 8);
        sprite.setPosition(GAME_WIDTH - 8, GAME_HEIGHT - 8);

        return sprite;
    }

    private Sprite setupButtonCloseSprite() {
        Sprite sprite = new Sprite(new Texture(INVENTORY_BUTTON_CLOSED_URL));
        sprite.setSize(8, 8);
        sprite.setPosition(GAME_WIDTH - 8, GAME_HEIGHT - 8);

        return sprite;
    }

    public Rectangle getButtonBox() {
        return new Rectangle(_buttonOpenSprite.getX(),
                _buttonOpenSprite.getY(),
                _buttonOpenSprite.getWidth(),
                _buttonOpenSprite.getHeight());
    }

    public void drawInventory(SpriteBatch batch) {
        if (!_inventoryOpen || Player.getInteractionMode() != Player.InteractionMode.INVENTORY) {
            _buttonOpenSprite.draw(batch);
            return;
        }
        _buttonCloseSprite.draw(batch);
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

    public boolean click(float curX, float curY) {
        for (InventorySlot slot : _inventorySlots) {
            if (slot.interact(curX, curY)) {
                _selectedSlot = slot;
                _inventoryOpen = false;
                return true;
            }
        }
        return false;
    }

    public void drawSelectedItemSprite(SpriteBatch batch) {
        Sprite sprite = _selectedSlot.getItem().getInventorySprite();
        sprite.draw(batch);
    }

    public void moveSelectedItemSprite(float curX, float curY) {
        Sprite sprite = _selectedSlot.getItem().getInventorySprite();
        sprite.setPosition(curX, curY);
        sprite.setSize(16, 16);
    }

    public ItemId getSelectedItemId() {
        return _selectedSlot.getItem().getItemId();
    }

    @Override
    public void dispose() {
        _inventorySprite.getTexture().dispose();
        _buttonOpenSprite.getTexture().dispose();
        _buttonCloseSprite.getTexture().dispose();
    }
}
