package com.kowaisugoi.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class EndingScreen implements Screen, InputProcessor {

    private SpriteBatch _batch;
    private ShapeRenderer _shapeRenderer;

    private OrthographicCamera _camera;
    private Viewport _viewport;

    private static final float ENDING_WIDTH = 160;
    private static final float ENDING_HEIGHT = 90;

    private Sprite _carSprite;
    private Sprite _scarySprite;
    private Sprite _roadSprite;

    private String _credits;

    private float _scareTimer = 0.0f;
    private static final float SCARE_TIME = 1.5f;

    private boolean _scarySwitch = false;
    private boolean _endOfCarAnimation = false;

    private BitmapFont _creditsFont;

    @Override
    public void show() {
        _camera = new OrthographicCamera(ENDING_WIDTH, ENDING_HEIGHT);
        _camera.translate(ENDING_WIDTH / 2, ENDING_HEIGHT / 2);
        _viewport = new StretchViewport(ENDING_WIDTH, ENDING_HEIGHT, _camera);

        _batch = new SpriteBatch();
        _shapeRenderer = new ShapeRenderer();

        _roadSprite = new Sprite(new Texture("endingCutScene/background.png"));
        _roadSprite.setPosition(-ENDING_WIDTH / 2, -ENDING_HEIGHT / 2);
        _scarySprite = new Sprite(new Texture("endingCutScene/background_scare.png"));
        _scarySprite.setPosition(-ENDING_WIDTH / 2, -ENDING_HEIGHT / 2);
        _carSprite = new Sprite(new Texture("endingCutScene/car.png"));

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/raleway/Raleway-Medium.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 32;
        parameter.borderColor = Color.GRAY;
        parameter.borderWidth = 2;

        _creditsFont = generator.generateFont(parameter);
        _creditsFont.setColor(1, 1, 1, 0);
        _creditsFont.getData().setScale(0.12f);

        //Load in the credits
        _credits = "";
        try {
            File creditFile = new File("credits.txt");
            BufferedReader in = new BufferedReader(new FileReader(creditFile));
            String line;
            while ((line = in.readLine()) != null) {
                _credits += line + "\n";
            }
            in.close();
        } catch (IOException e) {
            _credits = "";
            Gdx.app.log("ERROR", "Could not load credits");
        }

        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {
        updateAnimation(delta);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        _camera.update();
        _batch.setProjectionMatrix(_camera.combined);
        _shapeRenderer.setProjectionMatrix(_camera.combined);

        _batch.begin();
        if (!_endOfCarAnimation) {
            if (_scarySwitch) {
                _scarySprite.draw(_batch);
            } else {
                _roadSprite.draw(_batch);
            }
            _carSprite.draw(_batch);
        } else {
            _creditsFont.draw(_batch, _credits, 3, 87);
        }
        _batch.end();
    }

    private void updateAnimation(float delta) {
        float scaleAnimation = 0.15f * delta;
        float creditsFade = 0.20f * delta;

        if (_roadSprite.getScaleX() > 0.5f) {
            _roadSprite.scale(-scaleAnimation);
            _scarySprite.setScale(_roadSprite.getScaleX(), _roadSprite.getScaleY());
            _scareTimer += delta;

            if (_scareTimer >= SCARE_TIME) {
                _scarySwitch = true;
            }
        } else {
            _endOfCarAnimation = true;
            _creditsFont.setColor(1, 1, 1, _creditsFont.getColor().a + creditsFade);
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

    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.ESCAPE) {
            Gdx.app.exit();
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
        return false;
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
