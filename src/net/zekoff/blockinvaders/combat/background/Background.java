package net.zekoff.blockinvaders.combat.background;

import net.zekoff.androidgames.framework.Graphics;

/**
 * All backgrounds must extend this class.
 * 
 * @author Zekoff
 * 
 */
public abstract class Background {
	public static final float REGULAR_SPEED = 200;
	public float speed = REGULAR_SPEED;

	/**
	 * Called as often as possible to allow background to update itself.
	 * 
	 * @param deltaTime
	 *            The time since update() was last called, provided in seconds.
	 */
	public abstract void update(float deltaTime);

	/**
	 * Draws the background to the graphics context.
	 * 
	 * @param g
	 *            The graphics context.
	 */
	public abstract void drawBackground(Graphics g);

	/**
	 * Method that subclasses may optionally override to produce a scrolling
	 * foreground as well. This method must obviously be primarily an alpha
	 * layer.
	 * 
	 * @param g
	 *            The graphics context.
	 */
	public void drawForeground(Graphics g) {

	}
}
