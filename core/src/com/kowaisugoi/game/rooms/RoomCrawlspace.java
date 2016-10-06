package com.kowaisugoi.game.rooms;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.kowaisugoi.game.interactables.passages.DirectionalPassage;
import com.kowaisugoi.game.interactables.passages.Passage;
import com.kowaisugoi.game.system.GameUtil;

public class RoomCrawlspace extends StandardRoom {

    private static final String ROOM_URL = "rooms/crawlspace/crawlspace_noclutter.png";

    public RoomCrawlspace() {
        super(new Sprite(new Texture(ROOM_URL)));

        Passage turnAround = new DirectionalPassage(RoomId.CRAWLSPACE,
                RoomId.MAIN_HALL,
                new Rectangle(55, 0, 50, 10),
                GameUtil.Direction.DOWN);
        addPassage(turnAround);
    }
}
