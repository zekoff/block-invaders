package net.zekoff.blockinvaders.combat.entity;

import net.zekoff.androidgames.framework.Graphics;
import net.zekoff.blockinvaders.combat.bullet.Bullet;

/**
 * Essentially a marker interface, specifying that the given entity has an X and
 * Y position in space, and that it can be checked for collision (even if a
 * given entity always returns a null object for collision testing).
 * <p>
 * Notably, when checking collisions, the Entity is queried for collision
 * occurrence, then if collision has occurred both the Entity and the Bullet are
 * notified via their respective collidedWith() methods.
 * 
 * @author Zekoff
 * 
 */
public interface Entity {
	/**
	 * @return A BoundingShape object (either a Circle or an Aabb), updated
	 *         based on the implementing class's current location. Typically,
	 *         this will just mean setting the bounding box's x and y locations
	 *         to match the object's x and y location, then returning the
	 *         BoundingShape.
	 */
	// public BoundingShape getBoundingShape();

	/**
	 * @return A boolean indicating the live/expired status of the Entity. Used
	 *         by the EntityManager "garbage collector" to determine if the
	 *         object needs to be removed from its containing list.
	 */
	public boolean isExpired();

	public void update(float deltaTime);

	public void draw(Graphics g);

	public boolean testCollision(Bullet b);

	/**
	 * Process the results of a collision with a Bullet. This includes deducting
	 * the appropriate amount of resistance from the hit, updating colors if
	 * that is not done elsewhere, flagging the Entity to be deleted, etc.
	 * 
	 * @param b
	 */
	public void collidedWith(Bullet b);

	public float getX();

	public float getY();

}
