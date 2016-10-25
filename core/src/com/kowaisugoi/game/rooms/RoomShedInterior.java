package com.kowaisugoi.game.rooms;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.kowaisugoi.game.audio.AudioManager;
import com.kowaisugoi.game.audio.MusicId;
import com.kowaisugoi.game.control.flags.FlagId;
import com.kowaisugoi.game.control.flags.FlagManager;
import com.kowaisugoi.game.interactables.objects.ItemId;
import com.kowaisugoi.game.interactables.objects.PickupableItem;
import com.kowaisugoi.game.interactables.passages.DirectionalPassage;
import com.kowaisugoi.game.interactables.passages.Passage;
import com.kowaisugoi.game.interactables.scenic.ItemInteractableScenic;
import com.kowaisugoi.game.messages.Messages;
import com.kowaisugoi.game.screens.PlayGame;
import com.kowaisugoi.game.system.GameUtil;

public class RoomShedInterior extends StandardRoom {

    private static final String ROOM_URL = "rooms/shedinterior/shedinterior_temp.png";

    public RoomShedInterior() {
        super(new Sprite(new Texture(ROOM_URL)));

        PickupableItem shovel = new PickupableItem(new Sprite(new Texture("rooms/shedinterior/shovel.png")),
                new Sprite(new Texture("items/shovelicon.png")),
                new Rectangle(2, 56, 78, 25),
                ItemId.SHOVEL);

        PickupableItem hammer = new PickupableItem(new Sprite(new Texture("rooms/shedinterior/hammer.png")),
                new Sprite(new Texture("items/hammericon.png")),
                new Rectangle(87, 15, 15, 8),
                ItemId.HAMMER);

        PickupableItem stickDoused = new PickupableItem(new Sprite(new Texture("items/stickicon_doused.png")),
                new Rectangle(0, 0, 0, 0),
                ItemId.STICK_RAGS_ALCOHOL);

        Passage shedDoor = new DirectionalPassage(RoomId.SHED_INTERIOR,
                RoomId.SHED,
                new Rectangle(55, 0, 50, 10),
                GameUtil.Direction.DOWN);

        ItemInteractableScenic lighterFluid = new ItemInteractableScenic(Messages.getText("shedinterior.lighterfluid.thought"),
                Messages.getText("shedinterior.interaction.stickrags.lighterfluid"),
                new Rectangle(97, 58, 9, 15),
                ItemId.STICK_RAGS, stickDoused);

        shovel.setPickupText(Messages.getText("shedinterior.pickup.shovel"));
        hammer.setPickupText(Messages.getText("shedinterior.pickup.hammer"));
        addPassage(shedDoor);

        pushEnterRemark("shedinterior.enter.thought");
        addPickupableItem(shovel);
        addPickupableItem(hammer);

        addDescribable(lighterFluid);
    }

    @Override
    public void enter() {
        super.enter();
        AudioManager.playMusic(MusicId.HOWL, false);

        if (!PlayGame.getFlagManager().getFlag(FlagId.FLAG_NIGHT_TIME).getState()) {
            PlayGame.getFlagManager().setFlag(FlagId.FLAG_NIGHT_TIME, true);
            PlayGame.getRoomManager().getRoomFromId(RoomId.SHED).pushEnterRemark("shed.enter.night");
        }
    }
}
