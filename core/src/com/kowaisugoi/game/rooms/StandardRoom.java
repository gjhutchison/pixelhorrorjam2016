package com.kowaisugoi.game.rooms;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.kowaisugoi.game.interactables.Interactable;

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
    private List<Interactable> _interactables = new LinkedList<Interactable>();

    public StandardRoom(Sprite image) {
        _roomSprite = image;
        _roomSprite.setSize(GAME_WIDTH, GAME_HEIGHT);
    }

    public void addInteractable(Interactable interactable) {
        _interactables.add(interactable);
    }

    @Override
    public void draw(SpriteBatch batch) {
        _roomSprite.draw(batch);
    }

    @Override
    public void draw(ShapeRenderer batch) {
        for (Interactable interactable : _interactables) {
            interactable.draw(batch);
        }
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
    public void dispose() {
        _roomSprite.getTexture().dispose();
    }
}
