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
	
	@Override
	public void create() {
		main_menu_screen = new MenuScreen(this, 0);
		
		setScreen(main_menu_screen);
		
		//background main menu music
		Music mainMusic;
		mainMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/mainMusic.mp3"));
		mainMusic.setLooping(true);
		mainMusic.play();
		
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
				// TODO Auto-generated method stub
				
			}
			
			}, "Log-In", "<Enter Username Here>");
		}

		
	}

