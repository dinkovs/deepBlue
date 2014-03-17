package com.me.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.me.deepblue.DeepBlue;
import com.me.deepblue.Objects;

public class MenuScreen implements Screen{

	DeepBlue game;
	OrthographicCamera camera;
	Vector3 click;
	SpriteBatch batch;
	int wave_x = 0;
	
	public GameScreen play_screen;
	
	public MenuScreen(DeepBlue game){
		Objects.loadMainMenu();
		this.game = game;
		
		
		camera = new OrthographicCamera();
		camera.setToOrtho(true,1200,600);
		
		batch = new SpriteBatch();
		click = new Vector3();
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		camera.update();
		onClickListener();
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		
		//RENDERING CODE GOES HERE
		if(wave_x >= 1200)
			wave_x = 0;
		batch.draw(Objects.wave_sprite, wave_x, 0);
		batch.draw(Objects.wave_sprite1, wave_x - 1200, 0);
		wave_x++;
		
		batch.draw(Objects.background_sprite, 0, 0);
		batch.draw(Objects.tutorial_sprite, 359, 377);
		batch.draw(Objects.play_sprite, 57, 360);
		batch.draw(Objects.leaderboards_sprite, 640, 371);
		batch.draw(Objects.credits_sprite, 919, 348);
		
		batch.end();
	}

	private void onClickListener() {
		if(Gdx.input.isTouched()){
			click.set(Gdx.input.getX(),Gdx.input.getY(),0);
			camera.unproject(click);
			if(click.x >= 881 && click.x <= 1121 && click.y >= 501 && click.y <= 650){
				dispose();
				play_screen = new GameScreen(game);
				game.setScreen(play_screen);
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