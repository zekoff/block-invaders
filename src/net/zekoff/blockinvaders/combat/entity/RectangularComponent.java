package net.zekoff.blockinvaders.combat.entity;

import net.zekoff.blockinvaders.combat.collision.Aabb;

/**
 * An extension of Component for rectangular components.
 * 
 * @author Zekoff
 * 
 */
public class RectangularComponent extends Component {
	public RectangularComponent(float x, float y, float width, float height) {
		super(x, y);
		type = ComponentType.rect;
		this.width = width;
		this.height = height;
		this.hitbox = new Aabb(width, height);
		this.hitbox.x = x;
		this.hitbox.y = y;
	}

	public RectangularComponent(float x, float y, float width, float height,
			int baseRotation) {
		super(x, y);
		type = ComponentType.rect;
		this.width = width;
		this.height = height;
		this.hitbox = new Aabb(width, height);
		this.hitbox.x = x;
		this.hitbox.y = y;
		this.baseRotation = baseRotation;
	}
}
