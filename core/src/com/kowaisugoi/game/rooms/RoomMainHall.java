package com.kowaisugoi.game.rooms;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.kowaisugoi.game.audio.SoundId;
import com.kowaisugoi.game.graphics.FireAnimation;
import com.kowaisugoi.game.interactables.passages.DirectionalPassage;
import com.kowaisugoi.game.interactables.passages.Passage;
import com.kowaisugoi.game.interactables.scenic.Describable;
import com.kowaisugoi.game.interactables.scenic.GeneralDescribable;
import com.kowaisugoi.game.messages.Messages;
import com.kowaisugoi.game.system.GameUtil;

import java.util.ArrayList;

public class RoomMainHall extends StandardRoom {

    private static final String ROOM_URL = "rooms/mainhall/cozy_room.png";
    private FireAnimation _fireAnimation;
    int _currentSprite = 0;
    float _deltaBuffer = 0;
    /*
    private static final String[] OVERLAY_URLS = {"rooms/mainhall/cozy_overlay_1.png",
            "rooms/mainhall/cozy_overlay_2.png",
            "rooms/mainhall/cozy_overlay_3.png"};
    */
    //ArrayList<Sprite> _overlays = new ArrayList<Sprite>();
    //TODO: Fix overlays
    public RoomMainHall() {
        super(new Sprite(new Texture(ROOM_URL)));
        /*
        for (String overlayUrl : OVERLAY_URLS) {
            Sprite overlay = new Sprite(new Texture(overlayUrl));
            overlay.scale(0.8f);
            _overlays.add(overlay);
        }
        */
        Passage hallDoor = new DirectionalPassage(RoomId.MAIN_HALL, RoomId.HALLWAY, new Rectangle(5, 25, 30, 40), GameUtil.Direction.LEFT);
        Passage turnAround = new DirectionalPassage(RoomId.MAIN_HALL, RoomId.FRONTYARD, new Rectangle(55, 0, 50, 10), GameUtil.Direction.DOWN);

        Describable paintingDescription = new GeneralDescribable(Messages.getText("mainhall.painting.thought"),
                new Rectangle(55, 50, 40, 30));

        turnAround.setSoundEffect(SoundId.DOOR_CREAK);
        addDescribable(paintingDescription);
        addPassage(hallDoor);
        addPassage(turnAround);
        _fireAnimation = new FireAnimation(50, 0, 50);
    }

    public void drawFx(SpriteBatch batch) {
        super.draw(batch);
        //_overlays.get(currentSprite).draw(batch, 0.08f);
    }

    @Override
    public void draw(ShapeRenderer renderer) {
        _fireAnimation.draw(renderer);
        super.draw(renderer);
    }

    public void update(float delta) {
        super.update(delta);
        _fireAnimation.update(delta);
        /*
        deltaBuffer += delta;
        if (deltaBuffer >= 0.25) { // TODO: Randomly change this max value
            deltaBuffer = 0;
            currentSprite++;
            if (currentSprite >= OVERLAY_URLS.length) {
                currentSprite = 0;
            }
        }*/
    }

    @Override
    public void dispose() {

    }

    // TODO: dispose overlays
}
