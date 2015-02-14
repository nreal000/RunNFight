package com.noreal.runnfight;

import com.badlogic.gdx.Game;
import com.noreal.runnfight.screens.GameScreen;


public class RunNFight extends Game {

	@Override
	public void create () {
		setScreen(new GameScreen());
	}
}