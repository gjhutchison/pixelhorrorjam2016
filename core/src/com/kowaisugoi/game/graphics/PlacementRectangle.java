package com.kowaisugoi.game.graphics;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.kowaisugoi.game.screens.PlayGame;

public class PlacementRectangle extends Rectangle {
    public void draw(ShapeRenderer renderer) {
        renderer.setColor(0.9f, 0.9f, 0.9f, 0.5f);
        renderer.rect(x, y, width, height);
    }

    public String getCoordString() {
        float xS = x;
        float yS = y;
        float wS = width;
        float hS = height;

        // Ensure rectangles are reported with lower-left origin
        if (wS < 0) {
            xS += wS;
            wS = -wS;
        }

        if (hS < 0) {
            yS += hS;
            hS = -hS;
        }

        String coordString = "(";
        coordString += Math.round(xS) + ",";
        coordString += Math.round(yS) + ",";
        coordString += Math.round(wS) + ",";
        coordString += Math.round(hS) + ")";
        return coordString;
    }
}
