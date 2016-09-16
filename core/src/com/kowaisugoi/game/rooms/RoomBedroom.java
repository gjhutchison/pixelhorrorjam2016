package com.kowaisugoi.game.rooms;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.kowaisugoi.game.interactables.passages.DirectionalPassage;
import com.kowaisugoi.game.interactables.passages.Passage;
import com.kowaisugoi.game.system.GameUtil;

public class RoomBedroom extends StandardRoom {

    private static final String ROOM_URL = "rooms/bedroom/sleeptight_gross_draft.png";

    public RoomBedroom() {
        super(new Sprite(new Texture(ROOM_URL)));

        Passage passageHall = new DirectionalPassage(RoomId.BEDROOM,
                RoomId.HALLWAY,
                new Rectangle(50, 0, 50, 15),
                GameUtil.Direction.DOWN);

        Passage passageBathroom = new DirectionalPassage(RoomId.BEDROOM,
                RoomId.BATHROOM,
                new Rectangle(60, 10, 30, 40),
                GameUtil.Direction.UP);

        addPassage(passageHall);
        addPassage(passageBathroom);
    }
}
