package com.kowaisugoi.game.rooms;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.particles.renderers.PointSpriteRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.kowaisugoi.game.graphics.Transition;
import com.kowaisugoi.game.interactables.Interactable;
import com.kowaisugoi.game.interactables.objects.ItemId;
import com.kowaisugoi.game.interactables.objects.PickupableItem;
import com.kowaisugoi.game.interactables.passages.Passage;
import com.kowaisugoi.game.player.Player;

import java.util.LinkedList;
import java.util.List;

import static com.kowaisugoi.game.screens.PlayGame.GAME_HEIGHT;
import static com.kowaisugoi.game.screens.PlayGame.GAME_WIDTH;

/**
 * Use this class as a class to build other rooms off of to avoid
 * code duplication
 */
public abstract class StandardRoom implements Room {

    private Sprite _roomSprite;
    protected boolean _visible = false;
    protected List<PickupableItem> _pickupableItemList = new LinkedList<PickupableItem>();
    protected List<Passage> _passageList = new LinkedList<Passage>();
    private Transition _transition = null;

    public StandardRoom(Sprite image) {
        _roomSprite = image;
        _roomSprite.setSize(GAME_WIDTH, GAME_HEIGHT);
    }

    public void enter() {
        // On entering the typical room, allow the player to interact
        Player.setInteractionMode(Player.InteractionMode.NORMAL);

        // On entering the typical room, enable drawing the room immediately
        _visible = true;
    }

    public void enter(Transition t) {
        enter();
        _transition = t;
    }

    public void addPassage(Passage interactable) {
        _passageList.add(interactable);
    }

    public void addPickupableItem(PickupableItem interactable) {
        _pickupableItemList.add(interactable);
    }

    @Override
    public void setVisible(boolean visible) {
        _visible = visible;
    }

    @Override
    public void draw(SpriteBatch batch) {
        if (!_visible) {
            return;
        }

        _roomSprite.draw(batch);
        for (Interactable interactable : _pickupableItemList) {
            interactable.draw(batch);
        }
    }

    @Override
    public void draw(ShapeRenderer renderer) {
        if (!_visible) {
            return;
        }

        for (Interactable interactable : _pickupableItemList) {
            interactable.draw(renderer);
        }
    }

    @Override
    public void drawFx(SpriteBatch batch) {
    }

    @Override
    public void drawOnFace(SpriteBatch batch) {
        // In a standard room, passages only draw as transitions
        // TODO: May want to make it passage.getTransition.draw(renderer), in case doors get sprites
        for (Passage passage : _passageList) {
            passage.draw(batch);
        }
    }

    @Override
    public void drawOnFace(ShapeRenderer renderer) {
        if (_transition != null) {
            _transition.draw(renderer);
        }
        // In a standard room, passages only draw as transitions
        // TODO: May want to make it passage.getTransition.draw(renderer), in case doors get sprites
        for (Passage passage : _passageList) {
            passage.draw(renderer);
        }
    }

    @Override
    public void beautifyCursor(float curX, float curY) {
        for (Passage passage : _passageList) {
            passage.beautifyCursor(curX, curY);
        }
        for (PickupableItem pickupableItem : _pickupableItemList) {
            pickupableItem.beautifyCursor(curX, curY);
            return;
        }
    }

    @Override
    public boolean click(float curX, float curY) {
        for (Interactable interactable : _passageList) {
            if (interactable.click(curX, curY)) {
                return true;
            }
        }

        for (PickupableItem pickupableItem : _pickupableItemList) {
            if (pickupableItem.click(curX, curY)) {
                Player.getInventory().addItem(pickupableItem);
                _pickupableItemList.remove(pickupableItem);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean click(float curX, float curY, ItemId itemId) {
        for (Interactable interactable : _passageList) {

            if (interactable.getInteractionBox().contains(curX, curY)) {
                if (interactable.isItemInteractable()) {
                    if (interactable.itemIteract(itemId)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void update(float delta) {
        if (_transition != null) {
            _transition.update(delta);
        }
        for (Interactable interactable : _passageList) {
            interactable.update(delta);
        }
    }

    @Override
    public void dispose() {
        _roomSprite.getTexture().dispose();
    }
}
