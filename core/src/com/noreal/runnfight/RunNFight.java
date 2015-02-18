package com.noreal.runnfight;

import com.badlogic.gdx.Game;
import com.noreal.runnfight.screens.GameScreen;
import com.noreal.runnfight.screens.SpriteScreen;


public class RunNFight extends Game {
	@Override
	public void create () {
		setScreen(new GameScreen());
	}
}