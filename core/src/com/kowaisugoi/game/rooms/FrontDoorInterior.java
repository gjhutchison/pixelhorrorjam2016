package com.kowaisugoi.game.rooms;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.kowaisugoi.game.interactables.Interactable;
import com.kowaisugoi.game.rooms.passages.Passage;
import com.kowaisugoi.game.rooms.passages.StandardPassage;

import java.util.LinkedList;

public class FrontDoorInterior implements Room {
    private Sprite _roomSprite = new Sprite(new Texture("FrontDoorInterior.png"));
    private LinkedList<Interactable> _interactables = new LinkedList<Interactable>();
    private Passage _frontDoor = new StandardPassage(RoomId.FRONTYARD, new Rectangle(200, 200, 600, 600));
    private Passage _turnAround = new StandardPassage(RoomId.MAIN_HALL, new Rectangle(0, 0, 200, 1000));

    public FrontDoorInterior() {
        _interactables.push(_frontDoor);
    }

    @Override
    public Sprite getSprite() {
        return _roomSprite;
    }

    @Override
    public boolean click(float curX, float curY) {
        // This room doesn't need to react to its interactables, but we could catch the return values if we wanted
        for (Interactable inter : _interactables) {
            inter.click(curX, curY);
        }
        return true;
    }
}
