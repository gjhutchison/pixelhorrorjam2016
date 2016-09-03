package com.kowaisugoi.game.interactables.passages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.kowaisugoi.game.interactables.InteractionListener;
import com.kowaisugoi.game.player.Player;
import com.kowaisugoi.game.rooms.RoomId;

import java.util.LinkedList;

/**
 * Created by ecrothers on 2016-08-30.
 */
public class StandardPassage implements Passage {
    private LinkedList<InteractionListener> _listeners = new LinkedList<InteractionListener>();
    private Rectangle _interactionBox;
    private RoomId _destination;

    public StandardPassage(RoomId dest, Rectangle interactionBox) {
        _destination = dest;
        _interactionBox = interactionBox;
    }

    @Override
    public RoomId getDestination() {
        return _destination;
    }

    @Override
    public Rectangle getInteractionBox() {
        return _interactionBox;
    }

    @Override
    public void roomTransition() {

        Player.setCurrentRoom(_destination);
    }

    @Override
    public void draw(SpriteBatch batch) {
    }

    @Override
    public void draw(ShapeRenderer renderer) {
        if (Player.getDebug()) {
            renderer.setColor(0, 1, 0, 0.25f);
            renderer.rect(_interactionBox.x, _interactionBox.y, _interactionBox.width, _interactionBox.height);
        }
    }

    @Override
    public boolean click(float curX, float curY) {
        if (_interactionBox.contains(curX, curY)) {
            notifyListeners();
            roomTransition();
            return true;
        }
        return false;
    }

    @Override
    public void registerListener(InteractionListener lis) {
        _listeners.push(lis);
    }

    private void notifyListeners() {
        for (InteractionListener listener : _listeners) {
            listener.notifyListener();
        }
    }
}
