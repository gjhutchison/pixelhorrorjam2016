package com.kowaisugoi.game.rooms;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.kowaisugoi.game.interactables.PassageListener;
import com.kowaisugoi.game.interactables.passages.Passage;
import com.kowaisugoi.game.interactables.passages.StandardPassage;

public class RoomMainHall extends StandardRoom {

    private static final String ROOM_URL = "rooms/MainRoom.png";

    public RoomMainHall() {
        super(new Sprite(new Texture(ROOM_URL)));

        Passage frontDoor = new StandardPassage(RoomId.FRONTYARD, new Rectangle(65, 25, 30, 40));
        Passage turnAround = new StandardPassage(RoomId.FRONTYARD, new Rectangle(55, 0, 50, 10));

        PassageListener frontDoorListener = new PassageListener(RoomId.FRONTYARD);
        PassageListener turnAroundListener = new PassageListener(RoomId.FRONTYARD);

        frontDoor.registerListener(frontDoorListener);
        turnAround.registerListener(turnAroundListener);

        addInteractable(frontDoor);
        addInteractable(turnAround);
    }
}
