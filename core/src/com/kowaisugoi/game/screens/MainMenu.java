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
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kowaisugoi.game.MainGame;
import com.kowaisugoi.game.graphics.SnowAnimation;
import com.kowaisugoi.game.system.GlobalKeyHandler;

import java.util.Random;

public class MainMenu implements Screen, InputProcessor {

    private SpriteBatch _batch;
    private ShapeRenderer _shapeRenderer;

    private OrthographicCamera _camera;
    private Viewport _viewport;

    private BitmapFont _titleFont;
    private BitmapFont _font;
    private BitmapFont _tinyfont;

    private Random _random;

    private SnowAnimation _snowAnimation;

    private static final float MAIN_MENU_WIDTH = 320;
    private static final float MAIN_MENU_HEIGHT = 180;

    private Sprite _background;

    private float _fade;
    private boolean _fadeIn;

    @Override
    public void show() {
        _camera = new OrthographicCamera(MAIN_MENU_WIDTH, MAIN_MENU_HEIGHT);
        _camera.translate(MAIN_MENU_WIDTH / 2, MAIN_MENU_HEIGHT / 2);
        _viewport = new StretchViewport(MAIN_MENU_WIDTH, MAIN_MENU_HEIGHT, _camera);

        _batch = new SpriteBatch();
        _shapeRenderer = new ShapeRenderer();

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/raleway/Raleway-Medium.ttf"));
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
        parameter.size = 12;
        _tinyfont = generator.generateFont(parameter);
        _tinyfont.setColor(Color.WHITE);

        _background = new Sprite(new Texture("MainMenuBackground.png"));
        _background.setSize(MAIN_MENU_WIDTH, MAIN_MENU_HEIGHT);
        _background.setPosition(0, 0);

        _snowAnimation = new SnowAnimation(50, 4, MAIN_MENU_WIDTH, MAIN_MENU_HEIGHT);

        _fade = 1.5f;
        _fadeIn = true;

        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {

        _snowAnimation.updateSnow(delta);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        _camera.update();
        _batch.setProjectionMatrix(_camera.combined);
        _shapeRenderer.setProjectionMatrix(_camera.combined);


        _batch.begin();
        _background.draw(_batch);
        _batch.end();

        _shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        _snowAnimation.draw(_shapeRenderer);
        _shapeRenderer.end();

        _batch.begin();
        _titleFont.draw(_batch, MainGame.TITLE, 5, MAIN_MENU_HEIGHT - 10);
        _font.draw(_batch, "Click To Begin", 180, 20);
        _tinyfont.draw(_batch, "F4 Toggle Fullscreen", 5, 15);
        _batch.end();


        if (_fadeIn) {

            _fade -= 1.0 * delta;

            if (_fade < 0) {
                _fadeIn = false;
            }

            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            _shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            _shapeRenderer.setColor(0, 0, 0, _fade);
            _shapeRenderer.rect(0, 0, MAIN_MENU_WIDTH, MAIN_MENU_HEIGHT);
            _shapeRenderer.end();
            Gdx.gl.glDisable(GL20.GL_BLEND);
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
        _titleFont.dispose();
        _font.dispose();
        _tinyfont.dispose();

        _shapeRenderer.dispose();
        _batch.dispose();

        _background.getTexture().dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (GlobalKeyHandler.keyUp(keycode)) {
            return true;
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

}
