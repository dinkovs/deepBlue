package com.me.entities;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

//Speedy the Turtle
//Flipper the Dolphin
//Cuddles the Shark? <-- haha

public class Player extends SeaObjects {
	
	Player player;
	private final int MAX_BUBBLES = 10;
	
    //Attributes
    private int health;
    private int lives;
    private int score;
    
    //Power-up
    private ArrayList<Bubble> bubbles;
    
    public Player(ArrayList<Bubble> bubbles) {
	//START PLAYER IN MIDDLE OF SCREEN
	x = 960 - 64;
	y = 540 - 64;
	
	health = 100;
	lives = 3;
	score = 0;
	
	this.bubbles = bubbles;
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
  	public void handleInput(){
  		if(Gdx.input.isKeyPressed(Keys.A) || Gdx.input.isKeyPressed(Keys.LEFT))
  			x -= 5;
  		if(Gdx.input.isKeyPressed(Keys.D) || Gdx.input.isKeyPressed(Keys.RIGHT))
  			x += 5;
  		if(Gdx.input.isKeyPressed(Keys.S) || Gdx.input.isKeyPressed(Keys.DOWN))
  			y += 5;
  		if(Gdx.input.isKeyPressed(Keys.W) || Gdx.input.isKeyPressed(Keys.UP))
  			y -= 5;
  		if(Gdx.input.isKeyPressed(Keys.SPACE)) {
  			for (int i = 1; i <= 10; i++) {
  			System.out.println(i + ". SHOOTING NOW");
  			}
  		}
  	}

    
}
            
