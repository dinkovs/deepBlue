package com.me.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.me.deepblue.DeepBlue;
import com.me.deepblue.Images;

public class MenuScreen implements Screen {

	DeepBlue game;
	OrthographicCamera camera;
	Vector3 click;
	SpriteBatch batch;
	int wave_x;
	Music mainMusic;
	
	
	public GameScreen play_screen;
	public LeaderboardScreen leaderboard_screen;
	public CreditsScreen credits_screen;
	public InstructionsScreen instructions_screen;
	
	public MenuScreen(DeepBlue game, int wave_x){
		Images.loadMainMenu();
		this.game = game;
		this.wave_x = wave_x;
		
		camera = new OrthographicCamera();
		camera.setToOrtho(true,1200,600);
		
		batch = new SpriteBatch();
		click = new Vector3();
		
		mainMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/mainMusic.mp3"));
		mainMusic.setLooping(true);
		mainMusic.play();
		
	}
		
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		if(game.subMusic.isPlaying()) {
			game.subMusic.stop();
			game.subMusic.dispose();
		}
		
		camera.update();
		onClickListener();
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		
		//RENDERING CODE GOES HERE
		batch.draw(Images.blue_sprite, 0, 0);
		
		if(wave_x >= 1200)
			wave_x = 0;
		batch.draw(Images.wave_sprite, wave_x, 0);
		batch.draw(Images.wave_sprite1, wave_x - 1200, 0);
		wave_x++;
		

		batch.draw(Images.background_sprite, 0, 0);
		batch.draw(Images.tutorial_sprite, 359, 377);
		batch.draw(Images.play_sprite, 57, 360);
		batch.draw(Images.leaderboards_sprite, 640, 371);
		batch.draw(Images.credits_sprite, 919, 348);
		if(!game.enteredName)
			batch.draw(Images.usernamebg_sprite, 0, 0);
		
		batch.end();
	}

	private void onClickListener() {
		if(Gdx.input.isTouched()){
			mainMusic.stop();
			click.set(Gdx.input.getX(),Gdx.input.getY(),0);
			camera.unproject(click);
			if(click.x >= 57 && click.x <= 290 && click.y >= 360 && click.y <= 504){
				dispose();
				play_screen = new GameScreen(game);
				game.setScreen(play_screen);
				game.subMusic.play();
				game.subMusic.setVolume(.2f);
			}
			else if(click.x >= 639 && click.x <= 847 && click.y >= 372 && click.y <= 510){
				dispose();
				leaderboard_screen = new LeaderboardScreen(game, wave_x);
				game.setScreen(leaderboard_screen);
			}
			else if(click.x >= 356 && click.x <= 591 && click.y >= 380 && click.y <= 511){
				dispose();
				instructions_screen = new InstructionsScreen(game);
				game.setScreen(instructions_screen);
			}
			else if(click.x >= 918 && click.x <= 1146 && click.y >= 350 && click.y <= 480){
				dispose();
				credits_screen = new CreditsScreen(game);
				game.setScreen(credits_screen);
			}
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
}