package com.kowaisugoi.game.rooms;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.kowaisugoi.game.interactables.passages.DirectionalPassage;
import com.kowaisugoi.game.interactables.passages.Passage;
import com.kowaisugoi.game.interactables.scenic.Describable;
import com.kowaisugoi.game.interactables.scenic.GeneralDescribable;
import com.kowaisugoi.game.messages.Messages;
import com.kowaisugoi.game.system.GameUtil;

public class RoomBedroom extends StandardRoom {

    private static final String ROOM_URL = "rooms/bedroom/sleeptight3.png";

    public RoomBedroom() {
        super(new Sprite(new Texture(ROOM_URL)));

        Passage passageHall = new DirectionalPassage(RoomId.BEDROOM,
                RoomId.HALLWAY,
                new Rectangle(50, 0, 50, 15),
                GameUtil.Direction.DOWN);

        Passage passageBathroom = new DirectionalPassage(RoomId.BEDROOM,
                RoomId.BATHROOM,
                new Rectangle(70, 17, 27, 41),
                GameUtil.Direction.UP);

        Describable tableDescription = new GeneralDescribable(
                Messages.getText("bedroom.table.description"),
                new Rectangle(101, 8, 36, 31));
        Describable artDescription = new GeneralDescribable(
                Messages.getText("bedroom.art.description"),
                new Rectangle(110, 42, 20, 23));
        Describable bedDescription = new GeneralDescribable(
                Messages.getText("bedroom.bed.description"),
                new Rectangle(0, 2, 72, 49));

        addPassage(passageHall);
        addPassage(passageBathroom);

        addDescribable(tableDescription);
        addDescribable(artDescription);
        addDescribable(bedDescription);
    }
}
