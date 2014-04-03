package com.me.entities;

import java.awt.Dimension;
import java.awt.Point;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.me.deepblue.Images;

public class Fish {

	public Sprite image;
	
	protected static int separationRange=15;
	protected static int detectionRange=60;

	protected Point location = new Point(0, 0); // The current location of this
												// fish
	private int currentTheta;
	protected Color color;

	protected static Dimension map = new Dimension(1200, 600);

	private double currentSpeed;
	private int maxTurnTheta;
	public Rectangle boundingBox;

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
		boundingBox = new Rectangle (location.x,location.y,20,16);
		if(color == Color.GREEN)
			image = Images.school_sprite;
		else
			image = Images.school_sprite2;
	}

	/**
	 * Causes the bird to attempt to face a new direction. Based on
	 * maxTurnTheta, the bird may not be able to complete the turn.
	 * 
	 * @param newHeading
	 *            The direction in degrees that the bird should turn toward.
	 */
	public void move(int newHeading) {
		// determine if it is better to turn left or right for the new heading
		int left = (newHeading - currentTheta + 360) % 360;
		int right = (currentTheta - newHeading + 360) % 360;

		// after deciding which way to turn, find out if we can turn that much
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

		// Now move currentSpeed pixels in the direction the bird now faces.
		// Note: Because values are truncated, a speed of 1 will result in no
		// movement unless the bird is moving exactly vertically or
		// horizontally.
		location.x += (int) (currentSpeed * Math.cos(currentTheta * Math.PI
				/ 180))
				+ map.width;
		location.x %= map.width;
		location.y -= (int) (currentSpeed * Math.sin(currentTheta * Math.PI
				/ 180))
				- map.height;
		location.y %= map.height;
	}

	/*
	public void draw(Graphics g) {
		g.setColor(this.color);
		g.fillArc(location.x - 12, location.y - 12, 24, 24,
				currentTheta + 180 - 20, 40);

		if (showRanges) {
			drawRanges(g);
		}
	}
	*/
	
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
