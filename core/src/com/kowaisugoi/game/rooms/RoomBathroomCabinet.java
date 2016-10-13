package com.kowaisugoi.game.rooms;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.kowaisugoi.game.interactables.objects.ItemId;
import com.kowaisugoi.game.interactables.objects.PickupableItem;
import com.kowaisugoi.game.interactables.passages.DirectionalPassage;
import com.kowaisugoi.game.interactables.passages.Passage;
import com.kowaisugoi.game.interactables.scenic.Describable;
import com.kowaisugoi.game.interactables.scenic.GeneralDescribable;
import com.kowaisugoi.game.interactables.scenic.ItemInteractableScenic;
import com.kowaisugoi.game.messages.Messages;
import com.kowaisugoi.game.system.GameUtil;

public class RoomBathroomCabinet extends StandardRoom {

    private static final String ROOM_URL = "rooms/bathroomCabinet/bathroomCabinet.png";

    public RoomBathroomCabinet() {
        super(new Sprite(new Texture(ROOM_URL)));

        //TODO: Have dead uncle swing by on transition screen?
        Passage passageBack = new DirectionalPassage(RoomId.BATHROOM_CABINET,
                RoomId.BATHROOM,
                new Rectangle(2, 3, 23, 82),
                GameUtil.Direction.LEFT);

        Describable pills = new GeneralDescribable(Messages.getText("bathroomcabinet.pills.thought"),
                new Rectangle(48, 2, 15, 18));

        PickupableItem stickWrapped = new PickupableItem(new Sprite(new Texture("items/stickicon_wrapped.png")),
                new Rectangle(0, 0, 0, 0),
                ItemId.STICK_RAGS);

        ItemInteractableScenic bandages = new ItemInteractableScenic(Messages.getText("bathroomcabinet.bandage.thought"),
                Messages.getText("bathroomcabinet.interaction.stick.bandage"),
                new Rectangle(88, 1, 33, 19),
                ItemId.STICK, stickWrapped);

        addPassage(passageBack);
        addDescribable(pills);
        addDescribable(bandages);
    }
}
