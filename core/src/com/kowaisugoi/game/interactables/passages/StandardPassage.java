package com.kowaisugoi.game.interactables.passages;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.kowaisugoi.game.graphics.SlideTransition;
import com.kowaisugoi.game.graphics.Transition;
import com.kowaisugoi.game.interactables.InteractionListener;
import com.kowaisugoi.game.interactables.objects.ItemId;
import com.kowaisugoi.game.player.Player;
import com.kowaisugoi.game.rooms.RoomId;
import com.kowaisugoi.game.screens.World;
import com.kowaisugoi.game.system.GameUtil.Direction;

import java.util.LinkedList;

public class StandardPassage implements Passage {
    private LinkedList<InteractionListener> _listeners = new LinkedList<InteractionListener>();
    protected Rectangle _interactionBox;
    private RoomId _source, _destination;
    protected SlideTransition _transition;
    private Direction _direction;

    public StandardPassage(RoomId src, RoomId dest, Rectangle interactionBox, Direction direction) {
        _source = src;
        _destination = dest;
        _interactionBox = interactionBox;
        _transition = new SlideTransition(this, src, dest);
        _direction = direction;
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
    public void roomTransition(Transition t) {
        World.getPlayer().enterRoom(_destination, t);
    }

    @Override
    public Direction getDirection() {
        return _direction;
    }

    @Override
    public void draw(SpriteBatch batch) {
        _transition.draw(batch);
    }

    @Override
    public void draw(ShapeRenderer renderer) {
        _transition.draw(renderer);
        if (World.getPlayer().getDebug()) {
            if (World.getPlayer().getInteractionMode() == Player.InteractionMode.NORMAL) {
                renderer.setColor(0, 1, 0, 0.25f);
            } else {
                renderer.setColor(1, 0, 0, 0.25f);
            }
            renderer.rect(_interactionBox.x, _interactionBox.y, _interactionBox.width, _interactionBox.height);
        }
    }

    @Override
    public boolean click(float curX, float curY) {
        if (_interactionBox.contains(curX, curY)) {
            notifyListeners();

            World.getPlayer().setInteractionMode(Player.InteractionMode.NONE);
            _transition.startAnimation(_direction);
            return true;
        }
        return false;
    }

    @Override
    public void beautifyCursor(float curX, float curY) {
        if (_interactionBox.contains(curX, curY)) {
            switch (getDirection()) {
                case UP:
                    World.getPlayer().setCursor(Player.CursorType.UP_ARROW);
                    break;
                case DOWN:
                    World.getPlayer().setCursor(Player.CursorType.DOWN_ARROW);
                    break;
                case LEFT:
                    World.getPlayer().setCursor(Player.CursorType.LEFT_ARROW);
                    break;
                case RIGHT:
                    World.getPlayer().setCursor(Player.CursorType.RIGHT_ARROW);
                    break;
            }
        }
    }

    @Override
    public void update(float delta) {
        _transition.update(delta);
    }

    @Override
    public void registerListener(InteractionListener lis) {
        _listeners.push(lis);
    }

    @Override
    public boolean isItemInteractable() {
        return false;
    }

    @Override
    public boolean itemIteract(ItemId id) {
        return false;
    }

    private void notifyListeners() {
        for (InteractionListener listener : _listeners) {
            listener.notifyListener();
        }
    }
}
