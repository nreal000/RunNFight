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
    private Sprite small;
    private float rotationSpeed;

    public Test1Screen(final RunNFight gam) {
    	game = gam;
    	
    	 rotationSpeed = 0.5f;

         mapSprite = new Sprite(new Texture(Gdx.files.internal("sc_map.png")));
         mapSprite.setPosition(0, 0);
         mapSprite.setSize(WORLD_WIDTH, WORLD_HEIGHT);

         small = new Sprite(new Texture(Gdx.files.internal("sc_map.png")));
         small.setPosition(0,0);
         small.setSize(scale*small.getWidth(), scale*small.getHeight());
         
         float w = Gdx.graphics.getWidth();
         float h = Gdx.graphics.getHeight();
         cam = new OrthographicCamera(25, 25 * (h / w));
         cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);
         cam.update();

         batch = new SpriteBatch();
    }

    @Override
    public void render(float delta) {
    	handleCamInput();
        cam.update();
        batch.setProjectionMatrix(cam.combined);

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        mapSprite.draw(batch);
        small.draw(batch);
        batch.end();
    }
    
    private void handleCamInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.P)) {
            cam.zoom += 0.02;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.O)) {
            cam.zoom -= 0.02;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            cam.translate(-3, 0, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            cam.translate(3, 0, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            cam.translate(0, -3, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            cam.translate(0, 3, 0);
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

        cam.zoom = MathUtils.clamp(cam.zoom, 0.1f, 100/cam.viewportWidth);
        cam.position.x = MathUtils.clamp(cam.position.x, effectiveViewportWidth / 2f, WORLD_WIDTH - effectiveViewportWidth / 2f);
        cam.position.y = MathUtils.clamp(cam.position.y, effectiveViewportHeight / 2f, WORLD_HEIGHT - effectiveViewportHeight / 2f);
    
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
