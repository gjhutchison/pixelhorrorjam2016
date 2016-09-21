package com.kowaisugoi.game.rooms;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.kowaisugoi.game.control.flags.FlagId;
import com.kowaisugoi.game.interactables.passages.DirectionalPassage;
import com.kowaisugoi.game.interactables.passages.Passage;
import com.kowaisugoi.game.interactables.scenic.Describable;
import com.kowaisugoi.game.interactables.scenic.FlagDescribable;
import com.kowaisugoi.game.interactables.scenic.GeneralDescribable;
import com.kowaisugoi.game.messages.Messages;
import com.kowaisugoi.game.system.GameUtil;

public class RoomBathroomPeek extends StandardRoom {

    private static final String ROOM_URL = "rooms/bathroom/uncle3.png";

    public RoomBathroomPeek() {
        super(new Sprite(new Texture(ROOM_URL)));

        Passage passageBack = new DirectionalPassage(RoomId.BATHROOM,
                RoomId.BEDROOM,
                new Rectangle(50, 0, 50, 15),
                GameUtil.Direction.DOWN);

        addPassage(passageBack);

        Describable uncle = new FlagDescribable(Messages.getText("bathroom.uncle.thought.1"),
                new Rectangle(68, 25, 27, 58), FlagId.FLAG_BODY_FOUND);

        addDescribable(uncle);
    }

    public void swapUncleText() {
        // TODO: Do this much more elegantly, or deactivate/update describables without removing them
        _describableList.clear();

        Describable uncle = new GeneralDescribable(Messages.getText("bathroom.uncle.thought.2"),
                new Rectangle(68, 25, 27, 58));

        addDescribable(uncle);
    }
}
