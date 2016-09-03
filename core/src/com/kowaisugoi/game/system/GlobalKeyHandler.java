package com.kowaisugoi.game.system;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

/**
 * Prevents code duplication across MainMenu/PlayGame,
 * allows for easy modification of any global hotkeys
 * <p>
 * Created by ecrothers on 2016-09-01.
 */
public final class GlobalKeyHandler {
    static final int FULLSCREEN_KEY = Input.Keys.F4;
    static final int EXIT_KEY = Input.Keys.ESCAPE;

    public static boolean keyUp(int keycode) {
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

        if (keycode == EXIT_KEY) {
            Gdx.app.exit();
        }

        return false;
    }
}
