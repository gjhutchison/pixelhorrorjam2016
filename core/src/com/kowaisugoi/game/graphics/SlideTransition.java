package com.kowaisugoi.game.graphics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.kowaisugoi.game.interactables.passages.Passage;
import com.kowaisugoi.game.player.Player;
import com.kowaisugoi.game.rooms.RoomId;
import com.kowaisugoi.game.rooms.RoomManager;
import com.kowaisugoi.game.screens.World;
import com.kowaisugoi.game.system.GameUtil.Direction;

public class SlideTransition implements Transition {
    private boolean _animating = false;
    private boolean _roomChanged = false;
    private boolean _animationComplete = false;
    private Passage _passage;
    private RoomId _destination;
    private RoomId _source;

    private Direction _direction;

    private float _xPosition;
    private float _yPosition;

    private static final float SPEED_HORIZONTAL = 540f;
    private static final float SPEED_VERTICAL = SPEED_HORIZONTAL * (World.GAME_HEIGHT / World.GAME_WIDTH);

    private static final float HALF_TIME_HORIZONTAL = (World.GAME_WIDTH / SPEED_HORIZONTAL);
    private static final float HALF_TIME_VERTICAL = (World.GAME_HEIGHT / SPEED_VERTICAL);

    private float _animationLength;

    private static final float MAX_X = World.GAME_WIDTH;
    private static final float MIN_X = -World.GAME_WIDTH;

    private static final float MAX_Y = World.GAME_HEIGHT;
    private static final float MIN_Y = -World.GAME_HEIGHT;

    public SlideTransition(Passage p, RoomId source, RoomId destination) {
        _source = source;
        _destination = destination;
        _passage = p;
        _animationLength = 0;
        _xPosition = 0;
        _yPosition = 0;
    }

    void swapRoom() {
        if (_passage != null) {
            _passage.roomTransition(this);
        }
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

    }

    @Override
    public void update(float delta) {
        if (this.isAnimating()) {
            this.animateTransition(delta);
            if (_direction == Direction.UP || _direction == Direction.DOWN) {
                if (_animationLength > HALF_TIME_VERTICAL) {
                    swapRoom();
                }
            } else if (_direction == Direction.LEFT || _direction == Direction.RIGHT) {
                if (_animationLength > HALF_TIME_HORIZONTAL) {
                    swapRoom();
                }
            }

            if (_xPosition > MAX_X || _xPosition < MIN_X) {
                _animating = false;
                _animationComplete = true;
            } else if (_yPosition > MAX_Y || _yPosition < MIN_Y) {
                _animating = false;
                _animationComplete = true;
            }
        }
    }

    public boolean isAnimating() {
        return _animating;
    }

    public boolean isAnimationComplete() {
        return _animationComplete;
    }

    @Override
    public void draw(SpriteBatch batch) {
    }

    @Override
    public void draw(ShapeRenderer renderer) {
        if (renderer.isDrawing() && _animating) {
            renderer.setColor(Color.BLACK);
            renderer.rect(_xPosition, _yPosition, World.GAME_WIDTH, World.GAME_HEIGHT);
        }
    }
}
