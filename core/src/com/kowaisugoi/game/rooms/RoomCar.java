package com.kowaisugoi.game.rooms;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.kowaisugoi.game.audio.AudioManager;
import com.kowaisugoi.game.audio.MusicId;
import com.kowaisugoi.game.control.flags.FlagId;
import com.kowaisugoi.game.interactables.passages.DirectionalPassage;
import com.kowaisugoi.game.interactables.passages.Passage;
import com.kowaisugoi.game.interactables.scenic.Describable;
import com.kowaisugoi.game.interactables.scenic.GeneralDescribable;
import com.kowaisugoi.game.messages.Messages;
import com.kowaisugoi.game.screens.PlayGame;
import com.kowaisugoi.game.system.GameUtil;

public class RoomCar extends StandardRoom {

    private static final String ROOM_URL = "rooms/car/car.png";
    private static final String ROOM_URL2 = "rooms/car/car_night.png";

    private static final Sprite _roomSpirte2 = new Sprite(new Texture(ROOM_URL2));

    Describable _steeringWheel;

    public RoomCar() {
        super(new Sprite(new Texture(ROOM_URL)));

        Passage carDoor = new DirectionalPassage(RoomId.CAR, RoomId.PARKING_AREA, new Rectangle(140, 0, 20, 200), GameUtil.Direction.RIGHT);

        Describable airFreshener = new GeneralDescribable(Messages.getText("car.airfreshener.description"), new Rectangle(75, 57, 10, 10));
        _steeringWheel = new GeneralDescribable(Messages.getText("car.wheel.description"), new Rectangle(6, 6, 56, 44));

        addDescribable(airFreshener);
        addDescribable(_steeringWheel);
        addPassage(carDoor);

        pushEnterRemark("car.enter.cold");
    }

    @Override
    public void flagUpdate() {
        if (PlayGame.getFlagManager().getFlag(FlagId.FLAG_KEYS_MISSING).getState()) {
            pushEnterRemark("car.keysmissing");
        }

        if (PlayGame.getFlagManager().getFlag(FlagId.FLAG_NIGHT_TIME).getState()) {
            _steeringWheel.setDescription(Messages.getText("car.wheel.escape"));
            setSprite(_roomSpirte2);
        }
    }

    @Override
    public void enter() {
        super.enter();
        if (PlayGame.getFlagManager().getFlag(FlagId.FLAG_NIGHT_TIME).getState()) {
            AudioManager.playMusic(MusicId.DRONE, false);
        }
    }
}
