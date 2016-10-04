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

import java.util.LinkedList;

/**
 * Items which can be picked up, and therefore require an inventory sprite
 */
public class PickupableItem implements Item {
    private LinkedList<InteractionListener> _listeners = new LinkedList<InteractionListener>();
    private Rectangle _interactionBox;
    private Sprite _sprite;
    private ItemId _id;

    private SoundId _soundId;

    private String _pickupText;

    public PickupableItem(Sprite sprite, Rectangle interactionBox, ItemId id) {
        _interactionBox = interactionBox;
        _sprite = sprite;
        _sprite.setPosition(_interactionBox.getX(), _interactionBox.getY());
        _id = id;
    }

    public void setPickupText(String pickupText) {
        _pickupText = pickupText;
    }

    // Draw any world squares (debug interactable areas)
    public void draw(ShapeRenderer renderer) {
    }

    // Draw the world sprite
    public void draw(SpriteBatch batch) {
        _sprite.draw(batch);
    }

    // Draw the inventory sprite
    public Sprite getInventorySprite() {
        return _sprite;
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
    public boolean itemIteract(ItemId id) {
        return false;
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
}
