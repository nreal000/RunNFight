package com.noreal.runnfight.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.Screen;
import com.noreal.runnfight.RunNFight;

public class Test1Screen implements Screen {
	final RunNFight game;
	
	static final int WORLD_WIDTH = 100;
    static final int WORLD_HEIGHT = 100;

    private OrthographicCamera cam;
    private SpriteBatch batch;

    private float scale = .01f;
    
    private Sprite mapSprite;
    private Sprite player;
    private Sprite enemy;
    private float rotationSpeed;

    public Test1Screen(final RunNFight gam) {
    	game = gam;
    	
    	 rotationSpeed = 0.5f;

         mapSprite = new Sprite(new Texture(Gdx.files.internal("sc_map.png")));
         mapSprite.setPosition(0, 0);
         mapSprite.setSize(WORLD_WIDTH, WORLD_HEIGHT);

         player = new Sprite(new Texture(Gdx.files.internal("sc_map.png")));
         player.setPosition(011,0);
         player.setSize(scale*player.getWidth(), scale*player.getHeight());
         
         enemy = new Sprite(new Texture(Gdx.files.internal("sc_map.png")));
         enemy.setPosition(50,0);
         enemy.setSize(scale*enemy.getWidth(), scale*enemy.getHeight());
         
         float w = Gdx.graphics.getWidth();
         float h = Gdx.graphics.getHeight();
         cam = new OrthographicCamera(25, 25 * (h / w));
         cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);
         cam.update();

         batch = new SpriteBatch();
         
         System.out.println(w +" " + h);
    }

    @Override
    public void render(float delta) {
    	handleCamInput();
    	handlePlayerInput();
    	handleEnemy();
        cam.update();
        batch.setProjectionMatrix(cam.combined);

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        mapSprite.draw(batch);
        player.draw(batch);
        enemy.draw(batch);
        batch.end();
    }
    
    private void handleCamInput() {
    	float speed = 3;
    	
        if (Gdx.input.isKeyPressed(Input.Keys.P)) {
            cam.zoom += 0.02;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.O)) {
            cam.zoom -= 0.02;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            cam.translate(-speed, 0, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            cam.translate(speed, 0, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            cam.translate(0, -speed, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            cam.translate(0, speed, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.K)) {
            cam.rotate(-rotationSpeed, 0, 0, 1);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.L)) {
            cam.rotate(rotationSpeed, 0, 0, 1);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.X)) {
        	game.setScreen(new SpriteScreen());
        }
        float effectiveViewportWidth = cam.viewportWidth * cam.zoom;
        float effectiveViewportHeight = cam.viewportHeight * cam.zoom;

        cam.zoom = MathUtils.clamp(cam.zoom, 0.1f, WORLD_WIDTH/cam.viewportWidth);
        cam.position.x = MathUtils.clamp(cam.position.x, effectiveViewportWidth / 2f, WORLD_WIDTH - effectiveViewportWidth / 2f);
        cam.position.y = MathUtils.clamp(cam.position.y, effectiveViewportHeight / 2f, WORLD_HEIGHT - effectiveViewportHeight / 2f);
    
    }

    private void handlePlayerInput() {
    	float speed = 1;
    	if (Gdx.input.isKeyPressed(Input.Keys.W)) {
    		player.translateY(speed);
    	}
    	if (Gdx.input.isKeyPressed(Input.Keys.S)) {
    		player.translateY(-speed);
    	}
    	if (Gdx.input.isKeyPressed(Input.Keys.D)) {
    		player.translateX(speed);
    	}
    	if (Gdx.input.isKeyPressed(Input.Keys.A)) {
    		player.translateX(-speed);
    	}
        
    	stayIn();
    }
    
    private void handleEnemy() {
    	float speed = 1;
    	
    	float dx = player.getX() - enemy.getX();
    	float dy = player.getY() - enemy.getY();
    	
    	/*System.out.println(dx +" " + dy);*/
    	
    	float angle = MathUtils.atan2(dy, dx);
    	
    	/*System.out.println(angle);*/
    	
    	float anX = speed * MathUtils.cos(angle);
    	float anY = speed * MathUtils.sin(angle);
    	enemy.translate(anX, anY);
    }
    
    private void stayIn(){
    	if (player.getX() > WORLD_WIDTH - player.getWidth()){
        	player.setX(WORLD_WIDTH - player.getWidth());
        }
    	if (player.getX() < 0){
        	player.setX(0);
        }
    	if (player.getY() > WORLD_WIDTH - player.getHeight()){
        	player.setY(WORLD_WIDTH - player.getHeight());
        }
    	if (player.getY() < 0){
        	player.setY(0);
        }
    	
    	
    	if (enemy.getX() > WORLD_WIDTH - enemy.getWidth()){
        	enemy.setX(WORLD_WIDTH - enemy.getWidth());
        }
    	if (enemy.getX() < 0){
        	enemy.setX(0);
        }
    	if (enemy.getY() > WORLD_WIDTH - enemy.getHeight()){
        	enemy.setY(WORLD_WIDTH - enemy.getHeight());
        }
    	if (enemy.getY() < 0){
        	enemy.setY(0);
        }
    }
    
	@Override
	public void resize(int width, int height) {
        cam.viewportWidth = 30f;
        cam.viewportHeight = 30f * height/width;
        cam.update();
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
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
	public void dispose() {
		mapSprite.getTexture().dispose();
        batch.dispose();
	}

}
