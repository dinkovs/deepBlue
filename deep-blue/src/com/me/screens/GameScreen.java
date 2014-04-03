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
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.me.deepblue.DeepBlue;
import com.me.deepblue.Images;
import com.me.entities.Bubble;
import com.me.entities.Enemy;
import com.me.entities.Fish;
import com.me.entities.Flock;
import com.me.entities.Hook;
import com.me.entities.Player;
import com.me.entities.PowerUp;
import com.swarmconnect.SwarmLeaderboard;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Intersector;

public class GameScreen implements Screen {

	DeepBlue game;
	OrthographicCamera camera;
	public SpriteBatch batch;
	FileHandle fontFile;
	FreeTypeFontGenerator generator;
	Player player;
	PowerUp scorePlus;
	PowerUp scoreSpeedUp;
	PowerUp fishPowerUp;
	Hook hook1;
	Hook hook2;
	float powerUpCountDown;
	float fishCountDown;
	Enemy enemy;
	float score = 0;
	int tracker = 0;
	long start = System.currentTimeMillis(); // Keep track of enemy spawning
	long startGameTime = System.currentTimeMillis(); // Keep track of total game
														// length
	boolean firstEnemy = false;
	int levelSpeed = 1;
	Flock flock = new Flock();

	private BitmapFont font = new BitmapFont();
	// for the turtle
	Character speedy;
	ArrayList<Bubble> bubbles;
	ShapeRenderer sr; // <--- for bubbles, for now
	int t = 0;

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
		scorePlus = new PowerUp(1, 800);
		scoreSpeedUp = new PowerUp(2, 800);
		fishPowerUp = new PowerUp(3, 1200);
		hook1 = new Hook();
		hook2 = new Hook();

		// cartoon blocks
		fontFile = Gdx.files.internal("menu/Cartoon Blocks.ttf");
		generator = new FreeTypeFontGenerator(fontFile);
		font = generator.generateFont(70);
		generator.dispose();
		font.setScale(1, -1);
	}

	// Set up School Spawning
	public void spawnSchool() {

		for (int i = 0; i < 30; i++) {
			Fish fish = new Fish(Color.YELLOW);
			fish.setSpeed(2);
			fish.setMaxTurnTheta(2);
			flock.addFish(fish);
		}
		for (int i = 0; i < 30; i++) {
			Fish fish = new Fish(Color.GREEN);
			fish.setSpeed(2);
			fish.setMaxTurnTheta(2);
			flock.addFish(fish);
		}
	}

	// Spawn Enemy1 Function
	public void spawnEnemy() {
		// TODO Edit Spawn enemy function based on enemies height.
		enemy = new Enemy((int) camera.position.x + 700,
				100 + (int) (Math.random() * ((500 - 100) + 1)));
		firstEnemy = true;
	}

	// Flip Enemy to chase function
	public void turnEnemy() {
		if (enemy.forward) {
			Images.enemy1_sprite.flip(true, false);
			enemy.forward = false;
		}
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		update(Gdx.graphics.getDeltaTime());
		if(t != 1)
		{
			spawnSchool();
			t = 1;
		}

		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		// RENDERING CODE GOES HERE

		// Scroll Speed Increase for level feel TODO balance int increases are
		// too abrupt
		long currentGameTime = System.currentTimeMillis();
		if (currentGameTime - startGameTime > 50000) {
			levelSpeed++;
			startGameTime = System.currentTimeMillis();
			currentGameTime = System.currentTimeMillis();
		}
		camera.position.x += levelSpeed;
		// System.out.println(camera.position.x++);

		// Scrolling screen code
		if (camera.position.x - 1200 / 2 > Images.sea_sprite1.getX()) {
			Images.sea_sprite.setPosition(Images.sea_sprite1.getX(), 0);
			Images.sea_sprite1.setPosition(Images.sea_sprite.getX() + 1200, 0);
		}
		// Enemy Spawning Timer (Randomness to come)
		long current = System.currentTimeMillis();
		// System.out.println(start - current);
		if (current - start > 22000) {
			if (firstEnemy == false) {
				spawnEnemy();
				start = System.currentTimeMillis();
				current = System.currentTimeMillis();
			} else {
				if (!enemy.alive) {
					spawnEnemy();
					start = System.currentTimeMillis();
					current = System.currentTimeMillis();
				}
			}
		}
		// Enemy Pursue
		if (enemy != null && !enemy.checkPassed(player.x)) // Stops pursuing if
															// it has been
															// passed
		{
			enemy.pursue(player.y, 1);
		}
		// kill the enemy if it has gone off the screen
		else if (enemy != null && enemy.x < player.x + 2000) {
			enemy.killUnit();
		} else if (enemy != null) {
			enemy.chase(1);
			turnEnemy();
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

		// DRAW OBJECTS
		if (enemy != null)
			batch.draw(Images.enemy1_sprite, enemy.x, enemy.y);
		if (!scoreSpeedUp.activated)
			batch.draw(scoreSpeedUp.image, scoreSpeedUp.x, scoreSpeedUp.y);
		if (!scorePlus.activated)
			batch.draw(scorePlus.image, scorePlus.x, scorePlus.y);
		if (!fishPowerUp.activated)
			batch.draw(fishPowerUp.image, fishPowerUp.x, fishPowerUp.y);
		batch.draw(player.getImage(), player.x, player.y);
		batch.draw(hook1.image, hook1.x, hook1.y);
		batch.draw(hook2.image, hook2.x, hook2.y);

		

		// CHECK COLLISIONS

		/*
		 * if (player.boundingBox.overlaps(enemy.boundingBox)) {
		 * scoreSpeedUp.active = true; scoreSpeedUp.activated = true;
		 * powerUpCountDown = 10; }
		 */
		
		

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

		if (player.boundingBox.overlaps(fishPowerUp.boundingBox)
				&& !fishPowerUp.activated) {
			fishPowerUp.activated = true;
			fishPowerUp.active = true;
			fishCountDown = 30;
			player.form = 1;
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

		// CHECK POWERUPS
		if (powerUpCountDown <= 0) {
			scoreSpeedUp.active = false;
		} else {
			powerUpCountDown -= .02;
			font.draw(batch,
					"PowerUp: " + Integer.toString((int) powerUpCountDown),
					camera.position.x - 150, camera.position.y);
			if (scoreSpeedUp.active == true)
				score += .1;
		}

		if (fishCountDown <= 0) {
			fishPowerUp.active = false;
		} else {
			fishCountDown -= .02;
			font.draw(batch,
					"Fish Mode: " + Integer.toString((int) fishCountDown),
					camera.position.x - 150, camera.position.y + 300);
		}

		// RESET THE POWERUPS
		if ((System.currentTimeMillis() % 22000) < 1000)
			scoreSpeedUp.reset(camera.position.x);
		if ((System.currentTimeMillis() % 30000) < 1000)
			scorePlus.reset(camera.position.x);
		if ((System.currentTimeMillis() % 25000) < 1000)
			hook1.reset(camera.position.x);
		if ((System.currentTimeMillis() % 30000) < 1000)
			hook2.reset(camera.position.x);

		// DISPLAY SCORE
		font.draw(batch, "Score: " + Integer.toString((int) score),
				camera.position.x - 550, camera.position.y - 230);

		batch.end();

		// UPLOAD SCORE TO LEADERBOARD
		if (false) {
			SwarmLeaderboard.submitScore(15236, score);
		}

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
		return (int) camera.position.x;
	}
}
