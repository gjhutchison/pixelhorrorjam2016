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
                new Rectangle(50, 0, 50, 15),
                GameUtil.Direction.DOWN);

        Passage passageBedroom = new DirectionalPassage(RoomId.HALLWAY,
                RoomId.BEDROOM,
                new Rectangle(10, 15, 32, 60),
                GameUtil.Direction.UP);

        Passage passageKitchen = new DirectionalPassage(RoomId.HALLWAY,
                RoomId.KITCHEN,
                new Rectangle(117, 6, 26, 60),
                GameUtil.Direction.UP);

        addPassage(passageMainRoom);
        addPassage(passageBedroom);
        addPassage(passageKitchen);
    }
}
