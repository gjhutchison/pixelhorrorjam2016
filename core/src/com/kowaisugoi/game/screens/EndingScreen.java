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
import com.kowaisugoi.game.audio.AudioManager;
import com.kowaisugoi.game.audio.SoundId;
import com.kowaisugoi.game.graphics.SlideTransition;
import com.kowaisugoi.game.graphics.SnowAnimation;
import com.kowaisugoi.game.system.GameUtil;

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

    private Sprite _carPark;
    private Sprite _carInterrior;
    private Sprite _carInterriorScare;
    private SnowAnimation _snowAnimation;

    private String _credits;

    private float _scareTimer = 0.0f;
    private static final float SCARE_TIME = 4;

    private float _engineTimer = 0.0f;
    private static final float ENGINE_START_TIME = 0.5f;

    private float _endTimer = 0.0f;
    private static final float END_TIME = 1.5f;

    private float _creditDelayTimer = 0.0f;
    private static final float CREDIT_DELAY_TIMER = 1.5f;

    private BitmapFont _creditsFont;

    private SlideTransition _slideTransition;

    private boolean _roomSwitch = false;
    private boolean _scarySwitch = false;
    private boolean _endOfCarAnimation = false;

    public EndingScreen(SnowAnimation animation) {
        _snowAnimation = animation;
    }

    @Override
    public void show() {
        _camera = new OrthographicCamera(ENDING_WIDTH, ENDING_HEIGHT);
        _camera.translate(ENDING_WIDTH / 2, ENDING_HEIGHT / 2);
        _viewport = new StretchViewport(ENDING_WIDTH, ENDING_HEIGHT, _camera);

        _batch = new SpriteBatch();
        _shapeRenderer = new ShapeRenderer();

        _carPark = new Sprite(new Texture("rooms/parking/damaged_car_night.png"));
        _carPark.setSize(ENDING_WIDTH, ENDING_HEIGHT);
        _carInterrior = new Sprite(new Texture("rooms/car_v1.png"));
        _carInterrior.setSize(ENDING_WIDTH, ENDING_HEIGHT);
        _carInterriorScare = new Sprite(new Texture("rooms/car_scary.png"));
        _carInterriorScare.setSize(ENDING_WIDTH, ENDING_HEIGHT);

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/raleway/Raleway-Medium.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 32;
        parameter.borderColor = Color.GRAY;
        parameter.borderWidth = 2;

        _creditsFont = generator.generateFont(parameter);
        _creditsFont.setColor(1, 1, 1, 0);
        _creditsFont.getData().setScale(0.12f);

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

        _slideTransition = new SlideTransition(null, GameUtil.Direction.UP);
        _slideTransition.play();
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
            if (_slideTransition.isSwapped() && !_scarySwitch) {
                _carInterrior.draw(_batch);
            } else if (!_scarySwitch) {
                _carPark.draw(_batch);
            } else {
                _carInterriorScare.draw(_batch);
            }

        } else {
            _creditsFont.draw(_batch, _credits, 3, 87);
        }
        _batch.end();

        if (!_endOfCarAnimation) {
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

            _shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            if (!_slideTransition.isSwapped()) {
                _snowAnimation.draw(_shapeRenderer);
            }
            _slideTransition.draw(_shapeRenderer);
            _shapeRenderer.end();
        }
    }

    private void updateAnimation(float delta) {
        if (!_roomSwitch) {
            if (!_slideTransition.isComplete()) {
                _slideTransition.update(delta);
                if (!_slideTransition.isSwapped()) {
                    _snowAnimation.updateSnow(delta);
                }

                if (_slideTransition.isComplete()) {
                    _roomSwitch = true;
                }
            }
        } else {
            if (_engineTimer < ENGINE_START_TIME) {
                _engineTimer += delta;

                if (_engineTimer > ENGINE_START_TIME) {
                    AudioManager.playSound(SoundId.ENGINE_START);
                }
            } else if (_scareTimer < SCARE_TIME) {
                _scareTimer += delta;

                if (_scareTimer > SCARE_TIME) {
                    _scarySwitch = true;
                    AudioManager.playSound(SoundId.SCARE);
                }
            } else if (_endTimer < END_TIME) {
                _endTimer += delta;

                if (_endTimer > END_TIME) {
                    _endOfCarAnimation = true;
                }
            } else if (_creditDelayTimer < CREDIT_DELAY_TIMER) {
                _creditDelayTimer += delta;
            } else {
                _creditsFont.setColor(1, 1, 1, _creditsFont.getColor().a + (delta * 0.15f));
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
