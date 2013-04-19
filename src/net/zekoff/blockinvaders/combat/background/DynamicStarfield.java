package net.zekoff.blockinvaders.combat.background;

import java.util.ArrayList;
import java.util.Random;

import net.zekoff.androidgames.framework.Graphics;
import net.zekoff.androidgames.framework.impl.Pool;
import net.zekoff.androidgames.framework.impl.Pool.PoolObjectFactory;
import android.graphics.Color;

/**
 * Starfield background with dynamically-generated stars moving at different
 * speeds to simulate depth-of-field.
 * <p>
 * TODO: Make stars more visible when antialiased graphics are on. TODO: Make
 * stars less visible when antialiazed graphics are OFF.
 * 
 * @author Zekoff
 * 
 */
public class DynamicStarfield extends Background {
	public static final int STAR_COLOR = 0xffb0b0b0;
	Random rand = new Random(System.nanoTime());
	public int numStars = 40;
	public ArrayList<Star> stars = new ArrayList<Star>();
	public int bgColor = Color.BLACK;
	public ArrayList<Star> toDelete = new ArrayList<Star>();
	public Pool<Star> pool;
	public Star toAdd;

	/**
	 * Populates field with new stars and sets up a Pool to store stars as they
	 * scroll off.
	 */
	public DynamicStarfield(int numStars) {
		this.numStars = numStars;
		for (int i = 0; i < numStars; i++) {
			stars.add(new Star());
		}
		PoolObjectFactory<Star> starFactory = new PoolObjectFactory<Star>() {
			public Star createObject() {
				return new Star(true);
			}
		};
		pool = new Pool<Star>(starFactory, 10);
	}

	@Override
	public void update(float deltaTime) {
		for (int i = 0; i < stars.size(); i++) {
			stars.get(i).y += speed * 2 * deltaTime * stars.get(i).speedFactor;
			if (stars.get(i).y > 480) {
				pool.free(stars.get(i));
				toDelete.add(stars.get(i));
			}
		}
		// Free and reuse stars that scroll off the bottom...
		for (int i = 0; i < toDelete.size(); i++) {
			stars.remove(toDelete.get(i));
			if (stars.size() < numStars) {
				toAdd = pool.newObject();
				toAdd.x = rand.nextFloat() * 320;
				toAdd.y = -5;
				toAdd.speedFactor = rand.nextFloat() + .2f;
				stars.add(toAdd);
			}
		}
		toDelete.clear();
	}

	@Override
	public void drawBackground(Graphics g) {
		g.clear(Color.BLACK);
		for (int i = 0; i < stars.size(); i++) {
			g.drawPixel((int) stars.get(i).x, (int) stars.get(i).y, STAR_COLOR);
		}
	}

	/**
	 * Private class to represent a dynamically-generated star.
	 * 
	 * @author Zekoff
	 * 
	 */
	private class Star {
		float x;
		float y;
		float speedFactor;

		public Star() {
			x = rand.nextFloat() * 320;
			y = rand.nextFloat() * 480;
			speedFactor = rand.nextFloat() + .2f;
		}

		/**
		 * When not generating stars for initial population, use this
		 * constructor to ensure that stars start above the visible playing
		 * field.
		 * 
		 * @param filler
		 *            Fill this param with true/false to force star to start at
		 *            -5 y-position.
		 */
		public Star(boolean filler) {
			x = rand.nextFloat() * 320;
			y = -5;
			speedFactor = rand.nextFloat() + .2f;
		}
	}

}
