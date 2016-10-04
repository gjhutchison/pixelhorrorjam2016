package com.kowaisugoi.game.rooms;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.kowaisugoi.game.graphics.SnowAnimation;
import com.kowaisugoi.game.interactables.passages.DirectionalPassage;
import com.kowaisugoi.game.interactables.passages.Passage;
import com.kowaisugoi.game.interactables.scenic.Describable;
import com.kowaisugoi.game.interactables.scenic.GeneralDescribable;
import com.kowaisugoi.game.messages.Messages;
import com.kowaisugoi.game.system.GameUtil;

public class RoomCarPark extends StandardRoom {

    private SnowAnimation _snowAnimation;
    private static final String ROOM_URL = "rooms/carpark/carpark.png";

    public RoomCarPark() {
        super(new Sprite(new Texture(ROOM_URL)));
        _snowAnimation = new SnowAnimation(50, 6);

        Passage enterCar = new DirectionalPassage(RoomId.PARKING_AREA,
                RoomId.CAR,
                new Rectangle(31, 11, 45, 34),
                GameUtil.Direction.UP);
        Passage toPath = new DirectionalPassage(RoomId.PARKING_AREA,
                RoomId.ROAD,
                new Rectangle(97, 15, 36, 62),
                GameUtil.Direction.UP);

        addPassage(enterCar);
        addPassage(toPath);
    }

    @Override
    public void update(float delta) {
        _snowAnimation.updateSnow(delta);
        super.update(delta);
    }

    @Override
    public void draw(ShapeRenderer renderer) {
        _snowAnimation.draw(renderer);
        super.draw(renderer);
    }
}
