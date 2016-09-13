package com.kowaisugoi.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kowaisugoi.game.audio.AudioManager;
import com.kowaisugoi.game.audio.MusicId;
import com.kowaisugoi.game.system.GlobalKeyHandler;

public class SplashScreen implements Screen, InputProcessor {

    private Sprite _splash;
    private SpriteBatch _batch;

    private Viewport _viewport;
    private OrthographicCamera _camera;
    private ShapeRenderer _shapeRenderer;

    private static final float SPLASH_WIDTH = 640;
    private static final float SPLASH_HEIGHT = 360;

    private final static String SPLASH_URL = "splash.png";

    private float _fade;
    private float _delay;

    private boolean _fadeIn;
    private boolean _fadeOut;

    private static final float FADE_SPEED = 1.0f;

    @Override
    public void show() {
        _camera = new OrthographicCamera(SPLASH_WIDTH, SPLASH_HEIGHT);
        _camera.translate(SPLASH_WIDTH / 2, SPLASH_HEIGHT / 2);
        _viewport = new StretchViewport(SPLASH_WIDTH, SPLASH_HEIGHT, _camera);

        _batch = new SpriteBatch();
        _shapeRenderer = new ShapeRenderer();

        _splash = new Sprite(new Texture("splash.png"));
        _splash.setSize(SPLASH_HEIGHT, SPLASH_HEIGHT);
        _splash.setPosition(140, 0);

        _fade = 1.0f;

        _delay = 1.25f;

        _fadeIn = true;
        _fadeOut = false;

        AudioManager.playMusic(MusicId.GENERAL_MUSIC);

        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {

        fadeLogic(delta);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        _camera.update();
        _batch.setProjectionMatrix(_camera.combined);
        _shapeRenderer.setProjectionMatrix(_camera.combined);

        _shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        _shapeRenderer.setColor(0, 0, 0, 1);
        _shapeRenderer.rect(0, 0, SPLASH_WIDTH, SPLASH_HEIGHT);
        _shapeRenderer.end();

        _batch.begin();
        _splash.draw(_batch);
        _batch.end();

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        _shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        _shapeRenderer.setColor(0, 0, 0, _fade);
        if (_fade > 0) {
            _shapeRenderer.rect(0, 0, SPLASH_WIDTH, SPLASH_HEIGHT);
        }
        _shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    private void fadeLogic(float delta) {
        if (_fadeIn) {
            _fade -= FADE_SPEED * delta;
            if (_fade < 0) {
                _fadeIn = false;
                _fadeOut = true;
                _fade = -1;
            }
        } else if (_fadeOut && _delay > 0) {
            _delay -= delta;
        } else if (_fadeOut) {
            _fade += FADE_SPEED * delta;
            if (_fade > 1.5f) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());
                return;
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        _viewport.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        _batch.dispose();
        _shapeRenderer.dispose();
        _splash.getTexture().dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (!GlobalKeyHandler.keyUp(keycode)) {
            ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());
        }
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
