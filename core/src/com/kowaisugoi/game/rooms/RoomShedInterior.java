package com.kowaisugoi.game.rooms;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.kowaisugoi.game.interactables.passages.DirectionalPassage;
import com.kowaisugoi.game.interactables.passages.Passage;
import com.kowaisugoi.game.messages.Messages;
import com.kowaisugoi.game.system.GameUtil;

public class RoomShedInterior extends StandardRoom {

    private static final String ROOM_URL = "rooms/shedinterior/shedinterior.jpg";

    public RoomShedInterior() {
        super(new Sprite(new Texture(ROOM_URL)));

        Passage shedDoor = new DirectionalPassage(RoomId.SHED_INTERIOR,
                RoomId.SHED,
                new Rectangle(0, 1, 18, 78),
                GameUtil.Direction.LEFT);

        addPassage(shedDoor);

        pushEnterRemark(Messages.getText("shedinterior.enter.thought"));
    }
}
