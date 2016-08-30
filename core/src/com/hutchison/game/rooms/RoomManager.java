package com.hutchison.game.rooms;

import java.util.Map;

public class RoomManager {
    private Map<RoomId, Room> _roomMap;

    public Map<RoomId, Room> getRoomMap() {
        return _roomMap;
    }
}
