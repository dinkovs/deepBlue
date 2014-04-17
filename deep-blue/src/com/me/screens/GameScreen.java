package com.me.screens;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.me.deepblue.DeepBlue;
import com.me.deepblue.Images;
import com.me.entities.Bubble;
import com.me.entities.Eel;
import com.me.entities.Enemy;
import com.me.entities.Fish;
import com.me.entities.Flock;
import com.me.entities.Hook;
import com.me.entities.Jellyfish;
import com.me.entities.Player;
import com.me.entities.PowerUp;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Rectangle;

public class GameScreen implements Screen {

	public final int PLAYING = 0;
	public final int PAUSED = 1;
	public final int GAMEOVER = 2;
	public int gameState; 
	
	
	DeepBlue game;
	OrthographicCamera camera;
	public SpriteBatch batch;
	public MenuScreen main_menu_screen;
	FileHandle fontFile;
	FreeTypeFontGenerator generator;
	Player player;
	PowerUp scorePlus;
	PowerUp scoreSpeedUp;
	PowerUp fishPowerUp;
	PowerUp bubbleBeam;
	PowerUp lifePowerUp;
	Hook hook1;
	Hook hook2;
	float powerUpCountDown;
	float fishCountDown;
	ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	float score = 0;
	int tracker = 0;
	long start = System.currentTimeMillis(); // Keep track of enemy spawning
	long startGameTime = System.currentTimeMillis(); // Keep track of total game
														// length
	
	long jellyStartTime = System.currentTimeMillis();
	long jellyCurrentTime;
	long current; // Keep track of current time versus start time
	boolean firstEnemy = false;
	double levelSpeed = 1;
	Flock flock = new Flock();
	Random ran = new Random();
	ArrayList<Jellyfish> jellies = new ArrayList<Jellyfish>();
	ArrayList<String> data;
	boolean dataSaved;
	BufferedReader br;
	BufferedWriter wr;
	Eel eel;

	private BitmapFont font = new BitmapFont();
	// for the turtle
	Character speedy;
	ArrayList<Bubble> bubbles;
	ShapeRenderer sr; // <--- for bubbles, for now
	int t = 0;
	private float invincibleTimer;

	public GameScreen(DeepBlue game) {
		Images.loadPlay();
		this.game = game;

		camera = new OrthographicCamera();
		camera.setToOrtho(true, 1200, 600);

		batch = new SpriteBatch();

		// allows player to shoot bubbles
		sr = new ShapeRenderer();
		bubbles = new ArrayList<Bubble>();
		player = new Player(bubbles, this);

		// IMPLEMENT GAME OBJECTS
		scorePlus = new PowerUp(1, -800);
		scoreSpeedUp = new PowerUp(2, -800);
		fishPowerUp = new PowerUp(3, -800);
		bubbleBeam = new PowerUp(4, 1000);
		lifePowerUp = new PowerUp(5, -800);
		hook1 = new Hook();
		hook2 = new Hook();
		eel = new Eel(camera.position.x + 1000 , 390);
		data = getData();
		gameState = PLAYING;
		dataSaved = false;

		// cartoon blocks
		fontFile = Gdx.files.internal("menu/Cartoon Blocks.ttf");
		generator = new FreeTypeFontGenerator(fontFile);
		font = generator.generateFont(70);
		generator.dispose();
		font.setScale(1, -1);

	}

	/**
	 * This method gets all the current data in the text file
	 * @return
	 */
	public ArrayList<String> getData()
	{
		data = new ArrayList<String>();
		
		try
		{
			String line;
			br = new BufferedReader(
					new FileReader(
							System.getProperty("user.dir")+ "/leaderBoard.txt"));
			while ((line = br.readLine()) != null) {
				data.add(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}
	
	/**
	 * 
	 * @param str
	 * 	The string that you want to add so it should look liek this ->
	 * 
	 * 					140,Sponge Bob Square Tard	
	 * 
	 * @return
	 * 	returns a -1 if there is no new high 
	 *  returns index of new score
	 */
	public int checkNewScore(String str)
	{	
		String[] pieces = str.split(",",2);
		//System.out.println(pieces[0]);
		//System.out.println(Integer.parseInt(pieces[0]));
		int newScore = Integer.parseInt(pieces[0]);
		
		for(int i = 0; i < data.size(); i++)
		{
			String[] data_pieces = data.get(i).split(",",2);
			int oldScore = Integer.parseInt(data_pieces[0]);
			
			if(newScore > oldScore)
			{
				data.add(i,str);
				data.remove(data.size() - 1);
				return i;
			}	
		}
		return -1;
	}
	
	/**
	 * If there is a new score call this method to actually write it to the text file
	 */
	public void writeNewScores()
	{
		try {
			wr = new BufferedWriter(
					new FileWriter(
							System.getProperty("user.dir")+ "/leaderBoard.txt"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (int i = 0; i < 10; i++) {
			System.out.println(data.get(i));
			try {
				//System.out.println(data.get(i));
				wr.write(data.get(i));
				wr.newLine();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		try{
			wr.flush();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	// Set up School Spawning
	public void spawnSchool() {

		for (int i = 0; i < 20; i++) {
			Fish fish = new Fish(Color.YELLOW);
			fish.setSpeed(2);
			fish.setMaxTurnTheta(2);
			flock.addFish(fish);
		}
		for (int i = 0; i < 20; i++) {
			Fish fish = new Fish(Color.GREEN);
			fish.setSpeed(2);
			fish.setMaxTurnTheta(2);
			flock.addFish(fish);
		}
	}
	
	// Spawn Enemy Function
	public void spawnEnemies() {
		current = System.currentTimeMillis();
		if(current - start > (Math.random() * (20000-(levelSpeed*1000*2))) + (7000 - (levelSpeed*500)))
		{
			//Make sure enemy spawns off to the right of the screen with a random Y height
			enemies.add(new Enemy((int) camera.position.x + 700, 
					100 + (int) (Math.random() * ((500 - 100) + 1)),
					ran.nextInt(2)));
			start = System.currentTimeMillis();
			current = System.currentTimeMillis();
		}
	}
	
	//Check for enemies that have gone off the screen and remove them from the enemy array.
	public void removeEnemies() {
		for(int i = 0; i < enemies.size(); i++)
		{
			if(enemies.get(i).x < (player.x - 2000))
				enemies.remove(i);
		}
	}
	
	//Engage enemy AI depending on type of enemy
	public void enemyAI(int type, Enemy enemy)
	{
		if(type == 0) //shark
		{
			enemy.pursue(player.y, 1);
			enemy.x-=2;
		}
		else if(type == 1) //barracuda
		{
			enemy.burst(player.y, 2);
		}
	}
	
	//Jellyfish spawning
	public void spawnJellies()
	{
		jellyCurrentTime = System.currentTimeMillis();
		if(jellyCurrentTime - jellyStartTime > (Math.random() * (18000-(levelSpeed*1000*2))) + (8000 - (levelSpeed*1000)))
		{
			jellies.add(new Jellyfish((float) camera.position.x + 700, 
					(float) Math.random() * (500 - 50) + 50));
			jellyStartTime = System.currentTimeMillis();
			jellyCurrentTime = System.currentTimeMillis();
		}
	}
	
	//Remove Jellies
	public void removeJellies() {
		for(int i = 0; i < jellies.size(); i++)
		{
			if(jellies.get(i).x < (player.x - 2000))
				jellies.remove(i);
		}
	}
	
	//Spawn Eel
	public void spawnEel()
	{
		if (((System.currentTimeMillis() % 10000) < 1000))
		{
			if(eel.x < camera.position.x - 700)
				eel = new Eel(camera.position.x + 1000 , 390);
		}
	}

	@Override
	public void render(float delta) {
		
		Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		update(Gdx.graphics.getDeltaTime());
		
		switch(gameState) {
		case PLAYING:
			play(delta);
			break;
		case PAUSED:
			pause();
			break;
		case GAMEOVER:
			gameover();
			break;
		}
	}
	public void play(float delta) {
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		// RENDERING CODE GOES HERE

		if(Gdx.input.isKeyPressed(Keys.P))
				gameState = PAUSED;
		
		//Spawn the school make sure it hasn't already been spawned
		if(t != 1)
		{
			spawnSchool();
			t = 1;
		}

		// Scroll Speed Increase 
		long currentGameTime = System.currentTimeMillis();
		if (currentGameTime - startGameTime > 30000) {
			levelSpeed+= .5;
			startGameTime = System.currentTimeMillis();
			currentGameTime = System.currentTimeMillis();
			System.out.println(levelSpeed);
		}

		// Scrolling screen code
		if (camera.position.x - 1200 / 2 > Images.sea_sprite1.getX()) {
			Images.sea_sprite.setPosition(Images.sea_sprite1.getX(), 0);
			Images.sea_sprite1.setPosition(Images.sea_sprite.getX() + 1200, 0);
		}
		
		//Enemy Spawning
		spawnEnemies();
		
		//JellySpawning and removing
		spawnJellies();
		removeJellies();
		spawnEel();
		
		//Check for enemies that have gone off the screen
		removeEnemies();
		
		//Engage the Enemy AI
		for(int j = 0; j < enemies.size(); j++)
		{
			enemyAI(enemies.get(j).type, enemies.get(j));
		}
	

		// DRAW BACKGROUND
		Images.sea_sprite.draw(batch);
		Images.sea_sprite1.draw(batch);
		
		// DRAW AND UPDATE SCHOOL OF FISH 
		flock.move();
		for (int k = 0; k < flock.fishes.size(); k++) {
			Fish fish = (Fish) flock.fishes.elementAt(k);
			batch.draw(fish.image, fish.getLocation().x + camera.position.x - 600, fish.getLocation().y);
		}
		
		//DRAW THE ENEMIES
		for(int p = 0; p < enemies.size(); p++)
		{
			if(enemies.get(p).type == 1) {
				batch.draw(enemies.get(p).getImage(), enemies.get(p).x, enemies.get(p).y);
				enemies.get(p).boundingBox = new Rectangle (enemies.get(p).x + 10,enemies.get(p).y + 35,
						160,40);
			}
			else if(enemies.get(p).type == 0) {
				batch.draw(enemies.get(p).getImage(), enemies.get(p).x, enemies.get(p).y);
				enemies.get(p).boundingBox = new Rectangle (enemies.get(p).x,enemies.get(p).y + 100,
						100,80);
			}
		}
		
		//DRAW JELLIES
		for(int u = 0; u < jellies.size();u++)
		{
			batch.draw(jellies.get(u).getImage(), jellies.get(u).x, jellies.get(u).y);
			jellies.get(u).boundingBox = new Rectangle (jellies.get(u).x, jellies.get(u).y + 35 ,
					jellies.get(u).getImage().getRegionWidth(), jellies.get(u).getImage().getRegionHeight() - 35);
			
		}
		
		//DRAW EEL
		if(eel != null)
			batch.draw(eel.getImage(), eel.x, eel.y);

		// DRAW OBJECTS
		if (!scoreSpeedUp.activated)
			batch.draw(scoreSpeedUp.image, scoreSpeedUp.x, scoreSpeedUp.y);
		
		if (!scorePlus.activated)
			batch.draw(scorePlus.image, scorePlus.x, scorePlus.y);
		
		if (!fishPowerUp.activated)
			batch.draw(fishPowerUp.image, fishPowerUp.x, fishPowerUp.y);
		
		if (!bubbleBeam.activated)
			batch.draw(bubbleBeam.image, bubbleBeam.x, bubbleBeam.y);
		
		if (!lifePowerUp.activated)
			batch.draw(lifePowerUp.image, lifePowerUp.x, lifePowerUp.y);
		
		if ((int)(invincibleTimer*3)%2 == 0)
		{
			batch.draw(player.getImage(), player.x, player.y);
			player.boundingBox = new Rectangle (player.x + 10, player.y + 20, player.getImage().getRegionWidth() - 10, player.getImage().getRegionHeight() - 20);
		}
		
		batch.draw(hook1.image, hook1.x, hook1.y);
		batch.draw(Images.boat_sprite, hook1.x - 210, -3);
		batch.draw(hook2.image, hook2.x, hook2.y);
		batch.draw(Images.boat_sprite, hook2.x - 210, -3);

		// CHECK COLLISIONS
		for (int i = 0; i < enemies.size(); i++) {
			Enemy enemy = enemies.get(i);
			if (player.boundingBox.overlaps(enemy.boundingBox) && !player.invincible) {
				System.out.println("OMG NOOB");
				loseLife();
			}
			
		}
		for (int i = 0; i < jellies.size(); i++) {
			Jellyfish jellyfish = jellies.get(i);
			if (player.boundingBox.overlaps(jellyfish.boundingBox) && !player.invincible) {
				System.out.println("You've got to try this sandwich");
				loseLife();
			}
		}
		
		if (player.boundingBox.overlaps(scoreSpeedUp.boundingBox)
				&& !scoreSpeedUp.activated) {
			scoreSpeedUp.active = true;
			scoreSpeedUp.activated = true;
			powerUpCountDown = 10;
		}

		if (player.boundingBox.overlaps(scorePlus.boundingBox)
				&& !scorePlus.activated) {
			scorePlus.activated = true;
			score += 100;
		}
		
		if (player.boundingBox.overlaps(lifePowerUp.boundingBox)
				&& !lifePowerUp.activated) {
			lifePowerUp.activated = true;
			player.lives++;
		}

		if (player.boundingBox.overlaps(fishPowerUp.boundingBox)
				&& !fishPowerUp.activated) {
			fishPowerUp.activated = true;
			fishPowerUp.active = true;
			fishCountDown = 30;
			player.form = 1;
		}
		
		if (player.boundingBox.overlaps(bubbleBeam.boundingBox)
				&& !bubbleBeam.activated) {
			bubbleBeam.active = true;
			bubbleBeam.activated = true;
			score += 150;
			powerUpCountDown = 10;
			
		}

		if (player.boundingBox.overlaps(hook1.boundingBox) && player.y != 0
				&& !hook1.hooked) {
			hook1.pullUp();
			if ((hook1.y + hook1.image.getHeight() - 128) < player.y)
				player.pullUp();
		}

		if (player.boundingBox.overlaps(hook2.boundingBox) && player.y != 0
				&& !hook2.hooked) {
			hook2.pullUp();
			if ((hook2.y + hook2.image.getHeight() - 128) < player.y)
				player.pullUp();
		}
		if (player.y < 0 && !player.invincible)
			loseLife();
			

		// CHECK POWERUPS
		if (powerUpCountDown <= 0) {
			scoreSpeedUp.active = false;
		} else {
			powerUpCountDown -= .02;
			font.draw(batch,
					"Score Speed-Up: " + Integer.toString((int) powerUpCountDown),
					camera.position.x - 200, camera.position.y + 190);
			if (scoreSpeedUp.active == true)
				score += .1;
		}

		if (fishCountDown <= 0) {
			fishPowerUp.active = false;
			player.form = 0;
		} else {
			fishCountDown -= .02;
			font.draw(batch,
					"Fish Mode: " + Integer.toString((int) fishCountDown),
					camera.position.x - 150, camera.position.y + 240);
		}

		// RESET THE POWERUPS
		if ((System.currentTimeMillis() % 12000) < 1000)
			scoreSpeedUp.reset(camera.position.x);
		if ((System.currentTimeMillis() % 30000) < 1000)
			scorePlus.reset(camera.position.x);
		if ((System.currentTimeMillis() % 28000) < 1000)
			bubbleBeam.reset(camera.position.x);
		if ((System.currentTimeMillis() % lifePowerUp.resetTimer) < 1000)
			lifePowerUp.reset(camera.position.x);
		if ((System.currentTimeMillis() % fishPowerUp.resetTimer) < 1000)
			fishPowerUp.reset(camera.position.x);
		
		//Hook Resetting
		if ((System.currentTimeMillis() % (15000 - levelSpeed*1000)) < 1000)
			hook1.reset(camera.position.x);
		if ((System.currentTimeMillis() % (20000- levelSpeed*1000)) < 1000)
			hook2.reset(camera.position.x);
		

		// DISPLAY SCORE
		font.draw(batch, "Score: " + Integer.toString((int) score),
				camera.position.x - 590, 10);
		
		//DISPLAY LIFE
		for(int i = 1; i <= player.lives; i++) {
			int x = ((int) camera.position.x) + 600 - (i * (10 + Images.life_image.getWidth())) ;
			batch.draw(Images.life_sprite, x, 10);
		}
		
		if (player.invincible) {
			if (invincibleTimer <= 0)
				player.invincible = false;
			else 
				invincibleTimer -= 0.02;
		}
		
		camera.position.x += levelSpeed;
		// System.out.println(camera.position.x++);

		batch.end();

	}

	public void update(float dt) {
		camera.update();
		player.handleInput(camera.position.x);
		if (player.x <= camera.position.x - 600) {
			player.x = camera.position.x - 600;
		}
		score += 0.02;
		// for shooting bubbles
		for (int i = 0; i < bubbles.size(); i++) {
			bubbles.get(i).update(dt);
			if (bubbles.get(i).shouldRemove()) {
				bubbles.remove(i);
				i--;
			}
		}
	}
	
	public void loseLife() {
		if (player.lives > 0) {
			player.lives--;
			player.x = camera.position.x;
			player.y = camera.position.y;
			player.invincible = true;
			invincibleTimer = 5;
		}
		else {
			int i = checkNewScore(Integer.toString((int) score) + "," + game.username);
			if(!dataSaved && i >= 0)
				writeNewScores();
			gameState = GAMEOVER;
			dataSaved = true;
		}
	}

	public void draw() {
		// drawing bubbles
		for (int i = 0; i < bubbles.size(); i++) {
			bubbles.get(i).draw(sr);
		}
	}

	@Override
	public void show() {

	}

	@Override
	public void pause() {
		if(Gdx.input.isKeyPressed(Keys.U))
			gameState = PLAYING;
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(Images.pauseScreen_sprite, camera.position.x - 600, camera.position.y - 300);
		batch.end();
	}
	
	public void gameover() {
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(Images.gameOver_sprite, camera.position.x - 600, camera.position.y - 300);
		
		if(Gdx.input.isTouched()) {
			dispose();
			main_menu_screen = new MenuScreen(game, 0);
			game.setScreen(main_menu_screen);
		}
		
		batch.end();
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
