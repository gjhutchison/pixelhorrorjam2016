package com.kowaisugoi.game.graphics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.kowaisugoi.game.interactables.passages.Passage;
import com.kowaisugoi.game.screens.PlayGame;
import com.kowaisugoi.game.system.GameUtil.Direction;

public class SlideTransition implements Transition {
    private boolean _animating = false;
    private Passage _passage;
    private Direction _direction;

    private Sprite _sprite;

    private float _xPosition, _yPosition;

    public static final float DEFAULT_SPEED = 540f;

    private float _speedHorizontal = DEFAULT_SPEED;
    private float _speedVertical = _speedHorizontal * (PlayGame.GAME_HEIGHT / PlayGame.GAME_WIDTH);

    private float _halfTimeHorizontal = (PlayGame.GAME_WIDTH / _speedHorizontal);
    private float _halfTimeVertical = (PlayGame.GAME_HEIGHT / _speedVertical);

    private float _animationLength;
    private boolean _alreadySwapped;
    private boolean _alreadyComplete;

    private static final float MAX_X = PlayGame.GAME_WIDTH;
    private static final float MIN_X = -PlayGame.GAME_WIDTH;

    private static final float MAX_Y = PlayGame.GAME_HEIGHT;
    private static final float MIN_Y = -PlayGame.GAME_HEIGHT;

    public SlideTransition(Passage p, Direction direction) {
        _passage = p;
        _animationLength = 0;
        _xPosition = 0;
        _yPosition = 0;
        _animating = false;
        _direction = direction;
        _alreadySwapped = false;
        _alreadyComplete = false;

        switch (_direction) {
            case UP:
                _yPosition = MIN_Y;
                _xPosition = 0;
                break;
            case DOWN:
                _yPosition = MAX_Y;
                _xPosition = 0;
                break;
            case LEFT:
                _xPosition = MIN_X;
                _yPosition = 0;
                break;
            case RIGHT:
                _xPosition = MAX_X;
                _yPosition = 0;
                break;
            default:
                _animating = false;
                //_direction = 0;
        }
    }

    public SlideTransition(Passage p, Direction direction, Sprite image) {
        this(p, direction);
        _sprite = image;
    }

    public SlideTransition(Passage p, Direction direction, float speed) {
        this(p, direction);
        initSpeeds(speed);
    }

    public SlideTransition(Passage p, Direction direction, Sprite image, float speed) {
        this(p, direction);
        _sprite = image;
        initSpeeds(speed);
    }

    private void initSpeeds(float horizontalSpeed) {
        _speedHorizontal = horizontalSpeed;
        _speedVertical = _speedHorizontal * (PlayGame.GAME_HEIGHT / PlayGame.GAME_WIDTH);

        _halfTimeHorizontal = (PlayGame.GAME_WIDTH / _speedHorizontal);
        _halfTimeVertical = (PlayGame.GAME_HEIGHT / _speedVertical);
    }

    private void swapRoom() {
        if (!_alreadySwapped) {
            if (_passage != null) {
                _alreadySwapped = true;
                _passage.roomTransition();
            }
        }
    }

    private void animateTransition(float delta) {
        _animationLength += delta;
        switch (_direction) {
            case UP:
                _yPosition += _speedVertical * delta;
                break;
            case DOWN:
                _yPosition -= _speedVertical * delta;
                break;
            case LEFT:
                _xPosition += _speedHorizontal * delta;
                break;
            case RIGHT:
                _xPosition -= _speedHorizontal * delta;
                break;
            default:
                return;
        }
    }

    @Override
    public void update(float delta) {
        if (_animating) {
            this.animateTransition(delta);
            if (_direction == Direction.UP || _direction == Direction.DOWN) {
                if (_animationLength > _halfTimeVertical) {
                    swapRoom();
                }
            } else if (_direction == Direction.LEFT || _direction == Direction.RIGHT) {
                if (_animationLength > _halfTimeHorizontal) {
                    swapRoom();
                }
            }

            if ((_xPosition > MAX_X || _xPosition < MIN_X) ||
                    (_yPosition > MAX_Y || _yPosition < MIN_Y)) {
                _animating = false;

                if (!_alreadyComplete) {
                    _alreadyComplete = true;
                    _passage.transitionComplete();
                }
            }
        }
    }

    @Override
    public void play() {
        _animating = true;
    }

    @Override
    public void draw(SpriteBatch batch) {
        if (_sprite != null && _animating) {
            _sprite.setPosition(_xPosition, _yPosition);
            _sprite.draw(batch);
        }
    }

    @Override
    public void draw(ShapeRenderer renderer) {
        if (renderer.isDrawing() && _animating) {
            renderer.setColor(Color.BLACK);
            renderer.rect(_xPosition, _yPosition, PlayGame.GAME_WIDTH, PlayGame.GAME_HEIGHT);
        }
    }
}
