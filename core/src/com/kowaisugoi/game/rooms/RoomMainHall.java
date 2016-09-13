package com.kowaisugoi.game.rooms;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.kowaisugoi.game.interactables.passages.DirectionalPassage;
import com.kowaisugoi.game.interactables.passages.Passage;
import com.kowaisugoi.game.system.GameUtil;

import java.util.ArrayList;

public class RoomMainHall extends StandardRoom {

    private static final String ROOM_URL = "rooms/mainhall/cozy_room.png";
    int currentSprite = 0;
    float deltaBuffer = 0;
    private static final String[] OVERLAY_URLS = {"rooms/mainhall/cozy_overlay_1.png",
            "rooms/mainhall/cozy_overlay_2.png",
            "rooms/mainhall/cozy_overlay_3.png"};
    ArrayList<Sprite> _overlays = new ArrayList<Sprite>();

    public RoomMainHall() {
        super(new Sprite(new Texture(ROOM_URL)));

        for (String overlayUrl : OVERLAY_URLS) {
            Sprite overlay = new Sprite(new Texture(overlayUrl));
            overlay.scale(0.8f);
            _overlays.add(overlay);
        }

        Passage frontDoor = new DirectionalPassage(RoomId.MAIN_HALL, RoomId.HALLWAY, new Rectangle(5, 25, 30, 40), GameUtil.Direction.LEFT);
        Passage turnAround = new DirectionalPassage(RoomId.MAIN_HALL, RoomId.FRONTYARD, new Rectangle(55, 0, 50, 10), GameUtil.Direction.DOWN);

        addPassage(frontDoor);
        addPassage(turnAround);
    }

    public void drawFx(SpriteBatch batch) {
        super.draw(batch);

        _overlays.get(currentSprite).draw(batch, 0.08f);
    }

    public void update(float delta) {
        super.update(delta);

        deltaBuffer += delta;
        if (deltaBuffer >= 0.25) { // TODO: Randomly change this max value
            deltaBuffer = 0;
            currentSprite++;
            if (currentSprite >= OVERLAY_URLS.length) {
                currentSprite = 0;
            }
        }
    }

    // TODO: dispose overlays
}
