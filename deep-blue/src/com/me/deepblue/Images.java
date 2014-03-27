package com.me.deepblue;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Images {

	//Main Menu Images
	public static Texture blue_image;
	public static Sprite blue_sprite;
	public static Texture wave_image;
	public static Sprite wave_sprite;
	public static Sprite wave_sprite1;
	public static Texture background_image;
	public static Sprite background_sprite;
	public static Texture play_image;
	public static Sprite play_sprite;
	public static Texture tutorial_image;
	public static Sprite tutorial_sprite;
	public static Texture leaderboards_image;
	public static Sprite leaderboards_sprite;
	public static Texture credits_image;
	public static Sprite credits_sprite;
	
	//Play Images
	public static Texture sea_image;
	public static Sprite sea_sprite;
	public static Sprite sea_sprite1;
	public static Texture turtle_image;
	public static Sprite turtle_sprite;
	public static Texture fish_image;
	public static Sprite fish_sprite;
	public static Texture shark_image;
	public static Sprite shark_sprite;
	public static Texture enemy1_image;
	public static Sprite enemy1_sprite;
	public static Texture scoreplus_image;
	public static Sprite scoreplus_sprite;
	public static Texture scorespeedup_image;
	public static Sprite scorespeedup_sprite;
	
	public static void loadMainMenu(){
		blue_image = new Texture(Gdx.files.internal("menu/blueSea.jpg"));
		blue_image.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		blue_sprite = new Sprite(blue_image);
		blue_sprite.flip(false, true);
		
		wave_image = new Texture(Gdx.files.internal("menu/waves.png"));
		wave_image.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		wave_sprite = new Sprite(wave_image);
		wave_sprite.flip(false, true);
		wave_sprite1 = new Sprite(wave_image);
		wave_sprite1.flip(false, true);
		
		background_image = new Texture(Gdx.files.internal("menu/menubg.png"));
		background_image.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		background_sprite = new Sprite(background_image);
		background_sprite.flip(false, true);
		
		play_image = new Texture(Gdx.files.internal("menu/play.png"));
		play_image.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		play_sprite = new Sprite(play_image);
		play_sprite.flip(false, true);
		
		tutorial_image = new Texture(Gdx.files.internal("menu/tutorial.png"));
		tutorial_image.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		tutorial_sprite = new Sprite(tutorial_image);
		tutorial_sprite.flip(false, true);
		
		leaderboards_image = new Texture(Gdx.files.internal("menu/leaderboards.png"));
		leaderboards_image.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		leaderboards_sprite = new Sprite(leaderboards_image);
		leaderboards_sprite.flip(false, true);
		
		credits_image = new Texture(Gdx.files.internal("menu/credits.png"));
		credits_image.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		credits_sprite = new Sprite(credits_image);
		credits_sprite.flip(false, true);
	}
	
	public static void loadPlay(){
		sea_image = new Texture(Gdx.files.internal("play/background.jpg"));
		sea_image.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		sea_sprite = new Sprite(sea_image,0,0,1200,600);
		sea_sprite.flip(false, true);
		sea_sprite1 = new Sprite(sea_image,0,0,1200,600);
		sea_sprite1.flip(false, true);
		
		turtle_image = new Texture(Gdx.files.internal("play/turtle.png"));
		
		enemy1_image = new Texture(Gdx.files.internal("play/shark.png"));
		enemy1_sprite =  new Sprite(enemy1_image);
		enemy1_sprite.flip(false, true);
		
		scoreplus_image = new Texture(Gdx.files.internal("play/scoreplus.png"));
		scoreplus_sprite =  new Sprite(scoreplus_image);
		scoreplus_sprite.flip(false, true);
		
		scorespeedup_image = new Texture(Gdx.files.internal("play/scorespeedup.png"));
		scorespeedup_sprite =  new Sprite(scorespeedup_image);
		scorespeedup_sprite.flip(false, true);
		
		/*
		fish_image = new Texture(Gdx.files.internal("menu/fish.png"));
		fish_sprite = new Sprite(turtle_image);
		
		shark_image = new Texture(Gdx.files.internal("menu/shark.png"));
		shark_sprite = new Sprite(shark_image);
		*/
	}
	
}
