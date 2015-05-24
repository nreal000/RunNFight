package com.noreal.runnfight.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.noreal.runnfight.utils.Constants;


public class Player {
	Vector2 playerPos;
	int playerSize = 10;
	float MOVEMENT_SPEED = 50;
	float size = 10;
	Rectangle playerRect = new Rectangle();
	
	public Player(float x, float y){
		playerPos = new Vector2(x, y);
	}
	public Player(){
		playerPos = new Vector2(0, 0);
	}
	
	public void setPPos(float x,float y){
		playerPos = new Vector2(x, y);
	}
	
	public void update(){
	Vector2 direction = new Vector2(0, 0);
	float delta = Gdx.graphics.getDeltaTime() * MOVEMENT_SPEED;
	
	if (Gdx.input.isKeyPressed(Keys.DPAD_RIGHT)) {
		direction.x = 1 * delta; 
		}
	if (Gdx.input.isKeyPressed(Keys.DPAD_LEFT)) {
		direction.x = -1 * delta; 
		}
	if (Gdx.input.isKeyPressed(Keys.DPAD_UP)) {
		direction.y = 1 * delta; 
		}
	if (Gdx.input.isKeyPressed(Keys.DPAD_DOWN)) {
		direction.y = -1 * delta; 
		}
	if (direction.x != 0 || direction.y != 0) {
		playerPos.add(direction);
		if (playerPos.x < 0) playerPos.x = 0; 
		if (playerPos.x > Constants.APP_WIDTH - playerSize) playerPos.x = Constants.APP_WIDTH - playerSize;
		if (playerPos.y < 0) playerPos.y = 0;
		if (playerPos.y > Constants.APP_HEIGHT - playerSize) playerPos.y = Constants.APP_HEIGHT - playerSize;
		}
		playerRect.setPosition(playerPos);
		playerRect.setSize(size);
	}
}
