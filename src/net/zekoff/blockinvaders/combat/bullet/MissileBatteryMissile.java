package net.zekoff.blockinvaders.combat.bullet;

/**
 * Only tested for use with player weapons.
 * 
 * @author Zekoff
 * 
 */
public class MissileBatteryMissile extends TriangleMissile {
	float counter = 0;
	boolean targetSet = false;

	public MissileBatteryMissile() {
		super();
	}

	@Override
	public void update(float deltaTime) {
		counter += deltaTime;
		if (counter > .75) {
			if (!targetSet) {
				targetSet = true;
				selectClosestTarget();
				if (target == null) {
					yVel = -speed;
				}
				super.update(deltaTime);
				noTracking = true;
			}
			super.update(deltaTime);
		} else {
			x += xVel * deltaTime;
			y += yVel * deltaTime;
			yVel -= yVel / 3 * deltaTime;
			xVel -= xVel / 3 * deltaTime;
		}

	}
}
