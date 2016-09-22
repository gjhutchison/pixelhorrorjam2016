package com.kowaisugoi.game.control.flags;

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
        _flags.get(id).setState(value);
    }

    public Flag getFlag(FlagId id) {
        return _flags.get(id);
    }
}
