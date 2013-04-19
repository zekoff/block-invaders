package net.zekoff.blockinvaders.combat.bullet;

import net.zekoff.androidgames.framework.Graphics;
import net.zekoff.blockinvaders.combat.Assets;
import net.zekoff.blockinvaders.combat.collision.BoundingShape;
import net.zekoff.blockinvaders.combat.entity.Entity;
import android.graphics.Color;

/**
 * Bullets are individual objects that can strike ships and cause damage to
 * them. They are put into play by Weapons from the player (or by some
 * Abilities), or by Enemy ships.
 * <p>
 * The original vision of Bullets was to have them be able to morph into
 * different kinds via their type field, but the creation and deletion of new
 * bullet objects was not as costly as expected, while by contrast the coding
 * difficulty in implementing such a pool of bullets was not worth the runtime
 * savings. The code remains in place for this to be implemented again.
 * 
 * @author Zekoff
 * 
 */
public abstract class Bullet {
	public float x;
	public float y;
	public float xVel;
	public float yVel;
	public float speed;
	public int color;
	public float power;
	public DamageType damageType;
	public boolean isExpired;
	public Entity target;
	public BoundingShape hitbox;
	public BulletType type;
	public int rotation;
	public Payload payload = null;

	public enum BulletType {
		rect, circle
	}

	public enum DamageType {
		normal, energy, piercing
	}

	public void reset() {
		x = 0;
		y = 0;
		xVel = 0;
		yVel = 0;
		speed = 0;
		color = Color.WHITE;
		power = 0;
		damageType = DamageType.normal;
		isExpired = false;
		target = null;
		hitbox = null;
		type = BulletType.circle;
		rotation = 0;
		payload = null;
	}

	/**
	 * Bullets update their hitbox every frame; thus, the hitbox field can be
	 * directly accessed to get an accurate hitbox without going through an
	 * interface method. This is in contrast to other types of entities, which
	 * do not necessarily update their hitboxes every frame.
	 * <p>
	 * If the bullet gets too far off the screen in any direction, it is
	 * automatically marked for cleanup.
	 * 
	 * @param deltaTime
	 */
	public void update(float deltaTime) {
		hitbox.x = x;
		hitbox.y = y;
		if (y < -50 || y > 530)
			isExpired = true;
		x += xVel * deltaTime;
		y += yVel * deltaTime;
	}

	abstract public void draw(Graphics g);

	/**
	 * Notify the bullet that it has collided with some Entity.
	 * 
	 * @param e
	 *            The Entity with which the bullet has collided.
	 */
	public void collidedWith(Entity e) {
		Assets.pm.addBurstEmitter(x, y, 6, color);
		processPayload();
		isExpired = true;
	}

	public void processPayload() {
		if (payload != null) {
			if (payload.type == Payload.Type.ae) {
				AreaEffect b = new AreaEffect(x, y, payload.size);
				b.power = payload.power;
				b.durationMax = payload.duration;
				Assets.bm.playerBullets.add(b);
			}
		}
	}
}
