package com.kowaisugoi.game.rooms;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.kowaisugoi.game.interactables.passages.DirectionalPassage;
import com.kowaisugoi.game.interactables.passages.Passage;
import com.kowaisugoi.game.interactables.scenic.Describable;
import com.kowaisugoi.game.interactables.scenic.GeneralDescribable;
import com.kowaisugoi.game.messages.Messages;
import com.kowaisugoi.game.system.GameUtil;

public class RoomShed extends StandardRoom {

    private static final String ROOM_URL = "rooms/shed/shed.png";

    public RoomShed() {
        super(new Sprite(new Texture(ROOM_URL)));

        Passage backToFront = new DirectionalPassage(RoomId.SHED, RoomId.FRONTYARD, new Rectangle(55, 0, 50, 10), GameUtil.Direction.DOWN);
        addPassage(backToFront);
    }
}
