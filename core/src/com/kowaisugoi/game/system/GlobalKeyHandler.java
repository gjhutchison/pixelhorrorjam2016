package com.kowaisugoi.game.system;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.kowaisugoi.game.screens.PlayGame;

/**
 * Prevents code duplication across MainMenu/PlayGame,
 * allows for easy modification of any global hotkeys
 */
public final class GlobalKeyHandler {
    static final int FULLSCREEN_KEY = Input.Keys.F4;
    static final int DEBUG_KEY = Input.Keys.F8;
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

        if (keycode == DEBUG_KEY) {
            if (PlayGame.getDebug()) {
                PlayGame.setDebug(false);
            } else {
                PlayGame.setDebug(true);
            }
        }

        return false;
    }
}
