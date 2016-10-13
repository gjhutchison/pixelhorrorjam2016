package com.kowaisugoi.game.rooms;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.kowaisugoi.game.interactables.objects.ItemId;
import com.kowaisugoi.game.interactables.passages.BlockedPassage;
import com.kowaisugoi.game.interactables.passages.DirectionalPassage;
import com.kowaisugoi.game.interactables.passages.Passage;
import com.kowaisugoi.game.interactables.scenic.Describable;
import com.kowaisugoi.game.interactables.scenic.GeneralDescribable;
import com.kowaisugoi.game.messages.Messages;
import com.kowaisugoi.game.screens.PlayGame;
import com.kowaisugoi.game.system.GameUtil;

import java.util.LinkedList;
import java.util.List;

import static com.kowaisugoi.game.control.flags.FlagId.FLAG_BOARDS_REMOVED;

public class RoomBedroom extends StandardRoom {

    private static final String ROOM_URL = "rooms/bedroom/bedroom.png";
    private static final String ROOM_URL2 = "rooms/bedroom/bedroom_brokenboards.png";

    private final Sprite _roomSprite2 = new Sprite(new Texture(ROOM_URL2));

    private List<Describable> _describableList1;
    private List<Passage> _passageList1;

    public RoomBedroom() {
        super(new Sprite(new Texture(ROOM_URL)));

        _describableList1 = new LinkedList<Describable>();
        _passageList1 = new LinkedList<Passage>();

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
                new Rectangle(103, 11, 32, 25));
        Describable artDescription = new GeneralDescribable(
                Messages.getText("bedroom.art.description"),
                new Rectangle(110, 42, 20, 23));
        Describable bedDescription = new GeneralDescribable(
                Messages.getText("bedroom.bed.description"),
                new Rectangle(9, 16, 46, 21));


        BlockedPassage boards_1 = new BlockedPassage(RoomId.PARKING_AREA,
                RoomId.BATHROOM,
                new Rectangle(98, 18, 4, 41),
                GameUtil.Direction.UP,
                ItemId.HAMMER,
                Messages.getText("bedroom.boards.interact.locked"),
                Messages.getText("bedroom.boards.interact.unlocked"),
                null);
        boards_1.setUnlockedToggleFlag(FLAG_BOARDS_REMOVED);

        BlockedPassage boards_2 = new BlockedPassage(RoomId.BEDROOM,
                RoomId.BATHROOM,
                new Rectangle(65, 18, 5, 41),
                GameUtil.Direction.UP,
                ItemId.HAMMER,
                Messages.getText("bedroom.boards.interact.locked"),
                Messages.getText("bedroom.boards.interact.unlocked"),
                null);
        boards_2.setUnlockedToggleFlag(FLAG_BOARDS_REMOVED);

        addPassage(boards_1);
        addPassage(boards_2);

        addPassage(passageHall);
        addPassage(passageBathroom);

        addDescribable(tableDescription);
        addDescribable(artDescription);
        addDescribable(bedDescription);

        _describableList1.add(tableDescription);
        _describableList1.add(artDescription);
        _describableList1.add(bedDescription);

        _passageList1.add(passageBathroom);
        _passageList1.add(passageHall);
    }

    @Override
    public void flagUpdate() {
        if (PlayGame.getFlagManager().getFlag(FLAG_BOARDS_REMOVED).getState()) {
            setSprite(_roomSprite2);
            setDescriptionList(_describableList1);
            setPassageList(_passageList1);
        }
    }
}
