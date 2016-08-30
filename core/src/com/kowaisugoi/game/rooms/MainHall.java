package com.kowaisugoi.game.rooms;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by ecrothers on 2016-08-29.
 */
public class MainHall implements Room {
    private Sprite _roomSprite = new Sprite(new Texture("MainRoom.png"));

    @Override
    public Sprite getSprite() {
        return _roomSprite;
    }
}
