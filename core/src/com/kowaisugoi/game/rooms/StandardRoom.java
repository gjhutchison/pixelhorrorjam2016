package com.kowaisugoi.game.rooms;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.kowaisugoi.game.interactables.Interactable;
import com.kowaisugoi.game.interactables.objects.Item;
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
    private boolean _visible = false;
    protected List<Item> _itemList = new LinkedList<Item>();
    protected List<Passage> _passageList = new LinkedList<Passage>();

    public StandardRoom(Sprite image) {
        _roomSprite = image;
        _roomSprite.setSize(GAME_WIDTH, GAME_HEIGHT);
    }

    public void enter() {
        // On entering the typical room, allow the player to interact
        Player.setCanInteract(true);

        // On entering the typical room, enable drawing the room immediately
        _visible = true;
    }

    public void addPassage(Passage interactable) {
        _passageList.add(interactable);
    }

    public void addItem(Item interactable) {
        _itemList.add(interactable);
    }

    @Override
    public void setVisible(boolean visible) {
        _visible = visible;
    }

    @Override
    public void draw(SpriteBatch batch) {
        for (Passage passage : _passageList) {
            passage.draw(batch);
        }

        if (!_visible) {
            return;
        }

        _roomSprite.draw(batch);
        for (Interactable interactable : _itemList) {
            interactable.draw(batch);
        }
    }

    @Override
    public void draw(ShapeRenderer renderer) {
        for (Passage passage : _passageList) {
            passage.draw(renderer);
        }

        if (!_visible) {
            return;
        }

        for (Interactable interactable : _itemList) {
            interactable.draw(renderer);
        }
    }

    //TODO: be mouse entered or mouse exited
    @Override
    public boolean mouseMoved(float curX, float curY) {
        for (Passage passage : _passageList) {
            if (passage.mouseMoved(curX, curY)) {
                switch (passage.getDirection()) {
                    case UP:
                        Player.setCursor(Player.CursorType.UP_ARROW);
                        break;
                    case DOWN:
                        Player.setCursor(Player.CursorType.DOWN_ARROW);
                        break;
                    case LEFT:
                        Player.setCursor(Player.CursorType.LEFT_ARROW);
                        break;
                    case RIGHT:
                        Player.setCursor(Player.CursorType.RIGHT_ARROW);
                        break;
                }
                return true;
            }
        }
        Player.setCursor(Player.CursorType.REGULAR);
        return false;
    }

    @Override
    public boolean click(float curX, float curY) {
        for (Interactable interactable : _passageList) {
            if (interactable.click(curX, curY)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void update(float delta) {
        for (Interactable interactable : _passageList) {
            interactable.update(delta);
        }
    }

    @Override
    public void dispose() {
        _roomSprite.getTexture().dispose();
    }
}
