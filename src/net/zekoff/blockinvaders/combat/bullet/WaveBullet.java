package net.zekoff.blockinvaders.combat.bullet;

public class WaveBullet extends CircleBullet {
	float startX = 0;
	public float maxVelocity = 600;
	public float acceleration = 4000;

	protected WaveBullet() {
		super();
	}

	public WaveBullet(float x, float y, float radius) {
		super(x, y, radius);
		startX = x;
		xVel = maxVelocity;
	}

	@Override
	public void update(float deltaTime) {
		if (x <= startX) {
			if (xVel <= maxVelocity)
				xVel += acceleration * deltaTime;
		}
		if (x > startX) {
			if (xVel >= -maxVelocity)
				xVel -= acceleration * deltaTime;
		}
		super.update(deltaTime);
	}

}
