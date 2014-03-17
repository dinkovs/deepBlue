package com.me.units;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class Enemy 
{
	public int x;
	public int y;
	public boolean alive;
	public boolean forward = true;
	
	//Start the enemy at a given position
	public Enemy(int x, int y)
	{
		this.x = x;
		this.y = y;
		alive = true;
	}
	
	//Lame pursue algorithm only "chases" on the y at some vertical speed based on multiplier
	//Horizontal speed is determined by the camera speed
	public void pursue(int player_y, int multiplier)
	{
		if(y != player_y)
		{
			if(y > player_y)
				y -= multiplier;
			else
				y += multiplier;
		}	
	}
	
	//Accelerated Pursuing method
	public void accPursue(int player_y, int x_multiplier, int y_multiplier)
	{
		
	}
	
	//Method to check whether this enemy has been paced by the player
	//In which case a new AI approach will be used
	public boolean checkPassed(int player_x)
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
	
	//Method to kill the enemy (was somehow killed)
	public void killUnit()
	{
		this.alive = false;
	}
	
}
