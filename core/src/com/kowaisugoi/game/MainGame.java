package com.kowaisugoi.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.kowaisugoi.game.audio.AudioManager;
import com.kowaisugoi.game.screens.SplashScreen;

public class MainGame extends Game {
    public static String TITLE = "Cozy";
    public static String VERSION = "1.0.0";

    @Override
    public void create() {

        Gdx.app.log(TITLE, VERSION);
        Gdx.app.log(TITLE, "create()");
        Gdx.graphics.setTitle("Cozy");
        AudioManager.initSounds();
        setScreen(new SplashScreen());
    }

    @Override
    public void render() {
        super.render();
    }
}
