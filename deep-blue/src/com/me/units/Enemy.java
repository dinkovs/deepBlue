package com.me.units;

public class Enemy 
{
	public int x;
	public int y;
	
	public Enemy(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	//Lame pursue algorithm only "chases" on the y
	public void pursue(int player_y)
	{
		if(y != player_y)
		{
			if(y > player_y)
				y--;
			else
				y++;
		}
			
	}
	
}
