package com.kowaisugoi.game.rooms;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class RoomCrawlspace extends StandardRoom {

    private static final String ROOM_URL = "rooms/crawlspace/crawlspace.png";

    public RoomCrawlspace() {
        super(new Sprite(new Texture(ROOM_URL)));
    }
}
