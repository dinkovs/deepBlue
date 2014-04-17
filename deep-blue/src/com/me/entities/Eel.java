package com.me.entities;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.me.deepblue.Images;

/**
 * 
 * @author westwiatt
 *
 *This class is separate from enemy because  a jellyfish is intended to be treated
 *as a scripted obstacle with  no AI.
 */
public class Eel extends SeaObjects{

	TextureRegion[][] imageEel = TextureRegion.split(Images.eelAttack_image, 90, 235);
	public float imageIterator;
	
	public Eel(float x, float y)
	{
		super.x = x;
		super.y = y;
	}
	
	/*
	 * Iterates through the sprite sheet and retrieves the appropriate image of the jellyfish,
	 * making the animation possible
	 * 
	 * @Martin Dinkov
	 */
	public TextureRegion getImage() {
		if (imageIterator < 29)
	   		imageIterator += 0.06;
	   	else
	   		imageIterator = 0;
	   	return imageEel[(int)imageIterator / 5][4 - ((int)imageIterator % 5)];
	}
}
