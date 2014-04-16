package com.me.entities;


import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.me.deepblue.Images;
import com.me.screens.GameScreen;

//Speedy the Turtle
//Flipper the Dolphin
//Cuddles the Shark? <-- haha

public class Player extends SeaObjects {
	
	Player player;
	private final int MAX_BUBBLES = 10;
	TextureRegion[][] imageTurtle = TextureRegion.split(Images.turtle_image, 128, 100);
	TextureRegion[][] imageFish = TextureRegion.split(Images.fish_image, 128, 86);
	TextureRegion[][] imageTurtAttack = TextureRegion.split(Images.turtleAttack_image, 230, 67);
	TextureRegion[][] imageFishAttack = TextureRegion.split(Images.fishAttack_image, 230, 86);
	float imageIterator;
	
	/*
	 * 0 - SPEEDY THE TURTLE
	 * 1 - FREDDY THE FISH
	 * 2 - SPEEDY'S ATTACK
	 * 3 - FREDDY'S ATTACK
	 */
	
	public int form;
    public int lives;
    
    //Power-up
    private ArrayList<Bubble> bubbles;
	public boolean invincible;
    
    public Player(ArrayList<Bubble> bubbles, GameScreen play_screen) {
    	x = 400;
    	y = 250;
    	boundingBox = new Rectangle (this.x,this.y,128,100);
    	imageIterator = -1;
    	
    	form = 0;
    	lives = 3;
	
    	this.bubbles = bubbles;
    	invincible = false;
	}
    
    public TextureRegion getImage() {
		if (form == 0) {
			if (imageIterator < 49)
    			imageIterator += 0.25;
    		else
    			imageIterator = 0;
    		return imageTurtle[(int)imageIterator / 5][4 - ((int)imageIterator % 5)];
		}
		else if (form == 1) {
			if (imageIterator < 21)
    			imageIterator += 0.25;
    		else
    			imageIterator = 0;
    		return imageFish[(int)imageIterator / 2][1 - ((int)imageIterator % 2)];
		}
		else if (form == 2) {
			if (imageIterator < 21)
    			imageIterator += 0.1;
    		else
    			imageIterator = 0;
			return imageTurtAttack[(int)imageIterator / 2][1 - ((int)imageIterator % 2)];
				}
		else if (form == 3) {
			if (imageIterator < 21)
    			imageIterator += 0.1;
    		else
    			imageIterator = 0;
			return imageFishAttack[(int)imageIterator / 2][1 - ((int)imageIterator % 2)];
				}
		return null;
    }

    public int getLives() { 
    	return lives; 
    }
    public void loseLife() {
    	lives--;
    }
    
    //initial power-up
    public void shootBubs() {
    	if(bubbles.size() == MAX_BUBBLES) return; //10 is the max amount of bubbles
    	System.out.println("SHOOTING NOW");
    	bubbles.add(new Bubble(x, y, radians));
    }
    
    public void pullUp() {
    	y -= 10;
    	boundingBox.y -= 10;
    }
  
  	//movement
  	public void handleInput(float cameraX){
  		if(Gdx.input.isKeyPressed(Keys.A) || Gdx.input.isKeyPressed(Keys.LEFT)) {
  			if (form == 0 || form == 2) x -= 5;
	  		else if (form == 1 || form == 3) x -= 10;
			boundingBox.x = x;
  		}
  		if(Gdx.input.isKeyPressed(Keys.D) || Gdx.input.isKeyPressed(Keys.RIGHT)) {
  			if (x + 5 <= cameraX + 472) {
  				if (form == 0 || form == 2) x += 5;
  	  			else if (form == 1 || form == 3) x += 10;
  				boundingBox.x = x;
  			}
  			else x = cameraX + 472;
  		}
  		if(Gdx.input.isKeyPressed(Keys.S) || Gdx.input.isKeyPressed(Keys.DOWN)) {
  			if (y + 5 <= 472) {
  				if (form == 0 || form == 2) y += 5;
  	  			else if (form == 1 || form == 3) y += 7;
  				boundingBox.y = y;
  			}
  			else y = 472;
  		}
  		if(Gdx.input.isKeyPressed(Keys.W) || Gdx.input.isKeyPressed(Keys.UP)) {
  			if (y - 5 >= 0) {
  				if (form == 0 || form == 2) y -= 5;
  	  			else if (form == 1 || form == 3) y -= 7;
  				boundingBox.y = y;
  			}
  			else y = 0;
  		}
  		
  		/* SPACE NOT USED FOR BUBBLES
  		if(Gdx.input.isKeyPressed(Keys.SPACE)) {
  			for (int i = 1; i <= 10; i++) {
  			System.out.println(i + ". SHOOTING NOW");
  			}
  		}
  		*/
  	}

    
}

            
