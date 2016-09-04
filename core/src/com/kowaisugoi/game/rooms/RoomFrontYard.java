package com.kowaisugoi.game.rooms;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.kowaisugoi.game.graphics.SlideTransition.Direction;
import com.kowaisugoi.game.graphics.SnowAnimation;
import com.kowaisugoi.game.interactables.passages.Passage;
import com.kowaisugoi.game.interactables.passages.StandardPassage;

public class RoomFrontYard extends StandardRoom {

    private static final String ROOM_URL = "rooms/frontyard_v1.png";

    private SnowAnimation _snowAnimation;

    public RoomFrontYard() {
        super(new Sprite(new Texture(ROOM_URL)));

        _snowAnimation = new SnowAnimation(50, 6);

        // TODO: Rooms should probably locally store their own id (associated refactoring required)
        Passage frontDoor = new StandardPassage(RoomId.FRONTYARD, RoomId.MAIN_HALL, new Rectangle(65, 35, 20, 40), Direction.UP);

        addPassage(frontDoor);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        _snowAnimation.updateSnow(delta);
    }

    @Override
    public void draw(ShapeRenderer renderer) {
        super.draw(renderer);
        _snowAnimation.draw(renderer);
    }
}
