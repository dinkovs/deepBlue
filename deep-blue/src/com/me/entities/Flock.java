package com.me.entities;

import java.awt.Dimension;
import java.awt.Point;
import java.util.*;

public class Flock {

	public Vector fishes; // List of all fish in the map
	public static int separationRange = 15;
	public static int detectionRange = 50;
	private static Dimension map = new Dimension(1200, 600);

	public Flock() {
		fishes = new Vector(40, 10);
	}

	public void addFish(Fish fish) {
		fishes.addElement(fish);
	}

	synchronized public Vector move() {
		int movingFish = 0;

		Vector removedFish = new Vector(2, 1);


		while (movingFish < fishes.size()) {

			Fish fish = (Fish) fishes.elementAt(movingFish);
			fish.move(generalHeading(fish));

			movingFish++;
		}

		return removedFish;
	}


	public Point closestLocation(Point p, Point otherPoint) {
		int dX = Math.abs(otherPoint.x - p.x);
		int dY = Math.abs(otherPoint.y - p.y);
		int x = otherPoint.x;
		int y = otherPoint.y;

		if (Math.abs(map.width - otherPoint.x + p.x) < dX) {
			dX = map.width - otherPoint.x + p.x;
			x = otherPoint.x - map.width;
		}
		if (Math.abs(map.width - p.x + otherPoint.x) < dX) {
			dX = map.width - p.x + otherPoint.x;
			x = otherPoint.x + map.width;
		}

		if (Math.abs(map.height - otherPoint.y + p.y) < dY) {
			dY = map.height - otherPoint.y + p.y;
			y = otherPoint.y - map.height;
		}
		if (Math.abs(map.height - p.y + otherPoint.y) < dY) {
			dY = map.height - p.y + otherPoint.y;
			y = otherPoint.y + map.height;
		}

		return new Point(x, y);
	}

    private int generalHeading(Fish fish) {
          
        Point target = new Point(0, 0);
        int numFish = 0;
        
        for (int i=0; i < fishes.size(); i++) {
            Fish otherFish = (Fish)fishes.elementAt(i);
            Point otherLocation = closestLocation(fish.getLocation(), otherFish.getLocation());

            int distance = fish.getDistance(otherLocation);
            
            if (!fish.equals(otherFish) && distance > 0 && distance <= detectionRange)
            {
               
            	if (fish.getColor().equals(otherFish.getColor())) {
		            Point align = new Point((int)(100 * Math.cos(otherFish.getTheta() * Math.PI/180)),
		            (int)(-100 * Math.sin(otherFish.getTheta() * Math.PI/180)));
		            align = normalisePoint(align, 100); // alignment weight is 100
		            boolean tooClose = (distance < separationRange);
		            double weight = 200.0;
		            if (tooClose) {
		                weight *= Math.pow(1 - (double) distance / separationRange, 2);
		            }
		            else {
		                weight *= - Math.pow((double)( distance - separationRange ) / ( detectionRange - separationRange ), 2);
		            }
		            Point attract = sumPoints(otherLocation, -1.0, fish.getLocation(), 1.0);
		            attract = normalisePoint(attract, weight); // weight is variable
		            Point dist = sumPoints(align, 1.0, attract, 1.0);
		            dist = normalisePoint(dist, 100); // final weight is 100
		            target = sumPoints(target, 1.0, dist, 1.0);
            	}
            	//Other type of fish in the flock we will avoid them
            	else {
                    Point dist = sumPoints(fish.getLocation(), 1.0, otherLocation, -1.0);
                    dist = normalisePoint(dist, 1000);
                    double weight = Math.pow((1 - (double)distance/detectionRange), 2);
                    target = sumPoints(target, 1.0, dist, weight); // weight is variable
                }
               
                numFish++;
            }
        }

        if (numFish == 0) {
            return fish.getTheta();
        }
        else { 
            target = sumPoints(fish.getLocation(), 1.0, target, 1/(double)numFish);
        }
        
        // Turn the target location into a direction in degrees
        int targetTheta = (int)(180/Math.PI * Math.atan2(fish.getLocation().y - target.y, target.x - fish.getLocation().x));
        // Make sure the angle is 0-360
        return (targetTheta + 360) % 360; 
    }

	public Point sumPoints(Point p1, double w1, Point p2, double w2) {
		return new Point((int) (w1 * p1.x + w2 * p2.x), (int) (w1 * p1.y + w2
				* p2.y));
	}

	public double sizeOfPoint(Point p) {
		return Math.sqrt(Math.pow(p.x, 2) + Math.pow(p.y, 2));
	}

	public Point normalisePoint(Point p, double n) {
		if (sizeOfPoint(p) == 0.0) {
			return p;
		} else {
			double weight = n / sizeOfPoint(p);
			return new Point((int) (p.x * weight), (int) (p.y * weight));
		}
	}
}
