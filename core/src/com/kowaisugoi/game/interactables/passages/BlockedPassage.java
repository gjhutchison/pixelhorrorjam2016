package com.kowaisugoi.game.interactables.passages;

import com.badlogic.gdx.math.Rectangle;
import com.kowaisugoi.game.control.flags.Flag;
import com.kowaisugoi.game.interactables.objects.ItemId;
import com.kowaisugoi.game.rooms.RoomId;
import com.kowaisugoi.game.screens.PlayGame;
import com.kowaisugoi.game.system.GameUtil;

public class BlockedPassage extends DirectionalPassage {
    private String _lockedText = "";
    private boolean _unlocked = false;
    private ItemId _interactionItemId;

    public BlockedPassage(RoomId src,
                          RoomId dest,
                          Rectangle interactionBox,
                          GameUtil.Direction direction,
                          ItemId id,
                          String lockedText) {
        super(src, dest, interactionBox, direction);
        _lockedText = lockedText;
        _interactionItemId = id;
    }

    public BlockedPassage(RoomId src,
                          RoomId dest,
                          Rectangle interactionBox,
                          GameUtil.Direction direction,
                          Flag controlFlag) {

        super(src, dest, interactionBox, direction);
    }

    @Override
    public boolean click(float curX, float curY) {
        if (_interactionBox.contains(curX, curY)) {
            if (_unlocked) {
                return super.click(curX, curY);
            }
            PlayGame.getPlayer().think(_lockedText, 2.0f);
        }
        return false;
    }

    @Override
    public boolean isItemInteractable() {
        return true;
    }

    @Override
    public boolean itemIteract(ItemId id) {
        if (id == _interactionItemId) {
            _unlocked = true;
            return true;
        }
        return false;
    }
}
