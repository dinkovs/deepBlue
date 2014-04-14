package com.me.deepblue;


import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.me.screens.MenuScreen;
import com.me.deepblue.ScoreoidConfig;

public class DeepBlue extends Game implements ApplicationListener{

	public MenuScreen main_menu_screen;
	public SpriteBatch batch;
	
	@Override
	public void create() {
		main_menu_screen = new MenuScreen(this, 0);
		
		setScreen(main_menu_screen);
		
		//background main menu music
		Music mainMusic;
		mainMusic = Gdx.audio.newMusic(Gdx.files.internal("menu/mainMusic.mp3"));
		mainMusic.setLooping(true);
		mainMusic.play();

		}
	}

