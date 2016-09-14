package com.kowaisugoi.game.rooms;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.kowaisugoi.game.interactables.passages.DirectionalPassage;
import com.kowaisugoi.game.interactables.passages.Passage;
import com.kowaisugoi.game.messages.MessageProperties;
import com.kowaisugoi.game.screens.PlayGame;
import com.kowaisugoi.game.system.GameUtil;

public class RoomForestPath extends StandardRoom {

    private static final String ROOM_URL = "rooms/path/path.png";
    private boolean firstTime = true;

    public RoomForestPath() {
        super(new Sprite(new Texture(ROOM_URL)));

        Passage forward = new DirectionalPassage(RoomId.ROAD, RoomId.FRONTYARD, new Rectangle(60, 20, 30, 60), GameUtil.Direction.UP);
        Passage backward = new DirectionalPassage(RoomId.ROAD, RoomId.CAR, new Rectangle(55, 0, 50, 10), GameUtil.Direction.DOWN);

        addPassage(forward);
        addPassage(backward);
    }

    @Override
    public void enter() {
        super.enter();

        if (firstTime) {
            PlayGame.getPlayer().think(MessageProperties.getProperties().getProperty("thought.outside"), 2.0f);
            firstTime = false;
        }
    }
}
