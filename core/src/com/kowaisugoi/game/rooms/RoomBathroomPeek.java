package com.kowaisugoi.game.rooms;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.kowaisugoi.game.control.flags.FlagId;
import com.kowaisugoi.game.control.flags.FlagManager;
import com.kowaisugoi.game.interactables.passages.DirectionalPassage;
import com.kowaisugoi.game.interactables.passages.Passage;
import com.kowaisugoi.game.interactables.scenic.Describable;
import com.kowaisugoi.game.interactables.scenic.GeneralDescribable;
import com.kowaisugoi.game.messages.Messages;
import com.kowaisugoi.game.screens.PlayGame;
import com.kowaisugoi.game.system.GameUtil;

public class RoomBathroomPeek extends StandardRoom {

    private static final String ROOM_URL = "rooms/bathroom/uncle_cabinet_boards.png";
    private Describable _uncle;

    public RoomBathroomPeek() {
        super(new Sprite(new Texture(ROOM_URL)));

        Passage passageBack = new DirectionalPassage(RoomId.BATHROOM,
                RoomId.BEDROOM,
                new Rectangle(50, 0, 50, 15),
                GameUtil.Direction.DOWN);

        addPassage(passageBack);

        _uncle = new GeneralDescribable(Messages.getText("bathroom.uncle.thought.1"),
                new Rectangle(68, 25, 27, 58));

        addDescribable(_uncle);
    }

    @Override
    public boolean click(float curX, float curY) {
        if (_uncle.click(curX, curY)) {
            if (!PlayGame.getFlagManager().getFlag(FlagId.FLAG_BODY_FOUND).getState()) {
                PlayGame.getFlagManager().setFlag(FlagId.FLAG_BODY_FOUND, true);
                _uncle.setDescription(Messages.getText("bathroom.uncle.thought.2"));
                PlayGame.getRoomManager().getRoomFromId(RoomId.CAR).pushEnterRemark("car.enter.wannaleave");
            }

            return true;
        }

        return super.click(curX, curY);
    }
}
