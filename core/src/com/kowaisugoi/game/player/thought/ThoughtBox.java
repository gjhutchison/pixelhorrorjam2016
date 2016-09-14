package com.kowaisugoi.game.player.thought;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Disposable;
import com.kowaisugoi.game.screens.PlayGame;

public class ThoughtBox implements Disposable {
    private float _holdDuration = 0;
    private float _displayedFor;
    private float _fadeInDuration;
    private float _fadeOutDuration;
    private float _opacity;
    private GlyphLayout _layout;
    private String _text;
    private BitmapFont _thoughtFont;
    private float THOUGHT_X = 0;
    private float THOUGHT_Y = 0;
    private float THOUGHT_WIDTH = PlayGame.GAME_WIDTH;
    private float THOUGHT_HEIGHT = 10;
    private Color _color = new Color(1f, 1f, 1f, 1f);
    private float OPACITY_MAX = 0.8f;

    public ThoughtBox(String text, float holdDuration) {
        _holdDuration = holdDuration;
        _fadeOutDuration = 1.1f;
        _fadeInDuration = 0.5f;

        // TODO: Move this somewhere universal
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/raleway/Raleway-Medium.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 32;

        _thoughtFont = generator.generateFont(parameter);
        _thoughtFont.getData().setScale(0.12f,0.12f);
        _text = text;
        _layout = new GlyphLayout();
    }

    public void update(float delta) {
        _displayedFor += delta;

        if (_displayedFor < _fadeInDuration) {
            _opacity = ((_displayedFor)/_fadeInDuration) * OPACITY_MAX;
        } else if (_displayedFor < _fadeInDuration + _holdDuration) {
            _opacity = OPACITY_MAX;
        } else {
            _opacity =  (1.0f-(_displayedFor-_fadeInDuration-_holdDuration)/_fadeOutDuration) * OPACITY_MAX;
        }

        // Just in case
        if (_opacity < 0) {
            _opacity = 0;
        } else if (_opacity > OPACITY_MAX) {
            _opacity = OPACITY_MAX;
        }
    }

    public void draw(SpriteBatch batch) {
        float x_pos = (THOUGHT_X+THOUGHT_WIDTH)/2;
        float y_pos = (THOUGHT_Y+THOUGHT_HEIGHT)/2;

        _color.set(_color.r, _color.g, _color.b, _opacity);
        _thoughtFont.setColor(_color);
        _layout.setText(_thoughtFont,_text);
        x_pos -= _layout.width/2;
        y_pos += _layout.height/2;

        _thoughtFont.draw(batch, _layout, x_pos, y_pos);
    }

    public void draw(ShapeRenderer renderer) {
        renderer.setColor(0.05f, 0.05f, 0.05f, _opacity);
        renderer.rect(THOUGHT_X, THOUGHT_Y, THOUGHT_WIDTH, THOUGHT_HEIGHT);
    }

    @Override
    public void dispose() { }
}
