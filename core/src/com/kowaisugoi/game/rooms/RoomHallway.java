package com.kowaisugoi.game.rooms;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.kowaisugoi.game.audio.AudioManager;
import com.kowaisugoi.game.audio.MusicId;
import com.kowaisugoi.game.audio.SoundId;
import com.kowaisugoi.game.interactables.passages.DirectionalPassage;
import com.kowaisugoi.game.interactables.passages.Passage;
import com.kowaisugoi.game.screens.PlayGame;
import com.kowaisugoi.game.system.GameUtil;

import static com.kowaisugoi.game.control.flags.FlagId.FLAG_NIGHT_TIME;

public class RoomHallway extends StandardRoom {

    private static final String ROOM_URL = "rooms/hallway/hallway.png";
    private static final String ROOM_URL2 = "rooms/hallway/hallway_night.png";

    private final Sprite _roomSprite1 = new Sprite(new Texture(ROOM_URL));
    private final Sprite _roomSprite2 = new Sprite(new Texture(ROOM_URL2));

    public RoomHallway() {
        super(new Sprite(new Texture(ROOM_URL)));

        Passage passageMainRoom = new DirectionalPassage(RoomId.HALLWAY,
                RoomId.MAIN_HALL,
                new Rectangle(51, 0, 54, 10),
                GameUtil.Direction.DOWN);

        Passage passageBedroom = new DirectionalPassage(RoomId.HALLWAY,
                RoomId.BEDROOM,
                new Rectangle(15, 11, 30, 56),
                GameUtil.Direction.UP);

        Passage passageKitchen = new DirectionalPassage(RoomId.HALLWAY,
                RoomId.KITCHEN,
                new Rectangle(115, 7, 30, 58),
                GameUtil.Direction.UP);

        passageBedroom.setSoundEffect(SoundId.FLOOR_STEP);
        passageMainRoom.setSoundEffect(SoundId.DOOR_CREAK);
        passageKitchen.setSoundEffect(SoundId.FLOOR_STEP);
        addPassage(passageMainRoom);
        addPassage(passageBedroom);
        addPassage(passageKitchen);
    }

    @Override
    public void enter() {
        super.enter();
        AudioManager.playMusic(MusicId.HOWL, false);
    }

    @Override
    public void flagUpdate() {
        if (PlayGame.getFlagManager().getFlag(FLAG_NIGHT_TIME).getState()) {
            setSprite(_roomSprite2);
        } else {
            setSprite(_roomSprite1);
        }
    }
}
