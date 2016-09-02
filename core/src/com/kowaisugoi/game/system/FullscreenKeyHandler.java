package com.kowaisugoi.game.system;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

/**
 * Prevents code duplication, allows for easy modification of hotkey
 * Created by ecrothers on 2016-09-01.
 */
public final class FullscreenKeyHandler {
    static final int FULLSCREEN_KEY = Input.Keys.F4;

    public static boolean handleFullscreenKey(int keycode) {
        if (keycode == FULLSCREEN_KEY) {
            if (!Gdx.graphics.isFullscreen()) {
                Gdx.graphics.setDisplayMode(
                        Gdx.graphics.getDesktopDisplayMode().width,
                        Gdx.graphics.getDesktopDisplayMode().height,
                        true);
            } else {
                Gdx.graphics.setDisplayMode(
                        800,
                        450,
                        false);
            }
            return true;
        }
        return false;
    }
}
