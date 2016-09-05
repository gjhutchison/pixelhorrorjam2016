package com.kowaisugoi.game.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.Pixmap;
import com.kowaisugoi.game.player.inventory.PlayerInventory;
import com.kowaisugoi.game.player.thought.ThoughtBox;
import com.kowaisugoi.game.rooms.Room;
import com.kowaisugoi.game.rooms.RoomId;
import com.kowaisugoi.game.rooms.RoomManager;

/**
 * While this could be passed to each Room as the player enters it,
 * it might be better to embrace the global singleton anti-pattern
 * in order to save a great deal of superfluous parameter passing
 * <p>
 * Created by Owner on 8/29/2016.
 */
public final class Player {
    private Player() {
    } // don't construct this class

    private static RoomId _currentRoom;
    private static RoomManager _manager;

    private static InputProcessor _input;
    private static PlayerInventory _inventory;

    private static boolean _isInInventory = false;
    private static boolean _isDebug = false;
    private static boolean _canInteract = true;

    private static ThoughtBox _thought = null;

    public enum CursorType {
        REGULAR,
        UP_ARROW,
        DOWN_ARROW,
        LEFT_ARROW,
        RIGHT_ARROW,
        INVISIBLE
    }

    private static Cursor _downArrow = Gdx.graphics.newCursor(new Pixmap(Gdx.files.internal("cursors/down_arrow_cursor.png")), 16, 30);
    private static Cursor _upArrow = Gdx.graphics.newCursor(new Pixmap(Gdx.files.internal("cursors/up_arrow_cursor.png")), 16, 2);
    private static Cursor _leftArrow = Gdx.graphics.newCursor(new Pixmap(Gdx.files.internal("cursors/left_arrow_cursor.png")), 2, 16);
    private static Cursor _rightArrow = Gdx.graphics.newCursor(new Pixmap(Gdx.files.internal("cursors/right_arrow_cursor.png")), 30, 16);
    private static Cursor _regularCursor = Gdx.graphics.newCursor(new Pixmap(Gdx.files.internal("cursors/regular_cursor.png")), 0, 0);
    private static Cursor _invisCursor = Gdx.graphics.newCursor(new Pixmap(Gdx.files.internal("cursors/invisible_cursor.png")), 0, 0);

    public static void setInputProcessor(InputProcessor input) {
        _input = input;
    }

    public static ThoughtBox getThought() {
        return _thought;
    }

    public static Room getCurrentRoom() {
        return _manager.getRoomMap().get(_currentRoom);
    }

    public static RoomId getCurrentRoomId() {
        return _currentRoom;
    }

    public static void registerRoomManager(RoomManager manager) {
        _manager = manager;
    }

    public static void registerPlayerInventory(PlayerInventory inventory) {
        _inventory = inventory;
    }

    public static void setCurrentRoom(RoomId current) {
        RoomManager.getRoomFromId(current).enter();
        _currentRoom = current;
        _input.mouseMoved(Gdx.input.getX(), Gdx.input.getY());
    }

    public static boolean getDebug() {
        return _isDebug;
    }

    public static void setDebug(boolean debug) {
        _isDebug = debug;
    }

    public static void setCanInteract(boolean canInteract) {
        _canInteract = canInteract;

        if (!canInteract) {
            Gdx.graphics.setCursor(_invisCursor);
        } else {
            Gdx.graphics.setCursor(_regularCursor);
        }
    }

    public static boolean getCanInteract() {
        return _canInteract;
    }

    public static PlayerInventory getInventory() {
        return _inventory;
    }

    public static void setCursor(CursorType flavour) {
        switch (flavour) {
            case INVISIBLE:
                Gdx.graphics.setCursor(_invisCursor);
                break;
            case REGULAR:
                Gdx.graphics.setCursor(_regularCursor);
                break;
            case LEFT_ARROW:
                Gdx.graphics.setCursor(_leftArrow);
                break;
            case RIGHT_ARROW:
                Gdx.graphics.setCursor(_rightArrow);
                break;
            case UP_ARROW:
                Gdx.graphics.setCursor(_upArrow);
                break;
            case DOWN_ARROW:
                Gdx.graphics.setCursor(_downArrow);
                break;
        }
    }

    public static void think(String text) {
        ThoughtBox tb = new ThoughtBox(text);
        _thought = tb;
    }
}
