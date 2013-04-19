package net.zekoff.blockinvaders.combat.bullet;

import net.zekoff.androidgames.framework.Graphics;
import net.zekoff.blockinvaders.combat.collision.Circle;

/**
 * A standard, circular bullet for use by players or enemies.
 * 
 * @author Zekoff
 * 
 */
public class CircleBullet extends Bullet {

	float radius;

	protected CircleBullet() {
		super.reset();
		reset();
		type = BulletType.circle;
	}

	public CircleBullet(float x, float y, float radius) {
		this();
		this.x = x;
		this.y = y;
		this.radius = radius;
		hitbox = new Circle(radius);
	}

	public void reset() {
		super.reset();
		radius = 0;
	}

	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
	}

	@Override
	public void draw(Graphics g) {
		g.drawCircle(x, y, radius, color);
	}

}
