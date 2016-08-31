package com.kowaisugoi.game.rooms;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.kowaisugoi.game.interactables.Interactable;
import com.kowaisugoi.game.interactables.passages.Passage;
import com.kowaisugoi.game.interactables.passages.StandardPassage;

import java.util.LinkedList;

public class MainHall implements Room {
    private Sprite _roomSprite = new Sprite(new Texture("MainRoom.png"));
    private LinkedList<Interactable> _interactables = new LinkedList<Interactable>();
    private Passage _frontDoor = new StandardPassage(RoomId.FRONTYARD, new Rectangle(0, 0, 200, 200));
    private Passage _turnAround = new StandardPassage(RoomId.FRONT_DOOR_INTERIOR, new Rectangle(200, 200, 200, 200));

    public MainHall() {
        _interactables.push(_frontDoor);
        _interactables.push(_turnAround);
    }

    @Override
    public void draw(SpriteBatch batch) {
        _roomSprite.draw(batch);
        for (Interactable inter : _interactables) {
            inter.draw(batch);
        }
    }

    @Override
    public void draw(ShapeRenderer renderer) {
        for (Interactable inter : _interactables) {
            inter.draw(renderer);
        }
    }

    @Override
    public boolean click(float curX, float curY) {
        // For now, the Room is ambivalent about what happens with objects
        for (Interactable inter : _interactables) {
            inter.click(curX, curY);
        }
        return true;
    }

    @Override
    public void cleanUp() {
        _roomSprite.getTexture().dispose();
    }
}
