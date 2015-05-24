package com.noreal.runnfight.entities;

import com.badlogic.gdx.math.Vector2;

public class Entity {
	public float x;
	public float y;
	public int width;
	public int hieght;
	
	public Entity(float xx ,float yy){
		this.x = xx ;
		this.y = yy;				
		vectormaker();
	}
	public Vector2 vectormaker(){
		Vector2 thisVector = new Vector2(x, y);
		return thisVector;
	}
}
