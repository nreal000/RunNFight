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

public class Test1Screen implements Screen { // most comments made 6/26/15 so might not be reliable look for (?) to signify maybe
	final RunNFight game;
	
	static final int WORLD_WIDTH = Constants.APP_WIDTH, WORLD_HEIGHT = Constants.APP_HEIGHT; // so map is size of world?

    private OrthographicCamera cam;
    private SpriteBatch batch;

    
    private float vpW =800, vpH = 800;// so camera size of world?
    
    private Sprite mapSprite;

	private Texture playerImage, enemyImage, bulletImage; // images
    private Array<Circle> bulletsLeft, bulletsRight, bulletsUp, bulletsDown; // bullet direction array stuff
    private long lastBulletTime;// bullet cool down

	private Rectangle player, enemy;
	
	private float bulletSpeed = 100;
    
    public Test1Screen(final RunNFight gam) {
    	 game = gam;
    	
    	 playerImage = new Texture(Gdx.files.internal("light_blue.png"));
    	 enemyImage = new Texture(Gdx.files.internal("red_circle.png"));
    	 bulletImage = new Texture(Gdx.files.internal("yellow_circle.png"));
    	 
    	 setupMapSprite();
         setupPlayer();
    	 setupEnemy();
         
         float w = Gdx.graphics.getWidth();
         float h = Gdx.graphics.getHeight();
         cam = new OrthographicCamera(/*vpW, vpH * (h / w)*/);// check world constants
         cam.setToOrtho(false, vpW, vpH * (h/w));// makes camera size of world?
//         cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);
         cam.update();

         batch = new SpriteBatch();
         
         System.out.println(cam.viewportWidth + " " + cam.viewportHeight); // debugs for camera height ?
    
         bulletsRight = new Array<Circle>(); // declares bullet arrays for each direction // future self? better have a better way.
         bulletsLeft = new Array<Circle>();
         bulletsUp = new Array<Circle>();
         bulletsDown = new Array<Circle>();
       
         setupBulletsRight(); // initial setup so iterator isn't null.
         setupBulletsLeft();
         setupBulletsUp();
         setupBulletsDown();
    }

    @Override
    public void render(float delta) {
    	handlePlayerInput(); // player movement 
    	handleEnemy();// enemy movement
        cam.update();
        batch.setProjectionMatrix(cam.combined);  // tells the SpriteBatch to use the coordinate system specified by the camera. 

        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);// refresh

        batch.begin();
        mapSprite.draw(batch);
        batch.draw(playerImage, player.x, player.y, player.width, player.height);// draw player
//        System.out.println(player.x + " " + player.y);
        for (Circle bullet : bulletsRight){ // draws bullets
        	batch.draw(bulletImage, bullet.x, bullet.y, bullet.radius/10, bullet.radius/10);
        }
        for (Circle bullet : bulletsLeft){
        	batch.draw(bulletImage, bullet.x, bullet.y, bullet.radius/10, bullet.radius/10);
        }
        for (Circle bullet : bulletsUp){
        	batch.draw(bulletImage, bullet.x, bullet.y, bullet.radius/10, bullet.radius/10);
        }
        for (Circle bullet : bulletsDown){
        	batch.draw(bulletImage, bullet.x, bullet.y, bullet.radius/10, bullet.radius/10);
        }
        batch.draw(enemyImage, enemy.x, enemy.y, enemy.width, enemy.height);
        batch.end();
        // idea multiple bullet setups and iterations
        if (TimeUtils.nanoTime() - lastBulletTime > 100000000){ // wait time for bullets
        	if(Gdx.input.isKeyPressed(Input.Keys.DPAD_RIGHT)){//keys
        		setupBulletsRight();
        	}
         	if (Gdx.input.isKeyPressed(Keys.DPAD_LEFT)) {
         		setupBulletsLeft(); 
         		System.out.println(player.x + " " + player.y);
        		}
        	if (Gdx.input.isKeyPressed(Keys.DPAD_UP)) {
        		setupBulletsUp(); 
        		}
        	if (Gdx.input.isKeyPressed(Keys.DPAD_DOWN)) {
        		setupBulletsDown(); 
        		}
        }
        Iterator<Circle> iterRight = bulletsRight.iterator();// updates bullets per direction
        while(iterRight.hasNext()) {
	        Circle bullet = iterRight.next();
	        	bullet.x += bulletSpeed * Gdx.graphics.getDeltaTime();
	        	if(bullet.x >= Constants.APP_WIDTH){ 
	        		iterRight.remove();
	        		System.out.println(bullet.x + " gone");
	        	}
	        	if(Intersector.overlaps(bullet, enemy)) iterRight.remove();
//        }
	        
        }
        Iterator<Circle> iterLeft = bulletsLeft.iterator();
        while(iterLeft.hasNext()) {
	        Circle bullet = iterLeft.next();
	        	bullet.x -= bulletSpeed * Gdx.graphics.getDeltaTime();
	        	System.out.println(bullet.x);
	        	if(bullet.x <=0 ){
	        		iterLeft.remove();
	        		System.out.println(bullet.x + " gone");
	        	}
	        	if(Intersector.overlaps(bullet, enemy)) iterLeft.remove();
//        }
	        
        }
        Iterator<Circle> iterUp = bulletsUp.iterator();
        while(iterUp.hasNext()) {
	        Circle bullet = iterUp.next();
	        	bullet.y += bulletSpeed * Gdx.graphics.getDeltaTime();
	        	if(bullet.y >= Constants.APP_HEIGHT){ 
	        		iterUp.remove();
	        		System.out.println(bullet.y + " gone");
	        	}
	        	if(Intersector.overlaps(bullet, enemy)) iterUp.remove();
//        }
	        
        }
        Iterator<Circle> iterDown = bulletsDown.iterator();
        while(iterDown.hasNext()) {
	        Circle bullet = iterDown.next();
	        	bullet.y -= bulletSpeed * Gdx.graphics.getDeltaTime();
	        	if(bullet.y <0){ 
	        		iterDown.remove();
	        		System.out.println(bullet.y + " gone");
	        	}
	        	if(Intersector.overlaps(bullet, enemy)) iterDown.remove();
//        }
	        
        }
        
//        System.out.println(cam.viewportWidth + " " + cam.viewportHeight);
    }
    
    //handlers
    

    private void handlePlayerInput() {// player controls
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
    	if (Gdx.input.isKeyPressed(Input.Keys.G)) {
    		player.x += speed/10;
    	}
        
    	stayIn();
    }
    
    private void handleEnemy() {//enemy follow player logic
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
    
    
    // setups
    
    
    private void setupMapSprite(){//make background
    	mapSprite = new Sprite(new Texture(Gdx.files.internal("sc_map.png")));
        mapSprite.setPosition(0, 0);
        mapSprite.setSize(WORLD_WIDTH, WORLD_HEIGHT);
    }
    
    private void setupPlayer(){//make player
    	player = new Rectangle();
        player.x = 0;
        player.y = 0;
        player.width = 10;
        player.height = 10;
//        player.setSize(scale*player.getWidth(), scale*player.getHeight());
    }
    
	private void setupEnemy(){// make enemy
		enemy = new Rectangle();
		enemy.x = 100;
		enemy.y = 100;
        enemy.width = 5;
        enemy.height = 5;
	 }
	
	 private void setupBulletsRight(){
			Circle bullet = new Circle();
			bullet.x = player.getX() + player.width/2;
			bullet.y = player.getY() + player.height/2;
			bullet.radius = 25;
			bulletsRight.add(bullet);
			lastBulletTime = TimeUtils.nanoTime();
		}
	 private void setupBulletsLeft(){
			Circle bullet = new Circle();
			bullet.x = player.getX() + player.width/2;
			bullet.y = player.getY() + player.height/2;
			bullet.radius = 25;
			bulletsLeft.add(bullet);
//			System.out.print(bullet.x + " : ");
			lastBulletTime = TimeUtils.nanoTime();
		}
	 private void setupBulletsUp(){
			Circle bullet = new Circle();
			bullet.x = player.getX() + player.width/2;
			bullet.y = player.getY() + player.height/2;
			bullet.radius = 25;
			bulletsUp.add(bullet);
			lastBulletTime = TimeUtils.nanoTime();
		}
	 private void setupBulletsDown(){
			Circle bullet = new Circle();
			bullet.x = player.getX() + player.width/2;
			bullet.y = player.getY() + player.height/2;
			bullet.radius = 25;
			bulletsDown.add(bullet);
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
