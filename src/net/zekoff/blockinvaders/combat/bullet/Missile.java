package net.zekoff.blockinvaders.combat.bullet;

import java.util.List;

import net.zekoff.androidgames.framework.Graphics;
import net.zekoff.blockinvaders.combat.Assets;
import net.zekoff.blockinvaders.combat.collision.Circle;
import net.zekoff.blockinvaders.combat.enemy.Enemy;

/**
 * This basic Missile implementation scans the list of Enemies currently on the
 * field and selects the closest Enemy target, then follows that target until it
 * has been destroyed or otherwise disappears. For this reason, Missiles can NOT
 * be used by Enemies. This class should be subclassed or its code copied into
 * another implementation if Enemy ships wish to have homing missiles.
 * 
 * @author Zekoff
 * 
 */
public class Missile extends Bullet {
	public float radius = 7;
	public Enemy target = null;
	public boolean reacquireTarget = false;
	public TargetingType targetingType = TargetingType.random;
	private boolean newMissile = true;
	public boolean noTracking = false;

	public enum TargetingType {
		random, closest
	}

	public Missile() {
		speed = 250f;
		hitbox = new Circle(radius);
	}

	public Missile(TargetingType tt, boolean reacquireTarget) {
		this();
		this.reacquireTarget = reacquireTarget;
		targetingType = tt;
	}

	public void update(float deltaTime) {
		if (newMissile) {
			newMissile = false;
			if (targetingType == TargetingType.random)
				selectRandomTarget();
			else
				selectClosestTarget();
		}
		if (target == null || Assets.em.enemies.contains(target) == false) {
			target = null; // in case target was killed but still referenced in
							// this missile
			if (reacquireTarget) {
				if (targetingType == TargetingType.closest)
					selectClosestTarget();
				else
					selectRandomTarget();
			}
		}
		if (target != null && !noTracking) {
			// set movement toward target
			setMovement(target.x, target.y);
		}
		super.update(deltaTime);
		// if missile cannot find a new target, it will just fly off in its
		// current direction...
	}

	protected void selectClosestTarget() {
		List<Enemy> enemies = Assets.em.enemies;
		float dist = -1;
		float newDist = 0;
		for (Enemy e : enemies) {
			newDist = (float) Math.sqrt((Math.pow((e.x - x), 2) + Math.pow(e.y
					- y, 2)));
			if (dist == -1) {
				target = e;
				dist = newDist;
				continue;
			}
			if (newDist < dist) {
				dist = newDist;
				target = e;
			}
		}
	}

	private void selectRandomTarget() {
		if (Assets.em.enemies.size() != 0) {
			int targetNumber = Assets.rand.nextInt(Assets.em.enemies.size());
			target = Assets.em.enemies.get(targetNumber);
		}
	}

	@Override
	public void draw(Graphics g) {
		g.drawCircle(x, y, radius, color);
	}

	public void setMovement(float x, float y) {
		double yDist = y - this.y;
		double xDist = x - this.x;
		double angle = Math.atan2(xDist, yDist);
		xVel = (float) (Math.sin(angle) * speed);
		yVel = (float) (Math.cos(angle) * speed);
	}

}
