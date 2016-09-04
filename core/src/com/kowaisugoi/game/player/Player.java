package com.kowaisugoi.game.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.Pixmap;
import com.kowaisugoi.game.rooms.Room;
import com.kowaisugoi.game.rooms.RoomId;
import com.kowaisugoi.game.rooms.RoomManager;

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
    private static RoomManager _manager;
    private static boolean _isInInventory = false;
    private static boolean _isDebug = true;
    private static boolean _canInteract = true;
    private static Cursor _handCursor = Gdx.graphics.newCursor(new Pixmap(Gdx.files.internal("cursors/hand_cursor.png")), 16, 0);
    private static Cursor _regularCursor = Gdx.graphics.newCursor(new Pixmap(Gdx.files.internal("cursors/regular_cursor.png")), 0, 0);

    public static Room getCurrentRoom() {
        return _manager.getRoomMap().get(_currentRoom);
    }

    public static RoomId getCurrentRoomId() {
        return _currentRoom;
    }

    public static void registerRoomManager(RoomManager manager) {
        _manager = manager;
    }

    public static void setCurrentRoom(RoomId current) {
        RoomManager.getRoomFromId(current).enter();
        _currentRoom = current;
    }

    public static boolean getDebug() {
        return _isDebug;
    }

    public static void setCanInteract(boolean canInteract) {
        _canInteract = canInteract;
    }

    public static boolean getCanInteract() {
        return _canInteract;
    }

    public static void setCursorInteract() {
        Gdx.graphics.setCursor(_handCursor);
    }

    public static void setCursorNormal() {
        Gdx.graphics.setCursor(_regularCursor);
    }
}
