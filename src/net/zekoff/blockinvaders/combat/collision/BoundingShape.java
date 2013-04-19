package net.zekoff.blockinvaders.combat.collision;

/**
 * Provides all bounding-box code for things that need collision detection.
 * Ultimately, all things that can have collision reduce to one or more
 * BoundingShapes, which can be checked against other BoundingShapes for
 * collision.
 * <p>
 * BoundingShapes are automatically centered on the midpoint of whatever they
 * represent. Circle-circle collision is trivial to check; square-square
 * collsion slightly less so; and circle-square collision the most expensive.
 * 
 * @author Zekoff
 * 
 */
public abstract class BoundingShape {
	public BoundType type;
	public float x = -999;
	public float y = -999;
	public float radius;
	public float height;
	public float width;

	public enum BoundType {
		circle, aabb
	}

	public static boolean testCollision(BoundingShape s1, BoundingShape s2) {
		if (s1 == null || s2 == null)
			return false;
		if (s1.type == BoundType.circle && s2.type == BoundType.circle) {
			// Circle-circle checking
			float distanceBetween = (float) Math.sqrt(Math.pow(s2.x - s1.x, 2)
					+ Math.pow(s2.y - s1.y, 2));
			if (distanceBetween <= s1.radius + s2.radius)
				return true;
			if (distanceBetween <= s1.radius)
				return true;
			if (distanceBetween <= s2.radius)
				return true;
		} else if (s1.type == BoundType.aabb && s2.type == BoundType.aabb) {
			// Is s1's bottom higher than s2's top?
			if (s1.y + s1.height / 2 < s2.y - s2.height / 2)
				return false;
			// Is s1's top lower than s2's bottom?
			if (s1.y - s1.height / 2 > s2.y + s2.height / 2)
				return false;
			// Is s1's right left of s2's left?
			if (s1.x + s1.width / 2 < s2.x - s2.width / 2)
				return false;
			// Is s1's left right of s2's right?
			if (s1.x - s1.width / 2 > s2.x + s2.width / 2)
				return false;
			return true;
		} else {
			// Circle-AABB collision checking
			Circle circle;
			Aabb aabb;
			float pointX;
			float pointY;
			if (s1.type == BoundType.circle) {
				circle = (Circle) s1;
				aabb = (Aabb) s2;
			} else {
				circle = (Circle) s2;
				aabb = (Aabb) s1;
			}
			// set the X point
			if (circle.x < aabb.x - aabb.width / 2)
				pointX = aabb.x - aabb.width / 2;
			else if (circle.x > aabb.x + aabb.width / 2)
				pointX = aabb.x + aabb.width / 2;
			else
				pointX = circle.x;
			// set the Y point
			if (circle.y < aabb.y - aabb.height / 2)
				pointY = aabb.y - aabb.height / 2;
			else if (circle.y > aabb.y + aabb.height / 2)
				pointY = aabb.y + aabb.height / 2;
			else
				pointY = circle.y;
			// check for collision
			float distance = (float) Math.sqrt(Math.pow(pointX - circle.x, 2)
					+ Math.pow(pointY - circle.y, 2));
			if (distance <= circle.radius)
				return true;
		}
		return false;
	}
}
