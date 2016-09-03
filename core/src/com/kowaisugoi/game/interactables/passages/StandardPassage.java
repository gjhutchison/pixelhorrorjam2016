package com.kowaisugoi.game.interactables.passages;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.kowaisugoi.game.graphics.SlideTransition;
import com.kowaisugoi.game.interactables.InteractionListener;
import com.kowaisugoi.game.player.Player;
import com.kowaisugoi.game.rooms.RoomId;
import com.kowaisugoi.game.rooms.RoomManager;
import com.kowaisugoi.game.screens.PlayGame;

import java.util.LinkedList;

/**
 * Created by ecrothers on 2016-08-30.
 */
public class StandardPassage implements Passage {
    private SlideTransition _transition = new SlideTransition();
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
        // TODO: Dynamically set direction based on the interaction box location
        /*if (_interactionBox.getX() < PlayGame.GAME_WIDTH/2) {
            if (_interactionBox.getY() < PlayGame.GAME_HEIGHT/2) {
                _transition.startAnimation(SlideTransition.Direction.UP);
            } else {

            }
        } else {
            if (_interactionBox.getY() < PlayGame.GAME_HEIGHT/2) {

            } else {

            }
        }*/
        _transition.startAnimation(SlideTransition.Direction.UP);
        Player.setCurrentRoom(_destination);
    }

    @Override
    public void draw(SpriteBatch batch) {
        if (_transition.hasRoomChanged()) {
            RoomManager.getRoomMap().get(_destination).draw(batch);
        }
    }

    @Override
    public void draw(ShapeRenderer renderer) {
        _transition.draw(renderer);
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
    public void update(float delta) {
        _transition.update(delta);
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
