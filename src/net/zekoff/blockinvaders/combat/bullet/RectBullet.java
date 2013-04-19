package net.zekoff.blockinvaders.combat.bullet;

import net.zekoff.androidgames.framework.Graphics;
import net.zekoff.blockinvaders.combat.collision.Aabb;

/**
 * A standard, rectangular bullet for use by players or enemies.
 * 
 * @author Zekoff
 * 
 */
public class RectBullet extends Bullet {
	float width;
	float height;
	float rotation = 0;

	protected RectBullet() {
		super.reset();
		reset();
		type = BulletType.rect;
	}

	public RectBullet(float x, float y, float width, float height) {
		this();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		hitbox = new Aabb(width, height);
	}

	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
	}

	@Override
	public void draw(Graphics g) {
		g.drawRect(x, y, width, height, color);
	}

}
