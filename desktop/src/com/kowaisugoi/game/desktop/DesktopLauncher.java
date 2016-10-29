package com.kowaisugoi.game.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.kowaisugoi.game.MainGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.foregroundFPS = 60;
		config.vSyncEnabled = true;

		config.width = 800;
		config.height = 450;
		config.addIcon("icons/large.png", Files.FileType.Internal);
		config.addIcon("icons/med.png", Files.FileType.Internal);
		config.addIcon("icons/small.png", Files.FileType.Internal);

		new LwjglApplication(new MainGame(), config);
	}
}
