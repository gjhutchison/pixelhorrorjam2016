package com.kowaisugoi.game.graphics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import net.dermetfan.gdx.physics.box2d.PositionController;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class FireAnimation {
    private static final int FIRE_HEIGHT = 10;
    private static final int MAX_SPEED = 6;

    private float _originX;
    private float _originY;

    private List<Flame> _flameList;

    private static final Color[] colours = {Color.RED, Color.FIREBRICK, Color.YELLOW};

    public FireAnimation(float originX, float originY, int intensity) {
        _originX = originX;
        _originY = originY;
        _flameList = createFlameList(intensity);

        for (int i = 0; i < 20; i++) {
            if (i % 2 != 1) {
                update(0.05f);
            }
        }
    }

    public void draw(ShapeRenderer renderer) {
        for (Flame flame : _flameList) {
            Color colour = colours[flame.getColour()];
            renderer.setColor(colour.r, colour.g, colour.b, flame.getOpacity());
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
            float speed = random.nextInt(MAX_SPEED) + 2;
            float sway = (random.nextInt(2) == 1 ? random.nextFloat() + random.nextInt(2) : -random.nextFloat() - random.nextInt(2));
            int size = random.nextInt(2) + 1;
            int colour = random.nextInt(3);
            flameList.add(new Flame(position, speed, sway, size, colour));
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
        private float _sway;
        private float _animationOpacity;
        private float _animationLength;
        private float _currentLength;
        private int _size;
        private int _colour;

        Flame(Vector2 position, float speed, float sway, int size, int colour) {
            _position = position;
            _speed = speed;
            _sway = sway;
            _size = size;
            _animationLength = FIRE_HEIGHT / _speed;
            _animationOpacity = 1;
            _colour = colour;
        }

        Vector2 getPosition() {
            return _position;
        }

        int getSize() {
            return _size;
        }

        int getColour() {
            return _colour;
        }

        float getOpacity() {
            return _animationOpacity;
        }

        void animate(float delta) {
            _position.add(delta * _sway, _speed * delta);
            _currentLength += delta;

            _animationOpacity = 1 - (_currentLength / _animationLength);

            if (_position.y > _originY + FIRE_HEIGHT + _size || _animationOpacity < 0.05f) {
                _position.set(_originX, _originY);
                _animationOpacity = 1;
                _currentLength = 0;
            }
        }
    }
}
