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

    private static final String ROOM_URL = "rooms/bedroom/sleeptight_gross_draft_2.png";

    public RoomBedroom() {
        super(new Sprite(new Texture(ROOM_URL)));

        Passage passageHall = new DirectionalPassage(RoomId.BEDROOM,
                RoomId.HALLWAY,
                new Rectangle(50, 0, 50, 15),
                GameUtil.Direction.DOWN);

        Passage passageBathroom = new DirectionalPassage(RoomId.BEDROOM,
                RoomId.BATHROOM,
                new Rectangle(60, 10, 30, 40),
                GameUtil.Direction.UP);

        Describable tableDescription = new GeneralDescribable(
                Messages.getText("bedroom.table.description"),
                new Rectangle(23, 7, 25, 22));
        Describable artDescription = new GeneralDescribable(
                Messages.getText("bedroom.art.description"),
                new Rectangle(28, 36, 20, 20));
        Describable bedDescription = new GeneralDescribable(
                Messages.getText("bedroom.bed.description"),
                new Rectangle(100, 0, 60, 40));

        addPassage(passageHall);
        addPassage(passageBathroom);

        addDescribable(tableDescription);
        addDescribable(artDescription);
        addDescribable(bedDescription);
    }
}
