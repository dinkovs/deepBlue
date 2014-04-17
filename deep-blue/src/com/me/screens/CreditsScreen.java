package com.me.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.me.deepblue.DeepBlue;
import com.me.deepblue.Images;

public class CreditsScreen implements Screen{
	
	public MenuScreen main_menu_screen;
	DeepBlue game;
	OrthographicCamera camera;
	Vector3 click;
	SpriteBatch batch;
	
	public CreditsScreen (DeepBlue game) {
		this.game = game;
		Images.loadCredits();
		
		camera = new OrthographicCamera();
		camera.setToOrtho(true,1200,600);
		
		batch = new SpriteBatch();
		click = new Vector3();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		onClickListener();
		camera.update();
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(Images.credit_sprite, 0, 0);
		batch.end();
		
	}

	private void onClickListener() {
		if(Gdx.input.isTouched()){
			click.set(Gdx.input.getX(),Gdx.input.getY(),0);
			camera.unproject(click);
			if(click.x >= 10 && click.x <= 200 && click.y >= 600-138 && click.y <= 590){
				dispose();
				main_menu_screen = new MenuScreen(game, 0);
				game.setScreen(main_menu_screen);
			}
		}
	}
	
	
	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
