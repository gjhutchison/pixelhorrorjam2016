package com.kowaisugoi.game.rooms;

import java.util.HashMap;
import java.util.Map;

/**
 * Room mappings are final and global.
 */
public final class RoomManager {
    private HashMap<RoomId, Room> _roomMap = new HashMap<RoomId, Room>();

    public RoomManager() {
        _roomMap.put(RoomId.MAIN_HALL, new MainHall());
        _roomMap.put(RoomId.FRONT_DOOR_INTERIOR, new FrontDoorInterior());
        _roomMap.put(RoomId.FRONTYARD, new FrontYard());
    }

    public Map<RoomId, Room> getRoomMap() {
        return _roomMap;
    }
}
