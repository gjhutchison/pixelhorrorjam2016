package com.kowaisugoi.game.rooms;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.kowaisugoi.game.interactables.passages.DirectionalPassage;
import com.kowaisugoi.game.interactables.passages.Passage;
import com.kowaisugoi.game.system.GameUtil;

public class RoomHallway extends StandardRoom {

    private static final String ROOM_URL = "rooms/hallway/hallway.png";

    public RoomHallway() {
        super(new Sprite(new Texture(ROOM_URL)));

        Passage passageMainRoom = new DirectionalPassage(RoomId.HALLWAY,
                RoomId.MAIN_HALL,
                new Rectangle(20, 0, 120, 30),
                GameUtil.Direction.DOWN);

        addPassage(passageMainRoom);

    }
}
