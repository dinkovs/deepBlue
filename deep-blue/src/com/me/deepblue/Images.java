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
	public static Texture usernamebg_image;
	public static Sprite usernamebg_sprite;
	
	//Play Images
	public static Texture sea_image;
	public static Sprite sea_sprite;
	public static Sprite sea_sprite1;
	public static Texture turtle_image;
	public static Texture fish_image;
	public static Texture shark_image;
	public static Sprite shark_sprite;
	public static Texture scoreplus_image;
	public static Sprite scoreplus_sprite;
	public static Texture scorespeedup_image;
	public static Sprite scorespeedup_sprite;
	public static Texture fishpowerup_image;
	public static Sprite fishpowerup_sprite;
	public static Texture bubblebeam_image;
	public static Sprite bubblebeam_sprite;
	public static Texture hook_image;
	public static Sprite hook_sprite;
	public static Texture school_image;
	public static Sprite school_sprite;
	public static Texture school_image2;
	public static Sprite school_sprite2;
	public static Texture life_image;
	public static Sprite life_sprite;
	public static Texture lifepowerup_image;
	public static Sprite lifepowerup_sprite;
	public static Texture barracuda_image;
	public static Sprite barracuda_sprite;
	public static Texture jellyfish_image;
	public static Sprite boat_sprite;
	public static Texture boat_image;
	public static Texture gameOver_image;
	public static Sprite gameOver_sprite;
	
	//Leaderboard Images
	public static Texture goBack_image;
	public static Sprite goBack_sprite;
	public static Texture table_image;
	public static Sprite table_sprite;
	public static Texture leaderboardTitle_image;
	public static Sprite leaderboardTitle_sprite;

	
	
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
		
		usernamebg_image = new Texture(Gdx.files.internal("menu/usernamebg.png"));
		usernamebg_image.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		usernamebg_sprite = new Sprite(usernamebg_image);
		usernamebg_sprite.flip(false, true);
		
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
		
		turtle_image = new Texture(Gdx.files.internal("play/turtleSwimv2.png"));
		fish_image = new Texture(Gdx.files.internal("play/fish_spritesheetv2.png"));
		
		shark_image = new Texture(Gdx.files.internal("play/shark.png"));
		shark_sprite =  new Sprite(shark_image);
		shark_sprite.flip(false, true);
		
		barracuda_image = new Texture(Gdx.files.internal("play/barracuda_spritesheet.png"));
		
		scoreplus_image = new Texture(Gdx.files.internal("play/scoreplus.png"));
		scoreplus_sprite =  new Sprite(scoreplus_image);
		scoreplus_sprite.flip(false, true);
		
		scorespeedup_image = new Texture(Gdx.files.internal("play/scorespeedup.png"));
		scorespeedup_sprite =  new Sprite(scorespeedup_image);
		scorespeedup_sprite.flip(false, true);
		
		fishpowerup_image = new Texture(Gdx.files.internal("play/fishpowerup.png"));
		fishpowerup_sprite =  new Sprite(fishpowerup_image);
		fishpowerup_sprite.flip(false, true);
		
		bubblebeam_image = new Texture(Gdx.files.internal("play/bubblebeam.png"));
		bubblebeam_sprite =  new Sprite(bubblebeam_image);
		bubblebeam_sprite.flip(false, true);
		
		hook_image = new Texture(Gdx.files.internal("play/hook.png"));
		hook_sprite =  new Sprite(hook_image);
		hook_sprite.flip(false, true);
		
		life_image = new Texture(Gdx.files.internal("play/life.png"));
		life_sprite =  new Sprite(life_image);
		life_sprite.flip(false, true);
		
		lifepowerup_image = new Texture(Gdx.files.internal("play/lifeplus.png"));
		lifepowerup_sprite =  new Sprite(lifepowerup_image);
		lifepowerup_sprite.flip(false, true);
		
		gameOver_image = new Texture(Gdx.files.internal("play/gameover.png"));
		gameOver_sprite =  new Sprite(gameOver_image);
		gameOver_sprite.flip(false, true);
		
		school_image = new Texture(Gdx.files.internal("play/school.png"));
		school_sprite =  new Sprite(school_image);
		school_sprite.flip(true, true);
		
		school_image2 = new Texture(Gdx.files.internal("play/school2.png"));
		school_sprite2 =  new Sprite(school_image2);
		school_sprite2.flip(true, true);
		
		jellyfish_image = new Texture(Gdx.files.internal("play/jellyfish.png"));
		
		boat_image = new Texture(Gdx.files.internal("play/boat_hull.png"));
		boat_sprite =  new Sprite(boat_image);
		boat_sprite.flip(true, true);
		
		/*
		fish_image = new Texture(Gdx.files.internal("menu/fish.png"));
		fish_sprite = new Sprite(turtle_image);
		
		shark_image = new Texture(Gdx.files.internal("menu/shark.png"));
		shark_sprite = new Sprite(shark_image);
		*/
	}
	
	public static void loadLeaderboards() {
		goBack_image = new Texture(Gdx.files.internal("leaderboards/goback.png"));
		goBack_sprite = new Sprite(goBack_image);
		
		table_image = new Texture(Gdx.files.internal("leaderboards/table.png"));
		table_sprite = new Sprite(table_image);
		
		school_image = new Texture(Gdx.files.internal("play/school.png"));
		school_sprite =  new Sprite(school_image);
		school_sprite.flip(true, true);
		
		school_image2 = new Texture(Gdx.files.internal("play/school2.png"));
		school_sprite2 =  new Sprite(school_image2);
		school_sprite2.flip(true, true);
		
		leaderboardTitle_image = new Texture(Gdx.files.internal("leaderboards/leaderboard.png"));
		leaderboardTitle_sprite =  new Sprite(leaderboardTitle_image);
		leaderboardTitle_sprite.flip(false, true);
	}
	
}
