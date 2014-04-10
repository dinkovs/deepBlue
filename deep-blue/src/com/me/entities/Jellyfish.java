package com.me.entities;

import com.me.deepblue.Images;

/**
 * 
 * @author westwiatt
 *
 *This class is separate from enemy because  a jellyfish is intended to be treated
 *as a scripted obstacle with  no AI.
 */
public class Jellyfish extends SeaObjects{

	public Jellyfish(float x, float y)
	{
		super.x = x;
		super.y = y;
		image = Images.jellyfish_sprite;
	}
}
