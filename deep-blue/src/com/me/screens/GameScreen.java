package com.me.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.me.deepblue.DeepBlue;
import com.me.deepblue.Objects;
import com.me.units.Enemy;
import com.me.units.Player;

public class GameScreen implements Screen{

	DeepBlue game;
	OrthographicCamera camera;
	SpriteBatch batch;
	Player player;
	Enemy enemy;
	int tracker = 0;
	long start = System.currentTimeMillis();
	
	public GameScreen(DeepBlue game){
		Objects.loadPlay();
		this.game = game;
		
		camera = new OrthographicCamera();
		camera.setToOrtho(true,1200,600);
		
		batch = new SpriteBatch();
		player = new Player();
	}
	
	public void spawnEnemy()
	{
		enemy = new Enemy((int)camera.position.x + 700, 100 + (int)(Math.random() * ((500 - 100) + 1)));
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		update();
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		//RENDERING CODE GOES HERE
		
		//Default scroll speed
		camera.position.x++;
		//System.out.println(camera.position.x++);
		
		//Scrolling screen code
		if(camera.position.x -1200 / 2 > Objects.sea_sprite1.getX()){
			Objects.sea_sprite.setPosition(Objects.sea_sprite1.getX(),0);
	        Objects.sea_sprite1.setPosition(Objects.sea_sprite.getX() + 1200, 0);
	    }
		//Enemy Spawning Timer (Randomness to come)
		long current = System.currentTimeMillis();
		//System.out.println(start - current);
		if(current - start > 15000)
		{
			spawnEnemy();
			start = System.currentTimeMillis();
			current = System.currentTimeMillis();
		}
		//Enemy Pursue
		if(enemy != null && enemy.x > player.x) //Stops pursuing if it has been passed
			enemy.pursue(player.y);
		
		
		Objects.sea_sprite.draw(batch);
		Objects.sea_sprite1.draw(batch);	
		
		if(enemy != null)
			batch.draw(Objects.enemy1_sprite,enemy.x,enemy.y);
		
		//batch.draw(Objects.sea_sprite, 0, 0);
		
		batch.draw(Objects.turtle_sprite, player.x, player.y);
		batch.end();
	}
	
	public void update(){
		camera.update();
		player.update();
	}

	@Override
	public void show() {
		
		
	}

	@Override
	public void pause() {
		
		
	}

	@Override
	public void resume() {
		
		
	}

	@Override
	public void dispose() {
		
		
	}
	
	@Override
	public void resize(int width, int height) {	
	}

	@Override
	public void hide() {
	}
}
