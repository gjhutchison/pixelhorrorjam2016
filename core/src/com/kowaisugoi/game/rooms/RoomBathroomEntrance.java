package com.kowaisugoi.game.rooms;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.kowaisugoi.game.audio.SoundId;
import com.kowaisugoi.game.control.flags.FlagId;
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

public class RoomBathroomEntrance extends StandardRoom {

    private static final String ROOM_URL = "rooms/bathroom/bathroom_entrance_boards.png";
    private static final String ROOM_URL2 = "rooms/bathroom/bathroom_entrance.png";

    private final Sprite _roomSprite2 = new Sprite(new Texture(ROOM_URL2));

    private Describable _uncle;

    private List<Passage> _passageList2;

    public RoomBathroomEntrance() {
        super(new Sprite(new Texture(ROOM_URL)));

        _passageList2 = new LinkedList<Passage>();

        Passage passageBack = new DirectionalPassage(RoomId.BATHROOM,
                RoomId.BEDROOM,
                new Rectangle(50, 0, 50, 15),
                GameUtil.Direction.DOWN);

        Passage passageForward = new DirectionalPassage(RoomId.BATHROOM,
                RoomId.BATHROOM_CABINET,
                new Rectangle(100, 16, 22, 61),
                GameUtil.Direction.UP);

        passageBack.setSoundEffect(SoundId.CLICK);

        addPassage(passageBack);

        _uncle = new GeneralDescribable(Messages.getText("bathroom.uncle.thought.1"),
                new Rectangle(68, 25, 27, 58));

        addDescribable(_uncle);

        _passageList2.add(passageForward);
        _passageList2.add(passageBack);
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

    @Override
    public void flagUpdate() {
        if (PlayGame.getFlagManager().getFlag(FLAG_BOARDS_REMOVED).getState()) {
            setSprite(_roomSprite2);
            setPassageList(_passageList2);
        }
    }
}
