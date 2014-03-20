package com.me.entities;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

//sea turtle starts by shooting bubbles from its mouth
public class Bubble extends SeaObjects {
	
	//how much has bubbles stayed on the screen?
	private float duration;
	private float timer;
	
	private boolean remove;
	
	public Bubble(float x, float y, float radians) {
		this.x = x;
		this.y = y;
		this.radians = radians;
		
		speed = 150;
		dx = MathUtils.cos(radians) * speed;
		dy = MathUtils.sin(radians) * speed;
		
		width = height = 2;
		
		//bubbles stay on the screen for 2 seconds
		timer = 0;
		duration = 2;
		
	}
	
	public boolean shouldRemove() { return remove; }
	
	public void update(float dt) {
		//updating position
		x += dx * dt;
		y += dy * dt;
		
		timer += dt; 
		if(timer > duration) {
			remove = true;
		}
	}
	
	//DOESN'T WORK YET
	public void draw(ShapeRenderer sr) {
		sr.setColor(1, 1, 1, 1);
		sr.begin(ShapeType.Line);
		sr.circle(x - width / 2, y - height / 2, width / 2);
		sr.end();
		
	}
	
}
