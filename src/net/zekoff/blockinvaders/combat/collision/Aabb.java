package net.zekoff.blockinvaders.combat.collision;

/**
 * A concrete implementation of BoundingShape representing a rectangle.
 * 
 * @author Zekoff
 * 
 */
public class Aabb extends BoundingShape {
	public Aabb(float width, float height) {
		this.height = height;
		this.width = width;
		type = BoundType.aabb;
	}
}
