package com.kowaisugoi.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kowaisugoi.game.player.Player;
import com.kowaisugoi.game.player.inventory.PlayerInventory;
import com.kowaisugoi.game.rooms.RoomId;
import com.kowaisugoi.game.rooms.RoomManager;
import com.kowaisugoi.game.system.GlobalKeyHandler;

import static com.kowaisugoi.game.player.Player.InteractionMode.*;

public class PlayGame implements Screen, InputProcessor {
    // 640x360
    public static final float GAME_WIDTH = 160;
    public static final float GAME_HEIGHT = 90;

    private OrthographicCamera _camera;
    private Viewport _viewport;
    private SpriteBatch _batch;
    private ShapeRenderer _shapeRenderer;

    @Override
    public void show() {
        RoomManager manager = new RoomManager();
        PlayerInventory inventory = new PlayerInventory();
        _batch = new SpriteBatch();
        _shapeRenderer = new ShapeRenderer();
        _camera = new OrthographicCamera();
        _camera.translate(GAME_WIDTH / 2, GAME_HEIGHT / 2);
        _viewport = new StretchViewport(GAME_WIDTH, GAME_HEIGHT, _camera);

        Player.setInputProcessor(this);
        Player.registerRoomManager(manager);
        Player.registerPlayerInventory(inventory);
        Player.setCurrentRoom(RoomId.CAR);
        Player.setCursor(Player.CursorType.REGULAR);

        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {
        updateGame(delta);
        renderGame();
    }

    private void updateGame(float delta) {
        Vector3 position = screenToWorldPosition(Gdx.input.getX(), Gdx.input.getY(), _camera);

        Player.getCurrentRoom().update(delta);

        if (Player.getInteractionMode() == ITEM_INTERACTION) {
            Player.getInventory().moveSelectedItemSprite(position.x, position.y);
        }

        Player.getThought().update(delta);
        beautifyCursor(position.x, position.y);
    }

    private void renderGame() {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Draw room sprites
        _camera.update();
        _batch.setProjectionMatrix(_camera.combined);
        _batch.begin();
        Player.getCurrentRoom().draw(_batch);
        Player.getInventory().drawInventory(_batch);
        _batch.end();

        // Draw room shapes
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        _shapeRenderer.setProjectionMatrix(_camera.combined);
        _shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        Player.getCurrentRoom().draw(_shapeRenderer);
        _shapeRenderer.end();

        // Draw room FX
        Gdx.gl.glBlendFunc(GL20.GL_DST_COLOR, GL20.GL_ONE);
        _batch.begin();
        Player.getCurrentRoom().drawFx(_batch);
        _batch.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);

        // Draw inventory, if applicable
        _batch.begin();
        if (Player.getInteractionMode() == ITEM_INTERACTION) {
            Player.getInventory().drawSelectedItemSprite(_batch);
        }
        _batch.end();

        // Draw Thoughts
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        _shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        Player.getThought().draw(_shapeRenderer);
        _shapeRenderer.end();
        _batch.begin();
        Player.getThought().draw(_batch);
        _batch.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    @Override
    public void resize(int width, int height) {
        _viewport.update(width, height);
    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub

    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub

    }

    @Override
    public void hide() {
        // TODO Auto-generated method stub

    }

    @Override
    public void dispose() {
    }

    @Override
    public boolean keyDown(int keycode) {
        if (GlobalKeyHandler.keyUp(keycode)) {
            return true;
        }

        //TODO: Make the inventory accessable using the mouse instead with a button at the top of the screen
        if (keycode == Input.Keys.I) {
            Player.getInventory().toggleInventory();
            //TODO: Make sure player can interact with only the inventory instead of nothing

            if (Player.getInventory().isInventoryOpen()) {
                Player.setInteractionMode(INVENTORY);
            } else if (!Player.getInventory().isInventoryOpen() &&
                    Player.getInteractionMode() != ITEM_INTERACTION) {
                Player.setInteractionMode(NORMAL);
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
            Vector3 clickPosition = screenToWorldPosition(screenX, screenY, _camera);

            if (Player.getInteractionMode() == INVENTORY || Player.getInteractionMode() == ITEM_INTERACTION) {
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

    private Vector3 screenToWorldPosition(int screenX, int screenY, OrthographicCamera camera) {
        Vector3 vecCursorPos = new Vector3(screenX, screenY, 0);
        camera.unproject(vecCursorPos);
        return vecCursorPos;
    }

    private void handleMouseClick(float screenX, float screenY) {
        if (Player.getInteractionMode() == NORMAL) {
            if (Player.getInventory().getButtonBox().contains(screenX, screenY)) {
                Player.setInteractionMode(INVENTORY);
                Player.getInventory().toggleInventory();
                return;
            }
            Player.getCurrentRoom().click(screenX, screenY);
        }
    }

    private void beautifyCursor(float x, float y) {
        // Default cursor, in case nobody else wants to set it
        Player.setCursor(Player.CursorType.REGULAR);

        if (Player.getInventory().getButtonBox().contains(x, y)) {
            Player.setCursor(Player.CursorType.PICKUP);
            return;
        }

        if (Player.getInteractionMode() == NORMAL) {
            Player.getCurrentRoom().beautifyCursor(x, y);
        }
    }

    private void handleInventoryMouseClick(float screenX, float screenY) {
        if (Player.getInteractionMode() == INVENTORY) {
            if (Player.getInventory().getButtonBox().contains(screenX, screenY)) {
                Player.getInventory().toggleInventory();
                Player.setInteractionMode(NORMAL);
                return;
            }
            if (Player.getInventory().click(screenX, screenY)) {
                Player.setInteractionMode(ITEM_INTERACTION);
            }
        } else if (Player.getInteractionMode() == ITEM_INTERACTION) {
            Player.getCurrentRoom().click(screenX, screenY, Player.getInventory().getSelectedItemId());
            Player.setInteractionMode(NORMAL);
        }
    }
}
