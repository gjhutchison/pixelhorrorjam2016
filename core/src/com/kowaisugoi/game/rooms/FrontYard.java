package com.kowaisugoi.game.rooms;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.kowaisugoi.game.interactables.Interactable;
import com.kowaisugoi.game.rooms.passages.Passage;
import com.kowaisugoi.game.rooms.passages.StandardPassage;

import java.util.LinkedList;

public class FrontYard implements Room {
    private Sprite _roomSprite = new Sprite(new Texture("FrontYard.png"));
    private LinkedList<Interactable> _interactables = new LinkedList<Interactable>();
    private Passage _frontDoor = new StandardPassage(RoomId.MAIN_HALL, new Rectangle(200, 200, 600, 600));
    //private Passage _turnAround = new StandardPassage(RoomId.OUTSIDE, new Rectangle(200, 200, 200, 200));

    public FrontYard() {
        _interactables.push(_frontDoor);
    }

    @Override
    public void draw(SpriteBatch batch) {
        _roomSprite.draw(batch);
    }

    @Override
    public boolean click(float curX, float curY) {
        for (Interactable inter : _interactables) {
            inter.click(curX, curY);
        }
        return true;
    }
}
