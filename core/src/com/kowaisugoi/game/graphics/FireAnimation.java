package com.kowaisugoi.game.graphics;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class FireAnimation {
    private static final int FIRE_HEIGHT = 10;
    private static final int MAX_SPEED = 6;

    private float _originX;
    private float _originY;

    private List<Flame> _flameList;

    public FireAnimation(float originX, float originY, int intensity) {
        _originX = originX;
        _originY = originY;
        _flameList = createFlameList(intensity);
    }

    public void draw(ShapeRenderer renderer) {
        renderer.setColor(1, 0, 0, 1);
        for (Flame flame : _flameList) {
            Vector2 position = flame.getPosition();
            int size = flame.getSize();
            renderer.rect(position.x, position.y, size, size);
        }
    }

    private List<Flame> createFlameList(int intensity) {
        List<Flame> flameList = new LinkedList<Flame>();
        Random random = new Random();
        for (int i = 0; i < intensity; i++) {
            Vector2 position = new Vector2(_originX, _originY);
            float speed = random.nextInt(MAX_SPEED) + 1;
            int sway = random.nextInt(3) - 1;
            int size = random.nextInt(3) + 2;
            flameList.add(new Flame(position, speed, sway, size));
        }
        return flameList;
    }

    public void update(float delta) {
        for (Flame flame : _flameList) {
            flame.animate(delta);
        }
    }

    private class Flame {
        private Vector2 _position;
        private float _speed;
        private int _sway;
        private int _size;

        Flame(Vector2 position, float speed, int sway, int size) {
            _position = position;
            _speed = speed;
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
            _position.add(delta * _sway, _speed * delta);
            if (_position.y < -_size) {
                _position.y += FIRE_HEIGHT + _size;
            }
        }
    }
}
