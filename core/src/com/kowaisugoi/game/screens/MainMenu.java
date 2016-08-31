package com.kowaisugoi.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class MainMenu implements Screen {

    private SpriteBatch _batch;

    private BitmapFont _titleFont;
    private BitmapFont _font;

    private final float _width = PlayGame.GAME_WIDTH;
    private final float _height = PlayGame.GAME_HEIGHT;

    @Override
    public void show() {
        _batch = new SpriteBatch();
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/Raleway-Medium.ttf"));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = 56;

        _titleFont = generator.generateFont(parameter);
        _titleFont.setColor(Color.WHITE);


        parameter.size = 20;
        _font = generator.generateFont(parameter);
        _font.setColor(Color.WHITE);
    }

    @Override
    public void render(float delta) {
    }

    @Override
    public void resize(int width, int height) {
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
}
