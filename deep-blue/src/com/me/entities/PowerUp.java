package com.me.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.me.deepblue.Images;

public class PowerUp extends SeaObjects{
	
	public boolean active;
	public boolean activated;
	/*
	 * 1 - ScorePlus
	 * 2 - ScoreSpeedUp
	 * 3 - FishPowerUp
	 */
	int type;
	SpriteBatch batch;
	
	public PowerUp(int type, float x) {
		
		this.type = type;
		switch(type) {
		case 1:
			image = Images.scoreplus_sprite;
			break;
		case 2:
			image = Images.scorespeedup_sprite;
			break;
		case 3:
			image = Images.fishpowerup_sprite;
			break;
		}
		
		this.x = x;
		y = (float) (100 + (Math.random() * 400));
		
		boundingBox = new Rectangle (this.x, y, image.getWidth(), image.getHeight());
		
		active = false;
		activated = false;
	}
	
	public void reset(float x) {
		this.x = x + 700;
		y = (float) (100 + (Math.random() * 400));
		boundingBox.x = this.x;
		boundingBox.y = this.y;
		
		active = false;
		activated = false;
	}
	
	

}
