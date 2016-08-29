package com.hutchison.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.hutchison.game.screens.PlayGame;

public class MainGame extends Game {
	public static String TITLE = "DAE CABIN IN WOODS?";
	public static String VERSION = "0.0.1";

	@Override
	public void create() {

		Gdx.app.log(TITLE, VERSION);
		Gdx.app.log(TITLE, "create()");

		setScreen(new PlayGame());
	}

	@Override
	public void render() {
		super.render();
	}
}
