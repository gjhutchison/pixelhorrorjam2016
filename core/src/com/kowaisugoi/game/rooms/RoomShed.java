package com.kowaisugoi.game.rooms;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.kowaisugoi.game.audio.SoundId;
import com.kowaisugoi.game.interactables.objects.ItemId;
import com.kowaisugoi.game.interactables.objects.PickupableItem;
import com.kowaisugoi.game.interactables.passages.BlockedPassage;
import com.kowaisugoi.game.interactables.passages.DirectionalPassage;
import com.kowaisugoi.game.interactables.passages.Passage;
import com.kowaisugoi.game.interactables.scenic.ItemInteractableScenic;
import com.kowaisugoi.game.messages.Messages;
import com.kowaisugoi.game.system.GameUtil;

public class RoomShed extends StandardRoom {

    private static final String ROOM_URL = "rooms/shed/shed.png";

    public RoomShed() {
        super(new Sprite(new Texture(ROOM_URL)));

        BlockedPassage shedDoor = new BlockedPassage(RoomId.SHED,
                RoomId.SHED_INTERIOR,
                new Rectangle(39, 13, 39, 46),
                GameUtil.Direction.UP,
                ItemId.GLASS_WATER,
                Messages.getText("shed.door.interact.locked"),
                Messages.getText("shed.door.interact.unlocked"),
                SoundId.DOOR_LOCKED);
        Passage backToFront = new DirectionalPassage(RoomId.SHED, RoomId.FRONTYARD, new Rectangle(55, 0, 50, 10), GameUtil.Direction.DOWN);
        addPassage(backToFront);
        addPassage(shedDoor);

        PickupableItem glassSnow = new PickupableItem(new Sprite(new Texture("items/glass_snow.png")),
                new Rectangle(0, 0, 0, 0),
                ItemId.GLASS_SNOW);

        ItemInteractableScenic looseSnow = new ItemInteractableScenic(Messages.getText("shed.snow.thought"),
                Messages.getText("shed.interaction.snow.glass"),
                new Rectangle(110, 4, 23, 16),
                ItemId.GLASS, glassSnow);

        addDescribable(looseSnow);
    }
}
