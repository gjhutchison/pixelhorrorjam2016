package com.kowaisugoi.game.rooms;

import java.util.HashMap;
import java.util.Map;

/**
 * Room mappings are final and global.
 */
public final class RoomManager {
    private HashMap<RoomId, Room> _roomMap = new HashMap<RoomId, Room>();

    private RoomManager() {
    }

    public Map<RoomId, Room> getRoomMap() {
        return _roomMap;
    }
}
