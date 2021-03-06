package com.me.deepblue;


import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.me.screens.MenuScreen;

public class DeepBlue extends Game implements ApplicationListener{

	public MenuScreen main_menu_screen;
	public SpriteBatch batch;
	public boolean enteredName;
	public String username;
	public Music subMusic;
	
	@Override
	public void create() {
		main_menu_screen = new MenuScreen(this, 0);
		
		subMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/gameMusic.mp3"));
		
		setScreen(main_menu_screen);

	
			
		//username dialog box
		enteredName = false;
		Gdx.input.getTextInput(new TextInputListener() {

			@Override
			public void input(String text) {
				enteredName = true;
				username = text;
				System.out.println(username);
			}

			@Override
			public void canceled() {
				Gdx.app.exit();
			}
			
			}, "Log-In (12 characters max)", "<Username>");
		}

		
	}

