package com.kowaisugoi.game.rooms;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.kowaisugoi.game.rooms.passages.Passage;
import com.kowaisugoi.game.rooms.passages.StandardPassage;

public class MainHall implements Room {
    private Sprite _roomSprite = new Sprite(new Texture("MainRoom.png"));
    private Passage _frontDoor = new StandardPassage(RoomId.FRONTYARD, new Rectangle(0, 0, 200, 200));

    @Override
    public Sprite getSprite() {
        return _roomSprite;
    }
}
