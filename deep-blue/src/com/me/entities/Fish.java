package com.me.entities;

import java.awt.Dimension;
import java.awt.Point;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.me.deepblue.Images;

public class Fish {

	public Sprite image;
	
	protected static int separationRange=15;
	protected static int detectionRange=50;

	protected Point location = new Point(0, 0); // The current location of this
												// fish
	private int currentTheta;
	protected Color color;

	protected static Dimension map = new Dimension(1200, 600);

	private double currentSpeed;
	private int maxTurnTheta;

	Fish(int x, int y, int theta, Color color) {
		location.x = x;
		location.y = y;
		currentTheta = theta;
		this.color = color;
	}

	public Fish(Color color) {
		this((int) (Math.random() * map.width),
				(int) (Math.random() * map.height),
				(int) (Math.random() * 360), color);
		if(color == Color.GREEN)
			image = Images.school_sprite;
		else
			image = Images.school_sprite2;
	}

	
	public void move(int newHeading) {
		// determine if it is better to turn left or right for the new heading
		int left = (newHeading - currentTheta + 360) % 360;
		int right = (currentTheta - newHeading + 360) % 360;

		int thetaChange = 0;
		if (left < right) {
			// if left > than the max turn, then we can't fully adopt the new
			// heading
			thetaChange = Math.min(maxTurnTheta, left);
		} else {
			// right turns are negative degrees
			thetaChange = -Math.min(maxTurnTheta, right);
		}

		// Make the turn
		currentTheta = (currentTheta + thetaChange + 360) % 360;

		location.x += (int) (currentSpeed * Math.cos(currentTheta * Math.PI
				/ 180))
				+ map.width;
		location.x %= map.width;
		location.y -= (int) (currentSpeed * Math.sin(currentTheta * Math.PI
				/ 180))
				- map.height;
		location.y %= map.height;
	}

	
	public int getDistance(Fish otherFish) {
		//distance between this fish and another one
		int dX = otherFish.getLocation().x - location.x;
		int dY = otherFish.getLocation().y - location.y;

		return (int) Math.sqrt(Math.pow(dX, 2) + Math.pow(dY, 2));
	}

	
	public int getDistance(Point p) {
		//Distance between fish and a point
		int dX = p.x - location.x;
		int dY = p.y - location.y;

		return (int) Math.sqrt(Math.pow(dX, 2) + Math.pow(dY, 2));
	}

	static void setMapSize(Dimension newSize) {
		map = newSize;
	}

	public int getMaxTurnTheta() {
		return maxTurnTheta;
	}

	public void setMaxTurnTheta(int theta) {
		maxTurnTheta = theta;
	}

	public int getTheta() {
		return currentTheta;
	}

	public Point getLocation() {
		return location;
	}

	public void setSpeed(double speed) {
		currentSpeed = speed;
	}

	public Color getColor() {
		return color;
	}

}
