package com.noreal.runnfight.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class ShootBulletTank implements Screen {

	private static final int TANK_SIZE = 32;
	private static final int BULLET_SIZE = 5;
	private static final int MOVEMENT_SPEED = 50;
	
	// Used for drawing our items private 
	SpriteBatch spriteBatch;
	
	// Tank position 
	private Vector2 tank_pos;
	// bullet position 
	private Vector2 bullet_pos;
	// Tank direction 
	private Vector2 objectDirection;
	// Bullet direction 
	private Vector2 bulletDirection;
	
	private Pixmap pixmap;
	int screenWidth, screenHeight;
	public ShootBulletTank(){
		spriteBatch = new SpriteBatch();
		screenWidth = Gdx.graphics.getWidth();
		screenHeight = Gdx.graphics.getHeight();
		tank_pos = new Vector2(screenWidth / 2 - TANK_SIZE / 2, screenHeight / 2 - TANK_SIZE / 2);
		bullet_pos = null;
		objectDirection = new Vector2(1, 0); // Pointing right 
		bulletDirection = new Vector2(1, 0);
		pixmap = new Pixmap(32, 32, Pixmap.Format.RGB565);
	}
	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float delta) {
		update();
		GL20 gl = Gdx.graphics.getGL20();
		gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		gl.glClearColor(1, 1, 1, 0);
		gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		spriteBatch.begin();
		if (bullet_pos != null) { // Draw bullet 
			pixmap.drawRectangle(0, 0, BULLET_SIZE, BULLET_SIZE); spriteBatch.setColor(Color.BLUE);
			spriteBatch.draw(new Texture(pixmap), bullet_pos.x, bullet_pos.y, BULLET_SIZE, BULLET_SIZE);
			} // Draw object 
		pixmap.drawRectangle(0, 0, TANK_SIZE, TANK_SIZE);
		spriteBatch.setColor(Color.GREEN);
		spriteBatch.draw(new Texture(pixmap), tank_pos.x, tank_pos.y, TANK_SIZE, TANK_SIZE);
		spriteBatch.end();// - See more at: http://www.karimamin.com/archives/75#sthash.DOtmE4ht.dpuf
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}
	private void update() { Vector2 direction = new Vector2(0, 0);
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
		tank_pos.add(direction); if (tank_pos.x < 0) tank_pos.x = 0; 
		if (tank_pos.x > this.screenWidth - TANK_SIZE) tank_pos.x = this.screenWidth - TANK_SIZE;
		if (tank_pos.y < 0) tank_pos.y = 0;
		if (tank_pos.y > this.screenHeight - TANK_SIZE) tank_pos.y = this.screenHeight - TANK_SIZE;
		objectDirection.set(direction); 
		}
	if (Gdx.input.isKeyPressed(Keys.F)) {
		bullet_pos = new Vector2(tank_pos.cpy().add( TANK_SIZE / 2 - BULLET_SIZE / 2, TANK_SIZE / 2 - BULLET_SIZE / 2));
		bulletDirection.set(objectDirection); 
		}
	if (bullet_pos != null) {
		bullet_pos.add(bulletDirection);
		if (bullet_pos.x < 0 || bullet_pos.x > this.screenWidth || bullet_pos.y < 0 || bullet_pos.y > this.screenHeight) {
			bullet_pos = null; 
			} 
		} 
	}// - See more at: http://www.karimamin.com/archives/75#sthash.DOtmE4ht.dpuf

}

