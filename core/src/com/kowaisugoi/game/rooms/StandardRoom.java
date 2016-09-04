package com.kowaisugoi.game.rooms;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.kowaisugoi.game.interactables.Interactable;
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
    protected List<Interactable> _interactables = new LinkedList<Interactable>();

    public StandardRoom(Sprite image) {
        _roomSprite = image;
        _roomSprite.setSize(GAME_WIDTH, GAME_HEIGHT);
    }

    public void enter() {
        // On entering the typical room, allow the player to interact
        Player.setCanInteract(true);
    }

    public void addInteractable(Interactable interactable) {
        _interactables.add(interactable);
    }

    @Override
    public void draw(SpriteBatch batch) {
        _roomSprite.draw(batch);
        for (Interactable interactable : _interactables) {
            interactable.draw(batch);
        }
    }

    @Override
    public void draw(ShapeRenderer renderer) {
        for (Interactable interactable : _interactables) {
            interactable.draw(renderer);
        }
    }

    @Override
    public boolean mouseMoved(float curX, float curY) {
        for (Interactable interactable : _interactables) {
            if (interactable.mouseMoved(curX, curY)) {
                Player.setCursor(Player.CursorType.UP_ARROW);
                return true;
            }
        }
        Player.setCursor(Player.CursorType.REGULAR);
        return false;
    }

    @Override
    public boolean click(float curX, float curY) {
        for (Interactable interactable : _interactables) {
            if (interactable.click(curX, curY)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void update(float delta) {
        for (Interactable interactable : _interactables) {
            interactable.update(delta);
        }
    }

    @Override
    public void dispose() {
        _roomSprite.getTexture().dispose();
    }
}
