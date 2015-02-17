package com.noreal.runnfight.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameScreen implements Screen {
	
	static final int WORLD_WIDTH = 100;
    static final int WORLD_HEIGHT = 100;

    private OrthographicCamera cam;
    private SpriteBatch batch;

    private Sprite mapSprite;
    private float rotationSpeed;

    public GameScreen() {
    	 rotationSpeed = 0.5f;

         mapSprite = new Sprite(new Texture(Gdx.files.internal("sc_map.png")));
         mapSprite.setPosition(0, 0);
         mapSprite.setSize(WORLD_WIDTH, WORLD_HEIGHT);

         float w = Gdx.graphics.getWidth();
         float h = Gdx.graphics.getHeight();
         cam = new OrthographicCamera(30, 30 * (h / w));
         cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);
         cam.update();

         batch = new SpriteBatch();
    }

    @Override
    public void render(float delta) {
        //Clear the screen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

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
		// TODO Auto-generated method stub

	}

}
