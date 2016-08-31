package com.kowaisugoi.game.rooms.passages;

import com.badlogic.gdx.math.Rectangle;
import com.kowaisugoi.game.player.Player;
import com.kowaisugoi.game.rooms.RoomId;
import com.kowaisugoi.game.rooms.RoomManager;

/**
 * Created by ecrothers on 2016-08-30.
 */
public class StandardPassage implements Passage {
    private RoomManager _manager;
    private Rectangle _interactionBox;
    private RoomId _destination;

    public StandardPassage(RoomId dest, Rectangle interactionBox) {
        _destination = dest;
        _interactionBox = interactionBox;
    }

    @Override
    public RoomId getDestination() {
        return _destination;
    }

    @Override
    public Rectangle getInteractionBox() {
        return _interactionBox;
    }

    @Override
    public boolean click(float curX, float curY) {
        if (_interactionBox.contains(curX, curY)) {
            Player.setCurrentRoom(_destination);
            return true;
        }
        return false;
    }
}
