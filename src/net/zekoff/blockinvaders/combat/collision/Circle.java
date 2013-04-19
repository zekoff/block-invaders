package net.zekoff.blockinvaders.combat.collision;

/**
 * A concrete implementation of BoundingShape representing a circle.
 * 
 * @author Zekoff
 * 
 */
public class Circle extends BoundingShape {
	public Circle(float radius) {
		this.radius = radius;
		type = BoundType.circle;
	}
}
