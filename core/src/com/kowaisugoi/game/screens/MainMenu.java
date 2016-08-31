package com.kowaisugoi.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kowaisugoi.game.MainGame;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainMenu implements Screen, InputProcessor {

    private SpriteBatch _batch;
    private ShapeRenderer _shapeRenderer;

    private OrthographicCamera _camera;
    private Viewport _viewport;

    private BitmapFont _titleFont;
    private BitmapFont _font;

    private Random _random;

    private List<Snowflake> _snowflakes;

    private static final float MAIN_MENU_WIDTH = 320;
    private static final float MAIN_MENU_HEIGHT = 180;

    private Sprite _background;

    @Override
    public void show() {
        _camera = new OrthographicCamera(MAIN_MENU_WIDTH, MAIN_MENU_HEIGHT);
        _camera.translate(MAIN_MENU_WIDTH / 2, MAIN_MENU_HEIGHT / 2);
        _viewport = new StretchViewport(MAIN_MENU_WIDTH, MAIN_MENU_HEIGHT, _camera);

        _batch = new SpriteBatch();
        _shapeRenderer = new ShapeRenderer();

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/Raleway-Medium.ttf"));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = 64;
        parameter.borderColor = Color.GRAY;
        parameter.borderWidth = 2;

        _titleFont = generator.generateFont(parameter);
        _titleFont.setColor(Color.WHITE);

        parameter.borderWidth = 0;
        parameter.size = 20;
        _font = generator.generateFont(parameter);
        _font.setColor(Color.WHITE);

        _background = new Sprite(new Texture("MainMenuBackground.png"));
        _background.setSize(MAIN_MENU_WIDTH, MAIN_MENU_HEIGHT);
        _background.setPosition(0, 0);

        _random = new Random();

        _snowflakes = snowSetup();

        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {

        animateSnow(_snowflakes, delta);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        _camera.update();
        _batch.setProjectionMatrix(_camera.combined);
        _shapeRenderer.setProjectionMatrix(_camera.combined);


        _batch.begin();
        _background.draw(_batch);
        _batch.end();

        _shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        _shapeRenderer.setColor(Color.WHITE);
        for (Snowflake flake : _snowflakes) {
            Vector2 position = flake.getPosition();
            int size = flake.getSize();
            _shapeRenderer.rect(position.x, position.y, size, size);
        }

        _shapeRenderer.end();

        _batch.begin();
        _titleFont.draw(_batch, MainGame.TITLE, 5, MAIN_MENU_HEIGHT - 10);
        _font.draw(_batch, "Click To Begin", 180, 20);

        _batch.end();
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
        _titleFont.dispose();
        _font.dispose();

        _shapeRenderer.dispose();
        _batch.dispose();

        _background.getTexture().dispose();
    }

    private List<Snowflake> snowSetup() {
        List<Snowflake> flakes = new ArrayList<Snowflake>();
        final int flakeNum = 50;

        for (int i = 0; i < flakeNum; i++) {
            flakes.add(generateFlake());
        }

        return flakes;
    }

    private Snowflake generateFlake() {
        _random.nextInt((int) MAIN_MENU_WIDTH);
        _random.nextInt((int) MAIN_MENU_HEIGHT);
        _random.nextInt(3);

        Snowflake flake = new Snowflake(new Vector2(_random.nextInt((int) MAIN_MENU_WIDTH),
                _random.nextInt((int) MAIN_MENU_HEIGHT)),
                _random.nextInt(3) + 3,
                (_random.nextInt(2) == 1 ? -1 : 1),
                _random.nextInt(3) + 1);

        return flake;
    }

    private void animateSnow(List<Snowflake> flakes, float delta) {
        for (Snowflake flake : flakes) {
            flake.animate(delta);
        }
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
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
        ((Game) Gdx.app.getApplicationListener()).setScreen(new PlayGame());
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

    private class Snowflake {
        private Vector2 _position;
        private float _fallSpeed;
        private int _sway;
        private int _size;

        Snowflake(Vector2 position, float fallSpeed, int sway, int size) {
            _position = position;
            _fallSpeed = -fallSpeed;
            _sway = sway;
            _size = size;
        }

        Vector2 getPosition() {
            return _position;
        }

        int getSize() {
            return _size;
        }

        void animate(float delta) {
            _position.add((float) Math.sin(System.currentTimeMillis() / 1000) * delta * _sway, _fallSpeed * delta);
            if (_position.x > MAIN_MENU_WIDTH + _size) {
                _position.x -= MAIN_MENU_WIDTH - _size;
            } else if (_position.x < _size) {
                _position.x += MAIN_MENU_WIDTH + _size;
            }

            if (_position.y < -_size) {
                _position.y += MAIN_MENU_HEIGHT + _size;
            }
        }
    }
}
