package com.me.screens;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector3;
import com.me.deepblue.DeepBlue;
import com.me.deepblue.Images;
import com.me.entities.Fish;
import com.me.entities.Flock;

public class LeaderboardScreen implements Screen{

	int[] scores;
	String[] usernames;
	DeepBlue game;
	OrthographicCamera camera;
	Vector3 click;
	SpriteBatch batch;
	int wave_x;
	BufferedReader br;
	BufferedWriter wr;
	ArrayList<String> data;
	FileHandle fontFile;
	private BitmapFont font = new BitmapFont();
	public MenuScreen main_menu_screen;
	FreeTypeFontGenerator generator;
	//Flock flock = new Flock();
	boolean createFlock = true;
	
	public LeaderboardScreen (DeepBlue game, int wave_x) {
		this.game = game;
		this.wave_x = wave_x;
		Images.loadLeaderboards();
		
		camera = new OrthographicCamera();
		camera.setToOrtho(true,1200,600);
		
		batch = new SpriteBatch();
		click = new Vector3();
		
		// Coda-Heavy
				fontFile = Gdx.files.internal("data/Coda-Heavy.ttf");
				generator = new FreeTypeFontGenerator(fontFile);
				font = generator.generateFont(70);
				generator.dispose();
				font.setScale((float)0.75, -(float)0.45);

		
			/*br = new BufferedReader(new FileReader("/Users/westwiatt/Documents/Workspace/deep/deepBlue/leaderBoard.txt"));
			
			wr = new BufferedWriter(new FileWriter("/Users/westwiatt/Documents/Workspace/deep/deepBlue/leaderBoard.txt"));
			*/
		
		data = getData();
		
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
			br = new BufferedReader(new FileReader("/Users/martin/Desktop/DeepBlue/deepBlue6/leaderBoard.txt"));
			while((line = br.readLine()) != null)
			{
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
		String[] pieces = str.split("\\,");
		
		int newScore = Integer.parseInt(pieces[0]);
		
		for(int i = 0; i < data.size(); i++)
		{
			String[] data_pieces = data.get(i).split("\\,");
			int oldScore = Integer.getInteger(data_pieces[0]);
			
			if(newScore > oldScore)
			{
				data.add(str);
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
		ArrayList<String> data = getData();
		String[] sortedData = (String[]) data.toArray();
	    Arrays.sort(sortedData);
		
		for(int i = 0; i < sortedData.length;i++)
		{
			try
			{
			wr.write(sortedData[i]);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	/*
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
	*/
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		onClickListener();
		camera.update();
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		
		batch.draw(Images.blue_sprite, 0, 0);
		
		//Spawn the school make sure it hasn't already been spawned
		/*	if(createFlock)
			{
				spawnSchool();
				createFlock = false;
			}
		*/
		// DRAW AND UPDATE SCHOOL OF FISH 
		/*	flock.move();
			for (int k = 0; k < flock.fishes.size(); k++) {
				Fish fish = (Fish) flock.fishes.elementAt(k);
				batch.draw(fish.image, fish.getLocation().x + camera.position.x - 600, fish.getLocation().y);
			}
		*/
		if(wave_x >= 1200)
			wave_x = 0;
		batch.draw(Images.wave_sprite, wave_x, 0);
		batch.draw(Images.wave_sprite1, wave_x - 1200, 0);
		wave_x++;
		
		batch.draw(Images.table_sprite, 151, 182);
		
		font.draw(batch, "USERNAME",
				250, 190 + (font.getLineHeight()+20));
			font.draw(batch, "SCORE",
					770, 190 + (font.getLineHeight()+20));
		
		for(int i = 0; i < 10; i++) {
			String[] userdata = data.get(i).split(",",2);
			
			font.draw(batch, Integer.toString(i+1) + ". " + userdata[1],
				200, 190 - i*(font.getLineHeight()+20));
			font.draw(batch, userdata[0],
					800, 190 - i*(font.getLineHeight()+20));
		}
		
		batch.draw(Images.goBack_sprite, 10, 600-138);
		
		batch.end();
	}
	
	private void onClickListener() {
		if(Gdx.input.isTouched()){
			click.set(Gdx.input.getX(),Gdx.input.getY(),0);
			camera.unproject(click);
			if(click.x >= 10 && click.x <= 138 && click.y >= 600-138 && click.y <= 590){
				dispose();
				main_menu_screen = new MenuScreen(game, wave_x);
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
