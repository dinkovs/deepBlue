package com.me.entities;

import java.awt.Dimension;
import java.awt.Point;
import java.util.*;

public class Flock {

	public Vector fishes; // List of all fish in the map
	public static int separationRange = 15;
	public static int detectionRange = 60;
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

		// Loop through each bird to move.
		// Done this way, because the vector can change in the loop
		while (movingFish < fishes.size()) {

			Fish fish = (Fish) fishes.elementAt(movingFish);
			fish.move(generalHeading(fish));

			movingFish++;
		}

		return removedFish;
	}

	/**
	 * Sometimes, two birds are closer together if you go off one edge of the
	 * map and return on the other. This function will convert the "other point"
	 * into a point that closest to the point p, even if it is off the map.
	 * 
	 * @param p
	 *            The point to measure the distance to.
	 * @param otherPoint
	 *            The point to measure the distance from.
	 */
	public Point closestLocation(Point p, Point otherPoint) {
		int dX = Math.abs(otherPoint.x - p.x);
		int dY = Math.abs(otherPoint.y - p.y);
		int x = otherPoint.x;
		int y = otherPoint.y;

		// now see if the distance between birds is closer if going off one
		// side of the map and onto the other.
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

	/**
	 * This function determines the direction a Bird will turn towards for this
	 * step. The bird looks at every other bird and obstacle on the map to see
	 * if it is within the detection range. Predator will move toward birds.
	 * Birds will avoid birds of a different color and all obstacles.
	 * 
	 * @param bird
	 *            The bird to get the heading for
	 */
	
    private int generalHeading(Fish fish) {
        
        // Sum the location of all birds that are within our detection range
        Point target = new Point(0, 0);
        // Number of birds that are within the detection range
        int numFish = 0;
        
        // Loop thorough each bird to see if it is within our detection range
        for (int i=0; i < fishes.size(); i++) {
            Fish otherFish = (Fish)fishes.elementAt(i);
            Point otherLocation = closestLocation(fish.getLocation(), otherFish.getLocation());
            
            // get distance to the other Bird. Note, this distance accounts for
            // the fact that the shortest path may be through the edge of the map
            int distance = fish.getDistance(otherLocation);
            
            if (!fish.equals(otherFish) && distance > 0 && distance <= detectionRange)
            {
                /*
                 * If the other bird is the same color, the algorithm tells the
                 * bird to align its direction with the other Bird. If the distance
                 * between them differs from DetectionRange then a weighted forces
                 * is applied to move it towards that distance. This force is
                 * stronger when the birds are very close or towards the limit of detection.
                 */
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
            	else {
                    Point dist = sumPoints(fish.getLocation(), 1.0, otherLocation, -1.0);
                    dist = normalisePoint(dist, 1000);
                    double weight = Math.pow((1 - (double)distance/detectionRange), 2);
                    target = sumPoints(target, 1.0, dist, weight); // weight is variable
                }
               
                numFish++;
            }
        }
         // if no birds are close enough to detect, continue moving is same direction.
        if (numFish == 0) {
            return fish.getTheta();
        }
        else { // average target points and add to position
            target = sumPoints(fish.getLocation(), 1.0, target, 1/(double)numFish);
        }
        
        // Turn the target location into a direction in degrees
        int targetTheta = (int)(180/Math.PI * Math.atan2(fish.getLocation().y - target.y, target.x - fish.getLocation().x));
        // Make sure the angle is 0-360
        return (targetTheta + 360) % 360; // angle for Bird to steer towards
    }

	/**
	 * Add two points together, scaling both according to their weight
	 * 
	 * @param p1
	 *            The first point to add
	 * @param w1
	 *            The weight of the first point
	 * @param p2
	 *            The second point to add
	 * @param w2
	 *            The weight of the second point
	 */
	public Point sumPoints(Point p1, double w1, Point p2, double w2) {
		return new Point((int) (w1 * p1.x + w2 * p2.x), (int) (w1 * p1.y + w2
				* p2.y));
	}

	/**
	 * Distance from the top left of the map to a given point
	 * 
	 * @param p
	 *            The point to measure the distance to.
	 */
	public double sizeOfPoint(Point p) {
		return Math.sqrt(Math.pow(p.x, 2) + Math.pow(p.y, 2));
	}

	/**
	 * Normalize a point.
	 * 
	 * @param p
	 *            The point to normalize.
	 * @param n
	 *            The normalization value.
	 */
	public Point normalisePoint(Point p, double n) {
		if (sizeOfPoint(p) == 0.0) {
			return p;
		} else {
			double weight = n / sizeOfPoint(p);
			return new Point((int) (p.x * weight), (int) (p.y * weight));
		}
	}
}
