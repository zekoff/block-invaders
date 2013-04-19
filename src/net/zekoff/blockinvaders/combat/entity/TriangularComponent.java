package net.zekoff.blockinvaders.combat.entity;

import net.zekoff.blockinvaders.combat.collision.Circle;

/**
 * An extension of Component for triangular components. Uses a circular hitbox
 * that is slightly smaller than the circumscribing circle of the triangle.
 * 
 * @author Zekoff
 * 
 */
public class TriangularComponent extends Component {
	public TriangularComponent(float x, float y, float size) {
		super(x, y);
		type = ComponentType.triangle;
		this.radius = size;
		this.hitbox = new Circle(size);
		this.hitbox.x = x;
		this.hitbox.y = y;
	}

	public TriangularComponent(float x, float y, float size, int baseRotation) {
		super(x, y);
		type = ComponentType.triangle;
		this.radius = size;
		this.hitbox = new Circle(size);
		this.hitbox.x = x;
		this.hitbox.y = y;
		this.baseRotation = baseRotation;
	}

}
