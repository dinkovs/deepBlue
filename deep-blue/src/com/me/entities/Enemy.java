package com.me.entities;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.me.deepblue.Images;

public class Enemy extends SeaObjects
{
	public int x;
	public int y;
	public boolean forward = true;
	public int type;
	public boolean attacking = false;
	TextureRegion[][] imageBarracuda = TextureRegion.split(Images.barracuda_image,244,100);
	TextureRegion[][] imageShark = TextureRegion.split(Images.shark_image, 355, 250);
	float imageIterator;
	
	//KEYS FOR ENEMY TYPES
	/*
	 * Shark == 0
	 * Barracuda == 1
	 */
	
	//Start the enemy at a given position
	public Enemy(int x, int y, int type)
	{
		this.x = x;
		this.y = y;
		this.type = type;
		
		if(type == 1)
			boundingBox = new Rectangle (this.x, this.y, 460, 200);
		else if (type == 0)
			boundingBox = new Rectangle (this.x, this.y, 235, 90);
	}
	
	/*
	 * Iterates through the sprite sheet and retrieves the appropriate image of the enemy,
	 * making the animation possible
	 * 
	 * @Martin Dinkov
	 */
	public TextureRegion getImage() {
		if(type == 0) {
			if (imageIterator < 13)
	   			imageIterator += 0.25;
	   		else
	   			imageIterator = 0;
	   		return imageShark[(int)imageIterator / 2][1 - ((int)imageIterator % 2)];
	   	}
		if(type == 1) {
			if(imageIterator < 3)
				imageIterator += .25;
			else
				imageIterator = 0;
			return imageBarracuda[(int)imageIterator/2][1-((int)imageIterator % 2)];
		}
		return null;
	}
	
	//Lame pursue algorithm only "chases" on the y at some vertical speed based on multiplier
	//Horizontal speed is determined by the camera speed
	//return 1 when aligned 0 when not
	public int pursue(float player_y, int multiplier)
	{
		if((y > player_y + 10) || (y < player_y - 10))
		{
			if(y > player_y)
			{
				y -= multiplier;
				boundingBox.y -= multiplier;
			}
			else
			{
				y += multiplier;
				boundingBox.x += multiplier;
			}
			return 0;
		}
		else
		{
			return 1;
		}
	}
	
	//Algorithm that once aligned in the y direction will burst across the screen towards player
	public void burst(float player_y, int multiplier)
	{
		if(attacking == true)
		{
			x-= 5;
			boundingBox.x -= 5;
		}
		if(pursue(player_y, multiplier) == 1)
		{
			x -= 5;
			boundingBox.x -= 5;
			attacking = true;
		}
	}
	
	//Accelerated Pursuing method
	public void accPursue(int player_y, int x_multiplier, int y_multiplier)
	{
		
	}
	
	//Method to check whether this enemy has been paced by the player
	//In which case a new AI approach will be used
	public boolean checkPassed(float player_x)
	{
		if(player_x > x)
			return true; //Player has passed the enemy
		else
			return false; //Player has not passed the enemy
	}
	
	//Method that can be used by certain enemy AI to have them chase back to the player.
	public void chase(int multipler)
	{
		//TODO
	}	
	
	public Rectangle getBounds() {
		return boundingBox;
	}
	
}
