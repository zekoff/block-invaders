package net.zekoff.blockinvaders.combat.entity;

import net.zekoff.blockinvaders.combat.collision.Circle;

/**
 * An extension of Component for circular components.
 * 
 * @author Zekoff
 * 
 */
public class CircularComponent extends Component {
	public CircularComponent(float x, float y, float radius) {
		super(x, y);
		type = ComponentType.circle;
		this.radius = radius;
		this.hitbox = new Circle(radius);
		this.hitbox.x = x;
		this.hitbox.y = y;
	}
}
