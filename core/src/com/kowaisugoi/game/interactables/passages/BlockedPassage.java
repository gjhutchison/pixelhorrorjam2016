package com.kowaisugoi.game.interactables.passages;

import com.badlogic.gdx.math.Rectangle;
import com.kowaisugoi.game.audio.AudioManager;
import com.kowaisugoi.game.audio.SoundId;
import com.kowaisugoi.game.control.flags.Flag;
import com.kowaisugoi.game.interactables.objects.ItemId;
import com.kowaisugoi.game.rooms.RoomId;
import com.kowaisugoi.game.screens.PlayGame;
import com.kowaisugoi.game.system.GameUtil;

public class BlockedPassage extends DirectionalPassage {
    private String _lockedText = "";
    private boolean _unlocked = false;
    private ItemId _interactionItemId;

    private SoundId _lockedSoundId;

    public BlockedPassage(RoomId src,
                          RoomId dest,
                          Rectangle interactionBox,
                          GameUtil.Direction direction,
                          ItemId id,
                          String lockedText,
                          SoundId soundId) {
        super(src, dest, interactionBox, direction);
        _lockedText = lockedText;
        _interactionItemId = id;
        _lockedSoundId = soundId;
    }

    public BlockedPassage(RoomId src,
                          RoomId dest,
                          Rectangle interactionBox,
                          GameUtil.Direction direction,
                          Flag controlFlag) {

        super(src, dest, interactionBox, direction);
    }

    public void setLockedSoundId(SoundId id) {
        _lockedSoundId = id;
    }

    @Override
    public boolean click(float curX, float curY) {
        if (_interactionBox.contains(curX, curY)) {
            if (_unlocked) {
                return super.click(curX, curY);
            }
            AudioManager.playSound(_lockedSoundId);
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
