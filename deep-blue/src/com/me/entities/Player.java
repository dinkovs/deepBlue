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
	TextureRegion[][] image = TextureRegion.split(Images.turtle_image, 128, 100);
	int imageIterator;
	
    //Attributes
    public int health;
    private int lives;
    private int score;
    
    //Power-up
    private ArrayList<Bubble> bubbles;
    
    public Player(ArrayList<Bubble> bubbles, GameScreen play_screen) {
    	//START PLAYER IN MIDDLE OF SCREEN
    	x = 960 - 64;
    	y = 540 - 64;
    	boundingBox = new Rectangle (this.x,y,144,121);
    	imageIterator = -1;
    	
    	health = 100;
    	lives = 3;
    	score = 0;
	
	this.bubbles = bubbles;
	}
    
    public TextureRegion getImage() {
		
    	if (imageIterator < 49)
    		imageIterator++;
    	else
    		imageIterator = 0;
    	return image[imageIterator / 5][4 - (imageIterator % 5)];
    	
    }
    
    public int getHealth() { 
    	return health;
    }
    public void lostHealth() {
    	health -= 10;
    }
    public int getLives() { 
    	return lives; 
    }
    public void loseLife() {
    	lives--;
    }
    public int getScore() {
    	return score;
    }
    public void addScore() {
    	score++;
    }
    
    //initial power-up
    public void shootBubs() {
    	if(bubbles.size() == MAX_BUBBLES) return; //10 is the max amount of bubbles
    	System.out.println("SHOOTING NOW");
    	bubbles.add(new Bubble(x, y, radians));
    }
  
  	//movement
  	public void handleInput(float cameraX){
  		if(Gdx.input.isKeyPressed(Keys.A) || Gdx.input.isKeyPressed(Keys.LEFT)) {
  			if (x - 5 >= cameraX - 600) {
  				x -= 5;
  			}
  			else x = cameraX - 600;
			boundingBox.x = x;
  		}
  		if(Gdx.input.isKeyPressed(Keys.D) || Gdx.input.isKeyPressed(Keys.RIGHT)) {
  			if (x + 5 <= cameraX + 472) {
  				x += 5;
  				boundingBox.x = x;
  			}
  			else x = cameraX + 472;
  		}
  		if(Gdx.input.isKeyPressed(Keys.S) || Gdx.input.isKeyPressed(Keys.DOWN)) {
  			if (y + 5 <= 472) {
  				y += 5;
  				boundingBox.y = y;
  			}
  			else y = 472;
  		}
  		if(Gdx.input.isKeyPressed(Keys.W) || Gdx.input.isKeyPressed(Keys.UP)) {
  			if (y - 5 >= 0) {
  				y -= 5;
  				boundingBox.y = y;
  			}
  			else y = 0;
  		}
  		if(Gdx.input.isKeyPressed(Keys.SPACE)) {
  			for (int i = 1; i <= 10; i++) {
  			System.out.println(i + ". SHOOTING NOW");
  			}
  		}
  	}

    
}

            
