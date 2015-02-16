package com.noreal.runnfight.utils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class WorldUtils {
	public static World createWorld(){
		return new World(Constants.WORLD_GRAVITY, true);
	}
	
	   public static Body createRunner(World world) {
	        BodyDef bodyDef = new BodyDef();
	        bodyDef.type = BodyDef.BodyType.DynamicBody;
	        bodyDef.position.set(new Vector2(Constants.RUNNER_X, Constants.RUNNER_Y));
	        PolygonShape shape = new PolygonShape();
	        shape.setAsBox(Constants.RUNNER_WIDTH / 2, Constants.RUNNER_HEIGHT / 2);
	        Body body = world.createBody(bodyDef);
	        body.createFixture(shape, Constants.RUNNER_DENSITY);
	        body.resetMassData();
	        shape.dispose();
	        return body;
	    }
}
