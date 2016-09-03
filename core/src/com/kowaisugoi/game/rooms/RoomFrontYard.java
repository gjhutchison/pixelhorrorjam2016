package com.kowaisugoi.game.rooms;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.kowaisugoi.game.interactables.PassageListener;
import com.kowaisugoi.game.interactables.passages.Passage;
import com.kowaisugoi.game.interactables.passages.StandardPassage;

public class RoomFrontYard extends StandardRoom {

    private static final String ROOM_URL = "rooms/FrontYard.png";

    public RoomFrontYard() {
        super(new Sprite(new Texture(ROOM_URL)));

        Passage frontDoor = new StandardPassage(RoomId.MAIN_HALL, new Rectangle(70, 20, 20, 35));

        frontDoor.registerListener(new PassageListener(RoomId.MAIN_HALL));

        addInteractable(frontDoor);
    }
}
