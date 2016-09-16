package com.kowaisugoi.game.rooms;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.kowaisugoi.game.interactables.passages.DirectionalPassage;
import com.kowaisugoi.game.interactables.passages.Passage;
import com.kowaisugoi.game.system.GameUtil;

public class RoomBathroomPeek extends StandardRoom {

    private static final String ROOM_URL = "rooms/bathroom/uncle3.png";

    public RoomBathroomPeek() {
        super(new Sprite(new Texture(ROOM_URL)));

        Passage passageBack = new DirectionalPassage(RoomId.BATHROOM,
                RoomId.BEDROOM,
                new Rectangle(50, 0, 50, 15),
                GameUtil.Direction.DOWN);

        addPassage(passageBack);
    }
}
