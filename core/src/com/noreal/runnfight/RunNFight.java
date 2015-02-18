package com.noreal.runnfight;

import com.badlogic.gdx.Game;
import com.noreal.runnfight.screens.GameScreen;
import com.noreal.runnfight.screens.SpriteScreen;


public class RunNFight extends Game {
	GameScreen gameScreen;
	SpriteScreen spriteScreen;
	@Override
	public void create () {
		gameScreen = new GameScreen();
		spriteScreen = new SpriteScreen();
		setScreen(gameScreen);
	}
}