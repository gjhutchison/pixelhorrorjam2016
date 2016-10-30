package com.kowaisugoi.game.rooms;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.kowaisugoi.game.audio.SoundId;
import com.kowaisugoi.game.control.flags.FlagId;
import com.kowaisugoi.game.interactables.objects.ItemId;
import com.kowaisugoi.game.interactables.objects.PickupableItem;
import com.kowaisugoi.game.interactables.passages.DirectionalPassage;
import com.kowaisugoi.game.interactables.passages.Passage;
import com.kowaisugoi.game.interactables.scenic.Describable;
import com.kowaisugoi.game.interactables.scenic.GeneralDescribable;
import com.kowaisugoi.game.interactables.scenic.ItemInteractableScenic;
import com.kowaisugoi.game.messages.Messages;
import com.kowaisugoi.game.screens.PlayGame;
import com.kowaisugoi.game.system.GameUtil;

import static com.kowaisugoi.game.control.flags.FlagId.FLAG_NIGHT_TIME;

public class RoomKitchen extends StandardRoom {

    private static final String ROOM_URL = "rooms/kitchen/kitchen.png";
    private static final String ROOM_URL2 = "rooms/kitchen/kitchen_night.png";

    private final Sprite _roomSprite1 = new Sprite(new Texture(ROOM_URL));
    private final Sprite _roomSprite2 = new Sprite(new Texture(ROOM_URL2));

    public RoomKitchen() {
        super(new Sprite(new Texture(ROOM_URL)));

        Passage passageHall = new DirectionalPassage(RoomId.BEDROOM,
                RoomId.HALLWAY,
                new Rectangle(50, 0, 50, 15),
                GameUtil.Direction.DOWN);

        passageHall.setSoundEffect(SoundId.FLOOR_STEP);
        addPassage(passageHall);

        Describable sink = new GeneralDescribable(Messages.getText("kitchen.sink.thought"),
                new Rectangle(103, 31, 25, 18));
        sink.setItemInteractionMessage(ItemId.GLASS, Messages.getText("kitchen.interaction.sink.glass"));
        sink.setItemInteractionMessage(ItemId.GLASS_WATER, Messages.getText("kitchen.interaction.sink.glasswater"));

        Describable toaster = new GeneralDescribable(Messages.getText("kitchen.toaster.thought"),
                new Rectangle(76, 34, 13, 14));
        toaster.setItemInteractionMessage(ItemId.GLASS_WATER, Messages.getText("kitchen.interaction.outlet.glasswater"));
        toaster.setItemInteractionMessage(ItemId.STICK, Messages.getText("kitchen.interaction.toaster.stick"));
        toaster.setItemInteractionMessage(ItemId.HAMMER, Messages.getText("kitchen.interaction.toaster.hammer"));

        Describable outlet = new GeneralDescribable(Messages.getText("kitchen.outlet.thought"),
                new Rectangle(80, 56, 4, 7));
        outlet.setItemInteractionMessage(ItemId.GLASS_WATER, Messages.getText("kitchen.interaction.outlet.glasswater"));

        Describable window = new GeneralDescribable(Messages.getText("kitchen.window.thought"),
                new Rectangle(95, 49, 41, 17));

        Describable fridge = new GeneralDescribable(Messages.getText("kitchen.fridge.thought"),
                new Rectangle(48, 22, 24, 49));

        addDescribable(sink);
        addDescribable(toaster);
        addDescribable(outlet);
        addDescribable(window);
        addDescribable(fridge);

        PickupableItem glassWater = new PickupableItem(new Sprite(new Texture("items/glass_water.png")),
                new Rectangle(0, 0, 0, 0),
                ItemId.GLASS_WATER);

        ItemInteractableScenic kettle = new ItemInteractableScenic(Messages.getText("kitchen.stove.thought"),
                Messages.getText("kitchen.interaction.stove.snowglass"),
                new Rectangle(26, 31, 15, 15),
                ItemId.GLASS_SNOW, glassWater);

        PickupableItem glass = new PickupableItem(new Sprite(new Texture("rooms/kitchen/glass.png")),
                new Sprite(new Texture("items/glass.png")),
                new Rectangle(91, 33, 6, 8), ItemId.GLASS) {
            @Override
            public boolean click(float curX, float curY) {
                if (getInteractionBox().contains(curX, curY)) {
                    if (!PlayGame.getFlagManager().getFlag(FlagId.FLAG_BODY_FOUND).getState()) {
                        PlayGame.getPlayer().think(Messages.getText("kitchen.glass.thought"));
                        return false;
                    } else {
                        return super.click(curX, curY);
                    }
                } else {
                    return false;
                }
            }
        };
        glass.setPickupText(Messages.getText("kitchen.pickup.glass"));

        addDescribable(kettle);

        addPickupableItem(glass);
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
