package com.noreal.runnfight.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class SpriteScreen implements Screen {
	private OrthographicCamera camera;
    private SpriteBatch batch;
    private Texture texture;
    private Sprite sprite;
    private Sprite background;

    private boolean lockToSprite;
    private Vector2 vecCamera;
    private Vector2 vecSprite;
    
	public SpriteScreen(){
		float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        camera = new OrthographicCamera(w, h);
        batch = new SpriteBatch();

        lockToSprite = true;
        vecCamera = new Vector2();
        vecSprite = new Vector2();

        texture = new Texture(Gdx.files.internal("sc_map.png"));
        texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);

        TextureRegion region = new TextureRegion(texture, 0, 0, 512, 275);

        sprite = new Sprite(region);
        sprite.setSize(0.1f * sprite.getWidth(), 0.1f * sprite.getHeight());
        sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
        sprite.setPosition(-sprite.getWidth()/2, -sprite.getHeight()/2);

        background = new Sprite(region);
        background.setOrigin(background.getWidth() / 2, background.getHeight() / 2);
        System.out.println(background.getOriginX());
        background.setPosition(-background.getWidth() / 2, -background.getHeight() / 2);
	}
	@Override
	public void show() {
		
	}

	@Override
	public void render(float delta) {
		 camera.translate(vecCamera);

	        Gdx.gl.glClearColor(1, 1, 1, 1);
	        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

	        camera.update();

	        camera.translate(vecCamera.cpy().mul(-1));

	        float moveSensitivity = 0.9f;

	        Vector2 vecInputSprite = new Vector2();
	        if (Gdx.input.isKeyPressed(Keys.UP))
	            vecInputSprite.y += moveSensitivity;
	        if (Gdx.input.isKeyPressed(Keys.DOWN))
	            vecInputSprite.y -= moveSensitivity;
	        if (Gdx.input.isKeyPressed(Keys.LEFT))
	            vecInputSprite.x -= moveSensitivity;
	        if (Gdx.input.isKeyPressed(Keys.RIGHT))
	            vecInputSprite.x += moveSensitivity;
	        if (Gdx.input.isKeyPressed(Keys.N))
	            vecSprite.set(new Vector2());

	        Vector2 vecInputCamera = new Vector2();
	        if (Gdx.input.isKeyPressed(Keys.W))
	            vecInputCamera.y += moveSensitivity;
	        if (Gdx.input.isKeyPressed(Keys.S))
	            vecInputCamera.y -= moveSensitivity;
	        if (Gdx.input.isKeyPressed(Keys.A))
	            vecInputCamera.x -= moveSensitivity;
	        if (Gdx.input.isKeyPressed(Keys.D))
	            vecInputCamera.x += moveSensitivity;

	        if (Gdx.input.isKeyPressed(Keys.R)) {
	            vecCamera.set(new Vector2());
	            lockToSprite = false;
	        }

	        if (vecInputCamera.len2() != 0)
	            lockToSprite = false;
	        else if (Gdx.input.isKeyPressed(Keys.L))
	            lockToSprite = true;

	        if (lockToSprite) {
	            vecCamera.set(vecSprite);
	        } else {
	            vecCamera.add(vecInputCamera);
	        }

	        vecSprite.add(vecInputSprite);

	        batch.setProjectionMatrix(camera.combined);
	        batch.begin();
	        background.draw(batch);
	        sprite.setPosition(vecSprite.x, vecSprite.y);
	        sprite.draw(batch);
	        //batch.draw(sprite, vecSprite.x, vecSprite.y);
	        batch.end();
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
		 batch.dispose();
	     texture.dispose();
	}

}
