package com.kowaisugoi.game.rooms;

import java.util.HashMap;
import java.util.Map;

/**
 * Room mappings are final and global.
 */
public final class RoomManager {
    private HashMap<RoomId, Room> _roomMap = new HashMap<RoomId, Room>();

    public RoomManager() {
        _roomMap.put(RoomId.MAIN_HALL, new RoomMainHall());
        _roomMap.put(RoomId.HALLWAY, new RoomHallway());
        _roomMap.put(RoomId.FRONTYARD, new RoomFrontYard());
        _roomMap.put(RoomId.CAR, new RoomCar());
        _roomMap.put(RoomId.ROAD, new RoomForestPath());
        _roomMap.put(RoomId.BEDROOM, new RoomBedroom());
        _roomMap.put(RoomId.BATHROOM, new RoomBathroomPeek());
        _roomMap.put(RoomId.KITCHEN, new RoomKitchen());
        _roomMap.put(RoomId.CRAWLSPACE, new RoomCrawlspace());
        _roomMap.put(RoomId.PARKING_AREA, new RoomCarPark());
        _roomMap.put(RoomId.SHED, new RoomShed());
        _roomMap.put(RoomId.SHED_INTERIOR, new RoomShedInterior());
    }

    public Room getRoomFromId(RoomId roomId) {
        return _roomMap.get(roomId);
    }

    public void flagUpdate(){
        for (RoomId id : _roomMap.keySet()){
            _roomMap.get(id).flagUpdate();
        }
    }
    // Clean up all rooms, theoretically on closing the game
    public void cleanUp() {
        for (Room room : _roomMap.values()) {
            room.dispose();
        }
    }
}
