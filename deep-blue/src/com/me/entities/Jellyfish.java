package com.me.entities;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.me.deepblue.Images;

/**
 * 
 * @author westwiatt
 *
 *This class is separate from enemy because  a jellyfish is intended to be treated
 *as a scripted obstacle with  no AI.
 */
public class Jellyfish extends SeaObjects{

	TextureRegion[][] imageJelly = TextureRegion.split(Images.jellyfish_image, 74, 132);
	float imageIterator;
	
	public Jellyfish(float x, float y)
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
		if (imageIterator < 21)
	   		imageIterator += 0.5;
	   	else
	   		imageIterator = 0;
	   	return imageJelly[(int)imageIterator / 2][1 - ((int)imageIterator % 2)];
	}
}
