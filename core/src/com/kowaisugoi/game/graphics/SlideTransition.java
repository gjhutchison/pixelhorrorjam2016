package com.kowaisugoi.game.graphics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.kowaisugoi.game.player.Player;
import com.kowaisugoi.game.screens.PlayGame;

/**
 * Created by Owner on 9/1/2016.
 */
public class SlideTransition {
    private boolean _animating = false;
    private boolean _roomChange = false;
    private boolean _roomChanged = false;
    private boolean _animationComplete = false;

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

    public SlideTransition() {
        _animationLength = 0;
        _xPosition = 0;
        _yPosition = 0;
    }

    //TODO: base direction off of cursor location
    public void startAnimation(Direction direction) {
        _direction = direction;
        _animating = true;
        _roomChanged = false;
        _roomChange = false;
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

        if (_direction == Direction.UP || _direction == Direction.DOWN) {
            if (_animationLength > HALF_TIME_VERTICAL) {
                _roomChange = true;
            }
        } else if (_direction == Direction.LEFT || _direction == Direction.RIGHT) {
            if (_animationLength > HALF_TIME_HORIZONTAL) {
                _roomChange = true;
            }
        }

        if (_xPosition > MAX_X || _xPosition < MIN_X) {
            _animating = false;
            _roomChange = false;
            _animationComplete = true;
        } else if (_yPosition > MAX_Y || _yPosition < MIN_Y) {
            _animating = false;
            _roomChange = false;
            _animationComplete = true;
        }
    }

    public void update(float delta) {
        if (this.isAnimating()) {
            this.animateTransition(delta);
            if (this.isRoomChange() && !this.hasRoomChanged()) {
                //Player.setCurrentRoom(_transferTarget);
                this.changedRoom();
            }
        }
        /*if (!_disablePolling) {
            pollNotifications();
        }*/

        /*if (_transition.isAnimationComplete()) {
            _disablePolling = false;
            _transferingRoom = false;
        }*/
    }

    public boolean isRoomChange() {
        return _roomChange;
    }

    public boolean hasRoomChanged() {
        return _roomChanged;
    }

    public boolean isAnimating() {
        return _animating;
    }

    public boolean isAnimationComplete() {
        return _animationComplete;
    }

    public void changedRoom() {
        _roomChanged = true;
    }

    public void draw(ShapeRenderer renderer) {
        if (renderer.isDrawing() && _animating) {
            renderer.setColor(Color.BLACK);
            renderer.rect(_xPosition, _yPosition, PlayGame.GAME_WIDTH, PlayGame.GAME_HEIGHT);
        }
    }
}
