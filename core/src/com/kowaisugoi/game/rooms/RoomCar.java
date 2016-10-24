package com.kowaisugoi.game.rooms;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.kowaisugoi.game.control.flags.FlagId;
import com.kowaisugoi.game.interactables.passages.DirectionalPassage;
import com.kowaisugoi.game.interactables.passages.Passage;
import com.kowaisugoi.game.interactables.scenic.Describable;
import com.kowaisugoi.game.interactables.scenic.GeneralDescribable;
import com.kowaisugoi.game.messages.Messages;
import com.kowaisugoi.game.screens.PlayGame;
import com.kowaisugoi.game.system.GameUtil;

public class RoomCar extends StandardRoom {

    private static final String ROOM_URL = "rooms/car_v1.png";

    public RoomCar() {
        super(new Sprite(new Texture(ROOM_URL)));

        Passage carDoor = new DirectionalPassage(RoomId.CAR, RoomId.PARKING_AREA, new Rectangle(140, 0, 20, 200), GameUtil.Direction.RIGHT);

        Describable airFreshener = new GeneralDescribable(Messages.getText("car.airfreshener.description"), new Rectangle(75, 57, 10, 10));

        addDescribable(airFreshener);
        addPassage(carDoor);

        pushEnterRemark("car.enter.cold");
    }

    @Override
    public void flagUpdate() {
        if (PlayGame.getFlagManager().getFlag(FlagId.FLAG_KEYS_MISSING).getState()) {
            pushEnterRemark("car.keysmissing");
        }
    }
}
