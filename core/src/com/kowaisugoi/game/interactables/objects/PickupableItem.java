package com.kowaisugoi.game.interactables.objects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.kowaisugoi.game.audio.AudioManager;
import com.kowaisugoi.game.audio.SoundId;
import com.kowaisugoi.game.interactables.InteractionListener;
import com.kowaisugoi.game.player.Player;
import com.kowaisugoi.game.screens.PlayGame;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Items which can be picked up, and therefore require an inventory sprite
 */
public class PickupableItem implements Item {
    private LinkedList<InteractionListener> _listeners = new LinkedList<InteractionListener>();
    private Rectangle _interactionBox;
    private Sprite _sprite;
    private Sprite _invSprite;
    private ItemId _id;

    private SoundId _soundId;

    private String _pickupText;

    private boolean _pickedUp;

    private Map<ItemId, String> _itemInteractionMessages;

    public PickupableItem(Sprite sprite, Rectangle interactionBox, ItemId id) {
        _interactionBox = interactionBox;
        _sprite = sprite;
        _invSprite = sprite;
        _sprite.setSize(_interactionBox.getWidth(), _interactionBox.getHeight());
        _sprite.setPosition(_interactionBox.getX(), _interactionBox.getY());
        _id = id;

        _pickedUp = false;

        _itemInteractionMessages = new HashMap<ItemId, String>();
    }

    public PickupableItem(Sprite worldSprite, Sprite invSprite, Rectangle interactionBox, ItemId id) {
        _interactionBox = interactionBox;
        _sprite = worldSprite;
        _invSprite = invSprite;
        _sprite.setPosition(0, 0);
        _sprite.setSize(PlayGame.GAME_WIDTH, PlayGame.GAME_HEIGHT);
        _id = id;
    }

    public void setPickupText(String pickupText) {
        _pickupText = pickupText;
    }

    // Draw debug areas
    public void draw(ShapeRenderer renderer) {
        if (PlayGame.getDebug()) {
            renderer.setColor(0.5f, 0.3f, 0.1f, 0.25f);
            renderer.rect(_interactionBox.x, _interactionBox.y, _interactionBox.width, _interactionBox.height);
        }
    }

    // Draw the world sprite
    public void draw(SpriteBatch batch) {
        _sprite.draw(batch);
    }

    // Draw the inventory sprite
    public Sprite getInventorySprite() {
        return _invSprite;
    }

    public boolean isPickedUp() {
        return _pickedUp;
    }

    @Override
    public Rectangle getInteractionBox() {
        return _interactionBox;
    }

    @Override
    public boolean click(float curX, float curY) {
        if (_interactionBox.contains(curX, curY)) {
            notifyListeners();
            AudioManager.playSound(_soundId);
            if (_pickupText != null) {
                PlayGame.getPlayer().think(_pickupText);
                _pickedUp = true;
            }
            return true;
        }
        return false;
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void beautifyCursor(float curX, float curY) {
        if (_interactionBox.contains(curX, curY)) {
            PlayGame.getPlayer().setCursor(Player.CursorType.PICKUP);
        }
    }

    @Override
    public void registerListener(InteractionListener listener) {
        _listeners.push(listener);
    }

    @Override
    public void setSoundEffect(SoundId soundId) {
        _soundId = soundId;
    }

    @Override
    public boolean isItemInteractable() {
        return false;
    }

    @Override
    public boolean itemInteract(ItemId id) {
        return false;
    }

    @Override
    public String getItemInteractionMessage(ItemId id) {
        if (_itemInteractionMessages.containsKey(id)) {
            return _itemInteractionMessages.get(id);
        }
        return "";
    }

    @Override
    public void setItemInteractionMessage(ItemId id, String message) {
        _itemInteractionMessages.put(id, message);
    }

    public void notifyListeners() {
        for (InteractionListener listener : _listeners) {
            listener.notifyListener();
        }
    }

    @Override
    public ItemId getItemId() {
        return _id;
    }

    @Override
    public boolean checkInteraction(float curX, float curY) {
        return _interactionBox.contains(curX, curY);
    }
}
