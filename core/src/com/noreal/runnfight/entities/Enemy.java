package com.noreal.runnfight.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Enemy {
	public Vector2 enemyPos;
	public Rectangle enemyRect = new Rectangle();
	private Player player = new Player();
	
	public Enemy(float x, float y){
		enemyPos  = new Vector2(x,y);
		enemyRect.setSize(5);
	}
	
	public void update(){
		float enemySpeed = 100 * Gdx.graphics.getDeltaTime();
    	
    	float dx = player.getX() - enemyPos.x;
    	float dy = player.getY() - enemyPos.y;
    	
    	/*System.out.println(dx +" " + dy);*/
    	
    	float angle = MathUtils.atan2(dy, dx);
    	
    	/*System.out.println(angle);*/
    	
    	float anX = enemySpeed * MathUtils.cos(angle);
    	float anY = enemySpeed * MathUtils.sin(angle);
    	enemyPos.setPosition(enemyPos + anX, enemyPos.y + anY);  // translates the x and y
//    	System.out.println(anX + " " + anY);
	}
}
