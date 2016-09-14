package com.kowaisugoi.game.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;
import com.kowaisugoi.game.player.inventory.PlayerInventory;
import com.kowaisugoi.game.player.thought.ThoughtBox;
import com.kowaisugoi.game.rooms.Room;
import com.kowaisugoi.game.rooms.RoomId;
import com.kowaisugoi.game.rooms.RoomManager;
import com.kowaisugoi.game.screens.PlayGame;
import com.kowaisugoi.game.system.GlobalKeyHandler;

import static com.kowaisugoi.game.player.Player.InteractionMode.*;

public final class Player implements Disposable, InputProcessor {
    private PlayGame _world;
    private RoomId _currentRoom;
    private RoomManager _manager;

    private PlayerInventory _inventory;

    private boolean _isInInventory = false;
    private boolean _inventoryInteract = false;
    private boolean _itemCombine = false;

    private CursorType _cursorFlavor = CursorType.REGULAR;
    private CursorType _currentCursorFlavor = null;

    private InteractionMode _interactionMode = InteractionMode.NORMAL;

    private Pixmap _downArrowTex, _upArrowTex, _leftArrowTex, _rightArrowTex,
            _regularCursorTex, _pickupCursorTex, _invisCursorTex;
    private Cursor _downArrow, _upArrow, _leftArrow, _rightArrow,
            _regularCursor, _pickupCursor, _invisCursor;

    public enum InteractionMode {
        NORMAL,
        INVENTORY,
        ITEM_INTERACTION,
        NONE
    }

    private ThoughtBox _thought = new ThoughtBox("", 0);

    public enum CursorType {
        REGULAR,
        UP_ARROW,
        DOWN_ARROW,
        LEFT_ARROW,
        RIGHT_ARROW,
        PICKUP,
        INVISIBLE
    }

    public Player(PlayGame w, RoomManager manager, PlayerInventory inv) {
        _world = w;
        _inventory = inv;
        _manager = manager;

        _downArrowTex = new Pixmap(Gdx.files.internal("cursors/down_arrow_cursor.png"));
        _upArrowTex = new Pixmap(Gdx.files.internal("cursors/up_arrow_cursor.png"));
        _leftArrowTex = new Pixmap(Gdx.files.internal("cursors/left_arrow_cursor.png"));
        _rightArrowTex = new Pixmap(Gdx.files.internal("cursors/right_arrow_cursor.png"));
        _regularCursorTex = new Pixmap(Gdx.files.internal("cursors/regular_cursor.png"));
        _pickupCursorTex = new Pixmap(Gdx.files.internal("cursors/pickup_cursor.png"));
        _invisCursorTex = new Pixmap(Gdx.files.internal("cursors/invisible_cursor.png"));

        _downArrow = Gdx.graphics.newCursor(_downArrowTex, 16, 30);
        _upArrow = Gdx.graphics.newCursor(_upArrowTex, 16, 2);
        _leftArrow = Gdx.graphics.newCursor(_leftArrowTex, 2, 16);
        _rightArrow = Gdx.graphics.newCursor(_rightArrowTex, 30, 16);
        _regularCursor = Gdx.graphics.newCursor(_regularCursorTex, 0, 0);
        _pickupCursor = Gdx.graphics.newCursor(_pickupCursorTex, 0, 0);
        _invisCursor = Gdx.graphics.newCursor(_invisCursorTex, 0, 0);
    }

    public void startGame(RoomId start) {
        enterRoom(start);
    }

    @Override
    public boolean keyDown(int keycode) {
        if (GlobalKeyHandler.keyUp(keycode)) {
            return true;
        }

        //TODO: Make the inventory accessable using the mouse instead with a button at the top of the screen
        if (keycode == Input.Keys.I) {
            getInventory().toggleInventory();
            //TODO: Make sure player can interact with only the inventory instead of nothing

            if (getInventory().isInventoryOpen()) {
                setInteractionMode(INVENTORY);
            } else if (!getInventory().isInventoryOpen() &&
                    getInteractionMode() != ITEM_INTERACTION) {
                setInteractionMode(InteractionMode.NORMAL);
            }
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (button == Input.Buttons.LEFT) {
            Vector3 clickPosition = _world.screenToWorldPosition(screenX, screenY);

            if (getInteractionMode() == INVENTORY ||
                    getInteractionMode() == ITEM_INTERACTION) {
                handleInventoryMouseClick(clickPosition.x, clickPosition.y);
            } else {
                handleMouseClick(clickPosition.x, clickPosition.y);
            }
        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        // TODO Auto-generated method stub
        return false;
    }

    private void handleMouseClick(float screenX, float screenY) {
        if (getInteractionMode() == NORMAL) {
            if (getInventory().getButtonBox().contains(screenX, screenY)) {
                setInteractionMode(INVENTORY);
                getInventory().toggleInventory();
                return;
            }
            getCurrentRoom().click(screenX, screenY);
        }
    }

    private void handleInventoryMouseClick(float screenX, float screenY) {
        if (getInteractionMode() == INVENTORY) {
            if (getInventory().getButtonBox().contains(screenX, screenY)) {
                getInventory().toggleInventory();
                setInteractionMode(NORMAL);
                return;
            }
            if (getInventory().click(screenX, screenY)) {
                setInteractionMode(ITEM_INTERACTION);
            }
        } else if (getInteractionMode() == ITEM_INTERACTION) {
            getCurrentRoom().click(screenX, screenY, getInventory().getSelectedItemId());
            setInteractionMode(NORMAL);
        }
    }

    public ThoughtBox getThought() {
        return _thought;
    }

    public Room getCurrentRoom() {
        return _manager.getRoomMap().get(_currentRoom);
    }

    public RoomId getCurrentRoomId() {
        return _currentRoom;
    }

    /**
     * Enter the next room
     * @param newRoom: The room to enter
     */
    public void enterRoom(RoomId newRoom) {
        RoomManager.getRoomFromId(newRoom).enter();
        _currentRoom = newRoom;
    }

    public void setInteractionMode(InteractionMode mode) {
        _interactionMode = mode;

        if (_interactionMode == InteractionMode.NONE) {
            setCursor(CursorType.INVISIBLE);
        } else {
            setCursor(CursorType.REGULAR);
        }
    }

    public InteractionMode getInteractionMode() {
        return _interactionMode;
    }


    public PlayerInventory getInventory() {
        return _inventory;
    }

    public void setCursor(CursorType flavour) {
        _cursorFlavor = flavour;
    }

    public void changeCursor() {
        // Don't call Gdx.graphics if the cursor hasn't actually changed
        if (_cursorFlavor == _currentCursorFlavor) {
            return;
        }

        switch (_cursorFlavor) {
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
            case PICKUP:
                Gdx.graphics.setCursor(_pickupCursor);
                break;
        }
        _currentCursorFlavor = _cursorFlavor;
    }

    public void think(String text, float holdDuration) {
        ThoughtBox tb = new ThoughtBox(text, holdDuration);
        _thought = tb;
    }

    @Override
    public void dispose() {
        _downArrowTex.dispose();
        _upArrowTex.dispose();
        _leftArrowTex.dispose();
        _rightArrowTex.dispose();
        _regularCursorTex.dispose();
        _pickupCursorTex.dispose();
        _invisCursorTex.dispose();
    }

    public void update(float delta) {
        getCurrentRoom().update(delta);
        getThought().update(delta);
    }

    public void updateCursor(Vector3 position) {
        // Default cursor, in case nobody else wants to set it
        setCursor(CursorType.REGULAR);

        if (getInteractionMode() == InteractionMode.NORMAL) {
            getCurrentRoom().beautifyCursor(position.x, position.y);
        }

        if (getInventory().getButtonBox().contains(position.x, position.y)) {
            setCursor(Player.CursorType.PICKUP);
            return;
        }

        if (getInteractionMode() == InteractionMode.NONE) {
            setCursor(CursorType.INVISIBLE);
        }

        changeCursor();

        if (getInteractionMode() == InteractionMode.ITEM_INTERACTION) {
            getInventory().moveSelectedItemSprite(position.x, position.y);
        }
    }
}
