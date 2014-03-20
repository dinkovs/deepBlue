package com.me.screens;

import java.util.ArrayList;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.me.deepblue.DeepBlue;
import com.me.deepblue.Images;
import com.me.entities.Bubble;
import com.me.entities.Enemy;
import com.me.entities.Player;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class GameScreen implements Screen{

	DeepBlue game;
	OrthographicCamera camera;
	SpriteBatch batch;
	FileHandle fontFile;
	FreeTypeFontGenerator generator;
	Player player;
	Enemy enemy;
	float score = 0;
	int tracker = 0;
	long start = System.currentTimeMillis();	//Keep track of enemy spawning
	long startGameTime = System.currentTimeMillis(); //Keep track of total game length
	boolean firstEnemy = false;
	int levelSpeed = 1;
	
	private BitmapFont font = new BitmapFont();
	//for the turtle
	Character speedy;
	ArrayList<Bubble> bubbles;
	ShapeRenderer sr; // <--- for bubbles, for now
	
	public GameScreen(DeepBlue game) {
		Images.loadPlay();
		this.game = game;
		
		camera = new OrthographicCamera();
		camera.setToOrtho(true,1200,600);
		
		batch = new SpriteBatch();
		
		//allows player to shoot bubbles
		sr = new ShapeRenderer();
		bubbles = new ArrayList<Bubble>();
		player = new Player(bubbles, this);
		
		//cartoon blocks
		fontFile = Gdx.files.internal("menu/Cartoon Blocks.ttf");
		generator = new FreeTypeFontGenerator(fontFile);
		font = generator.generateFont(70);
		generator.dispose();
		font.setScale(1,-1);
	}
	
	//Spawn Enemy1 Function
	public void spawnEnemy()
	{
		//TODO Edit Spawn enemy function based on enemies height.
		enemy = new Enemy((int)camera.position.x + 700, 100 + (int)(Math.random() * ((500 - 100) + 1)));
		firstEnemy = true;
	}
	
	//Flip Enemy to chase function
	public void turnEnemy()
	{
		if(enemy.forward)
		{
			Images.enemy1_sprite.flip(true, false); 
			enemy.forward = false;
		}
	}
	
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		update(Gdx.graphics.getDeltaTime());
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		//RENDERING CODE GOES HERE
		
		//Scroll Speed Increase for level feel TODO balance int increases are too abrupt
		long currentGameTime = System.currentTimeMillis();
		if(currentGameTime - startGameTime > 50000)
		{
			levelSpeed++;
			startGameTime = System.currentTimeMillis();
			currentGameTime = System.currentTimeMillis();
		}
		camera.position.x += levelSpeed;
		//System.out.println(camera.position.x++);
		
		//Scrolling screen code
		if(camera.position.x -1200 / 2 > Images.sea_sprite1.getX()){
			Images.sea_sprite.setPosition(Images.sea_sprite1.getX(),0);
	        Images.sea_sprite1.setPosition(Images.sea_sprite.getX() + 1200, 0);
	    }
		//Enemy Spawning Timer (Randomness to come)
		long current = System.currentTimeMillis();
		//System.out.println(start - current);
		if(current - start > 22000)
		{
			if(firstEnemy == false)
			{
				spawnEnemy();	
				start = System.currentTimeMillis();
				current = System.currentTimeMillis();
			} else {
				if(!enemy.alive)
				{
					spawnEnemy();	
					start = System.currentTimeMillis();
					current = System.currentTimeMillis();
				}
			}
		}
		//Enemy Pursue
		if(enemy != null && !enemy.checkPassed(player.x)) //Stops pursuing if it has been passed
		{
			enemy.pursue(player.y,1);
		}
		//kill the enemy if it has gone off the screen
		else if(enemy != null && enemy.x < player.x + 2000)
		{
			enemy.killUnit();
		}
		else if(enemy != null)
		{
			enemy.chase(1);
			turnEnemy();
		}
		
		
		Images.sea_sprite.draw(batch);
		Images.sea_sprite1.draw(batch);	
		
		if(enemy != null)
			batch.draw(Images.enemy1_sprite,enemy.x,enemy.y);
		
		//batch.draw(Objects.sea_sprite, 0, 0);
		
		batch.draw(Images.turtle_sprite, player.x, player.y);
		font.draw(batch, "Score: " + Integer.toString((int)score), camera.position.x - 550, camera.position.y - 230);

		batch.end();
		
		}
	
	public void update(float dt){
		camera.update();
		player.handleInput();
		score += 0.03;
		//for shooting bubbles
		for(int i = 0; i < bubbles.size(); i++) {
			bubbles.get(i).update(dt);
			if (bubbles.get(i).shouldRemove()) {
				bubbles.remove(i);
				i--;
			}	
		}
	}
	
	public void draw() {
		//drawing bubbles
		for(int i = 0; i < bubbles.size(); i++) {
			bubbles.get(i).draw(sr);
			
		//drawing score - DOESN'T WORK YET
		batch.setColor(Color.CYAN);
		batch.begin();
		font.draw(batch, "Score: ", 1000, 1000);
		batch.end();
		}
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
	public int getCameraX() {
		return (int)camera.position.x;
	}
}
