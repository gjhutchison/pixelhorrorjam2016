package com.kowaisugoi.game.player;

import com.kowaisugoi.game.rooms.RoomId;

/**
 * While this could be passed to each Room as the player enters it,
 * it might be better to embrace the global singleton anti-pattern
 * in order to save a great deal of superfluous parameter passing
 *
 * Created by Owner on 8/29/2016.
 */
public final class Player {
    private Player(){} // don't construct this class

    private static RoomId _currentRoom;

    public static RoomId getCurrentRoom() {
        return _currentRoom;
    }

    public static void setCurrentRoom(RoomId current) {
        _currentRoom = current;
    }
}
