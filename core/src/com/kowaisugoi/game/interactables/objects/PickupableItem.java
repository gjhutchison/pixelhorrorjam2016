package com.kowaisugoi.game.interactables.objects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.kowaisugoi.game.interactables.InteractionListener;

import java.util.LinkedList;

/**
 * Items which can be picked up, and therefore require an inventory sprite
 * Created by ecrothers on 2016-08-30.
 */
public class PickupableItem implements Item {
    private LinkedList<InteractionListener> _listeners = new LinkedList<InteractionListener>();
    private Rectangle _interactionBox;
    public Sprite _sprite;

    public PickupableItem(Sprite sprite, Rectangle interactionBox) {
        _interactionBox = interactionBox;
    }

    // Draw any world squares (debug interactable areas)
    public void draw(ShapeRenderer renderer) {
    }

    // Draw the world sprite
    public void draw(SpriteBatch batch) {
    }

    // Draw the inventory sprite
    public void drawInventorySprite(SpriteBatch batch) {
    }

    @Override
    public Rectangle getInteractionBox() {
        return _interactionBox;
    }

    @Override
    public boolean click(float curX, float curY) {
         if (_interactionBox.contains(curX, curY)) {
            notifyListeners();
            return true;
        }
        return false;
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public boolean mouseMoved(float curX, float curY) {
        if (_interactionBox.contains(curX, curY)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void registerListener(InteractionListener listener) {
        _listeners.push(listener);
    }

    public void notifyListeners() {
        for (InteractionListener listener : _listeners) {
            listener.notifyListener();
        }
    }
}
