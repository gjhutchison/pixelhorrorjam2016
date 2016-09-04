package com.kowaisugoi.game.rooms;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.kowaisugoi.game.graphics.SnowAnimation;
import com.kowaisugoi.game.interactables.Interactable;
import com.kowaisugoi.game.interactables.passages.Passage;
import com.kowaisugoi.game.interactables.passages.StandardPassage;

public class RoomFrontYard extends StandardRoom {

    private static final String ROOM_URL = "rooms/FrontYard.png";

    private SnowAnimation _snowAnimation;

    public RoomFrontYard() {
        super(new Sprite(new Texture(ROOM_URL)));

        _snowAnimation = new SnowAnimation(50, 6);

        Passage frontDoor = new StandardPassage(RoomId.MAIN_HALL, new Rectangle(70, 20, 20, 35));

        addInteractable(frontDoor);
    }

    @Override
    public void update(float delta) {
        for (Interactable interactable : _interactables) {
            _snowAnimation.updateSnow(delta);
            interactable.update(delta);
        }
    }

    @Override
    public void draw(ShapeRenderer renderer) {
        for (Interactable interactable : _interactables) {
            _snowAnimation.draw(renderer);
            interactable.draw(renderer);
        }
    }
}
