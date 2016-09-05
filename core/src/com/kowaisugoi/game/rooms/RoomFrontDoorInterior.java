package com.kowaisugoi.game.rooms;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.kowaisugoi.game.interactables.passages.Passage;
import com.kowaisugoi.game.interactables.passages.StandardPassage;
import com.kowaisugoi.game.system.GameUtil;

public class RoomFrontDoorInterior extends StandardRoom {
    private static final String ROOM_URL = "FrontDoorInterior.jpg";

    public RoomFrontDoorInterior() {
        super(new Sprite(new Texture(ROOM_URL)));

        Passage frontDoor = new StandardPassage(RoomId.FRONT_DOOR_INTERIOR, RoomId.MAIN_HALL, new Rectangle(70, 20, 20, 35), GameUtil.Direction.UP);

        addPassage(frontDoor);
    }
}
