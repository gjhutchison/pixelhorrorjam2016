package com.kowaisugoi.game.control.flags;

import com.kowaisugoi.game.screens.PlayGame;

import java.util.HashMap;
import java.util.Map;

/**
 * Flag mappings are final and global.
 */
public final class FlagManager {
    private Map<FlagId, Flag> _flags = new HashMap<FlagId, Flag>();

    public FlagManager() {
        // Initialize all flags (to false)
        for (FlagId id : FlagId.values()) {
            _flags.put(id, new Flag(id));
        }
    }

    public void setFlag(FlagId id, boolean value) {
        if (!_flags.containsKey(id)) {
            return;
        }

        _flags.get(id).setState(value);
        PlayGame.getRoomManager().flagUpdate();
    }

    public void toggleFlag(FlagId id) {
        if (!_flags.containsKey(id)) {
            return;
        }

        _flags.get(id).toggleState();
        PlayGame.getRoomManager().flagUpdate();
    }

    public Flag getFlag(FlagId id) {
        return _flags.get(id);
    }
}
