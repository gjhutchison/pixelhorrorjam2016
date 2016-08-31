package com.kowaisugoi.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kowaisugoi.game.player.Player;
import com.kowaisugoi.game.rooms.RoomId;
import com.kowaisugoi.game.rooms.RoomManager;

public class PlayGame implements Screen, InputProcessor {

    private static final float MAX_FPS = 60.0f;
    private static final float UPDATE_INTERVAL = 1.0f / MAX_FPS;
    private static final float START_TIME = System.currentTimeMillis();
    // 640x360
    public static final float GAME_WIDTH = 160;
    public static final float GAME_HEIGHT = 90;

    private float _timeSinceLastUpdate;
    private int _numberOfFrames;
    private int _fps;

    private OrthographicCamera _camera;
    private Viewport _viewport;
    private SpriteBatch _batch;
    private RoomManager _manager;

    @Override
    public void show() {
        RoomManager manager = new RoomManager();
        Player.registerRoomManager(manager);
        Player.setCurrentRoom(RoomId.MAIN_HALL);
        _batch = new SpriteBatch();
        _camera = new OrthographicCamera();
        _camera.translate(GAME_WIDTH / 2, GAME_HEIGHT / 2);
        _viewport = new StretchViewport(GAME_WIDTH, GAME_HEIGHT, _camera);

        Gdx.input.setInputProcessor(this);

        _timeSinceLastUpdate = 0.0f;
    }

    @Override
    public void render(float delta) {
        if (shouldUpdate(delta)) {
            updateGame(delta);
            renderGame(delta);
        }
    }

    private void updateGame(float delta) {

    }

    private void renderGame(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        _camera.update();
        _batch.setProjectionMatrix(_camera.combined);
        _batch.begin();
        Player.getCurrentRoom().draw(_batch);
        _batch.end();
    }

    private boolean shouldUpdate(float delta) {
        _timeSinceLastUpdate += delta;
        if (_timeSinceLastUpdate >= UPDATE_INTERVAL) {
            updateFps(delta);
            resetUpdateTime();
            return true;
        }
        return false;
    }

    private void updateFps(float delta) {
        _numberOfFrames++;
        _fps = (int) (_numberOfFrames / (START_TIME - System.currentTimeMillis()));
    }

    private void resetUpdateTime() {
        if (_timeSinceLastUpdate >= 1) {
            _timeSinceLastUpdate = 0.0f;
        }
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
        // TODO Auto-generated method stub
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
            handleMouseClick(clickPosition.x, clickPosition.y);
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

    private void handleMouseClick(float curX, float curY) {
        Player.getCurrentRoom().click(curX, curY);
    }

    private Vector3 screenToWorldPosition(int screenX, int screenY, OrthographicCamera camera) {
        Vector3 vecCursorPos = new Vector3(screenX, screenY, 0);
        camera.unproject(vecCursorPos);
        return vecCursorPos;
    }

}
