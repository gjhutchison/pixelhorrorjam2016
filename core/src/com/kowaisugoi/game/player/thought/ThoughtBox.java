package com.kowaisugoi.game.player.thought;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.TimeUtils;
import com.kowaisugoi.game.screens.PlayGame;

public class ThoughtBox implements Disposable {
    private long _createdTime;
    private long _duration;
    private long _opacity;
    private String _text;
    private BitmapFont _thoughtFont;
    private float THOUGHT_X = 0;
    private float THOUGHT_Y = 0;
    private float THOUGHT_WIDTH = PlayGame.GAME_WIDTH;
    private float THOUGHT_HEIGHT = 20;

    public ThoughtBox(String text) {
        _createdTime = TimeUtils.millis();
        _duration = 5000;

        // TODO: Move this somewhere universal
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/raleway/Raleway-Medium.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 36;

        _thoughtFont = generator.generateFont(parameter);
        _thoughtFont.getData().setScale(0.15f,0.15f);
        _thoughtFont.setColor(1f,1f,1f,1f);
        _text = text;
    }

    public void update(float delta) {
        // TODO: Update opacity based on delta
    }

    public void draw(SpriteBatch batch) {
        //TODO: Text position will require arithmetic
        float x_pos = (THOUGHT_X+THOUGHT_WIDTH)/2;
        float y_pos = (THOUGHT_Y+THOUGHT_HEIGHT)/2;

        x_pos -= _text.length()*2;
        y_pos += 5;

        _thoughtFont.draw(batch, _text, x_pos, y_pos);
    }

    public void draw(ShapeRenderer renderer) {
        renderer.setColor(0.05f, 0.05f, 0.05f, 0.6f);
        renderer.rect(THOUGHT_X, THOUGHT_Y, THOUGHT_WIDTH, THOUGHT_HEIGHT);
    }

    @Override
    public void dispose() { }
}
