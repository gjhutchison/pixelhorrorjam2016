package com.kowaisugoi.game.interactables.passages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.kowaisugoi.game.player.Player;
import com.kowaisugoi.game.rooms.RoomId;

/**
 * Created by ecrothers on 2016-08-30.
 */
public class StandardPassage implements Passage {
    private Rectangle _interactionBox;
    private RoomId _destination;

    public StandardPassage(RoomId dest, Rectangle interactionBox) {
        _destination = dest;
        _interactionBox = interactionBox;
    }

    @Override
    public RoomId getDestination() {
        return _destination;
    }

    @Override
    public Rectangle getInteractionBox() {
        return _interactionBox;
    }

    @Override
    public void draw(SpriteBatch batch) {
    }

    @Override
    public void draw(ShapeRenderer renderer) {
        /*Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glDisable(GL20.GL_BLEND);*/

        if (Player.getDebug()) {
            ShapeRenderer shapeRenderer = new ShapeRenderer();
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(0, 255, 0, 150);
            shapeRenderer.rect(_interactionBox.x, _interactionBox.y, _interactionBox.width, _interactionBox.height);
            shapeRenderer.end();
        }
    }

    @Override
    public boolean click(float curX, float curY) {
        if (_interactionBox.contains(curX, curY)) {
            Player.setCurrentRoom(_destination);
            return true;
        }
        return false;
    }
}
