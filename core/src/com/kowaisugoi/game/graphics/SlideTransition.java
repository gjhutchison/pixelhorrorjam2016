package com.kowaisugoi.game.graphics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.kowaisugoi.game.interactables.passages.Passage;
import com.kowaisugoi.game.player.Player;
import com.kowaisugoi.game.rooms.RoomId;
import com.kowaisugoi.game.rooms.RoomManager;
import com.kowaisugoi.game.screens.PlayGame;

/**
 * Created by Owner on 9/1/2016.
 */
public class SlideTransition {
    private boolean _animating = false;
    private boolean _roomChanged = false;
    private boolean _roomDoneChanged = false;
    private boolean _animationComplete = false;
    private Passage _passage;
    private RoomId _destination;

    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    private Direction _direction;

    private float _xPosition;
    private float _yPosition;

    private static final float SPEED_HORIZONTAL = 540f;
    private static final float SPEED_VERTICAL = SPEED_HORIZONTAL * (PlayGame.GAME_HEIGHT / PlayGame.GAME_WIDTH);

    private static final float HALF_TIME_HORIZONTAL = (PlayGame.GAME_WIDTH / SPEED_HORIZONTAL);
    private static final float HALF_TIME_VERTICAL = (PlayGame.GAME_HEIGHT / SPEED_VERTICAL);

    private float _animationLength;

    private static final float MAX_X = PlayGame.GAME_WIDTH;
    private static final float MIN_X = -PlayGame.GAME_WIDTH;

    private static final float MAX_Y = PlayGame.GAME_HEIGHT;
    private static final float MIN_Y = -PlayGame.GAME_HEIGHT;

    public SlideTransition(Passage p, RoomId destination) {
        _destination = destination;
        _passage = p;
        _animationLength = 0;
        _xPosition = 0;
        _yPosition = 0;
    }

    void swapRoom() {
        if (_passage != null) {
            _passage.roomTransition();
        }

        // Reset animation
        _animationLength = 0;
        _xPosition = 0;
        _yPosition = 0;
        _animating = false;
        _roomChanged = false;
        _roomDoneChanged = false;
        _animationComplete = false;
    }

    public void startAnimation(Direction direction) {
        _direction = direction;
        _animating = true;
        _roomChanged = false;
        _animationComplete = false;
        _animationLength = 0;
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

    public void animateTransition(float delta) {
        _animationLength += delta;
        switch (_direction) {
            case UP:
                _yPosition += SPEED_VERTICAL * delta;
                break;
            case DOWN:
                _yPosition -= SPEED_VERTICAL * delta;
                break;
            case LEFT:
                _xPosition += SPEED_HORIZONTAL * delta;
                break;
            case RIGHT:
                _xPosition -= SPEED_HORIZONTAL * delta;
                break;
            default:
                return;
        }

        /*if (_direction == Direction.UP || _direction == Direction.DOWN) {
            if (_animationLength > HALF_TIME_VERTICAL) {
                _roomChanged = true;
            }
        } else if (_direction == Direction.LEFT || _direction == Direction.RIGHT) {
            if (_animationLength > HALF_TIME_HORIZONTAL) {
                _roomChanged = true;
            }
        }*/

        if (_xPosition > MAX_X || _xPosition < MIN_X) {
            _animating = false;
            _animationComplete = true;
            _roomChanged = true;
        } else if (_yPosition > MAX_Y || _yPosition < MIN_Y) {
            _animating = false;
            _animationComplete = true;
            _roomChanged = true;
        }
    }

    public void update(float delta) {
        if (this.isAnimating()) {
            this.animateTransition(delta);
            if (_roomChanged && !_roomDoneChanged) {
                swapRoom();
                _roomDoneChanged = true;
            }
        }
    }

    public boolean isAnimating() {
        return _animating;
    }

    public boolean isAnimationComplete() {
        return _animationComplete;
    }

    public void draw(SpriteBatch batch) {
        if (_direction == Direction.UP || _direction == Direction.DOWN) {
            if (_animationLength > HALF_TIME_VERTICAL) {
                RoomManager.getRoomMap().get(this._destination).draw(batch);
            }
        } else if (_direction == Direction.LEFT || _direction == Direction.RIGHT) {
            if (_animationLength > HALF_TIME_HORIZONTAL) {
                RoomManager.getRoomMap().get(this._destination).draw(batch);
            }
        }
    }

    public void draw(ShapeRenderer renderer) {
        if (renderer.isDrawing() && _animating) {
            renderer.setColor(Color.BLACK);
            renderer.rect(_xPosition, _yPosition, PlayGame.GAME_WIDTH, PlayGame.GAME_HEIGHT);
        }
    }
}
