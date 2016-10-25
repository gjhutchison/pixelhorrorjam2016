package com.kowaisugoi.game.rooms;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.kowaisugoi.game.audio.AudioManager;
import com.kowaisugoi.game.audio.MusicId;
import com.kowaisugoi.game.audio.SoundId;
import com.kowaisugoi.game.graphics.SnowAnimation;
import com.kowaisugoi.game.interactables.objects.ItemId;
import com.kowaisugoi.game.interactables.objects.PickupableItem;
import com.kowaisugoi.game.interactables.passages.BlockedPassage;
import com.kowaisugoi.game.interactables.passages.DirectionalPassage;
import com.kowaisugoi.game.interactables.passages.Passage;
import com.kowaisugoi.game.interactables.scenic.Container;
import com.kowaisugoi.game.messages.Messages;
import com.kowaisugoi.game.screens.PlayGame;
import com.kowaisugoi.game.system.GameUtil.Direction;

import static com.kowaisugoi.game.control.flags.FlagId.FLAG_NIGHT_TIME;

public class RoomFrontYard extends StandardRoom {

    private static final String ROOM_URL = "rooms/frontyard/frontyard.png";
    private static final String ROOM_URL2 = "rooms/frontyard/frontyard_night.png";

    private final Sprite _roomSprite1 = new Sprite(new Texture(ROOM_URL));
    private final Sprite _roomSprite2 = new Sprite(new Texture(ROOM_URL2));

    private SnowAnimation _snowAnimation;

    public RoomFrontYard() {
        super(new Sprite(new Texture(ROOM_URL)));

        _snowAnimation = new SnowAnimation(50, 6);

        // TODO: Rooms should probably locally store their own id (associated refactoring required)
        Passage backToRoad = new DirectionalPassage(RoomId.FRONTYARD, RoomId.ROAD, new Rectangle(55, 0, 50, 10), Direction.DOWN);
        Passage toShed = new DirectionalPassage(RoomId.FRONTYARD, RoomId.SHED, new Rectangle(2, 12, 30, 65), Direction.UP);
        Passage frontDoor = new BlockedPassage(RoomId.FRONTYARD,
                RoomId.MAIN_HALL,
                new Rectangle(65, 35, 20, 40),
                Direction.UP, ItemId.KEY_HOUSE,
                Messages.getText("frontyard.door.interact.locked"),
                Messages.getText("frontyard.interaction.key.door"),
                SoundId.DOOR_LOCKED);

        backToRoad.setSoundEffect(SoundId.SNOW_CRUNCH);
        toShed.setSoundEffect(SoundId.SNOW_CRUNCH);
        frontDoor.setSoundEffect(SoundId.DOOR_CREAK);

        PickupableItem dankKey = new PickupableItem(new Sprite(new Texture("items/key.png")),
                new Rectangle(95, 28, 32, 32),
                ItemId.KEY_HOUSE);

        Container floorMat = new Container(new Sprite(new Texture("fakerock_1.png")),
                new Sprite(new Texture("fakerock_2.png")),
                new Sprite(new Texture("fakerock_3.png")),
                new Rectangle(88, 30, 8, 8));

        floorMat.setThoughts(Messages.getText("frontyard.fakerock.interact.container1"),
                Messages.getText("frontyard.fakerock.interact.container2"),
                Messages.getText("frontyard.fakerock.interact.container3"));

        floorMat.setPickupableItem(dankKey);
        addContainer(floorMat);
        addPassage(frontDoor);
        addPassage(backToRoad);
        addPassage(toShed);

        pushEnterRemark("frontyard.enter.thought");
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

    @Override
    public void enter() {
        super.enter();
        AudioManager.playMusic(MusicId.WIND, false);
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
