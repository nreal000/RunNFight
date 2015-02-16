package com.noreal.runnfight.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.noreal.runnfight.RunNFight;
import com.noreal.runnfight.utils.Constants;
public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "RunNFight";
	     config.width = Constants.APP_WIDTH;
	     config.height = Constants.APP_HEIGHT;
		new LwjglApplication(new RunNFight(), config);
	}
}
