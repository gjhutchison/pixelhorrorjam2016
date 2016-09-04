package com.kowaisugoi.game.rooms;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.kowaisugoi.game.interactables.passages.Passage;
import com.kowaisugoi.game.interactables.passages.StandardPassage;

public class RoomCar extends StandardRoom {

    private static final String ROOM_URL = "rooms/car_v1.png";

    public RoomCar() {
        super(new Sprite(new Texture(ROOM_URL)));

        Passage carDoor = new StandardPassage(RoomId.FRONTYARD, new Rectangle(140, 0, 20, 200));

        addInteractable(carDoor);
    }
}
