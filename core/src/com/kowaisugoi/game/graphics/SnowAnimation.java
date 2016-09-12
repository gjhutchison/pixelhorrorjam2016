package com.kowaisugoi.game.graphics;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static com.kowaisugoi.game.screens.World.GAME_HEIGHT;
import static com.kowaisugoi.game.screens.World.GAME_WIDTH;

public class SnowAnimation {

    private int _stormSize;
    private float _stormSpeed;
    private Random _random;

    private float _gameWidth;
    private float _gameHeight;

    private List<Snowflake> _snowflakes;

    public SnowAnimation(int size, float stormSpeed) {
        _stormSize = size;
        _stormSpeed = stormSpeed;

        _gameWidth = GAME_WIDTH;
        _gameHeight = GAME_HEIGHT;

        _random = new Random();

        _snowflakes = initializeSnowflakes();
    }

    public SnowAnimation(int size, float stormSpeed, float width, float height) {
        _stormSize = size;
        _stormSpeed = stormSpeed;

        _gameWidth = width;
        _gameHeight = height;

        _random = new Random();

        _snowflakes = initializeSnowflakes();
    }

    private List<Snowflake> initializeSnowflakes() {
        List<Snowflake> snowflakes = new LinkedList<Snowflake>();
        for (int i = 0; i < _stormSize; i++) {
            Vector2 position = new Vector2(_random.nextInt((int) _gameWidth), _random.nextInt((int) _gameHeight));
            float fallSpeed = _random.nextInt((int) _stormSpeed) + 1;
            int sway = (_random.nextInt(2) == 1 ? 1 : -1);
            int size = (_random.nextInt(3) + 1);

            snowflakes.add(new Snowflake(position, fallSpeed, sway, size));
        }

        return snowflakes;
    }

    public void draw(ShapeRenderer renderer) {
        renderer.setColor(0.8f, 0.8f, 0.8f, 0.75f);
        for (Snowflake snowflake : _snowflakes) {
            Vector2 position = snowflake.getPosition();
            int size = snowflake.getSize();
            renderer.rect(position.x, position.y, size, size);
        }
    }

    public void updateSnow(float delta) {
        for (Snowflake snowflake : _snowflakes) {
            snowflake.animate(delta);
        }
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
            if (_position.x > _gameWidth + _size) {
                _position.x -= _gameWidth - _size;
            } else if (_position.x < _size) {
                _position.x += _gameWidth + _size;
            }

            if (_position.y < -_size) {
                _position.y += _gameHeight + _size;
            }
        }
    }
}


