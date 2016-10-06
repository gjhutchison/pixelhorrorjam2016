package com.kowaisugoi.game.rooms;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.kowaisugoi.game.interactables.objects.ItemId;
import com.kowaisugoi.game.interactables.objects.PickupableItem;
import com.kowaisugoi.game.interactables.passages.DirectionalPassage;
import com.kowaisugoi.game.interactables.passages.Passage;
import com.kowaisugoi.game.messages.Messages;
import com.kowaisugoi.game.screens.PlayGame;
import com.kowaisugoi.game.system.GameUtil;

public class RoomShedInterior extends StandardRoom {

    private static final String ROOM_URL = "rooms/shedinterior/shedinterior_noshovel.png";

    public RoomShedInterior() {
        super(new Sprite(new Texture(ROOM_URL)));

        PickupableItem shovel = new PickupableItem(new Sprite(new Texture("rooms/shedinterior/shovel.png")),
                new Sprite(new Texture("items/shovelicon.png")),
                new Rectangle(2, 56, 78, 25),
                ItemId.SHOVEL);

        Passage shedDoor = new DirectionalPassage(RoomId.SHED_INTERIOR,
                RoomId.SHED,
                new Rectangle(55, 0, 50, 10),
                GameUtil.Direction.DOWN);

        shovel.setPickupText(Messages.getText("shedinterior.pickup.shovel"));

        addPassage(shedDoor);

        pushEnterRemark("shedinterior.enter.thought");
        addPickupableItem(shovel);
    }
}
