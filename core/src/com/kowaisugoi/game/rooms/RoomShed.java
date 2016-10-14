package com.kowaisugoi.game.rooms;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
import com.kowaisugoi.game.interactables.scenic.ItemInteractableScenic;
import com.kowaisugoi.game.messages.Messages;
import com.kowaisugoi.game.screens.PlayGame;
import com.kowaisugoi.game.system.GameUtil;
import static com.kowaisugoi.game.control.flags.FlagId.FLAG_SHED_OPENED;

public class RoomShed extends StandardRoom {

    private static final String ROOM_URL = "rooms/shed/shed_v2_frozen.png";
    private static final String ROOM_URL2 = "rooms/shed/shed_v2.png";

    private final Sprite _roomSprite1 = new Sprite(new Texture(ROOM_URL));
    private final Sprite _roomSprite2 = new Sprite(new Texture(ROOM_URL2));

    private SnowAnimation _snowAnimation;

    public RoomShed() {
        super(new Sprite(new Texture(ROOM_URL)));

        _snowAnimation = new SnowAnimation(50, 6);

        BlockedPassage shedDoor = new BlockedPassage(RoomId.SHED,
                RoomId.SHED_INTERIOR,
                new Rectangle(39, 13, 39, 46),
                GameUtil.Direction.UP,
                ItemId.GLASS_WATER,
                Messages.getText("shed.door.interact.locked"),
                Messages.getText("shed.door.interact.unlocked"),
                SoundId.DOOR_LOCKED);
        shedDoor.setUnlockedToggleFlag(FLAG_SHED_OPENED);

        shedDoor.setItemInteractionMessage(ItemId.GLASS,
                Messages.getText("shed.interaction.glass.doorfrozen"));
        shedDoor.setItemInteractionMessage(ItemId.GLASS_SNOW,
                Messages.getText("shed.interaction.glasssnow.doorfrozen"));

        Passage backToFront = new DirectionalPassage(RoomId.SHED, RoomId.FRONTYARD, new Rectangle(55, 0, 50, 10), GameUtil.Direction.DOWN);
        addPassage(backToFront);
        addPassage(shedDoor);

        backToFront.setSoundEffect(SoundId.SNOW_CRUNCH);

        PickupableItem glassSnow = new PickupableItem(new Sprite(new Texture("items/glass_snow.png")),
                new Rectangle(0, 0, 0, 0),
                ItemId.GLASS_SNOW);

        ItemInteractableScenic looseSnow = new ItemInteractableScenic(Messages.getText("shed.snow.thought"),
                Messages.getText("shed.interaction.snow.glass"),
                new Rectangle(98, 4, 61, 45),
                ItemId.GLASS, glassSnow);

        addDescribable(looseSnow);
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
        AudioManager.playMusic(MusicId.WIND, false);
    }

    @Override
    public void flagUpdate() {
        if (PlayGame.getFlagManager().getFlag(FLAG_SHED_OPENED).getState()) {
            setSprite(_roomSprite2);
        } else {
            setSprite(_roomSprite1);
        }
    }
}
