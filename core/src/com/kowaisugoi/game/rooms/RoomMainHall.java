package com.kowaisugoi.game.rooms;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.kowaisugoi.game.interactables.passages.Passage;
import com.kowaisugoi.game.interactables.passages.StandardPassage;
import com.kowaisugoi.game.system.GameUtil;

public class RoomMainHall extends StandardRoom {

    private static final String ROOM_URL = "rooms/cozy_room.png";

    public RoomMainHall() {
        super(new Sprite(new Texture(ROOM_URL)));

        Passage frontDoor = new StandardPassage(RoomId.MAIN_HALL, RoomId.FRONTYARD, new Rectangle(65, 25, 30, 40), GameUtil.Direction.UP);
        Passage turnAround = new StandardPassage(RoomId.MAIN_HALL, RoomId.FRONTYARD, new Rectangle(55, 0, 50, 10), GameUtil.Direction.DOWN);

        addPassage(frontDoor);
        addPassage(turnAround);
    }
}
