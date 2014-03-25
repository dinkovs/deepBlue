package com.me.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PowerUp extends SeaObjects{
	
	int type;
	SpriteBatch batch;
	
	public PowerUp(int type, int x) {
		
		this.type = type;
		if(type == 2) type -= 1;
		
		this.x = x;
		y = (float) (100 + (Math.random() * 400));
		
	}
	
	

}
