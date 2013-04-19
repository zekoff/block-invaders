package net.zekoff.blockinvaders.combat.entity;

import net.zekoff.androidgames.framework.Graphics;
import net.zekoff.blockinvaders.combat.collision.BoundingShape;
import android.graphics.Paint;

/**
 * Ships and enemies are made up of one or more Component parts. This abstract
 * class provides all code for both rectangular and circular component types.
 * Components are responsible for drawing themselves to the screen and keeping
 * track of their associated hitboxes.
 * <p>
 * Player ships only use components for drawing themselves; their hitboxes are
 * single, separate objects kept up with as fields in the Ship object.
 * 
 * @author Zekoff
 * 
 */
public abstract class Component {
	public float x; // Represents the x-offset from the center of its parent
					// entity
	public float y; // Represents the y-offset from the center of its parent
					// entity
	public float radius;
	public float height;
	public float width;
	public BoundingShape hitbox;
	public ComponentType type;
	public int baseRotation = 0;

	public enum ComponentType {
		rect, circle, triangle
	}

	public Component(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public BoundingShape getBoundingShape() {
		hitbox.x = x;
		hitbox.y = y;
		return hitbox;
	}

	public void draw(Graphics g, float x, float y, Paint paint) {
		draw(g, x, y, paint, 0);
	}

	public void draw(Graphics g, float x, float y, Paint paint, int rotation) {
		g.drawComponent(this, x, y, paint, rotation);
	}
}
