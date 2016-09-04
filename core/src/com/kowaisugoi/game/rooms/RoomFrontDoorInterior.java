package com.kowaisugoi.game.rooms;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.kowaisugoi.game.interactables.Interactable;
import com.kowaisugoi.game.interactables.passages.Passage;
import com.kowaisugoi.game.interactables.passages.StandardPassage;

import java.util.LinkedList;

public class RoomFrontDoorInterior extends StandardRoom {
    private static final String ROOM_URL = "FrontDoorInterior.jpg";

    //private Sprite _roomSprite = new Sprite(new Texture("FrontDoorInterior.jpg"));

    public RoomFrontDoorInterior() {
        super(new Sprite(new Texture(ROOM_URL)));

        Passage frontDoor = new StandardPassage(RoomId.MAIN_HALL, new Rectangle(70, 20, 20, 35));

        addInteractable(frontDoor);
    }
}
