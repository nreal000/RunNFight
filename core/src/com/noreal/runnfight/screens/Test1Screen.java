package com.noreal.runnfight.screens;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.Screen;
import com.noreal.runnfight.RunNFight;
import com.noreal.runnfight.utils.Constants;

public class Test1Screen implements Screen {
	final RunNFight game;
	
	static final int WORLD_WIDTH = Constants.APP_WIDTH;
    static final int WORLD_HEIGHT = Constants.APP_HEIGHT;

    private OrthographicCamera cam;
    private SpriteBatch batch;

    
    private float vpW =800;
    private float vpH = 800;
    
    private Sprite mapSprite;

	private Texture playerImage;
	private Texture enemyImage;
	private Texture bulletImage;
	private float rotationSpeed;

    private Array<Circle> bullets;
    private long lastBulletTime;

	private Rectangle player;
	private Rectangle enemy;
	
	private Vector2 bulletPos, bulletDir;
	private float bulletSpeed;
    
    public Test1Screen(final RunNFight gam) {
    	game = gam;
    	
    	 rotationSpeed = 0.5f;
    	 
    	 playerImage = new Texture(Gdx.files.internal("light_blue.png"));
    	 enemyImage = new Texture(Gdx.files.internal("red_circle.png"));
    	 bulletImage = new Texture(Gdx.files.internal("yellow_circle.png"));
    	 
    	 setupMapSprite();
         setupPlayer();
    	 setupEnemy();
         
         float w = Gdx.graphics.getWidth();
         float h = Gdx.graphics.getHeight();
         cam = new OrthographicCamera(/*vpW, vpH * (h / w)*/);// check world constants
         cam.setToOrtho(false, vpW, vpH * (h/w));
//         cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);
         cam.update();

         batch = new SpriteBatch();
         
         System.out.println(cam.viewportWidth + " " + cam.viewportHeight);
    
         bullets = new Array<Circle>();
         setupBullets();
    }

    @Override
    public void render(float delta) {
//    	handleCamInput();
//    	handlePlayerCam();
    	handlePlayerInput();
    	handleEnemy();
        cam.update();
        batch.setProjectionMatrix(cam.combined);  // tells the SpriteBatch to use the coordinate system specified by the camera. 

        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        mapSprite.draw(batch);
        batch.draw(playerImage, player.x, player.y, player.width, player.height);
        for (Circle bullet : bullets){
        	batch.draw(bulletImage, bullet.x, bullet.y, bullet.radius*2, bullet.radius*2);
        }
        batch.draw(enemyImage, enemy.x, enemy.y, enemy.width, enemy.height);
        batch.end();
        
        if (TimeUtils.nanoTime() - lastBulletTime > 10000000 && Gdx.input.isKeyPressed(Input.Keys.Z)) setupBullets();
        
//        if (Gdx.input.isKeyPressed(Input.Keys.Z)) {\\\
        Iterator<Circle> iter = bullets.iterator();
        while(iter.hasNext()) {
	        Circle bullet = iter.next();
	        	bullet.setPosition(bulletPos.add(bulletDir)); * Gdx.graphics.getDeltaTime();
	        	if(bullet.x <0 || bullet.x >= Constants.APP_WIDTH) iter.remove();
//	        	if(Intersector.overlaps(bullet, enemy)) iter.remove();
//        }
	        
        }
        
//        System.out.println(cam.viewportWidth + " " + cam.viewportHeight);
    }
    
    //handlers
    
/*    private void handleCamInput() {
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
    
    }*/

    
    /*private void handlePlayerCam(){
    	float speed = 1
    	
    	if (cam.viewportWidth/2 ) {
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
    }*/
    private void handlePlayerInput() {
    	float speed = 100 * Gdx.graphics.getDeltaTime();
    	if (Gdx.input.isKeyPressed(Input.Keys.W)) {
    		player.y += speed;
    	}
    	if (Gdx.input.isKeyPressed(Input.Keys.S)) {
    		player.y -= speed;
    	}
    	if (Gdx.input.isKeyPressed(Input.Keys.D)) {
    		player.x += speed;
    	}
    	if (Gdx.input.isKeyPressed(Input.Keys.A)) {
    		player.x -= speed;
    	}
        
    	stayIn();
    }
    
    private void handleEnemy() {
    	float speed = 100 * Gdx.graphics.getDeltaTime();
    	
    	float dx = player.getX() - enemy.getX();
    	float dy = player.getY() - enemy.getY();
    	
    	/*System.out.println(dx +" " + dy);*/
    	
    	float angle = MathUtils.atan2(dy, dx);
    	
    	/*System.out.println(angle);*/
    	
    	float anX = speed * MathUtils.cos(angle);
    	float anY = speed * MathUtils.sin(angle);
    	enemy.setPosition(enemy.getX() + anX, enemy.getY() + anY);  // translates the x and y
//    	System.out.println(anX + " " + anY);
    }
    
    private void handleKeycalls() {
    	Vector2 direction = new Vector2(0, 0);
    	float delta = Gdx.graphics.getDeltaTime() * bulletSpeed;
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
    	bulletDir.set(direction);
    }
    
    // setups
    
    
    private void setupMapSprite(){
    	mapSprite = new Sprite(new Texture(Gdx.files.internal("sc_map.png")));
        mapSprite.setPosition(0, 0);
        mapSprite.setSize(WORLD_WIDTH, WORLD_HEIGHT);
    }
    
    private void setupPlayer(){
    	player = new Rectangle();
        player.x = 0;
        player.y = 0;
        player.width = 10;
        player.height = 10;
//        player.setSize(scale*player.getWidth(), scale*player.getHeight());
    }
    
	private void setupEnemy(){
		enemy = new Rectangle();
		bulletPos = new Vector2(50, 50);
        enemy.setPosition(bulletPos);
        enemy.width = 5;
        enemy.height = 5;
	 }
	
	 private void setupBullets(){
			Circle bullet = new Circle();
			bullet.x = player.getX();
			bullet.y = player.getY();
			bullet.radius = 25;
			bullets.add(bullet);
			lastBulletTime = TimeUtils.nanoTime();
		}
	
    private void stayIn(){
    	if (player.getX() > WORLD_WIDTH - player.getWidth()){
        	player.setX(WORLD_WIDTH - player.getWidth());
        }
    	if (player.getX() < 0){
        	player.setX(0);
        }
    	if (player.getY() > WORLD_HEIGHT - player.getHeight()){
        	player.setY(WORLD_HEIGHT - player.getHeight());
        }
    	if (player.getY() < 0){
        	player.setY(0);
        }
    	
//    	System.out.println(player.getX() + " " + player.getY());
    	
    	if (enemy.getX() > WORLD_WIDTH - enemy.getWidth()){
        	enemy.setX(WORLD_WIDTH - enemy.getWidth());
        }
    	if (enemy.getX() < 0){
        	enemy.setX(0);
        }
    	if (enemy.getY() > WORLD_HEIGHT - enemy.getHeight()){
        	enemy.setY(WORLD_HEIGHT - enemy.getHeight());
        }
    	if (enemy.getY() < 0){
        	enemy.setY(0);
        }
    }
	@Override
	public void resize(int width, int height) {
        cam.viewportWidth = vpW;
        cam.viewportHeight = vpH * height/width;
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
		playerImage.dispose();
		enemyImage.dispose();
		bulletImage.dispose();
        batch.dispose();
	}

}
