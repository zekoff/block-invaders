package net.zekoff.blockinvaders.combat.bullet;

public class StabilizingBullet extends CircleBullet {

	protected StabilizingBullet() {
		super();
	}

	public StabilizingBullet(float x, float y, float radius) {
		super(x, y, radius);
	}

	@Override
	public void update(float deltaTime) {
		if (xVel > 0) {
			xVel -= xVel / .2 * deltaTime;
		} else if (xVel < 0) {
			xVel += -xVel / .2 * deltaTime;
		}
		super.update(deltaTime);
	}

}
