package com.kowaisugoi.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.kowaisugoi.game.screens.MainMenu;

public class MainGame extends Game {
    public static String TITLE = "Cozy";
    public static String VERSION = "0.0.1";

    @Override
    public void create() {

        Gdx.app.log(TITLE, VERSION);
        Gdx.app.log(TITLE, "create()");
        setScreen(new MainMenu());
    }

    @Override
    public void render() {
        super.render();
    }
}
